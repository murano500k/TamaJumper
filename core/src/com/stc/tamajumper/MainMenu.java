package com.stc.tamajumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


/**
 * Created by artem on 1/11/18.
 */

public class MainMenu extends ScreenAdapter {
    TamaJumperGame parent;
    OrthographicCamera guiCam;
    Rectangle soundBounds;
    Rectangle settingsBounds;

    Rectangle playBounds;
    Rectangle highscoresBounds;
    Rectangle helpBounds;
    Vector3 touchPoint;

    public MainMenu (TamaJumperGame game) {
        this.parent = game;

        guiCam = new OrthographicCamera(320, 480);
        guiCam.position.set(320 / 2, 480 / 2, 0);
        soundBounds = new Rectangle(0, 0, Config.PIXELS.DOUBLE_DIMEN, Config.PIXELS.DOUBLE_DIMEN);
        settingsBounds = new Rectangle(guiCam.viewportWidth-Config.PIXELS.DOUBLE_DIMEN, 0, Config.PIXELS.DOUBLE_DIMEN, Config.PIXELS.DOUBLE_DIMEN);
        playBounds = new Rectangle(160 - 150, 200 + 18, 300, 36);
        highscoresBounds = new Rectangle(160 - 150, 200 - 18, 300, 36);
        helpBounds = new Rectangle(160 - 150, 200 - 18 - 36, 300, 36);
        touchPoint = new Vector3();
        parent.getAssets().playMusic();
    }

    public void update () {
        if (Gdx.input.justTouched()) {
            System.out.println("justTouched");

            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (playBounds.contains(touchPoint.x, touchPoint.y)) {
                parent.getAssets().playSound(parent.getAssets().clickSound);
                parent.changeScreen(TamaJumperGame.GAME);

                return;
            }
            if (highscoresBounds.contains(touchPoint.x, touchPoint.y)) {
                parent.getAssets().playSound(parent.getAssets().clickSound);
                System.err.println("Not implemented");
                //parent.setScreen(new HighscoresScreen(parent));
                return;
            }
            if (helpBounds.contains(touchPoint.x, touchPoint.y)) {
                parent.getAssets().playSound(Assets.clickSound);
                System.err.println("Not implemented");
                //parent.setScreen(new HelpScreen(parent));
                return;
            }
            if (soundBounds.contains(touchPoint.x, touchPoint.y)) {
                boolean lastValue = parent.getPreferences().isSoundEffectsEnabled();
                parent.getPreferences().setSoundEffectsEnabled(!lastValue);
                if(lastValue) parent.getAssets().stopMusic();
                else {
                    parent.getAssets().playSound(Assets.clickSound);
                }
                return;
            }
            if (settingsBounds.contains(touchPoint.x, touchPoint.y)) {
                System.out.println("open settings");
                parent.changeScreen(TamaJumperGame.PREFERENCES);
                return;
            }
        }
    }

    public void draw () {
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        guiCam.update();
        parent.batcher.setProjectionMatrix(guiCam.combined);

        parent.batcher.disableBlending();
        parent.batcher.begin();
        parent.batcher.draw(Assets.backgroundRegionMenu, 0, 0, 320, 480);
        parent.batcher.end();

        parent.batcher.enableBlending();
        parent.batcher.begin();
        parent.batcher.draw(Assets.logo, 160 - 274 / 2, 480 - 10 - 142, 274, 142);
        parent.batcher.draw(Assets.mainMenu, 10, 200 - 110 / 2, 300, 110);
        parent.batcher.draw(parent.getPreferences().isSoundEffectsEnabled() ? Assets.soundOn : Assets.soundOff,
                soundBounds.x, soundBounds.y, soundBounds.width, soundBounds.height);
        Sprite sprite = new Sprite(Assets.pause);
        sprite.setRotation(90);
        sprite.setBounds(settingsBounds.x, settingsBounds.y,settingsBounds.width,settingsBounds.height);
        sprite.draw(parent.batcher);
        parent.batcher.end();
    }

    @Override
    public void render (float delta) {
        update();
        draw();
    }

    @Override
    public void pause () {
    }
}
