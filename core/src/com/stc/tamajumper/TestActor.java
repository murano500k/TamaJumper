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
        setWidth(Config.PLATFORM_WIDTH);
        setHeight(Config.PLATFORM_HEIGHT);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(Assets.platform,getX(),getY(),getWidth(),getHeight());
    }
}
