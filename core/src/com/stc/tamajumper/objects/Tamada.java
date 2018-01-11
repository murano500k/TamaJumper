package com.stc.tamajumper.objects;

import com.stc.tamajumper.World;

/**
 * Created by artem on 1/11/18.
 */

public class Tamada extends DynamicGameObject {
    public static final int TAMADA_STATE_JUMP = 0;
    public static final int TAMADA_STATE_FALL = 1;
    public static final int TAMADA_STATE_HIT = 2;
    public static final float TAMADA_JUMP_VELOCITY = 11;
    public static final float TAMADA_MOVE_VELOCITY = 20;
    public static final float TAMADA_WIDTH = 0.8f;
    public static final float TAMADA_HEIGHT = 0.8f;

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

        if (position.x < 0) position.x = World.WORLD_WIDTH;
        if (position.x > World.WORLD_WIDTH) position.x = 0;

        stateTime += deltaTime;
    }

    public void hitPlatform () {
        velocity.y = TAMADA_JUMP_VELOCITY;
        state = TAMADA_STATE_JUMP;
        stateTime = 0;
    }

}
