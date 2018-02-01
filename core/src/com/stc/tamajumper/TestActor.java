package com.stc.tamajumper;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by artem on 1/30/18.
 */

public class TestActor extends Actor {

    public TestActor(float x,float y) {
        setX(x);
        setY(y);
        setWidth(PlatformActor.HEIGHT);
        setHeight(PlatformActor.HEIGHT);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(Assets.platform,getX(),getY(),getWidth(),getHeight());
    }
}
