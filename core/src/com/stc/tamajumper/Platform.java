package com.stc.tamajumper;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import java.util.Random;

import static com.stc.tamajumper.Config.PIXELS.WORLD_WIDTH;
import static com.stc.tamajumper.Config.PLATFORM_PULVERIZE_TIME;

/**
 * Created by artem on 1/30/18.
 */

public class Platform extends MyActor {




    private static final float DURATION_MOVE_LEFT_TO_RIGHT = 5f;
    public static final float WIDTH = Config.PIXELS.PLAYER_DIMEN*2;
    public static final float HEIGHT = Config.PIXELS.PLAYER_DIMEN/3f;
    private final boolean reversed;

    public enum Type{
        NORMAL,
        MOVING,
        BREAKABLE,
        BROKEN,
        SPRING
    }


    private Type type;
    private final float seed;
    private boolean startedMoving=false;



    public Platform(float x, float y, Type type, float startTime, boolean reversed) {
        super(x,y);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        this.type=type;
        this.reversed=reversed;
        seed = startTime;
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        if(objectState==ObjectState.DESTROY && stateTime>PLATFORM_PULVERIZE_TIME){
            remove();
            return;
        }


        if(type==Type.MOVING){
            if(!startedMoving && stateTime>=seed){
                startedMoving=true;
                addAction(getMoveAction());
            }
        }
    }

    @Override
    public TextureRegion getTexture() {
        if(objectState==ObjectState.DESTROY){
            return Assets2.animPlatformBreak.getKeyFrame(stateTime, Animation.ANIMATION_NONLOOPING);
        }else {
            switch (type){
                case MOVING:
                    return Assets2.texturePlatformMoving;
                case BREAKABLE:
                    return Assets2.texturePlatformBreakable;
                case SPRING:
                    return Assets2.texturePlatformSpring;
                case NORMAL:
                default:
                    return Assets2.texturePlatformNormal;
            }
        }
    }

    public static Platform generatePlatform(float y, Random random){
        boolean reversed=random.nextBoolean();
        float x = random.nextInt((int)(WORLD_WIDTH/Platform.WIDTH)) *Platform.WIDTH;

        float startTime= random.nextFloat()*DURATION_MOVE_LEFT_TO_RIGHT;
        int seed = random.nextInt(5);
        Type type;
        if(seed==0){
            type=Type.MOVING;
            if(reversed)x=Config.VIEWPORT_WIDTH-Platform.WIDTH;
            else x=0;
        }else if(seed==1){
            type=Type.SPRING;
        }else if(seed==2){
            type=Type.BREAKABLE;
        }else {
            type=Type.NORMAL;
        }
        return new Platform(x,y,type,startTime,reversed);
    }

    public Type getType() {
        return type;
    }

    public ObjectState getState(){
        return objectState;
    }


    public void pulverize(){
        objectState=ObjectState.DESTROY;
        stateTime=0;
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
    private Action getMoveAction(){
         SequenceAction sequenceAction = new SequenceAction();

         MoveToAction moveActionReturn = new MoveToAction();
         moveActionReturn.setPosition(Config.VIEWPORT_WIDTH-getWidth(),getY());
         moveActionReturn.setDuration(DURATION_MOVE_LEFT_TO_RIGHT);



        MoveToAction moveAction = new MoveToAction();
        moveAction.setPosition(0,getY());
        moveAction.setDuration(DURATION_MOVE_LEFT_TO_RIGHT);

        if(reversed) {
            sequenceAction.addAction(moveActionReturn);
            sequenceAction.addAction(moveAction);
        }else {
            sequenceAction.addAction(moveAction);
            sequenceAction.addAction(moveActionReturn);
        }

        RepeatAction repeatAction=new RepeatAction();
        repeatAction.setAction(sequenceAction);

        repeatAction.setCount(RepeatAction.FOREVER);
        return repeatAction;
    }

    }

