package com.stc.tamajumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by artem on 1/19/18.
 */

class PreferencesScreen implements Screen {
    private Slider accelSensitivitySlider;
    private Skin skin;
    TamaJumperGame parent;
    private Stage stage;
    private TextField sliderValue;


    public PreferencesScreen(TamaJumperGame tamaJumperGame) {

        parent = tamaJumperGame;
        ScreenViewport viewport = new ScreenViewport();
        viewport.setUnitsPerPixel(0.25f);
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);




    }

    @Override
    public void show() {

// Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        // temporary until we have asset manager in
        skin = new Skin(Gdx.files.internal("atlas/uiskin.json"));

        accelSensitivitySlider = new Slider(
                Config.AccelerometerValues.MIN_VALUE_SENSITIVITY,
                Config.AccelerometerValues.MAX_VALUE_SENSITIVITY,
                0.1f,
                false,
                skin);
        accelSensitivitySlider.setValue(AppPreferences.getAccelSensitivity());
        accelSensitivitySlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                AppPreferences.setAccelSensitivity(accelSensitivitySlider.getValue());
                sliderValue.setText(String.valueOf(accelSensitivitySlider.getValue()));
                return false;
            }
        });

        TextButton exit = new TextButton("Back", skin);
        TextField sliderLabel = new TextField("Control \nsensitivity",skin);
        sliderValue = new TextField("Value",skin);
        sliderValue.setText(String.valueOf(accelSensitivitySlider.getValue()));

        //add buttons to table
        table.add(sliderLabel).fillX().uniformX();
        table.row();
        table.add(sliderValue).fillX().uniformX();
        table.row();
        table.add(accelSensitivitySlider).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(exit).fillX().uniformX();

        // create button listeners
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(TamaJumperGame.MENU);
            }
        });
    }

    @Override
    public void render(float delta) {
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        parent.batcher.disableBlending();
        parent.batcher.begin();
        parent.batcher.draw(Assets.backgroundRegionMenu, 0, 0, 320, 480);
        parent.batcher.end();

        parent.batcher.enableBlending();
        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        // change the stage's viewport when teh screen size is changed
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // dispose of assets when not needed anymore
        stage.dispose();
    }
}