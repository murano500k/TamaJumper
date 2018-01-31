package com.stc.tamajumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

import static com.stc.tamajumper.Config.PIXELS.FRUSTUM_HEIGHT;
import static com.stc.tamajumper.Config.PIXELS.WORLD_HEIGHT;
import static com.stc.tamajumper.Config.PIXELS.WORLD_WIDTH;
import static com.stc.tamajumper.Config.PLATFORM_HEIGHT;
import static com.stc.tamajumper.Config.VIEWPORT_HEIGHT;
import static com.stc.tamajumper.Config.VIEWPORT_WIDTH;
import static com.stc.tamajumper.TamaActor.JUMP_VELOCITY;

/**
 * Created by artem on 1/30/18.
 */

class DemoScreen extends ScreenAdapter implements InputProcessor {

    private GameState gameState;

    public enum GameState{
        RUNNING,
        PAUSED,
        READY,
        GAME_OVER,
        LEVEL_END
    }

    private final TamaJumperGame game;
    private final Vector3 touchPoint;
    private Stage stage;
    private OrthographicCamera camera;
    private Viewport viewport;
    public static final Vector2 gravity = new Vector2(0, -12);
    public final Random rand;
    private Group platforms;
    private TamaActor tama;
    private Group coins;
    private Group enemies;
    private Image pauseButton;



    public DemoScreen(TamaJumperGame game) {
        this.game=game;
        rand = new Random();
        touchPoint = new Vector3();
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(gameState==GameState.RUNNING) {
            stage.act(delta);
            if (Gdx.input.justTouched()) {
                camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            }
            if (tama.getY() > camera.position.y) camera.position.y = tama.getY();
            camera.update();
            stage.getBatch().setProjectionMatrix(camera.combined);
            checkCollisions();
        }else {
            if(Gdx.input.justTouched()){
                switch (gameState){
                    case READY:
                    case PAUSED:
                        gameState=GameState.RUNNING;
                        return;
                    case LEVEL_END:
                    case GAME_OVER:
                    default:
                        game.changeScreen(TamaJumperGame.MENU);
                        return;
                }
            }
            stage.getBatch().begin();
            switch (gameState){
                case READY:
                case PAUSED:
                    stage.getBatch().draw(Assets.ready,WORLD_WIDTH/2,FRUSTUM_HEIGHT/2,WORLD_WIDTH/2,WORLD_WIDTH/5);
                    break;
                case LEVEL_END:
                case GAME_OVER:
                default:
                    stage.getBatch().draw(Assets.gameOver,WORLD_WIDTH/2,FRUSTUM_HEIGHT/2,WORLD_WIDTH/2,WORLD_WIDTH/5);
                    break;
            }
            stage.getBatch().end();
        }
        stage.draw();
    }

    private void checkCollisions() {
        PlatformActor platform= (PlatformActor) checkGroupCollisions(platforms,true);
        if(platform!=null){
            System.out.println("platform was hit");
            tama.hitPlatform(platform);
        }

        CoinActor coin= (CoinActor) checkGroupCollisions(coins,false);
        if(coin!=null){
            System.out.println("platform was hit");
            coin.destroy();
        }


        EnemyActor enemy = (EnemyActor) checkGroupCollisions(enemies,false);
        if(enemy!=null){
            System.out.println("enemy was hit");
            if(!tama.hitEnemy(enemy)){
                gameOver();
            }
        }

    }

    private void gameOver() {
        gameState=GameState.GAME_OVER;
        Assets.playSound(Assets.gameOverSound);
    }

    private Actor checkGroupCollisions(Group group, boolean legsOnly){
        Actor actor = group.hit(tama.getRight(),tama.getTop() - tama.getHeight(),false);
        if(actor==null) {
            actor = group.hit(tama.getRight()-tama.getWidth(),tama.getTop() - tama.getHeight(),false);
        }
        if(!legsOnly) {
            if (actor == null) {
                actor = group.hit(tama.getRight() - tama.getWidth(), tama.getTop(), false);
            }
            if (actor == null) {
                actor = group.hit(tama.getRight(), tama.getTop(), false);
            }
        }
        return actor;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

    }

    @Override
    public void show() {
        super.show();
        gameState=GameState.RUNNING;
        camera=new OrthographicCamera(Config.PIXELS.FRUSTUM_WIDTH,Config.PIXELS.FRUSTUM_HEIGHT);
        camera.position.set(VIEWPORT_WIDTH / 2, VIEWPORT_HEIGHT / 2, 0);
        viewport =new FillViewport(Config.PIXELS.FRUSTUM_WIDTH,Config.PIXELS.FRUSTUM_HEIGHT, camera);
        stage=new Stage( viewport);

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);


        generateObjects();

    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }


    private void generateObjects() {
        tama=new TamaActor(stage.getWidth()/2,stage.getHeight()/2);
        stage.addActor(tama);
        float y = PLATFORM_HEIGHT / 2;
        float maxJumpHeight = JUMP_VELOCITY * JUMP_VELOCITY / (2 * -gravity.y);
        platforms = new Group();
        coins = new Group();
        enemies = new Group();


        while (y < WORLD_HEIGHT - WORLD_WIDTH / 2) {
            PlatformActor platform =PlatformActor.generatePlatform(y,rand);
            platforms.addActor(platform);



            if (y > WORLD_HEIGHT / 5 && rand.nextFloat() > (1-Config.ENEMY_GENERATION_PROBABILITY)) {
                EnemyActor enemy = new EnemyActor(platform.getX()+ rand.nextFloat(), platform.getY()
                        + rand.nextFloat() * 2, rand.nextBoolean());
                enemies.addActor(enemy);
            }

            if (rand.nextFloat() > (1-Config.COIN_GENERATION_PROBABILITY)) {
                CoinActor coin = new CoinActor(platform.getX()+ rand.nextFloat(), platform.getY()
                        + rand.nextFloat() * 3);
                coins.addActor(coin);
            }



            y += (maxJumpHeight*0.9f - 0.5f);
            float levelProgressDifficulty=(WORLD_HEIGHT-y)/WORLD_HEIGHT;

            float randomSeed= rand.nextFloat();

            if(randomSeed<0.5f) randomSeed*=2;
            else if(randomSeed<0.75f)randomSeed/=1.2f;
            else randomSeed/=2;
            y -= (randomSeed) * maxJumpHeight*levelProgressDifficulty;
        }
        stage.addActor(platforms);
        stage.addActor(coins);
        stage.addActor(enemies);
    }

    @Override
    public boolean keyDown(int keycode) {

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.BACK){
            game.changeScreen(TamaJumperGame.MENU);
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
