package com.stc.tamajumper;

public class Coin extends GameObject {


    float stateTime;

    public Coin (float x, float y) {
        super(x, y, CoinActor.WIDTH, CoinActor.HEIGHT);
        stateTime = 0;
    }

    public void update (float deltaTime) {
        stateTime += deltaTime;
    }
}

