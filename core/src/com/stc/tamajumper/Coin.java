package com.stc.tamajumper;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

/**
 * Created by artem on 1/31/18.
 */

public class Coin extends MyActor{


    public static final float WIDTH = 48;
    public static final float HEIGHT = 48;
    public static final int COIN_SCORE = 10;
    public static final int COIN_RED_SCORE = 20;
    public static final int COIN_GOLD_SCORE = 100;
    public  final int score;

    public static int getRandomScore(){
        int score;
        int scoreInt = new Random().nextInt(10);
        if(scoreInt<=5) score=COIN_SCORE;
        else if(scoreInt<=8) score=COIN_RED_SCORE;
        else score=COIN_GOLD_SCORE;
        return score;
    }



    public Coin(float x, float y, int score) {
        super(x, y);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        this.score=score;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(objectState==ObjectState.DESTROY) remove();
    }


    @Override
    public TextureRegion getTexture() {
        return Assets.getCoinAnim(score).getKeyFrame(stateTime,Animation.ANIMATION_LOOPING);
    }
    public void destroy(){
        objectState=ObjectState.DESTROY;
        Assets.playSound(Assets.coinSound);
    }

    public int getValue() {
        return COIN_SCORE;
    }
}
