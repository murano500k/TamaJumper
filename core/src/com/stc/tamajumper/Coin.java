package com.stc.tamajumper;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.stc.tamajumper.Config.PIXELS.PLAYER_DIMEN;

/**
 * Created by artem on 1/31/18.
 */

public class Coin extends MyActor{


    public static final float WIDTH = PLAYER_DIMEN;
    public static final float HEIGHT = PLAYER_DIMEN;
    public static final int COIN_SCORE = 10;



    public Coin(float x, float y) {
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

    public int getValue() {
        return COIN_SCORE;
    }
}
