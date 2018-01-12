package com.stc.tamajumper;

import static com.stc.tamajumper.Config.*;
import static com.stc.tamajumper.Config.PIXELS.*;

/**
 * Created by artem on 1/11/18.
 */

public class Tamada extends DynamicGameObject {

    public int state;
    public float stateTime;



    public Tamada(float x, float y) {
        super(x, y, TAMADA_WIDTH, TAMADA_HEIGHT);
        state = TAMADA_STATE_FALL;
        stateTime = 0;
    }
    public void update (float deltaTime) {
        velocity.add(World.gravity.x * deltaTime, World.gravity.y * deltaTime);
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        bounds.x = position.x - bounds.width / 2;
        bounds.y = position.y - bounds.height / 2;

        if (velocity.y > 0 && state != TAMADA_STATE_HIT) {
            if (state != TAMADA_STATE_JUMP) {
                state = TAMADA_STATE_JUMP;
                stateTime = 0;
            }
        }

        if (velocity.y < 0 && state != TAMADA_STATE_HIT) {
            if (state != TAMADA_STATE_FALL) {
                state = TAMADA_STATE_FALL;
                stateTime = 0;
            }
        }

        if (position.x < 0) position.x = WORLD_WIDTH;
        if (position.x > WORLD_WIDTH) position.x = 0;

        stateTime += deltaTime;
    }

    public void hitPlatform () {
        velocity.y = TAMADA_JUMP_VELOCITY;
        state = TAMADA_STATE_JUMP;
        stateTime = 0;
    }

}
