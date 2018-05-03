package com.stc.tamajumper;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;

import java.util.Random;

import static com.stc.tamajumper.Config.PIXELS.PLAYER_DIMEN;
import static com.stc.tamajumper.Config.PIXELS.WORLD_WIDTH;

public class BgPlanet extends MyActor {
    private static final float BG_DEFAULT_MOVING_SPEED = PLAYER_DIMEN*2;

    public  final int type;
    private final boolean reversed;


    public static BgPlanet createBgPlanet(float y){
        Random r= new Random();
        int type= r.nextInt(Assets.bgPlanets.size());
        float x = r.nextInt((int)WORLD_WIDTH);
        return new BgPlanet(x,y,type, r.nextBoolean());
    }

    public BgPlanet(float x, float y, int type, boolean reversed) {
        super(x, y);
        this.reversed=reversed;
        this.type=type;
        setWidth(getTexture().getRegionWidth());
        setHeight(getTexture().getRegionHeight());
        addAction(getMoveAction(reversed,
                BG_DEFAULT_MOVING_SPEED * (Assets.bgPlanets.size()-type)/ Assets.bgPlanets.size()));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (getX() < -getWidth()) setX(Config.VIEWPORT_WIDTH);
        if (getX() > Config.VIEWPORT_WIDTH) setX(-getWidth());
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
        return Assets.getBgPlanetTexture(type);
    }


    public Action getMoveAction(boolean reverse, float speed){
        float duration=WORLD_WIDTH/4/speed;
        MoveByAction moveByAction = new MoveByAction();
        moveByAction.setDuration(duration);
        if(reverse) moveByAction.setAmountX(-WORLD_WIDTH/4);
        else moveByAction.setAmountX(WORLD_WIDTH/4);
        RepeatAction repeatAction=new RepeatAction();
        repeatAction.setAction(moveByAction);
        repeatAction.setCount(RepeatAction.FOREVER);
        return repeatAction;
    }

}
