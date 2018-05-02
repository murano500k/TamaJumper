package com.stc.tamajumper;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class Ufo extends SmartEnemy {
    private static final float DURATION_MOVE_LEFT_TO_RIGHT = 4f;
    private final float seed;
    private boolean started = false;
    boolean reversed;
    public Ufo(float y, boolean r, float seed) {
        super(0, y);
        reversed=r;
        this.seed=seed;
        //akiaddAction(getAction());
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        Sprite sprite =new Sprite(getTexture());
        sprite.setBounds(getX(),getY(),getWidth(),getHeight());
        sprite.flip(reversed,false);
        sprite.draw(batch);
    }
    @Override
    public void act(float delta){
        super.act(delta);
        if(!started && stateTime>=seed){
            started=true;
            addAction(getAction());
        }
        if(stateTime%DURATION_MOVE_LEFT_TO_RIGHT==0) stateTime=0;
    }

    @Override
    public TextureRegion getTexture() {
        return Assets2.getEnemyAnim(Enemy.Type.UFO).getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
    }

    Action getAction(){
        Action sequenceAction;
        if(reversed) sequenceAction=getReversedSequenceAction();
        else  sequenceAction=getSequenceAction();
        RepeatAction repeatAction=new RepeatAction();
        repeatAction.setAction(sequenceAction);

        repeatAction.setCount(RepeatAction.FOREVER);
        return repeatAction;
    }

    Action getReversedSequenceAction(){
        SequenceAction sequenceAction= new SequenceAction();

        MoveToAction moveToActionReturn = new MoveToAction();
        moveToActionReturn.setX(Config.VIEWPORT_WIDTH+getWidth());
        moveToActionReturn.setY(getY());
        moveToActionReturn.setDuration(0);
        sequenceAction.addAction(moveToActionReturn);

        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setX(0-getWidth());
        moveToAction.setY(getY()+Config.VIEWPORT_WIDTH/2);
        moveToAction.setDuration(DURATION_MOVE_LEFT_TO_RIGHT);
        sequenceAction.addAction(moveToAction);

        return sequenceAction;
    }
    Action getSequenceAction(){
        SequenceAction sequenceAction= new SequenceAction();

        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setX(Config.VIEWPORT_WIDTH+getWidth());
        moveToAction.setY(getY()+Config.VIEWPORT_WIDTH/2);
        moveToAction.setDuration(DURATION_MOVE_LEFT_TO_RIGHT);
        sequenceAction.addAction(moveToAction);

        MoveToAction moveToActionReturn = new MoveToAction();
        moveToActionReturn.setX(0-getWidth());
        moveToActionReturn.setY(getY());
        moveToActionReturn.setDuration(0);
        sequenceAction.addAction(moveToActionReturn);

        return sequenceAction;
    }


}
