package com.stc.tamajumper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.stc.tamajumper.Config.BG_OBJECTS_TYPES_COUNT;
import static com.stc.tamajumper.Config.BG_OBJECT_MOVE_VELOCITY;
import static com.stc.tamajumper.CoinActor.HEIGHT;
import static com.stc.tamajumper.Config.PIXELS.FRUSTUM_HEIGHT;
import static com.stc.tamajumper.Config.PIXELS.WORLD_HEIGHT;
import static com.stc.tamajumper.Config.PIXELS.WORLD_WIDTH;
import static com.stc.tamajumper.Config.PLATFORM_HEIGHT;
import static com.stc.tamajumper.Config.PLATFORM_PULVERIZE_TIME;
import static com.stc.tamajumper.Config.PLATFORM_STATE_PULVERIZING;
import static com.stc.tamajumper.Config.PLATFORM_TYPE_MOVING;
import static com.stc.tamajumper.Config.PLATFORM_TYPE_STATIC;
import static com.stc.tamajumper.Config.PLATFORM_WIDTH;
import static com.stc.tamajumper.TamaActor.JUMP_VELOCITY;
import static com.stc.tamajumper.TamaActor.MOVE_VELOCITY;
import static com.stc.tamajumper.Config.TAMADA_STATE_HIT;
import static com.stc.tamajumper.Config.WORLD_STATE_GAME_OVER;
import static com.stc.tamajumper.Config.WORLD_STATE_NEXT_LEVEL;
import static com.stc.tamajumper.Config.WORLD_STATE_RUNNING;

/**
 * Created by artem on 1/11/18.
 */

public class World {






    public final WorldListener listener;

    public final Tamada tama;
    public final List<Platform> platforms;
    public final List<BgObject> bgObjects;

    public final Random rand;
    private TamaJumperGame game;

    public float heightSoFar;
    public int score;
    public int state;
    public Castle castle;
    public List<Coin> coins;
    public List<Spring> springs;
    public List<Squirrel> squirrels;



    public interface WorldListener {

        public void jump (Platform platform);

        public void highJump ();

        public void hit ();

        public void coin ();
    }




    public World (WorldListener listener, TamaJumperGame game) {
        this.game = game;
        this.listener = listener;
        this.tama = new Tamada(5, 1);
        this.platforms = new ArrayList<Platform>();
        this.bgObjects = new ArrayList<BgObject>();
        this.coins = new ArrayList<>();
        this.springs = new ArrayList<>();
        this.squirrels = new ArrayList<>();
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
            BgObject bgObject = new BgObject(size, x, y, getRandomBgObjectVelocity(), game.getAssets().getBgObjectTexture(size));
            bgObjects.add(bgObject);

            y += (maxJumpHeight - 0.5f);
            y -= rand.nextFloat() * (maxJumpHeight / 3);
        }
        castle = new Castle(WORLD_WIDTH / 2, y);

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
        float maxJumpHeight = JUMP_VELOCITY * JUMP_VELOCITY / (2 * -Config.gravity.y);
 

        while (y < WORLD_HEIGHT - WORLD_WIDTH / 2) {
            int type = rand.nextFloat() > Config.PLATFORM_RATIO_MOVING_TO_STATIC ? PLATFORM_TYPE_MOVING : PLATFORM_TYPE_STATIC;
            float x = rand.nextFloat() * (WORLD_WIDTH - PLATFORM_WIDTH) + PLATFORM_WIDTH / 2;
            boolean breakable = rand.nextBoolean();
            //System.out.println("add platform: x="+x+", y="+y);
            Platform platform = new Platform(breakable, type, x, y);


            if (y > WORLD_HEIGHT / 5 && rand.nextFloat() > (1-Config.ENEMY_GENERATION_PROBABILITY)) {
                Squirrel squirrel = new Squirrel(platform.position.x + rand.nextFloat(), platform.position.y
                        + Squirrel.SQUIRREL_HEIGHT + rand.nextFloat() * 2);
                squirrels.add(squirrel);
            }


            if (rand.nextFloat() > (1-Config.SPRING_GENERATION_PROBABILITY) && type != Config.PLATFORM_TYPE_MOVING
                    && !breakable) {
                Spring spring = new Spring(platform.position.x, platform.position.y + Config.PLATFORM_HEIGHT / 2
                        + Spring.SPRING_HEIGHT / 2);
                springs.add(spring);
                platform.setSpring(spring);
            }

            platforms.add(platform);

            if (rand.nextFloat() > (1-Config.COIN_GENERATION_PROBABILITY)) {
                Coin coin = new Coin(platform.position.x + rand.nextFloat(), platform.position.y + HEIGHT
                        + rand.nextFloat() * 3);
                coins.add(coin);
            }

            y += (maxJumpHeight*0.9f - 0.5f);
            float levelProgressDifficulty=(WORLD_HEIGHT-y)/WORLD_HEIGHT;
            System.out.println("levelProgressDifficulty="+levelProgressDifficulty);
            float randomSeed= rand.nextFloat();

            if(randomSeed<0.5f) randomSeed*=2;
            else if(randomSeed<0.75f)randomSeed/=1.2f;
            else randomSeed/=2;
            y -= (randomSeed) * maxJumpHeight*levelProgressDifficulty;
        }
    }

    public void update (float deltaTime, float accelX) {
        accelX = updateAccelValue(accelX);
        float lastH=heightSoFar;
        updateTama(deltaTime, accelX);
        float deltaH=heightSoFar-lastH;
        if(heightSoFar<FRUSTUM_HEIGHT/2) deltaH=0;
        updateBgObjects(deltaTime, accelX, deltaH);
        updatePlatforms(deltaTime);
        updateCoins(deltaTime);
        updateSquirrels(deltaTime);

        if (tama.state != TAMADA_STATE_HIT) checkCollisions();
        checkGameOver();
    }

    private float updateAccelValue(float accelX) {
        accelX*=game.getPreferences().getAccelSensitivity();
        return accelX;
    }

    private void updateSquirrels(float deltaTime) {
        int len = squirrels.size();
        for (int i = 0; i < len; i++) {
            Squirrel squirrel = squirrels.get(i);
            squirrel.update(deltaTime);
        }

    }


    private void updateCoins(float deltaTime) {
        int len = coins.size();
        for (int i = 0; i < len; i++) {
            Coin coin = coins.get(i);
            coin.update(deltaTime);
        }
    }

    private void updateBgObjects(float deltaTime, float accelX, float deltaY) {
        for (BgObject bgObject: bgObjects) {
            bgObject.update(deltaTime, accelX, deltaY);
        }
    }

    private void checkCollisions() {
        checkSquirrelCollisions();
        checkPlatformCollisions();
        checkCastleCollisions();
        checkItemCollisions ();

    }

    private void checkSquirrelCollisions() {
        int len = squirrels.size();
        for (int i = 0; i < len; i++) {
            Squirrel squirrel = squirrels.get(i);
            if (squirrel.bounds.overlaps(tama.bounds)) {
                tama.hitEnemy();
                state = WORLD_STATE_GAME_OVER;
            }
        }
    }

    private void checkCastleCollisions() {
        if (castle.bounds.overlaps(tama.bounds)) {
            state = WORLD_STATE_NEXT_LEVEL;
        }
    }

    private void checkPlatformCollisions () {
        if (tama.velocity.y > 0) return;

        int len = platforms.size();
        for (int i = 0; i < len; i++) {
            Platform platform = platforms.get(i);
            if (tama.position.y > platform.position.y) {
                if (tama.bounds.overlaps(platform.bounds)) {
                    score += Config.PLATFORM_SCORE;
                    tama.hitPlatform();
                    if(platform.getSpring()==null){
                        listener.jump(platform);
                    }
                    if (platform.canBreak) {
                        platform.pulverize();
                    }
                    break;
                }
            }
        }
    }
    private void checkItemCollisions () {
        int len = coins.size();
        for (int i = 0; i < len; i++) {
            Coin coin = coins.get(i);
            if (tama.bounds.overlaps(coin.bounds)) {
                coins.remove(coin);
                len = coins.size();
                listener.coin();
                score += CoinActor.COIN_SCORE;
            }

        }

        if (tama.velocity.y > 0) return;

        len = springs.size();
        for (int i = 0; i < len; i++) {
            Spring spring = springs.get(i);
            if (tama.position.y > spring.position.y) {
                if (tama.bounds.overlaps(spring.bounds)) {
                    tama.hitSpring();
                    listener.highJump();
                }
            }
        }

    }


    private void updateTama(float deltaTime, float accelX) {
        if (tama.state != TAMADA_STATE_HIT && tama.position.y <= 0.5f) tama.hitPlatform();
        if (tama.state != TAMADA_STATE_HIT) tama.velocity.x = -accelX / 10 * MOVE_VELOCITY;
        tama.update(deltaTime);
        heightSoFar = Math.max(tama.position.y, heightSoFar);
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
        if (heightSoFar - 7.5f > tama.position.y) {
            state = WORLD_STATE_GAME_OVER;
        }
    }

    }
