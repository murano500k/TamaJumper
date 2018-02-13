package com.stc.tamajumper;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Created by artem on 1/31/18.
 */

public class Controller {

    public static float getAccelX(){
        float rawAccelX=0;
        if (Gdx.app.getType() == Application.ApplicationType.Android ||
                Gdx.app.getType() == Application.ApplicationType.iOS) {
            rawAccelX= Gdx.input.getAccelerometerX();
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) rawAccelX = 5f;
            if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) rawAccelX = -5f;
        }
        return rawAccelX* Prefs.getAccelSensitivity();
    }
}
