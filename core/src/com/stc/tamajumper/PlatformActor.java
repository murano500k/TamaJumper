package com.stc.tamajumper;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.Random;

import static com.stc.tamajumper.Config.PIXELS.WORLD_WIDTH;
import static com.stc.tamajumper.Config.PLATFORM_WIDTH;

/**
 * Created by artem on 1/30/18.
 */

public class PlatformActor extends MyActor {

    public enum Type{
        NORMAL,
        MOVING,
        BREAKABLE,
        BROKEN,
        SPRING
    }
    public enum State{
        EXIST,
        DESTROY
    }

    private Type type;
    private State state;



    public PlatformActor(float x, float y,Type type) {
        super(x,y);
        setWidth(Config.PLATFORM_WIDTH);
        setHeight(Config.PLATFORM_HEIGHT);
        setType(type);
        setState(State.EXIST);
        texture=Assets.platform;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }


    public static PlatformActor generatePlatform(float y, Random random){
        float x = random.nextFloat() * (WORLD_WIDTH - PLATFORM_WIDTH) + PLATFORM_WIDTH / 2;
        int seed = random.nextInt(10);
        Type type;
        if(seed==0){
            type=Type.MOVING;
        }else if(seed==1){
            type=Type.BROKEN;
        }else if(seed==2){
            type=Type.BREAKABLE;
        }else if(seed==3){
            type=Type.SPRING;
        }else {
            type=Type.NORMAL;
        }
        return new PlatformActor(x,y,type);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
