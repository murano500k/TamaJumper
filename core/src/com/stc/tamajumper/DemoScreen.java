package com.stc.tamajumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

import static com.stc.tamajumper.Config.PIXELS.WORLD_HEIGHT;
import static com.stc.tamajumper.Config.PIXELS.WORLD_WIDTH;
import static com.stc.tamajumper.Config.PLATFORM_HEIGHT;
import static com.stc.tamajumper.Config.TAMADA_JUMP_VELOCITY;
import static com.stc.tamajumper.Config.VIEWPORT_HEIGHT;
import static com.stc.tamajumper.Config.VIEWPORT_WIDTH;

/**
 * Created by artem on 1/30/18.
 */

class DemoScreen extends ScreenAdapter implements InputProcessor {

    private final TamaJumperGame game;
    private final Vector3 touchPoint;
    private Stage stage;
    private OrthographicCamera camera;
    private Viewport viewport;
    public static final Vector2 gravity = new Vector2(0, -12);
    public final Random rand;
    private Group platforms;
    private TamaActor tama;
    private World.WorldListener worldListener;


    public DemoScreen(TamaJumperGame game) {
        this.game=game;
        rand = new Random();
        touchPoint = new Vector3();
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        }
        if (tama.getY() > camera.position.y) camera.position.y = tama.getY();
        camera.update();
        stage.getBatch().setProjectionMatrix(camera.combined);
        checkCollisions();
        stage.draw();

    }

    private void checkCollisions() {

        PlatformActor platform= (PlatformActor) platforms.hit(tama.getRight(),tama.getY(),false);
        if(platform==null){
            platform= (PlatformActor) platforms.hit(tama.getRight()-tama.getWidth(),tama.getY(),false);
        }
        if(platform!=null){
            System.out.println("platform was hit");
            tama.hitPlatform(platform);
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

    }

    @Override
    public void show() {
        super.show();
        camera=new OrthographicCamera(Config.PIXELS.FRUSTUM_WIDTH,Config.PIXELS.FRUSTUM_HEIGHT);
        camera.position.set(VIEWPORT_WIDTH / 2, VIEWPORT_HEIGHT / 2, 0);
        viewport =new FillViewport(Config.PIXELS.FRUSTUM_WIDTH,Config.PIXELS.FRUSTUM_HEIGHT, camera);
        stage=new Stage( viewport);

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);

        worldListener = new World.WorldListener() {


            @Override
            public void jump(Platform platform) {
                game.getAssets().playJumpSound(platform);
            }

            @Override
            public void highJump() {
                game.getAssets().playHighJumpSound();

            }

            @Override
            public void hit() {
                game.getAssets().stopMusic();
                game.getAssets().playSound(game.getAssets().gameOverSound);
            }

            @Override
            public void coin() {
                game.getAssets().playSound(game.getAssets().coinSound);
            }
        };


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
        float maxJumpHeight = TAMADA_JUMP_VELOCITY * TAMADA_JUMP_VELOCITY / (2 * -gravity.y);
        platforms = new Group();


        while (y < WORLD_HEIGHT - WORLD_WIDTH / 2) {

            platforms.addActor(PlatformActor.generatePlatform(y,rand));

            y += (maxJumpHeight*0.9f - 0.5f);
            float levelProgressDifficulty=(WORLD_HEIGHT-y)/WORLD_HEIGHT;
            System.out.println("levelProgressDifficulty="+levelProgressDifficulty);
            float randomSeed= rand.nextFloat();

            if(randomSeed<0.5f) randomSeed*=2;
            else if(randomSeed<0.75f)randomSeed/=1.2f;
            else randomSeed/=2;
            y -= (randomSeed) * maxJumpHeight*levelProgressDifficulty;
        }
        stage.addActor(platforms);
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
