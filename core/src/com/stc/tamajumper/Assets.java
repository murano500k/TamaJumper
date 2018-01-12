package com.stc.tamajumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Assets {
    private static final int MEDIUM_BG_OBJECTS_COUNT = 2;
    private static final int LARGE_BG_OBJECTS_COUNT = 4;
    public static Texture background;
    public static TextureRegion backgroundRegion;

    public static Texture items;
    public static TextureRegion mainMenu;
    public static TextureRegion pauseMenu;
    public static TextureRegion ready;
    public static TextureRegion gameOver;
    public static TextureRegion highScoresRegion;
    public static TextureRegion logo;
    public static TextureRegion soundOn;
    public static TextureRegion soundOff;
    public static TextureRegion arrow;
    public static TextureRegion pause;
    public static TextureRegion spring;
    public static TextureRegion castle;
    public static Animation coinAnim;
    public static Animation tamadaJump;
    public static Animation tamadaFall;
    public static TextureRegion tamadaHit;
    public static Animation squirrelFly;
    public static TextureRegion platform;
    public static Animation brakingPlatform;
    public static BitmapFont font;

    public static Sound jumpSound;
    public static Sound highJumpSound;
    public static Sound hitSound;
    public static Sound coinSound;
    public static Sound clickSound;
    private static TextureRegion bgObjectSmall;
    private static ArrayList<TextureRegion> bgObjectsMedium;
    private static ArrayList<TextureRegion> bgObjectsLarge;

    public static Texture loadTexture (String file) {
        return new Texture(Gdx.files.internal(file));
    }


    public static TextureRegion getBgObjectTexture(float id){
        if(id==1)return bgObjectSmall;
        else if(id==2){
            int rnd = new Random().nextInt(MEDIUM_BG_OBJECTS_COUNT);
            return bgObjectsMedium.get(rnd);
        }else {
            int rnd = new Random().nextInt(LARGE_BG_OBJECTS_COUNT);
            return bgObjectsLarge.get(rnd);
        }
    }
    public static void load () {
        background = loadTexture("data/bg.png");
        background.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);

        backgroundRegion = new TextureRegion(background, 0, 0,100,100);

        bgObjectsMedium= new ArrayList<>();
        bgObjectsLarge= new ArrayList<>();

        Texture bgObjTexture = loadTexture("data/bg_object_small.png");
        bgObjectSmall=new TextureRegion(bgObjTexture, 0, 0, Config.PIXELS.PLAYER_DIMEN, Config.PIXELS.PLAYER_DIMEN);

        bgObjTexture = loadTexture("data/bg_object_medium0.png");
        bgObjectsMedium.add(new TextureRegion(bgObjTexture, 0, 0, Config.PIXELS.DOUBLE_DIMEN, Config.PIXELS.DOUBLE_DIMEN));

        bgObjTexture = loadTexture("data/bg_object_medium1.png");
        bgObjectsMedium.add(new TextureRegion(bgObjTexture, 0, 0, Config.PIXELS.DOUBLE_DIMEN, Config.PIXELS.DOUBLE_DIMEN));


        bgObjTexture = loadTexture("data/bg_object_large0.png");
        bgObjectsLarge.add(new TextureRegion(bgObjTexture, 0, 0, Config.PIXELS.PLAYER_DIMEN*3, Config.PIXELS.PLAYER_DIMEN*3));


        bgObjTexture = loadTexture("data/bg_object_large1.png");
        bgObjectsLarge.add(new TextureRegion(bgObjTexture, 0, 0, Config.PIXELS.PLAYER_DIMEN*3, Config.PIXELS.PLAYER_DIMEN*3));


        bgObjTexture = loadTexture("data/bg_object_large2.png");
        bgObjectsLarge.add(new TextureRegion(bgObjTexture, 0, 0, Config.PIXELS.PLAYER_DIMEN*3, Config.PIXELS.PLAYER_DIMEN*3));


        bgObjTexture = loadTexture("data/bg_object_large3.png");
        bgObjectsLarge.add(new TextureRegion(bgObjTexture, 0, 0, Config.PIXELS.PLAYER_DIMEN*3, Config.PIXELS.PLAYER_DIMEN*3));


        items = loadTexture("data/items.png");
        mainMenu = new TextureRegion(items, 0, 224, 300, 110);
        pauseMenu = new TextureRegion(items, 224, 128, 192, 48);
        ready = new TextureRegion(items, 320, 224, 192, 32);
        gameOver = new TextureRegion(items, 352, 256, 160, 96);
        highScoresRegion = new TextureRegion(Assets.items, 0, 257, 300, 110 / 3);
        logo = new TextureRegion(items, 0, 352, 274, 142);
        soundOff = new TextureRegion(items, 0, 0, Config.PIXELS.DOUBLE_DIMEN, Config.PIXELS.DOUBLE_DIMEN);
        soundOn = new TextureRegion(items, 64, 0, Config.PIXELS.DOUBLE_DIMEN, Config.PIXELS.DOUBLE_DIMEN);
        arrow = new TextureRegion(items, 0, 64, Config.PIXELS.DOUBLE_DIMEN, Config.PIXELS.DOUBLE_DIMEN);
        pause = new TextureRegion(items, 64, 64, Config.PIXELS.DOUBLE_DIMEN, Config.PIXELS.DOUBLE_DIMEN);

        spring = new TextureRegion(items, 128, 0,
                Config.PIXELS.PLAYER_DIMEN, Config.PIXELS.PLAYER_DIMEN);
        castle = new TextureRegion(items, 128, 64,
                Config.PIXELS.DOUBLE_DIMEN, Config.PIXELS.DOUBLE_DIMEN);

        coinAnim = new Animation(0.2f,
                new TextureRegion(items, 128, 32,
                        Config.PIXELS.PLAYER_DIMEN, Config.PIXELS.PLAYER_DIMEN),
                new TextureRegion(items, 160, 32,
                        Config.PIXELS.PLAYER_DIMEN, Config.PIXELS.PLAYER_DIMEN),
                new TextureRegion(items, 192, 32,
                        Config.PIXELS.PLAYER_DIMEN, Config.PIXELS.PLAYER_DIMEN),
                new TextureRegion(items, 160, 32,
                        Config.PIXELS.PLAYER_DIMEN, Config.PIXELS.PLAYER_DIMEN));

        tamadaJump = new Animation(0.2f,
                new TextureRegion(items, 0, 128,
                        Config.PIXELS.PLAYER_DIMEN, Config.PIXELS.PLAYER_DIMEN),
                new TextureRegion(items, 32, 128,
                        Config.PIXELS.PLAYER_DIMEN, Config.PIXELS.PLAYER_DIMEN));
        tamadaFall = new Animation(0.2f,
                new TextureRegion(items, 64, 128,
                        Config.PIXELS.PLAYER_DIMEN, Config.PIXELS.PLAYER_DIMEN),
                new TextureRegion(items, 96, 128,
                        Config.PIXELS.PLAYER_DIMEN, Config.PIXELS.PLAYER_DIMEN));
        tamadaHit = new TextureRegion(items, 128, 128,
                Config.PIXELS.PLAYER_DIMEN, Config.PIXELS.PLAYER_DIMEN);
        squirrelFly = new Animation(0.2f,
                new TextureRegion(items, 0, 160,
                        Config.PIXELS.PLAYER_DIMEN, Config.PIXELS.PLAYER_DIMEN),
                new TextureRegion(items, 32, 160,
                        Config.PIXELS.PLAYER_DIMEN, Config.PIXELS.PLAYER_DIMEN));

        platform = new TextureRegion(items, 64, 160,
                Config.PIXELS.PLATFORM_W, Config.PIXELS.PLATFORM_H);

        brakingPlatform = new Animation(0.2f,
                new TextureRegion(items, 64, 160, Config.PIXELS.PLATFORM_W, Config.PIXELS.PLATFORM_H),
                new TextureRegion(items, 64, 176, Config.PIXELS.PLATFORM_W, Config.PIXELS.PLATFORM_H),
                new TextureRegion(items, 64, 192, Config.PIXELS.PLATFORM_W, Config.PIXELS.PLATFORM_H),
                new TextureRegion(items, 64, 208, Config.PIXELS.PLATFORM_W, Config.PIXELS.PLATFORM_H));

        font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"), false);

        jumpSound = Gdx.audio.newSound(Gdx.files.internal("data/jump.wav"));
        highJumpSound = Gdx.audio.newSound(Gdx.files.internal("data/highjump.wav"));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("data/hit.wav"));
        coinSound = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
        clickSound = Gdx.audio.newSound(Gdx.files.internal("data/click.wav"));
    }

    public static void playSound (Sound sound) {
        if (Settings.soundEnabled) sound.play(1);
    }
}
