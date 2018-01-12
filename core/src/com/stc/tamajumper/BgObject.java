package com.stc.tamajumper;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.stc.tamajumper.Config.*;
import static com.stc.tamajumper.Config.PIXELS.*;


public class BgObject extends DynamicGameObject {
    private final float movingSpeed;
    public final float size;
    public final TextureRegion texture;
    public float jumpRatio;




    public BgObject(float size, float x, float y, float movingSpeed) {
        super(x, y, size,size);
        this.size = size;
        this.movingSpeed=movingSpeed;
        this.texture=Assets.getBgObjectTexture(size);
    }

    public void update(float deltaTime, float accelX, float deltaY) {

        velocity.x=-accelX / 10 * BG_OBJECT_MOVE_VELOCITY+movingSpeed*size*BG_OBJECT_MOVE_RATIO;

        velocity.y=deltaY/deltaTime*size*BG_OBJECT_MOVE_RATIO;
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);

        bounds.x = position.x - bounds.width / 2;
        bounds.y = position.y - bounds.height / 2;


            position.add(velocity.x * deltaTime, 0);
            bounds.x = position.x - PLATFORM_WIDTH / 2;
            bounds.y = position.y - PLATFORM_HEIGHT / 2;

        if (position.x < -size) position.x = WORLD_WIDTH+WORLD_WIDTH+size;
        if (position.x > WORLD_WIDTH+size) position.x = -size;

    }




}
