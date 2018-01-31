package com.stc.tamajumper;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import static com.stc.tamajumper.Config.PIXELS.WORLD_WIDTH;
import static com.stc.tamajumper.Config.TAMADA_JUMP_VELOCITY;
import static com.stc.tamajumper.Config.TAMADA_MOVE_VELOCITY;
import static com.stc.tamajumper.PlatformActor.Type.BREAKABLE;
import static com.stc.tamajumper.PlatformActor.Type.BROKEN;
import static com.stc.tamajumper.PlatformActor.Type.NORMAL;


/**
 * Created by artem on 1/30/18.
 */

public class TamaActor extends MyActor {
    public enum State{
        JUMP,
        FALL,
        HIT
    }
    public State state;
    public TamaActor(float x, float y) {
        super(x, y);
        setWidth(Config.TAMADA_WIDTH);
        setHeight(Config.TAMADA_HEIGHT);
        state = State.FALL;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        switch (state) {
            case FALL:
                texture = Assets.tamadaFall.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
                break;
            case JUMP:
                texture = Assets.tamadaJump.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
                break;
            case HIT:
            default:
                texture = Assets.tamadaHit;
        }
        Sprite sprite =new Sprite(texture);
        sprite.setBounds(getX(),getY(),getWidth(),getHeight());
        sprite.flip(velocity.x<0,false);
        sprite.draw(batch);
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        float rawAccelX = 0;

        if (Gdx.app.getType() == Application.ApplicationType.Android ||
                Gdx.app.getType() == Application.ApplicationType.iOS) {
            rawAccelX= Gdx.input.getAccelerometerX();
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) rawAccelX = 5f;
            if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) rawAccelX = -5f;
        }
        float normalizedAccelX=AppPreferences.getNormalizedAcelX(rawAccelX);

        stateTime+=deltaTime;
        if (state != State.HIT && getY() <= 0.5f) hitPlatform(null);
        if (state != State.HIT) velocity.x = -normalizedAccelX / 10 * TAMADA_MOVE_VELOCITY;

        velocity.add(World.gravity.x * deltaTime, World.gravity.y * deltaTime);
        setPosition(getX()+velocity.x * deltaTime, getY()+velocity.y * deltaTime);


        if (velocity.y > 0 && state != State.HIT) {
            if (state != State.JUMP) {
                state = State.JUMP;
                stateTime = 0;
            }
        }

        if (velocity.y < 0 && state != State.HIT) {
            if (state != State.FALL) {
                state = State.FALL;
                stateTime = 0;
            }
        }

        if (getX() < 0) setX(WORLD_WIDTH);
        if (getX() > WORLD_WIDTH) setX(0);
    }

    public void hitPlatform(PlatformActor platform) {
        PlatformActor.Type type = NORMAL;
        if(platform!=null){
            type=platform.getType();
            if(type==BREAKABLE || type==BROKEN) platform.setState(PlatformActor.State.DESTROY);
        }
        if(type!=BROKEN) {
            velocity.y = TAMADA_JUMP_VELOCITY;
            state = State.JUMP;
            stateTime = 0;
        }

    }

}
