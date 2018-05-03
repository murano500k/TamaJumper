package com.stc.tamajumper;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

import static com.stc.tamajumper.Config.PIXELS.WORLD_WIDTH;

public class BgStar extends MyActor {
    public  final int type;

    public BgStar(float x, float y, int type) {
        super(x, y);
        this.type=type;
        setWidth(getTexture().getRegionWidth());
        setHeight(getTexture().getRegionHeight());
    }

    public static BgStar createBgStar(float y){
        Random r= new Random();
        int type= r.nextInt(Assets.bgStars.size());
        float x = r.nextInt((int)(WORLD_WIDTH)) *1f;
        return new BgStar(x,y,type);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(getTexture(),getX(),getY() );
    }

    @Override
    public TextureRegion getTexture() {
        return Assets.getBgStarTexture(type);
    }
}
