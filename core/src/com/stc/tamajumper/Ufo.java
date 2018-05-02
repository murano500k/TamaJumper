package com.stc.tamajumper;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class Ufo extends SmartEnemy {
    boolean reversed;
    public Ufo(float y, boolean r) {
        super(0, y);
        reversed=r;
        addAction(getAction());
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
        moveToActionReturn.setX(Config.VIEWPORT_WIDTH);
        moveToActionReturn.setY(getY());
        moveToActionReturn.setDuration(0);
        sequenceAction.addAction(moveToActionReturn);

        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setX(0);
        moveToAction.setY(getY()+Config.VIEWPORT_WIDTH/2);
        moveToAction.setDuration(4);
        sequenceAction.addAction(moveToAction);

        return sequenceAction;
    }
    Action getSequenceAction(){
        SequenceAction sequenceAction= new SequenceAction();

        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setX(Config.VIEWPORT_WIDTH);
        moveToAction.setY(getY()+Config.VIEWPORT_WIDTH/2);
        moveToAction.setDuration(4);
        sequenceAction.addAction(moveToAction);

        MoveToAction moveToActionReturn = new MoveToAction();
        moveToActionReturn.setX(0);
        moveToActionReturn.setY(getY());
        moveToActionReturn.setDuration(0);
        sequenceAction.addAction(moveToActionReturn);

        return sequenceAction;
    }


}
