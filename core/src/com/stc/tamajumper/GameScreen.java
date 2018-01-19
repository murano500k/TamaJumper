package com.stc.tamajumper;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import static com.stc.tamajumper.Config.VIEWPORT_HEIGHT;
import static com.stc.tamajumper.Config.VIEWPORT_WIDTH;
import static com.stc.tamajumper.Config.*;


public class GameScreen extends ScreenAdapter {
    static final int GAME_READY = 0;
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_LEVEL_END = 3;
    static final int GAME_OVER = 4;

    TamaJumperGame game;

    int state;
    OrthographicCamera guiCam;
    Vector3 touchPoint;
    World world;
    World.WorldListener worldListener;
    WorldRenderer renderer;
    Rectangle pauseBounds;
    Rectangle resumeBounds;
    Rectangle quitBounds;
    int lastScore;
    String scoreString;

    GlyphLayout glyphLayout = new GlyphLayout();

    public GameScreen(final TamaJumperGame game) {
        this.game = game;

        state = GAME_READY;
        guiCam = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        guiCam.position.set(VIEWPORT_WIDTH / 2, VIEWPORT_HEIGHT / 2, 0);
        touchPoint = new Vector3();
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
        world = new World(worldListener,game);
        renderer = new WorldRenderer(game.batcher, world);
        pauseBounds = new Rectangle(VIEWPORT_WIDTH - 64, VIEWPORT_HEIGHT - 64, 64, 64);
        resumeBounds = new Rectangle(VIEWPORT_WIDTH/2 - 96, VIEWPORT_WIDTH/2, 192, 36);
        quitBounds = new Rectangle(VIEWPORT_WIDTH/2 - 96, VIEWPORT_WIDTH/2 - 36, 192, 36);
        lastScore = 0;
        scoreString = "SCORE: 0";
    }

    public void update (float deltaTime) {
        if (deltaTime > 0.1f) deltaTime = 0.1f;

        switch (state) {
            case GAME_READY:
                updateReady();
                break;
            case GAME_RUNNING:
                updateRunning(deltaTime);
                break;
            case GAME_PAUSED:
                updatePaused();
                break;
            case GAME_LEVEL_END:
                updateLevelEnd();
                break;
            case GAME_OVER:
                updateGameOver();
                break;
        }
    }
    private void updateRunning (float deltaTime) {
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (pauseBounds.contains(touchPoint.x, touchPoint.y)) {
                game.getAssets().playSound(Assets.clickSound);
                state = GAME_PAUSED;
                return;
            }
        }

        Application.ApplicationType appType = Gdx.app.getType();

        // should work also with Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)
        if (appType == Application.ApplicationType.Android ||
                appType == Application.ApplicationType.iOS) {
            world.update(deltaTime, Gdx.input.getAccelerometerX());
        } else {
            float accel = 0;
            if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) accel = 5f;
            if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) accel = -5f;
            world.update(deltaTime, accel);
        }
        if (world.score != lastScore) {
            lastScore = world.score;
            scoreString = "SCORE: " + lastScore;
        }
        if (world.state == WORLD_STATE_NEXT_LEVEL) {
            game.setScreen(new WinScreen(game));
        }
        if (world.state == WORLD_STATE_GAME_OVER) {
            state = GAME_OVER;
            scoreString = "SCORE: " + lastScore;
            game.getAssets().stopMusic();
            game.getAssets().playSound(Assets.gameOverSound);
        }
    }

        private void updateReady () {
        if (Gdx.input.justTouched()) {
            state = GAME_RUNNING;
        }
    }
    private void updatePaused () {
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if(state == GAME_PAUSED){
                game.getAssets().playSound(Assets.clickSound);
                state = GAME_RUNNING;
                return;
            }
        }
    }

    private void updateLevelEnd () {
        if (Gdx.input.justTouched()) {
            world = new World(worldListener, game);
            renderer = new WorldRenderer(game.batcher, world);
            world.score = lastScore;
            state = GAME_READY;
        }
    }

    private void updateGameOver () {
        if (Gdx.input.justTouched()) {
            game.setScreen(new MainMenu(game));
        }
    }

    public void draw () {
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        guiCam.update();
        game.batcher.setProjectionMatrix(guiCam.combined);
        game.batcher.enableBlending();
        game.batcher.begin();
        switch (state) {
            case GAME_READY:
                presentReady();
                break;
            case GAME_RUNNING:
                presentRunning();
                break;
            case GAME_PAUSED:
                presentPaused();
                break;
            case GAME_LEVEL_END:
                presentLevelEnd();
                break;
            case GAME_OVER:
                presentGameOver();
                break;
        }
        game.batcher.end();
    }

    private void presentReady () {
        game.batcher.draw(Assets.ready, VIEWPORT_WIDTH/2 - 192 / 2, VIEWPORT_HEIGHT/2 - 32 / 2, 192, 32);
    }

    private void presentRunning () {
        game.batcher.draw(Assets.pause, Config.VIEWPORT_WIDTH - 64, Config.VIEWPORT_HEIGHT - 64, 64, 64);
        game.getAssets().font.draw(game.batcher, scoreString, 16, Config.VIEWPORT_HEIGHT - 20);
    }

    private void presentPaused () {
        game.batcher.draw(Assets.pauseMenu, VIEWPORT_WIDTH/2 - 192 / 2, VIEWPORT_HEIGHT/2 - 32 / 2, 192, 48);
        game.getAssets().font.draw(game.batcher, scoreString, 16, Config.VIEWPORT_HEIGHT - 20);
    }

    private void presentLevelEnd () {
        glyphLayout.setText(Assets.font, "the princess is ...");
        game.getAssets().font.draw(game.batcher, glyphLayout, VIEWPORT_WIDTH/2 - glyphLayout.width / 2, Config.VIEWPORT_HEIGHT - 40);
        glyphLayout.setText(Assets.font, "in another castle!");
        game.getAssets().font.draw(game.batcher, glyphLayout, VIEWPORT_WIDTH/2 - glyphLayout.width / 2, 40);
    }

    private void presentGameOver () {
        game.batcher.draw(Assets.gameOver, VIEWPORT_WIDTH/2 - 160 / 2, VIEWPORT_HEIGHT/2 - 96 / 2, 160, 96);
        glyphLayout.setText(Assets.font, scoreString);
        game.getAssets().font.draw(game.batcher, scoreString, VIEWPORT_WIDTH/2 - glyphLayout.width / 2, Config.VIEWPORT_HEIGHT - 20);
    }

    @Override
    public void render (float delta) {
        update(delta);
        draw();
    }

    @Override
    public void pause () {
        if (state == GAME_RUNNING) state = GAME_PAUSED;
    }
}