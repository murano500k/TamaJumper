package com.stc.tamajumper;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static com.stc.tamajumper.Config.PIXELS.PLAYER_DIMEN;

/**
 * Created by artem on 2/2/18.
 */

public class HelpScreen extends BaseMenuScreen {

    public HelpScreen(TamaJumperGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        addUiObjects();
    }

    private void addUiObjects(){
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.row().pad(0,0,PLAYER_DIMEN,0);
        table.add(new Label("Help",skin));
        table.row().pad(0,0,PLAYER_DIMEN,0);
        table.add(new Label("Just try it!",skin));

        table.row().pad(PLAYER_DIMEN,0,0,0);
        TextButton btnBack = new TextButton("Back", skin);
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.changeScreen(TamaJumperGame.MENU);
            }
        });
        table.add(btnBack).uniformX();
    }
}
