package com.stc.tamajumper;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by artem on 2/2/18.
 */

public class MyStage extends Stage implements InputProcessor{

    private final Game game;

    public MyStage(Viewport viewport, Game game) {
        super(viewport);
        this.game=game;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.BACK){
            game.changeScreen(Game.MENU);
        }
        return false;
    }

}
