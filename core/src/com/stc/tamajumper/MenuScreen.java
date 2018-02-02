package com.stc.tamajumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;

import static com.stc.tamajumper.Config.VIEWPORT_HEIGHT;
import static com.stc.tamajumper.Config.VIEWPORT_WIDTH;

/**
 * Created by artem on 2/1/18.
 */

public class MenuScreen extends ScreenAdapter implements InputProcessor {


    private final TamaJumperGame game;
    private final Vector3 touchPoint;
    private OrthographicCamera camera;
    private FillViewport viewport;
    private MyStage stage;
    private Group buttons;
    private Skin skin;
    private static final int BTN_HEIGHT = VIEWPORT_WIDTH/5;
    private static final int BTN_WIDTH = VIEWPORT_WIDTH/2;
    private TextButton btnPlay, btnSettings,btnExit,btnHelp, btnHighscores;

    public MenuScreen(TamaJumperGame game) {
        this.game=game;
        touchPoint = new Vector3();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        //checkButtonsClicked();
        stage.draw();

    }

    private void checkButtonsClicked() {

    }


    @Override
    public void show() {
        super.show();
        skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));
        camera=new OrthographicCamera(VIEWPORT_WIDTH,VIEWPORT_HEIGHT);
        camera.position.set(VIEWPORT_WIDTH / 2, VIEWPORT_HEIGHT /2, 0);
        viewport =new FillViewport(VIEWPORT_WIDTH,VIEWPORT_HEIGHT, camera);
        stage = new MyStage(viewport, game);

        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
        generateMenu();
    }

    private void generateMenu() {
        buttons=new Group();
        btnPlay=createButton("Play", TamaJumperGame.GAME);
        btnPlay.setPosition(VIEWPORT_WIDTH/2-BTN_WIDTH/2,VIEWPORT_HEIGHT*2/3);
        buttons.addActor(btnPlay);

        btnSettings =createButton("Settings",TamaJumperGame.PREFERENCES);
        btnSettings.setPosition(VIEWPORT_WIDTH/2-BTN_WIDTH/2,btnPlay.getY()-BTN_HEIGHT);
        buttons.addActor(btnSettings);

        btnHelp =createButton("Help",TamaJumperGame.HELP);
        btnHelp.setPosition(VIEWPORT_WIDTH/2-BTN_WIDTH/2,btnSettings.getY()-BTN_HEIGHT);
        buttons.addActor(btnHelp);



        btnHighscores =createButton("Highscores",TamaJumperGame.HIGHSCORES);
        btnHighscores.setPosition(VIEWPORT_WIDTH/2-BTN_WIDTH/2,btnHelp.getY()-BTN_HEIGHT);
        buttons.addActor(btnHighscores);


        btnExit =createButton("Exit",TamaJumperGame.EXIT);
        btnExit.setPosition(VIEWPORT_WIDTH/2-BTN_WIDTH/2,btnHighscores.getY()-BTN_HEIGHT);
        buttons.addActor(btnExit);


        stage.addActor(buttons);


        Label title=new Label("Tama Jumper",skin);
        title.setPosition(VIEWPORT_WIDTH/2-BTN_WIDTH/2,VIEWPORT_HEIGHT-BTN_HEIGHT);
        title.setScale(5f);
        stage.addActor(title);
    }
    private TextButton createButton(final String text, final int screen){
        TextButton btn=new TextButton(text, skin);
        btn.setWidth(BTN_WIDTH);
        btn.setHeight(BTN_HEIGHT);
        btn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("button "+text+" clicked");
                game.changeScreen(screen);
            }
        });
        return btn;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

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
