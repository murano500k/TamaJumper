package com.stc.tamajumper;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by artem on 1/31/18.
 */

public class CoinActor extends MyActor{


    public static final float WIDTH = 1f;
    public static final float HEIGHT = 1f;
    public static final int COIN_SCORE = 10;



    public CoinActor(float x, float y) {
        super(x, y);
        setWidth(WIDTH);
        setHeight(HEIGHT);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(objectState==ObjectState.DESTROY) remove();
    }


    @Override
    public TextureRegion getTexture() {
        return Assets.coinAnim.getKeyFrame(stateTime,Animation.ANIMATION_LOOPING);
    }
    public void destroy(){
        objectState=ObjectState.DESTROY;
        Assets.playSound(Assets.coinSound);
    }
}
