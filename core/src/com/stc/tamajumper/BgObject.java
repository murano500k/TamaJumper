package com.stc.tamajumper;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;

import java.util.Random;

import static com.stc.tamajumper.Config.PIXELS.PLAYER_DIMEN;
import static com.stc.tamajumper.Config.PIXELS.WORLD_WIDTH;

public class BgObject extends MyActor {
    private static final float BG_DEFAULT_MOVING_SPEED = PLAYER_DIMEN*2;

    public  final int type;

    private Action moveAction;
    private final boolean reversed;


    public static BgObject createBgObject(float y){
        Random r= new Random();
        int type= r.nextInt(Assets2.bgObjects.size());
        float width = Assets2.getBgObjectTexture(type).getRegionWidth();
        float x = r.nextInt((int)(WORLD_WIDTH/width)) *width;
        return new BgObject(x,y,type, r.nextBoolean());
    }

    public BgObject(float x, float y, int type, boolean reversed) {
        super(x, y);
        this.reversed=reversed;
        this.type=type;
        setWidth(getTexture().getRegionWidth());
        setHeight(getTexture().getRegionHeight());
        moveAction = ActionManager.initMoveAction(reversed,
                BG_DEFAULT_MOVING_SPEED * (Assets2.bgObjects.size()-type)/Assets2.bgObjects.size());
        addAction(moveAction);
    }

    @Override
    public void act(float delta) {
        super.act(delta);


        if (getX() < -getWidth()*1.5f) setX(WORLD_WIDTH);
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
        return Assets2.getBgObjectTexture(type);
    }

}
