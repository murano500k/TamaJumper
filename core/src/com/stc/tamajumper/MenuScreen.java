package com.stc.tamajumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static com.stc.tamajumper.Config.VIEWPORT_WIDTH;

public class MenuScreen extends BaseMenuScreen   {


    private final Vector3 touchPoint;
    private Group buttons;
    private static final int BTN_HEIGHT = VIEWPORT_WIDTH/5;
    private static final int BTN_WIDTH = VIEWPORT_WIDTH/2;
    private TextButton btnPlay, btnSettings,btnExit,btnHelp, btnHighscores;

    public MenuScreen(final Game game) {
        super(game);
        touchPoint = new Vector3();

    }



    @Override
    public void show() {
        super.show();
        generateMenu();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            game.changeScreen(Game.GAME);
        }
    }

    private void generateMenu() {
        buttons=new Group();
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title=new Label("Tama Jumper",skin);
        table.row().padBottom(Config.PIXELS.PLAYER_DIMEN);
        table.add(title).expandX();



        btnPlay=createButton("Play", Game.GAME);
        buttons.addActor(btnPlay);

        table.row().padBottom(Config.PIXELS.PLAYER_DIMEN);
        table.add(btnPlay);

        btnSettings =createButton("Settings", Game.PREFERENCES);
        buttons.addActor(btnSettings);
        table.row().padBottom(Config.PIXELS.PLAYER_DIMEN);
        table.add(btnSettings);

        btnHelp =createButton("Help", Game.HELP);
        buttons.addActor(btnHelp);
        table.row().padBottom(Config.PIXELS.PLAYER_DIMEN);
        table.add(btnHelp);


        btnHighscores =createButton("Highscores", Game.HIGHSCORES);
        buttons.addActor(btnHighscores);
        table.row().padBottom(Config.PIXELS.PLAYER_DIMEN);
        table.add(btnHighscores);

        btnExit =createButton("Exit", Game.EXIT);
        buttons.addActor(btnExit);
        table.row().padBottom(Config.PIXELS.PLAYER_DIMEN);
        table.add(btnExit);

    }
    private TextButton createButton(final String text, final int screen){
        TextButton btn=new TextButton(text, skin);
        btn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.changeScreen(screen);
            }
        });
        return btn;
    }


}
