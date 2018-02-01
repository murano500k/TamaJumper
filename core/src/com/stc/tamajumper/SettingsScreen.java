package com.stc.tamajumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;

import static com.stc.tamajumper.Config.PIXELS.PLAYER_DIMEN;
import static com.stc.tamajumper.Config.VIEWPORT_HEIGHT;
import static com.stc.tamajumper.Config.VIEWPORT_WIDTH;

/**
 * Created by artem on 2/1/18.
 */

public class SettingsScreen extends ScreenAdapter {

    private final TamaJumperGame game;
    private final Skin skin;
    private Stage stage;
    private OrthographicCamera camera;
    private FillViewport viewport;

    public SettingsScreen(TamaJumperGame game) {
        this.game = game;
        skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));

    }

    @Override
    public void show() {
        super.show();
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(VIEWPORT_WIDTH / 2, VIEWPORT_HEIGHT / 2, 0);
        viewport = new FillViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
        addUiObjects();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }
    private void addUiObjects(){
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        final Slider volumeSlider = new Slider(0,1,0.1f,false,skin);
        volumeSlider.setName("volume");
        volumeSlider.setValue(AppPreferences.getSoundVolume());
        volumeSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                AppPreferences.setSoundVolume(volumeSlider.getValue());
                return false;
            }
        });

        final Slider sensitivitySlider = new Slider(0,1,0.1f,false,skin);
        sensitivitySlider.setName("sensitivity");
        sensitivitySlider.setValue(AppPreferences.getAccelSensitivity());
        sensitivitySlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                AppPreferences.setAccelSensitivity(sensitivitySlider.getValue());
                return false;
            }
        });
        TextButton btnBack = new TextButton("Back", skin);
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.changeScreen(TamaJumperGame.MENU);
            }
        });

        table.add(new Label("Sound volume",skin)).uniformX();
        table.row().pad(0,0,PLAYER_DIMEN,0);;
        table.add(volumeSlider).fillX().uniformX();
        table.row();
        table.add(new Label("Control sensitivity",skin)).uniformX();
        table.row().pad(0,0,PLAYER_DIMEN,0);
        table.add(sensitivitySlider).fillX().uniformX();
        table.row();

        table.add(btnBack).uniformX();

    }
    @Override
    public void dispose() {
        // dispose of assets when not needed anymore
        stage.dispose();
    }
}
