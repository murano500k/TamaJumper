package com.stc.tamajumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

import static com.stc.tamajumper.Config.PIXELS.WORLD_HEIGHT;
import static com.stc.tamajumper.Config.PIXELS.WORLD_WIDTH;
import static com.stc.tamajumper.Config.VIEWPORT_HEIGHT;
import static com.stc.tamajumper.Config.VIEWPORT_WIDTH;

/**
 * Created by artem on 1/30/18.
 */

class DemoScreen extends ScreenAdapter implements InputProcessor {

    private static final String MESSAGE_READY = "Ready?";
    private static final String MESSAGE_GAME_OVER = "Game Over";
    private static final String MESSAGE_PAUSED = "Paused";
    private Label labelScore;
    private Label message;
    private TextButton btnPause;

    public enum GameState{
        RUNNING,
        PAUSED,
        READY,
        GAME_OVER,
        LEVEL_END
    }

    public GameState gameState;
    private final Skin skin;
    private final TamaJumperGame game;
    private final Vector3 touchPoint;
    private Stage stage;
    private OrthographicCamera camera;
    private Viewport viewport;
    public final Random rand;
    private Group platforms;
    private TamaActor tama;
    private Group coins;
    private Group enemies;
    private float heightSoFar=0;




    public DemoScreen(TamaJumperGame game) {
        this.game=game;
        rand = new Random();
        touchPoint = new Vector3();
        skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));
    }

    @Override
    public void show() {
        super.show();
        gameState=GameState.READY;
        camera=new OrthographicCamera(VIEWPORT_WIDTH,VIEWPORT_HEIGHT);
        camera.position.set(VIEWPORT_WIDTH/ 2, VIEWPORT_HEIGHT / 2, 0);
        viewport =new FillViewport(VIEWPORT_WIDTH,VIEWPORT_HEIGHT, camera);
        stage=new Stage( viewport);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
        generateObjects();
        btnPause=new TextButton("Pause", skin);
        btnPause.setWidth(PlatformActor.WIDTH);
        btnPause.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("pause clicked");
                gameState=GameState.PAUSED;
            }
        });
        labelScore = new Label("Score: 0", skin);
        updateUi();
        stage.addActor(btnPause);
        stage.addActor(labelScore);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.justTouched()){
                switch (gameState){
                    case RUNNING:
                        break;
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
        System.out.println("state="+gameState);
        switch (gameState){
            case READY:
                drawMessage(MESSAGE_READY);
                break;
            case PAUSED:
                drawMessage(MESSAGE_PAUSED);
                break;
            case LEVEL_END:
            case GAME_OVER:
                if(AppPreferences.setHighscore(tama.getScore())){
                    drawMessage(MESSAGE_GAME_OVER+"\nNew highscore!");
                }else drawMessage(MESSAGE_GAME_OVER);
                break;
            default:
                drawMessage(null);
                break;
        }
        if(gameState==GameState.RUNNING){
            stage.act(delta);
            checkCollisions();
            if(tama.getY()>camera.position.y) {
                camera.position.y=tama.getY();
                camera.update();
            }
            checkGameOver();
        }
        updateUi();
        stage.draw();
    }

    private void checkGameOver() {
        heightSoFar = Math.max(tama.getY(), heightSoFar);
        if (heightSoFar - stage.getHeight()/2 > tama.getY()) {
            gameState=GameState.GAME_OVER;
        }
    }

    private void updateUi() {
        String scoreText="Score: "+tama.getScore();
        labelScore.setText(scoreText);
        labelScore.setPosition(16,camera.position.y+VIEWPORT_HEIGHT/2-labelScore.getHeight()*2.5f);
        btnPause.setPosition(stage.getWidth()-btnPause.getWidth(),camera.position.y+VIEWPORT_HEIGHT/2-btnPause.getHeight()*1.75f);
    }
    private void drawMessage(String text){
        if(message!=null)message.remove();

        if(text==null) {
            stage.addActor(btnPause);
        }else {
            message=new Label(text,skin);
            message.setPosition(camera.position.x-message.getWidth()/2,camera.position.y-message.getHeight());
            message.setText(text);
            stage.addActor(message);
            btnPause.remove();
        }
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
    public void dispose() {
        super.dispose();
        stage.dispose();
    }


    private void generateObjects() {
        tama=new TamaActor(stage.getWidth()/2,stage.getHeight()/2);
        stage.addActor(tama);
        float y = PlatformActor.HEIGHT / 2;
        float maxJumpHeight = TamaActor.JUMP_VELOCITY * TamaActor.JUMP_VELOCITY / (2 * -Config.GRAVITY.y);
        platforms = new Group();
        coins = new Group();
        enemies = new Group();


        while (y < WORLD_HEIGHT - WORLD_WIDTH / 2) {
            PlatformActor platform =PlatformActor.generatePlatform(y,rand);
            platforms.addActor(platform);



            if (y > WORLD_HEIGHT / 5 && rand.nextFloat() > (1-Config.ENEMY_GENERATION_PROBABILITY)) {
                EnemyActor enemy = new EnemyActor(rand.nextFloat()*(WORLD_WIDTH-EnemyActor.WIDTH), platform.getY()
                        + rand.nextFloat() * 2 *Config.PIXELS.PLAYER_DIMEN, rand.nextBoolean());
                enemies.addActor(enemy);
            }

            if (platform.getType()== PlatformActor.Type.NORMAL && rand.nextFloat() > (1-Config.COIN_GENERATION_PROBABILITY)) {
                CoinActor coin = new CoinActor(platform.getX()+CoinActor.WIDTH/2
                        , platform.getY()+PlatformActor.HEIGHT);
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
