package com.stc.tamajumper;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Flower extends SmartEnemy {


    private Platform platform;


    public Flower(Platform platform) {
        super(platform.getX(),platform.getY());
        this.platform=platform;
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(getTexture(),platform.getX()+platform.getWidth()/4,platform.getTop());

    }

    @Override
    public TextureRegion getTexture() {
        if(platform.getState()==ObjectState.EXIST){
            return Assets2.getEnemyAnim(Enemy.Type.FLOWER).getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
        } else {
            return Assets2.getEnemyAnim(Enemy.Type.FLOWER).getKeyFrame(stateTime, Animation.ANIMATION_NONLOOPING);
        }
    }
}
