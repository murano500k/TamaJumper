package com.stc.tamajumper;

import com.badlogic.gdx.graphics.g2d.Batch;

public class SmartEnemy extends MyActor {

    public static final float WIDTH = Config.PIXELS.PLAYER_DIMEN ;
    public static final float HEIGHT = Config.PIXELS.PLAYER_DIMEN;

    public SmartEnemy(float x, float y) {
        super(x, y);
        setWidth(WIDTH);
        setHeight(HEIGHT);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(getTexture(),getX(),getY());
    }


}
