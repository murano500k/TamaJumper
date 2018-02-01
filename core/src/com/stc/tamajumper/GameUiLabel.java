package com.stc.tamajumper;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.awt.HeadlessException;


/**
 * Created by artem on 2/1/18.
 */

public class GameUiLabel extends Label {

    private final Camera camera;

    public GameUiLabel(String s, Skin skin, Camera camera, float x, float y) throws HeadlessException {
        super(s, skin);
        this.camera=camera;
        setPosition(x,y);

    }

    @Override
    public float getY() {
        return super.getY()+camera.position.y;
    }
}
