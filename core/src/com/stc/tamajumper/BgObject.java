package com.stc.tamajumper;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

import static com.stc.tamajumper.Config.BG_OBJECT_MOVE_RATIO;
import static com.stc.tamajumper.Config.BG_OBJECT_MOVE_VELOCITY;
import static com.stc.tamajumper.Config.PIXELS.WORLD_WIDTH;


public class BgObject extends DynamicGameObject {
    private final float movingSpeed;
    public final float size;
    public final TextureRegion texture;
    public float alpha;
    public boolean flipped;


    public BgObject(float size, float x, float y, float movingSpeed, TextureRegion textureRegion) {
        super(x, y, size,size);
        this.size = size;
        if(size==1)alpha=0.1f;
        else if(size==2)alpha=0.2f;
        else alpha=0.3f;
        this.movingSpeed=movingSpeed;
        this.texture=textureRegion;
        this.flipped=new Random().nextBoolean();
    }

    @Override
    void update(float deltaTime) {

    }

    public void update(float deltaTime, float accelX, float deltaY) {

        velocity.x=-accelX / 10 * BG_OBJECT_MOVE_VELOCITY+movingSpeed*size*BG_OBJECT_MOVE_RATIO;

        velocity.y=deltaY/deltaTime*size*BG_OBJECT_MOVE_RATIO;
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);

        bounds.x = position.x - bounds.width / 2;
        bounds.y = position.y - bounds.height / 2;


            position.add(velocity.x * deltaTime, 0);
            bounds.x = position.x - bounds.width / 2;
            bounds.y = position.y - bounds.height / 2;

        if (position.x < -size) position.x = WORLD_WIDTH+WORLD_WIDTH+size;
        if (position.x > WORLD_WIDTH+size) position.x = -size;

    }




}
