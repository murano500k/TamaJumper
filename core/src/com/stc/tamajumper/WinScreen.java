package com.stc.tamajumper;

import static com.stc.tamajumper.Config.VIEWPORT_HEIGHT;
import static com.stc.tamajumper.Config.VIEWPORT_WIDTH;

/**
 * Created by artem on 2/2/18.
 */

public class WinScreen extends BaseMenuScreen {


    public WinScreen(TamaJumperGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        addUiObjects();
    }

    private void addUiObjects(){
        Kon6 kon6 = new Kon6(0, VIEWPORT_HEIGHT*0.2f,VIEWPORT_WIDTH, VIEWPORT_HEIGHT*0.6f);
        stage.addActor(kon6);
    }
}
