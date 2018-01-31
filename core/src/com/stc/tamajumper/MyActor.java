package com.stc.tamajumper;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by artem on 1/30/18.
 */

public abstract class MyActor extends Actor {

    protected TextureRegion texture;
    protected float stateTime;
    public final Vector2 velocity;
    public final Vector2 accel;

    public MyActor(float x, float y) {
        setX(x);
        setY(y);
        stateTime = 0;
        velocity = new Vector2();
        accel = new Vector2();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime+=delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }
}
