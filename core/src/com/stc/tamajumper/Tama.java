package com.stc.tamajumper;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

import java.util.Random;

import static com.stc.tamajumper.Config.PIXELS.WORLD_WIDTH;
import static com.stc.tamajumper.Platform.Type.BREAKABLE;


/**
 * Created by artem on 1/30/18.
 */

public class Tama extends MyActor {



    public static final float JUMP_VELOCITY = Config.PIXELS.PLAYER_DIMEN *15;
    public static final float HIGH_JUMP_VELOCITY = JUMP_VELOCITY *2f;
    public static final float MOVE_VELOCITY = Config.PIXELS.PLAYER_DIMEN *20;
    public static final float WIDTH = 48 ;
    public static final float HEIGHT = Config.PIXELS.PLAYER_DIMEN;
    private int score;
    private boolean hasShield=false;
    private MoveByAction currentAction;
    public static final float MAX_FACE_VELOCITY = 4;
    public static final float MIN_FACE_VELOCITY = 1;
    private boolean isProfile;
    private boolean gameStarted=false;

    public void setGameStarted(boolean b) {
        gameStarted=b;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public enum TamaState {
        JUMP,
        HIGHJUMP,
        FALL,
        HIGHFALL,
        HIT
    }
    public TamaState tamaState;




    public Tama(float x, float y, int startingScore) {
        super(x, y);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        score=startingScore;
        tamaState = TamaState.FALL;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        Sprite sprite =new Sprite(getTexture());
        sprite.setBounds(getX(),getY(),getWidth(),getHeight());
        sprite.flip(velocity.x<0,false);
        sprite.draw(batch);
    }

    @Override
    public TextureRegion getTexture() {
        if(tamaState==TamaState.HIT){
            return Assets.animDie.getKeyFrame(stateTime, Animation.ANIMATION_NONLOOPING);
        }
        if(gameStarted) {
            if (!isProfile) {
                isProfile = Math.abs(Controller.getAccelX()) >= MAX_FACE_VELOCITY;
            } else {
                isProfile = Math.abs(Controller.getAccelX()) >= MIN_FACE_VELOCITY;
            }
        }
        if(isProfile) {
            switch (tamaState) {
                case FALL:
                    return Assets.animProfileFall.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
                case HIGHFALL:
                    return Assets.animProfileHighFall.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
                case JUMP:
                    return Assets.animProfileJump.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
                case HIGHJUMP:
                    return Assets.animProfileHighJump.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
                case HIT:
                default:
                    return Assets.animDie.getKeyFrame(stateTime, Animation.ANIMATION_NONLOOPING);
            }
        }else {
            switch (tamaState) {
                case FALL:
                    return Assets.animFaceFall.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
                case HIGHFALL:
                    return Assets.animFaceHighFall.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
                case JUMP:
                    return Assets.animFaceJump.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
                case HIGHJUMP:
                    return Assets.animFaceHighJump.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
                case HIT:
                default:
                    return Assets.animDie.getKeyFrame(stateTime, Animation.ANIMATION_NONLOOPING);
            }
        }
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        if(gameStarted) {
            float normalizedAccelX = Controller.getAccelX();
            if (tamaState != TamaState.HIT && getY() <= 0.5f)
                hitPlatform(Platform.generatePlatform(0, new Random()));
            if (tamaState != TamaState.HIT) velocity.x = -normalizedAccelX / 10 * MOVE_VELOCITY;

        velocity.add(Config.GRAVITY.x * deltaTime, Config.GRAVITY.y * deltaTime);
        currentAction = new MoveByAction();
        currentAction.setDuration(deltaTime);
        currentAction.setAmount(velocity.x * deltaTime,velocity.y * deltaTime);
        addAction(currentAction);

        if (velocity.y > 0 && tamaState != TamaState.HIT) {
            if (tamaState == TamaState.FALL ||
                    tamaState == TamaState.HIGHFALL) {
                tamaState = TamaState.JUMP;
                stateTime = 0;
            }
        }

        if (velocity.y < 0 && tamaState != TamaState.HIT) {
            if(tamaState==TamaState.JUMP) {
                tamaState=TamaState.FALL;
                stateTime=0;
            }else if(tamaState==TamaState.HIGHJUMP) {
                tamaState=TamaState.HIGHFALL;
                stateTime=0;
            }
        }

            if (getX() < 0) setX(WORLD_WIDTH);
            if (getX() > WORLD_WIDTH) setX(0);
        }
    }

    public void hitPlatform(Platform platform) {
        if(tamaState == TamaState.FALL || tamaState == TamaState.HIGHFALL) {
            if(platform.getType()==BREAKABLE) {
                    platform.pulverize();
            }
            if(platform.getType()== Platform.Type.SPRING){
                velocity.y = HIGH_JUMP_VELOCITY;
                tamaState = TamaState.HIGHJUMP;
            }else {
                velocity.y = JUMP_VELOCITY;
                tamaState = TamaState.JUMP;
            }
            Assets.playSound(platform.getJumpSound());
            stateTime = 0;
            score++;
        }
    }
    public void hitCoin(Coin coin){
        score+=coin.getValue();
        coin.destroy();
    }

    public int getScore() {
        return score;
    }

    public boolean hitEnemy(SmartEnemy enemy) {
        if(hasShield){
            hasShield=false;
            return true;
        }
        if(currentAction!=null){
            removeAction(currentAction);
        }

        tamaState = TamaState.HIT;
        stateTime = 0;
        return false;
    }

}