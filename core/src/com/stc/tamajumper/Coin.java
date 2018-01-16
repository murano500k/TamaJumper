package com.stc.tamajumper;

import static com.stc.tamajumper.Config.*;

public class Coin extends GameObject {


    float stateTime;

    public Coin (float x, float y) {
        super(x, y, COIN_WIDTH, COIN_HEIGHT);
        stateTime = 0;
    }

    public void update (float deltaTime) {
        stateTime += deltaTime;
    }
}

