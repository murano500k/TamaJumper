package com.stc.tamajumper;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by artem on 1/11/18.
 */

public class DynamicGameObject extends com.stc.tamajumper.GameObject {
    public final Vector2 velocity;
    public final Vector2 accel;

    public DynamicGameObject (float x, float y, float width, float height) {
        super(x, y, width, height);
        velocity = new Vector2();
        accel = new Vector2();
    }
}
