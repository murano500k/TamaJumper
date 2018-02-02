package com.stc.tamajumper;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.stc.tamajumper.Config.PIXELS.PLAYER_DIMEN;

/**
 * Created by artem on 2/2/18.
 */

public class LevelEnd extends MyActor{

    public static final float WIDTH = PLAYER_DIMEN*2;
    public static final float HEIGHT = PLAYER_DIMEN*2;


    public LevelEnd(float x, float y) {
        super(x, y);
        setWidth(WIDTH);
        setHeight(HEIGHT);
    }

    @Override
    public TextureRegion getTexture() {
        return Assets.castle;
    }
}
