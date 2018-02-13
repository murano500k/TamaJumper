package com.stc.tamajumper;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;

import static com.stc.tamajumper.Config.PIXELS.PLAYER_DIMEN;
import static com.stc.tamajumper.Config.PIXELS.WORLD_WIDTH;

/**
 * Created by artem on 1/31/18.
 */

public class Enemy extends MyActor {
    private static final float ENEMY_MOVING_SPEED = 2;
    public static final float WIDTH = PLAYER_DIMEN*1.5f;
    public static final float HEIGHT = PLAYER_DIMEN;

    public enum Type{
        SQUIRREL
    }


    private Type type;

    private final Action moveAction;
    private final boolean reversed;


    public Enemy(float x, float y, boolean reversed) {
        super(x, y);
        this.reversed=reversed;
        setWidth(WIDTH);
        setHeight(HEIGHT);
        moveAction=ActionManager.initMoveAction(reversed,ENEMY_MOVING_SPEED);
        addAction(moveAction);
    }

    @Override
    public void act(float delta) {
        super.act(delta);


        if (getX() < -getWidth()) setX(WORLD_WIDTH);
        if (getX() > WORLD_WIDTH) setX(-getWidth());
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
        return Assets.squirrelFly.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
    }
}
