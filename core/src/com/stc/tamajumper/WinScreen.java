package com.stc.tamajumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;

import java.util.Random;


/**
 * Created by artem on 1/11/18.
 */

public class WinScreen extends ScreenAdapter {
    TamaJumperGame game;
    OrthographicCamera cam;
    TextureRegion princess;
    String[] messages;
    String[] messagesFinal = { "Princess: Oh dear!\n What have you done?",
            "Tama: I came to \nrescue you!",
            "Princess: you are\n mistaken\nI need no rescueing",
            "Tama: So all this \nwork for nothing?",
            "Princess: I have \ncake and tea!\nWould you like some?",
            "Tama: I'd be my \npleasure!",
            "And they ate cake\nand drank tea\nhappily ever \nafter\n\n\n\n\n\n\nKära Emma!\nDu är fantastisk!\nDu blev ferdig\n med spelet!"
    };
    String[] messagesNotFinal = { "Tama: No princess \nin this castle.",
            "Tama: Let's go \nto the next one!"
    };
    int currentMessage = 0;
    boolean isFinalLevel;

    public WinScreen(TamaJumperGame game) {
        this.game = game;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 320, 480);
        princess = new TextureRegion(Assets.arrow.getTexture(), 210, 122, -40, 38);
        isFinalLevel = (new Random().nextFloat()>0.9f);
        messages = isFinalLevel ? messagesFinal : messagesNotFinal;
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            currentMessage++;
            if(currentMessage == messages.length) {
                currentMessage--;
                game.setScreen(isFinalLevel ? new MainMenu(game) : new GameScreen(game));
            }
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        game.batcher.setProjectionMatrix(cam.combined);
        game.batcher.begin();
        game.batcher.draw(Assets.backgroundRegionMenu, 0, 0);
        game.batcher.draw(Assets.castle, 60, 120, 200, 200);
        game.batcher.draw(Assets.tamadaFall.getKeyFrame(0, Animation.ANIMATION_LOOPING), 120, 200);
        Assets.font.draw(game.batcher, messages[currentMessage], 0, 400, 320, Align.center, false);
        if(isFinalLevel) game.batcher.draw(princess,150, 200);
        game.batcher.end();
    }
}
