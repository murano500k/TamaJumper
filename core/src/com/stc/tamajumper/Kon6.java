package com.stc.tamajumper;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by artem on 2/19/18.
 */

public class Kon6 extends MyActor {



    public Kon6(float x, float y, float width, float height) {
        super(x, y);
        setWidth(width);
        setHeight(height);
    }

    @Override
    public TextureRegion getTexture() {
        return Assets2.textureKon6;
    }
}
