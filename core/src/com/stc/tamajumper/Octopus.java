package com.stc.tamajumper;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class Octopus extends SmartEnemy {
    private static final float MAX_FALL_VELOCITY = 300f;
    private static final float DURATION_MOVE_LEFT_TO_RIGHT = 5f;
    boolean reversed;
    Tama tama;
    public Octopus(float y, boolean r,Tama tama) {
        super(0, y);
        this.tama=tama;
        reversed=r;
        addAction(getAction());
    }

    private Action getAction() {
        SequenceAction sequenceAction = new SequenceAction();

        MoveByAction moveActionReturn = new MoveByAction();
        moveActionReturn.setAmountX(Config.VIEWPORT_WIDTH);
        moveActionReturn.setDuration(DURATION_MOVE_LEFT_TO_RIGHT);
        sequenceAction.addAction(moveActionReturn);



        MoveByAction moveAction = new MoveByAction();
        moveAction.setAmountX(-Config.VIEWPORT_WIDTH);
        moveAction.setDuration(DURATION_MOVE_LEFT_TO_RIGHT);
        sequenceAction.addAction(moveAction);


        RepeatAction repeatAction=new RepeatAction();
        repeatAction.setAction(sequenceAction);

        repeatAction.setCount(RepeatAction.FOREVER);
        return repeatAction;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        Sprite sprite =new Sprite(getTexture());
        sprite.setBounds(getX(),getY(),getWidth(),getHeight());
        sprite.flip(reversed,false);
        sprite.draw(batch);
    }

    @Override
    public TextureRegion getTexture() {
        return Assets2.getEnemyAnim(Enemy.Type.OCTOPUS).getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
    }
    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        if(getY()<tama.getY()-Config.VIEWPORT_HEIGHT){
            setY(Config.PIXELS.WORLD_HEIGHT);
        }
        if(tama.isGameStarted()) {
            if(velocity.y>=-MAX_FALL_VELOCITY){
                velocity.add(0, Config.GRAVITY.y / 2 * deltaTime);
            }
            MoveByAction currentAction = new MoveByAction();
            currentAction.setDuration(deltaTime);
            currentAction.setAmount(velocity.x * deltaTime,velocity.y * deltaTime);
            addAction(currentAction);
        }
    }
     public void hitPlatform(){
        velocity.y=0;
     }

}
