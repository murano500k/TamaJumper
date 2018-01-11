package com.stc.tamajumper.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.stc.tamajumper.Config;
import com.stc.tamajumper.utils.Assets;

/**
 * Created by artem on 1/11/18.
 */

public class Background {
    private final TextureRegion textureRegion;
    private Rectangle textureRegionBounds1;
    private Rectangle textureRegionBounds2;
    private int speed = 100;
    private float lastHeight;
    public Background() {
        lastHeight=0;
        textureRegion = Assets.backgroundRegion;
        //textureRegionBounds1 = new Rectangle(0 - Config.VIEWPORT_WIDTH / 2, 0, Config.VIEWPORT_WIDTH, Config.VIEWPORT_HEIGHT);
        //textureRegionBounds2 = new Rectangle(Config.VIEWPORT_WIDTH / 2, 0, Config.VIEWPORT_WIDTH, Config.VIEWPORT_HEIGHT);
        textureRegionBounds1 = new Rectangle(0, 0 - Config.VIEWPORT_HEIGHT / 2, Config.VIEWPORT_WIDTH, Config.VIEWPORT_HEIGHT);
        textureRegionBounds2 = new Rectangle(0, Config.VIEWPORT_HEIGHT / 2, Config.VIEWPORT_WIDTH, Config.VIEWPORT_HEIGHT);
    }
    public void act(float heightSoFar) {
        float delta = heightSoFar-lastHeight;
        lastHeight=heightSoFar;

        if (leftBoundsReached(delta)) {
            resetBounds();
        } else {
            updateYBounds(-delta);
        }
    }
    public void draw(Batch batch) {
        batch.draw(textureRegion, textureRegionBounds1.x, textureRegionBounds1.y, Config.VIEWPORT_WIDTH,
                Config.VIEWPORT_HEIGHT);
        batch.draw(textureRegion, textureRegionBounds2.x, textureRegionBounds2.y, Config.VIEWPORT_WIDTH,
                Config.VIEWPORT_HEIGHT);
    }

    private boolean leftBoundsReached(float delta) {
        return (textureRegionBounds2.y - delta) <= 0;
    }

    private void updateYBounds(float delta) {
        textureRegionBounds1.y += delta;
        textureRegionBounds2.y += delta;
    }

    private void resetBounds() {
        textureRegionBounds1 = textureRegionBounds2;
        textureRegionBounds2 = new Rectangle(0, Config.VIEWPORT_HEIGHT, Config.VIEWPORT_WIDTH, Config.VIEWPORT_HEIGHT);
    }

}
