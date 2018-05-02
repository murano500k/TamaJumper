package com.stc.tamajumper;

import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;

import static com.stc.tamajumper.Config.PIXELS.WORLD_WIDTH;

/**
 * Created by artem on 1/31/18.
 */

public class ActionManager {

    public static RepeatAction initMoveAction(boolean reverse, float speed){
        float duration=WORLD_WIDTH*2/speed;
        MoveByAction moveByAction = new MoveByAction();
        moveByAction.setDuration(duration);
        moveByAction.setAmountX(WORLD_WIDTH*1.5f);
        moveByAction.setReverse(reverse);
        RepeatAction repeatAction=new RepeatAction();
        repeatAction.setAction(moveByAction);
        repeatAction.setCount(RepeatAction.FOREVER);
        return repeatAction;
    }
}
