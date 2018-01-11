package com.stc.tamajumper.test;

import com.badlogic.gdx.ScreenAdapter;

/**
 * Created by artem on 1/11/18.
 */

public class TestStarter extends com.stc.tamajumper.GameStarter {
    @Override
    public ScreenAdapter getStartScreen() {
        return new com.stc.tamajumper.GameScreen(this);
    }
}
