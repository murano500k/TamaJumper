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
    public Animation animProfileJump;
    public Animation animProfileHighJump;
    public Animation animFaceDie;
    public Animation animFaceJump;
    public Animation animFaceHighJump;



    public static final float JUMP_VELOCITY = Config.PIXELS.PLAYER_DIMEN *15;
    public static final float HIGH_JUMP_VELOCITY = JUMP_VELOCITY *1.5f;
    public static final float MOVE_VELOCITY = Config.PIXELS.PLAYER_DIMEN *20;
    public static final float WIDTH = 48 ;
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
        initAnimations();
    }

    private void initAnimations(){
        
        animProfileJump=initAnimation("person-profile-jump-",7);
        animProfileHighJump=initAnimation("person-profile-superjump-",7);
        animFaceJump=initAnimation("person-face-jump-",7);
        animFaceHighJump=initAnimation("person-face-super-jump-",7);
        animFaceDie=initAnimation("person-die-",4);
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
        System.out.println("ax="+Controller.getAccelX()+" state="+tamaState);
        if(Math.abs(Controller.getAccelX())>2) {
            switch (tamaState) {
                case FALL:
                    return animProfileJump.getKeyFrame(0, Animation.ANIMATION_LOOPING);
                case JUMP:
                    return animProfileJump.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
                case HIGHJUMP:
                    return animProfileHighJump.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
                case HIT:
                default:
                    return animFaceDie.getKeyFrame(stateTime, Animation.ANIMATION_NONLOOPING);
            }
        }else {
            switch (tamaState) {
                case FALL:
                    return animFaceJump.getKeyFrame(0, Animation.ANIMATION_LOOPING);
                case JUMP:
                    return animFaceJump.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
                case HIGHJUMP:
                    return animFaceHighJump.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
                case HIT:
                default:
                    return animFaceDie.getKeyFrame(stateTime, Animation.ANIMATION_NONLOOPING);
            }
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


    private Animation initAnimation(String imgName, int i){
        if(i==7){
            return new Animation(0.2f,
                    Assets.tama1Atlas.createSprite(imgName+1),
                    Assets.tama1Atlas.createSprite(imgName+2),
                    Assets.tama1Atlas.createSprite(imgName+3),
                    Assets.tama1Atlas.createSprite(imgName+4),
                    Assets.tama1Atlas.createSprite(imgName+5),
                    Assets.tama1Atlas.createSprite(imgName+6),
                    Assets.tama1Atlas.createSprite(imgName+7)
            );
        }else {
            return new Animation(0.2f,
                    Assets.tama1Atlas.createSprite(imgName+1),
                    Assets.tama1Atlas.createSprite(imgName+2),
                    Assets.tama1Atlas.createSprite(imgName+3),
                    Assets.tama1Atlas.createSprite(imgName+4)
            );
        }
    }
}
/*/home/artem/projects/TamaJumper/android/assets/forArtem/money-1.png
/home/artem/projects/TamaJumper/android/assets/forArtem/money-2.png
/home/artem/projects/TamaJumper/android/assets/forArtem/money-3.png
/home/artem/projects/TamaJumper/android/assets/forArtem/money-4.png
/home/artem/projects/TamaJumper/android/assets/forArtem/money-5.png
/home/artem/projects/TamaJumper/android/assets/forArtem/money-6.png
/home/artem/projects/TamaJumper/android/assets/forArtem/money-7.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-die-1.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-die-2.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-die-3.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-die-4.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-jump-1.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-jump-2.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-jump-3.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-jump-4.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-jump-5.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-jump-6.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-jump-7.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-super-jump-1.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-super-jump-2.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-super-jump-3.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-super-jump-4.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-super-jump-5.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-super-jump-6.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-super-jump-7.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-jump-1.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-jump-2.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-jump-3.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-jump-4.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-jump-5.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-jump-6.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-jump-7.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-superjump-1.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-superjump-2.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-superjump-3.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-superjump-4.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-superjump-5.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-superjump-6.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-superjump-7.png
/home/artem/projects/TamaJumper/android/assets/forArtem/platform_bad.png
/home/artem/projects/TamaJumper/android/assets/forArtem/platform_motion-.png
/home/artem/projects/TamaJumper/android/assets/forArtem/platform_normal-.png
/home/artem/projects/TamaJumper/android/assets/forArtem/platform_one-off-.png*/