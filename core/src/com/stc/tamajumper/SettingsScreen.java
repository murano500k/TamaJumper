package com.stc.tamajumper;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static com.stc.tamajumper.Config.PIXELS.PLAYER_DIMEN;

/**
 * Created by artem on 2/1/18.
 */

public class SettingsScreen extends BaseMenuScreen{

    public SettingsScreen(TamaJumperGame game) {
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
        final Slider volumeSlider = new Slider(0,1,0.1f,false,skin);
        volumeSlider.setName("volume");
        volumeSlider.setValue(Prefs.getSoundVolume());
        volumeSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                Prefs.setSoundVolume(volumeSlider.getValue());
                return false;
            }
        });

        final Slider sensitivitySlider = new Slider(Config.AccelerometerValues.MIN_VALUE_SENSITIVITY,
                Config.AccelerometerValues.MAX_VALUE_SENSITIVITY,0.1f,false,skin);
        sensitivitySlider.setName("sensitivity");
        sensitivitySlider.setValue(Prefs.getAccelSensitivity());
        sensitivitySlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                Prefs.setAccelSensitivity(sensitivitySlider.getValue());
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
        table.add(new Label("Horizontal speed",skin)).uniformX();
        table.row().pad(0,0,PLAYER_DIMEN,0);
        table.add(sensitivitySlider).fillX().uniformX();
        table.row();

        table.add(btnBack).uniformX();

    }
}
