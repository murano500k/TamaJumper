package com.stc.tamajumper;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import static com.stc.tamajumper.Config.*;
import static com.stc.tamajumper.Config.PIXELS.*;

/**
 * Created by artem on 1/11/18.
 */

public class World {






    public final WorldListener listener;
    public static final Vector2 gravity = new Vector2(0, -12);

    public final Tamada tamada;
    public final List<Platform> platforms;
    public final List<BgObject> bgObjects;

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
        this.bgObjects = new ArrayList<BgObject>();
        rand = new Random();
        generateLevel();

        this.heightSoFar = 0;
        this.score = 0;
        this.state = WORLD_STATE_RUNNING;
    }

    private void generateLevel() {
        System.out.println("generate level:");
        generatePlatforms();
        generateBgObjects();
    }

    private void generateBgObjects() {
        float y = PLATFORM_HEIGHT / 2;
        float maxJumpHeight = FRUSTUM_HEIGHT/4;

        while (y < WORLD_HEIGHT - WORLD_WIDTH / 2) {

            int type = rand.nextInt(BG_OBJECTS_TYPES_COUNT)+1;
            float size = type;
            if(type==3) size=4;
            float x = rand.nextFloat() * (WORLD_WIDTH - type) + type / 2;
            BgObject bgObject = new BgObject(size, x, y, getRandomBgObjectVelocity());
            bgObjects.add(bgObject);

            y += (maxJumpHeight - 0.5f);
            y -= rand.nextFloat() * (maxJumpHeight / 3);
        }
    }

    private float getRandomBgObjectVelocity() {
        int randomNumber=rand.nextInt(3);
        switch (randomNumber){
            case 0:
                return  0;
            case 1:
                return BG_OBJECT_MOVE_VELOCITY;
            case 2:
                return -BG_OBJECT_MOVE_VELOCITY;
        }
        return 0;
    }

    private void generatePlatforms() {
        float y = PLATFORM_HEIGHT / 2;
        float maxJumpHeight = TAMADA_JUMP_VELOCITY * TAMADA_JUMP_VELOCITY / (2 * -gravity.y);


        while (y < WORLD_HEIGHT - WORLD_WIDTH / 2) {
            int type = rand.nextFloat() > 0.8f ? PLATFORM_TYPE_MOVING : PLATFORM_TYPE_STATIC;
            float x = rand.nextFloat() * (WORLD_WIDTH - PLATFORM_WIDTH) + PLATFORM_WIDTH / 2;
            System.out.println("add platform: x="+x+", y="+y);
            Platform platform = new Platform(type, x, y);
            platforms.add(platform);

            y += (maxJumpHeight - 0.5f);
            y -= rand.nextFloat() * (maxJumpHeight);
        }
    }

    public void update (float deltaTime, float accelX) {
        float lastH=heightSoFar;
        updateTama(deltaTime, accelX);
        float deltaH=heightSoFar-lastH;
        if(heightSoFar<FRUSTUM_HEIGHT/2) deltaH=0;
        updateBgObjects(deltaTime, accelX, deltaH);
        updatePlatforms(deltaTime);
        if (tamada.state != TAMADA_STATE_HIT) checkCollisions();
        checkGameOver();
    }

    private void updateBgObjects(float deltaTime, float accelX, float deltaY) {
        for (BgObject bgObject: bgObjects) {
            bgObject.update(deltaTime, accelX, deltaY);
        }
    }

    private void checkCollisions() {
        checkPlatformCollisions();
    }

    private void updateTama(float deltaTime, float accelX) {
        if (tamada.state != TAMADA_STATE_HIT && tamada.position.y <= 0.5f) tamada.hitPlatform();
        if (tamada.state != TAMADA_STATE_HIT) tamada.velocity.x = -accelX / 10 * TAMADA_MOVE_VELOCITY;
        tamada.update(deltaTime);
        heightSoFar = Math.max(tamada.position.y, heightSoFar);
    }

    private void updatePlatforms(float deltaTime) {
        int len = platforms.size();
        for (int i = 0; i < len; i++) {
            Platform platform = platforms.get(i);
            platform.update(deltaTime);
            if (platform.state == PLATFORM_STATE_PULVERIZING && platform.stateTime > PLATFORM_PULVERIZE_TIME) {
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
