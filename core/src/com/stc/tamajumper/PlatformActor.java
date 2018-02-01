package com.stc.tamajumper;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;

import java.util.Random;

import static com.stc.tamajumper.Config.PIXELS.WORLD_WIDTH;
import static com.stc.tamajumper.Config.PLATFORM_PULVERIZE_TIME;

/**
 * Created by artem on 1/30/18.
 */

public class PlatformActor extends MyActor {




    private static final float PLATFORM_MOVING_SPEED = 2;
    public static final float WIDTH = Config.PIXELS.DOUBLE_DIMEN;
    public static final float HEIGHT = Config.PIXELS.HALF_DIMEN;
    public enum Type{
        NORMAL,
        MOVING,
        BREAKABLE,
        BROKEN,
        SPRING;
    }


    private Type type;
    private final Action moveAction;



    public PlatformActor(float x, float y,Type type) {
        super(x,y);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        this.type=type;
        moveAction=ActionManager.initMoveAction(new Random().nextBoolean(), PLATFORM_MOVING_SPEED);
        if(type==Type.MOVING){
            addAction(moveAction);
        }
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        if(objectState==ObjectState.DESTROY && stateTime>PLATFORM_PULVERIZE_TIME){
            remove();
            return;
        }

        if (getX() < -getWidth()) setX(WORLD_WIDTH);
        if (getX() > WORLD_WIDTH) setX(-getWidth());
    }

    @Override
    public TextureRegion getTexture() {
        if(objectState==ObjectState.DESTROY){
            return Assets.brakingPlatform.getKeyFrame(stateTime, Animation.ANIMATION_NONLOOPING);
        }else {
            return Assets.platform;
        }
    }

    public static PlatformActor generatePlatform(float y, Random random){
        float x = random.nextFloat() * (WORLD_WIDTH - PlatformActor.WIDTH*2);
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




    public void pulverize(){
        objectState=ObjectState.DESTROY;
        stateTime=0;
        removeAction(moveAction);
    }


    public Sound getJumpSound() {
        switch (type){
            case SPRING:
                return Assets.jumpSounds.get(Config.JUMP_SOUND_INDEX_HIGH);
            case MOVING:
                return Assets.jumpSounds.get(Config.JUMP_SOUND_INDEX_MOVING);
            case BREAKABLE:
            case BROKEN:
                return Assets.jumpSounds.get(Config.JUMP_SOUND_INDEX_BREAK);
            case NORMAL:
            default:
                return Assets.jumpSounds.get(Config.JUMP_SOUND_INDEX_DEFAULT);
        }
    }
}
