package com.stc.tamajumper;

import com.badlogic.gdx.math.Vector2;
import com.stc.tamajumper.objects.Background;
import com.stc.tamajumper.objects.ParallaxBackground;
import com.stc.tamajumper.objects.ParallaxLayer;
import com.stc.tamajumper.objects.Platform;
import com.stc.tamajumper.objects.Tamada;
import com.stc.tamajumper.utils.Assets;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.stc.tamajumper.WorldRenderer.FRUSTUM_HEIGHT;
import static com.stc.tamajumper.WorldRenderer.FRUSTUM_WIDTH;


/**
 * Created by artem on 1/11/18.
 */

public class World {


    public static final float WORLD_WIDTH = FRUSTUM_WIDTH;
    public static final float LEVEL_HEIGHT_SCREENS = 20;
    public static final float WORLD_HEIGHT = FRUSTUM_HEIGHT * LEVEL_HEIGHT_SCREENS;

    public static final int WORLD_STATE_RUNNING = 0;
    public static final int WORLD_STATE_NEXT_LEVEL = 1;
    public static final int WORLD_STATE_GAME_OVER = 2;




    public final WorldListener listener;
    public static final Vector2 gravity = new Vector2(0, -12);

    public final Tamada tamada;
    public final List<Platform> platforms;
    public final Background bg;
    public final Random rand;

    public float heightSoFar;
    public int score;
    public int state;


    public interface WorldListener {
        public void jump ();

        public void highJump ();

        public void hit ();

        public void coin ();
    }




    public World (WorldListener listener) {
        this.listener = listener;
        this.tamada = new Tamada(5, 1);
        this.platforms = new ArrayList<Platform>();
        bg=new Background();
        rand = new Random();
        generateLevel();

        this.heightSoFar = 0;
        this.score = 0;
        this.state = WORLD_STATE_RUNNING;
    }

    private void generateLevel() {
        System.out.println("generate level:");
        float y = Platform.PLATFORM_HEIGHT / 2;
        float maxJumpHeight = Tamada.TAMADA_JUMP_VELOCITY * Tamada.TAMADA_JUMP_VELOCITY / (2 * -gravity.y);

        while (y < WORLD_HEIGHT - WORLD_WIDTH / 2) {
            int type = rand.nextFloat() > 0.8f ? Platform.PLATFORM_TYPE_MOVING : Platform.PLATFORM_TYPE_STATIC;
            float x = rand.nextFloat() * (WORLD_WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;
            System.out.println("add platform: x="+x+", y="+y);
            Platform platform = new Platform(type, x, y);
            platforms.add(platform);

            y += (maxJumpHeight - 0.5f);
            y -= rand.nextFloat() * (maxJumpHeight / 3);
        }
    }

    public void update (float deltaTime, float accelX) {
        updateTama(deltaTime, accelX);
        updateBg(heightSoFar);
        updatePlatforms(deltaTime);
        if (tamada.state != Tamada.TAMADA_STATE_HIT) checkCollisions();
        checkGameOver();
    }

    private void updateBg(float heightSoFar) {
        bg.act(heightSoFar);
    }

    private void checkCollisions() {
        checkPlatformCollisions();
    }

    private void updateTama(float deltaTime, float accelX) {
        if (tamada.state != Tamada.TAMADA_STATE_HIT && tamada.position.y <= 0.5f) tamada.hitPlatform();
        if (tamada.state != Tamada.TAMADA_STATE_HIT) tamada.velocity.x = -accelX / 10 * Tamada.TAMADA_MOVE_VELOCITY;
        tamada.update(deltaTime);
        heightSoFar = Math.max(tamada.position.y, heightSoFar);
    }

    private void updatePlatforms (float deltaTime) {
        int len = platforms.size();
        for (int i = 0; i < len; i++) {
            Platform platform = platforms.get(i);
            platform.update(deltaTime);
            if (platform.state == Platform.PLATFORM_STATE_PULVERIZING && platform.stateTime > Platform.PLATFORM_PULVERIZE_TIME) {
                platforms.remove(platform);
                len = platforms.size();
            }
        }
    }

    private void checkGameOver () {
        if (heightSoFar - 7.5f > tamada.position.y) {
            state = WORLD_STATE_GAME_OVER;
        }
    }
    private void checkPlatformCollisions () {
        if (tamada.velocity.y > 0) return;

        int len = platforms.size();
        for (int i = 0; i < len; i++) {
            Platform platform = platforms.get(i);
            if (tamada.position.y > platform.position.y) {
                if (tamada.bounds.overlaps(platform.bounds)) {
                    score += Config.PLATFORM_SCORE;
                    tamada.hitPlatform();
                    listener.jump();
                    if (rand.nextFloat() > 0.5f) {
                        platform.pulverize();
                    }
                    break;
                }
            }
        }
    }

    }
