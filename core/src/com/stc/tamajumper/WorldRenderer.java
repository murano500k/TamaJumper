package com.stc.tamajumper;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stc.tamajumper.objects.Platform;
import com.stc.tamajumper.objects.Tamada;
import com.stc.tamajumper.utils.Animation;
import com.stc.tamajumper.utils.Assets;

/**
 * Created by artem on 1/11/18.
 */
public class WorldRenderer {
    static final int FRUSTUM_WIDTH = 10;
    static final int FRUSTUM_HEIGHT = 15;
    World world;
    OrthographicCamera cam;
    SpriteBatch batch;

    public WorldRenderer (SpriteBatch batch, World world) {
        this.world = world;
        this.cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        this.cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
        this.batch = batch;

    }

    public void render() {
        if (world.tamada.position.y > cam.position.y) cam.position.y = world.tamada.position.y;
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        renderBackground();
        renderObjects();
    }


    private void renderBackground() {
        System.out.println("renderBg: cam.x="+cam.position.x+" cam.y="+cam.position.y);

        batch.disableBlending();
        batch.begin();
        world.bg.draw(batch);
        //batch.draw(Assets.background, 0, cam.position.y-FRUSTUM_HEIGHT/2, 0, 0, 700, 1000);
        batch.end();
    }


    public void renderObjects () {
        batch.enableBlending();
        batch.begin();
        renderTamada();
        renderPlatforms();

        batch.end();
    }

    private void renderPlatforms() {
        int len = world.platforms.size();
        for (int i = 0; i < len; i++) {
            Platform platform = world.platforms.get(i);
            TextureRegion keyFrame = Assets.platform;
            if (platform.state == Platform.PLATFORM_STATE_PULVERIZING) {
                keyFrame = Assets.brakingPlatform.getKeyFrame(platform.stateTime, Animation.ANIMATION_NONLOOPING);
            }

            batch.draw(keyFrame, platform.position.x - 1, platform.position.y - 0.25f, 2, 0.5f);
        }
    }

    private void renderTamada() {
        TextureRegion keyFrame;
        switch (world.tamada.state) {
            case Tamada.TAMADA_STATE_FALL:
                keyFrame = Assets.tamadaFall.getKeyFrame(world.tamada.stateTime, Animation.ANIMATION_LOOPING);
                break;
            case Tamada.TAMADA_STATE_JUMP:
                keyFrame = Assets.tamadaJump.getKeyFrame(world.tamada.stateTime, Animation.ANIMATION_LOOPING);
                break;
            case
                    Tamada.TAMADA_STATE_HIT:
            default:
                keyFrame = Assets.tamadaHit;
        }

        float side = world.tamada.velocity.x < 0 ? -1 : 1;
        if (side < 0)
            batch.draw(keyFrame, world.tamada.position.x + 0.5f, world.tamada.position.y - 0.5f, side * 1, 1);
        else
            batch.draw(keyFrame, world.tamada.position.x - 0.5f, world.tamada.position.y - 0.5f, side * 1, 1);
    }
}
