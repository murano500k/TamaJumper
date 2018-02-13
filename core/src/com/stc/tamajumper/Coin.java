package com.stc.tamajumper;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by artem on 1/31/18.
 */

public class Coin extends MyActor{


    public static final float WIDTH = 48;
    public static final float HEIGHT = 48;
    public static final int COIN_SCORE = 10;
    private final Animation animation;


    public Coin(float x, float y) {
        super(x, y);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        animation = Assets2.animCoin;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(objectState==ObjectState.DESTROY) remove();
    }


    @Override
    public TextureRegion getTexture() {
        return animation.getKeyFrame(stateTime,Animation.ANIMATION_LOOPING);
    }
    public void destroy(){
        objectState=ObjectState.DESTROY;
        Assets.playSound(Assets.coinSound);
    }

    public int getValue() {
        return COIN_SCORE;
    }
}
