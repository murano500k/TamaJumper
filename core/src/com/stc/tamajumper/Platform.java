package com.stc.tamajumper;

import static com.stc.tamajumper.Config.PIXELS.WORLD_WIDTH;
import static com.stc.tamajumper.Config.PLATFORM_STATE_NORMAL;
import static com.stc.tamajumper.Config.PLATFORM_STATE_PULVERIZING;
import static com.stc.tamajumper.Config.PLATFORM_TYPE_MOVING;
import static com.stc.tamajumper.Config.PLATFORM_VELOCITY;

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
        super(x, y, PlatformActor.WIDTH, PlatformActor.HEIGHT);
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
            bounds.x = position.x - PlatformActor.WIDTH / 2;
            bounds.y = position.y - PlatformActor.HEIGHT / 2;

            if (position.x < PlatformActor.WIDTH / 2) {
                velocity.x = -velocity.x;
                position.x = PlatformActor.WIDTH / 2;
            }
            if (position.x > WORLD_WIDTH - PlatformActor.WIDTH / 2) {
                velocity.x = -velocity.x;
                position.x = WORLD_WIDTH - PlatformActor.WIDTH / 2;
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
