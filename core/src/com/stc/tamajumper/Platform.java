package com.stc.tamajumper;

import java.util.Random;

import static com.stc.tamajumper.Config.*;
import static com.stc.tamajumper.Config.PIXELS.*;

/**
 * Created by artem on 1/11/18.
 */

public class Platform extends DynamicGameObject {

    public final boolean canBreak;
    public final int type;
    public int state;
    public float stateTime;
    private Spring spring;

    public Platform (boolean breakable, int type, float x, float y) {
        super(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
        this.type = type;
        this.state = PLATFORM_STATE_NORMAL;
        this.stateTime = 0;
        if (type == PLATFORM_TYPE_MOVING) {
            velocity.x = PLATFORM_VELOCITY;
        }
        canBreak = breakable;
    }

    public void update (float deltaTime) {
        if (type == PLATFORM_TYPE_MOVING) {
            position.add(velocity.x * deltaTime, 0);
            bounds.x = position.x - PLATFORM_WIDTH / 2;
            bounds.y = position.y - PLATFORM_HEIGHT / 2;

            if (position.x < PLATFORM_WIDTH / 2) {
                velocity.x = -velocity.x;
                position.x = PLATFORM_WIDTH / 2;
            }
            if (position.x > WORLD_WIDTH - PLATFORM_WIDTH / 2) {
                velocity.x = -velocity.x;
                position.x = WORLD_WIDTH - PLATFORM_WIDTH / 2;
            }
        }

        stateTime += deltaTime;
    }

    public void pulverize () {
        state = PLATFORM_STATE_PULVERIZING;
        stateTime = 0;
        velocity.x = 0;
    }

    @Override
    public String toString() {
        return "Platform{" +
                "canBreak=" + canBreak +
                ", type=" + type +
                ", state=" + state +
                ", stateTime=" + stateTime +
                '}';
    }

    public void setSpring(Spring spring) {
        this.spring = spring;
    }

    public Spring getSpring() {
        return spring;
    }
}
