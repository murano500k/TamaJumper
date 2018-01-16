package com.stc.tamajumper;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

import static com.stc.tamajumper.Config.*;
import static com.stc.tamajumper.Config.PIXELS.*;

/**
 * Created by artem on 1/11/18.
 */
public class WorldRenderer {
    World world;
    OrthographicCamera cam;
    SpriteBatch batch;
    Random random;
    public WorldRenderer (SpriteBatch batch, World world) {
        this.world = world;
        this.cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        this.cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
        this.batch = batch;
        random=new Random();
    }

    public void render() {
        if (world.tama.position.y > cam.position.y) cam.position.y = world.tama.position.y;
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        renderBackground();
        renderObjects();
    }


    private void renderBackground() {
        batch.disableBlending();
        batch.begin();
        batch.draw(Assets.backgroundRegion, cam.position.x - FRUSTUM_WIDTH / 2, cam.position.y - FRUSTUM_HEIGHT / 2-PLATFORM_HEIGHT, FRUSTUM_WIDTH,
                FRUSTUM_HEIGHT);
        batch.end();
    }

    private void renderBgObjects() {
        int len = world.bgObjects.size();
        for (int i = 0; i < len; i++) {
            BgObject bgObject = world.bgObjects.get(i);
            Sprite sprite= new Sprite(bgObject.texture);
            sprite.setAlpha(bgObject.alpha);
            sprite.flip(bgObject.flipped,false);
            sprite.setBounds(bgObject.position.x , bgObject.position.y , bgObject.bounds.width, bgObject.bounds.height);
            sprite.draw(batch );
        }
    }


    public void renderObjects () {
        batch.enableBlending();
        batch.begin();
        renderBgObjects();
        renderPlatforms();
        renderItems ();
        renderSquirrels();
        renderTamada();
        renderCastle();
        batch.end();
    }

    private void renderCastle() {
        Castle castle = world.castle;
        batch.draw(Assets.castle, castle.position.x - 1, castle.position.y - 1, 2, 2);
    }

    private void renderPlatforms() {
        int len = world.platforms.size();
        for (int i = 0; i < len; i++) {
            Platform platform = world.platforms.get(i);
            TextureRegion keyFrame = Assets.platform;
            if (platform.state == PLATFORM_STATE_PULVERIZING) {
                keyFrame = Assets.brakingPlatform.getKeyFrame(platform.stateTime, Animation.ANIMATION_NONLOOPING);
            }

            batch.draw(keyFrame, platform.position.x - 1, platform.position.y - 0.25f, 2, 0.5f);
        }
    }

    private void renderTamada() {
        TextureRegion keyFrame;
        switch (world.tama.state) {
            case TAMADA_STATE_FALL:
                keyFrame = Assets.tamadaFall.getKeyFrame(world.tama.stateTime, Animation.ANIMATION_LOOPING);
                break;
            case TAMADA_STATE_JUMP:
                keyFrame = Assets.tamadaJump.getKeyFrame(world.tama.stateTime, Animation.ANIMATION_LOOPING);
                break;
            case
                    TAMADA_STATE_HIT:
            default:
                keyFrame = Assets.tamadaHit;
        }

        float side = world.tama.velocity.x < 0 ? -1 : 1;
        if (side < 0)
            batch.draw(keyFrame, world.tama.position.x + 0.5f, world.tama.position.y - 0.5f, side * 1, 1);
        else
            batch.draw(keyFrame, world.tama.position.x - 0.5f, world.tama.position.y - 0.5f, side * 1, 1);
    }

    private void renderItems () {
        int len = world.springs.size();
        for (int i = 0; i < len; i++) {
            Spring spring = world.springs.get(i);
            batch.draw(Assets.spring, spring.position.x - 0.5f, spring.position.y - 0.5f, 1, 1);
        }

        len = world.coins.size();
        for (int i = 0; i < len; i++) {
            Coin coin = world.coins.get(i);
            TextureRegion keyFrame = Assets.coinAnim.getKeyFrame(coin.stateTime, Animation.ANIMATION_LOOPING);
            batch.draw(keyFrame, coin.position.x - 0.5f, coin.position.y - 0.5f, 1, 1);
        }
    }

    private void renderSquirrels () {
        int len = world.squirrels.size();
        for (int i = 0; i < len; i++) {
            Squirrel squirrel = world.squirrels.get(i);
            TextureRegion keyFrame = Assets.squirrelFly.getKeyFrame(squirrel.stateTime, Animation.ANIMATION_LOOPING);
            float side = squirrel.velocity.x < 0 ? -1 : 1;
            if (side < 0)
                batch.draw(keyFrame, squirrel.position.x + 0.5f, squirrel.position.y - 0.5f, side * 1, 1);
            else
                batch.draw(keyFrame, squirrel.position.x - 0.5f, squirrel.position.y - 0.5f, side * 1, 1);
        }
    }
}
