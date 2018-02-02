package com.stc.tamajumper;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

import java.util.Random;

import static com.stc.tamajumper.Config.PIXELS.WORLD_WIDTH;
import static com.stc.tamajumper.Platform.Type.BREAKABLE;
import static com.stc.tamajumper.Platform.Type.BROKEN;


/**
 * Created by artem on 1/30/18.
 */

public class Tama extends MyActor {
    public static final float JUMP_VELOCITY = Config.PIXELS.PLAYER_DIMEN *15;
    public static final float HIGH_JUMP_VELOCITY = JUMP_VELOCITY *1.5f;
    public static final float MOVE_VELOCITY = Config.PIXELS.PLAYER_DIMEN *20;
    public static final float WIDTH = Config.PIXELS.PLAYER_DIMEN ;
    public static final float HEIGHT = Config.PIXELS.PLAYER_DIMEN;
    private int score;
    private boolean hasShield=false;
    private MoveByAction currentAction;


    public enum TamaState {
        JUMP,
        HIGHJUMP,
        FALL,
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
        switch (tamaState) {
            case FALL:
                return Assets.tamadaFall.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
            case JUMP:
                return Assets.tamadaJump.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
            case HIGHJUMP:
                return Assets.tamadaJump.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
            case HIT:
            default:
                return Assets.tamadaHit;
        }
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        float normalizedAccelX=Controller.getAccelX();
        if (tamaState != TamaState.HIT && getY() <= 0.5f) hitPlatform(Platform.generatePlatform(0,new Random()));
        if (tamaState != TamaState.HIT) velocity.x = -normalizedAccelX / 10 * MOVE_VELOCITY;

        velocity.add(Config.GRAVITY.x * deltaTime, Config.GRAVITY.y * deltaTime);
        currentAction = new MoveByAction();
        currentAction.setDuration(deltaTime);
        currentAction.setAmount(velocity.x * deltaTime,velocity.y * deltaTime);
        addAction(currentAction);

        if (velocity.y > 0 && tamaState != TamaState.HIT) {
            if (tamaState == TamaState.FALL) {
                tamaState = TamaState.JUMP;
                stateTime = 0;
            }
        }

        if (velocity.y < 0 && tamaState != TamaState.HIT) {
            if (tamaState != TamaState.FALL) {
                tamaState = TamaState.FALL;
                stateTime = 0;
            }
        }

        if (getX() < 0) setX(WORLD_WIDTH);
        if (getX() > WORLD_WIDTH) setX(0);
    }

    public void hitPlatform(Platform platform) {
        if(tamaState == TamaState.FALL) {
            if(platform.getType()==BREAKABLE || platform.getType()==BROKEN) {
                    platform.pulverize();
            }
            if(platform.getType()== Platform.Type.SPRING){
                velocity.y = HIGH_JUMP_VELOCITY;
                tamaState = TamaState.HIGHJUMP;
            }else {
                velocity.y = JUMP_VELOCITY;
                tamaState = TamaState.JUMP;
                Assets.playSound(platform.getJumpSound());
            }
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

    public boolean hitEnemy(Enemy enemy) {
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
