package com.stc.tamajumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
    public static Music music;

    public static Sound highJumpSound;
    public static Sound gameOverSound;
    public static Sound clickSound;
    public static List<Sound> jumpSounds;
    public static Sound coinSound;

    private static TextureRegion bgObjectSmall;
    private static ArrayList<TextureRegion> bgObjectsMedium;
    private static ArrayList<TextureRegion> bgObjectsLarge;
    public static TextureRegion backgroundRegionMenu;
    private static Random random=new Random();
    private static ArrayList<Music> musicSounds;
    private static TextureAtlas atlas;
    private final TamaJumperGame parent;

    public Assets(TamaJumperGame tamaJumperGame) {
        this.parent=tamaJumperGame;
        load();
    }

    public static Texture loadTexture (String file) {
        return new Texture(Gdx.files.internal(file));
    }


    public TextureRegion getBgObjectTexture(float id){
        if(id==1)return bgObjectSmall;
        else if(id==2){
            int rnd = new Random().nextInt(MEDIUM_BG_OBJECTS_COUNT);
            return bgObjectsMedium.get(rnd);
        }else {
            int rnd = new Random().nextInt(LARGE_BG_OBJECTS_COUNT);
            return bgObjectsLarge.get(rnd);
        }

    }
    public void load () {
        loadSounds();
        background = loadTexture("data/bg.png");
        backgroundRegionMenu = new TextureRegion(background, 0, 0, 650,1067);
        backgroundRegion = new TextureRegion(background, 0, 0,100,100);

        bgObjectsMedium= new ArrayList<>();
        bgObjectsLarge= new ArrayList<>();

        Texture bgObjTexture = loadTexture("data/bg_object_small.png");

        bgObjectSmall=new TextureRegion(bgObjTexture, 0, 0, 32, 32);

        bgObjTexture = loadTexture("data/bg_object_medium0.png");
        bgObjectsMedium.add(new TextureRegion(bgObjTexture, 0, 0, 64, 64));

        bgObjTexture = loadTexture("data/bg_object_medium1.png");
        bgObjectsMedium.add(new TextureRegion(bgObjTexture, 0, 0, 64, 64));


        bgObjTexture = loadTexture("data/bg_object_large0.png");
        bgObjectsLarge.add(new TextureRegion(bgObjTexture, 0, 0, 32*4, 32*4));


        bgObjTexture = loadTexture("data/bg_object_large1.png");
        bgObjectsLarge.add(new TextureRegion(bgObjTexture, 0, 0, 32*4, 32*4));


        bgObjTexture = loadTexture("data/bg_object_large2.png");
        bgObjectsLarge.add(new TextureRegion(bgObjTexture, 0, 0, 32*4, 32*4));


        bgObjTexture = loadTexture("data/bg_object_large3.png");
        bgObjectsLarge.add(new TextureRegion(bgObjTexture, 0, 0, 32*4, 32*4));


        items = loadTexture("data/items.png");
        mainMenu = new TextureRegion(items, 0, 224, 300, 110);
        pauseMenu = new TextureRegion(items, 224, 128, 192, 48);
        ready = new TextureRegion(items, 320, 224, 192, 32);
        gameOver = new TextureRegion(items, 352, 256, 160, 96);
        highScoresRegion = new TextureRegion(Assets.items, 0, 257, 300, 110 / 3);
        logo = new TextureRegion(items, 0, 352, 274, 142);
        soundOff = new TextureRegion(items, 0, 0, 64, 64);
        soundOn = new TextureRegion(items, 64, 0, 64, 64);
        arrow = new TextureRegion(items, 0, 64, 64, 64);
        pause = new TextureRegion(items, 64, 64, 64, 64);

        spring = new TextureRegion(items, 128, 0,
                32, 32);
        castle = new TextureRegion(items, 128, 64,
                64, 64);

        coinAnim = new Animation(0.2f,
                new TextureRegion(items, 128, 32,
                        32, 32),
                new TextureRegion(items, 160, 32,
                        32, 32),
                new TextureRegion(items, 192, 32,
                        32, 32),
                new TextureRegion(items, 160, 32,
                        32, 32));

        tamadaJump = new Animation(0.2f,
                new TextureRegion(items, 0, 128,
                        32, 32),
                new TextureRegion(items, 32, 128,
                        32, 32));
        tamadaFall = new Animation(0.2f,
                new TextureRegion(items, 64, 128,
                        32, 32),
                new TextureRegion(items, 96, 128,
                        32, 32));
        tamadaHit = new TextureRegion(items, 128, 128,
                32, 32);
        squirrelFly = new Animation(0.2f,
                new TextureRegion(items, 0, 160,
                        32, 32),
                new TextureRegion(items, 32, 160,
                        32, 32));

        platform = new TextureRegion(items, 64, 160,
                64, 16);

        brakingPlatform = new Animation(0.2f,
                new TextureRegion(items, 64, 160, 64, 16),
                new TextureRegion(items, 64, 176, 64, 16),
                new TextureRegion(items, 64, 192, 64, 16),
                new TextureRegion(items, 64, 208, 64, 16));

        font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"), false);

        loadSounds();
    }





    public void playMusic(){
        stopMusic();
        if (AppPreferences.isMusicEnabled()) {
            int index=1;
            //index=random.nextInt(musicSounds.size());
            musicSounds.get(Config.MUSIC_SOUND_INDEX).play();

        }
    }
    public void stopMusic(){
        musicSounds.get(Config.MUSIC_SOUND_INDEX).stop();
        /*for (int i = 0; i < musicSounds.size(); i++) {
            if(musicSounds.get(i).isPlaying())
                musicSounds.get(i).stop();
        }*/
    }

    private Music addMusic(String path) {
        Music music=Gdx.audio.newMusic(Gdx.files.internal(path));
        music.setLooping(false);
        music.setVolume(Config.VOLUME_MUSIC);
        music.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                musicSounds.get(random.nextInt(musicSounds.size())).play();
            }
        });
        musicSounds.add(music);
        return music;
    }

    public void playJumpSound(Platform platform){
        int index;

        //index=random.nextInt(jumpSounds.size());
        index=Config.JUMP_SOUND_INDEX_DEFAULT;
        if(platform.canBreak)index=Config.JUMP_SOUND_INDEX_BREAK;
        else if(platform.type==Config.PLATFORM_TYPE_MOVING){
            index=Config.JUMP_SOUND_INDEX_MOVING;
        }
        if (AppPreferences.isSoundEffectsEnabled())
            playSound(jumpSounds.get(index));
    }



    public void playHighJumpSound() {
        if (AppPreferences.isSoundEffectsEnabled()){
            playSound(jumpSounds.get(Config.JUMP_SOUND_INDEX_HIGH));
        }
    }


    public static void playSound (Sound sound) {
        if (AppPreferences.isSoundEffectsEnabled()) sound.play(1);
    }


    private void loadAtlas(){
        atlas = new TextureAtlas(AssetNames.Files.ALL_OBJECTS_ATLAS);

        mainMenu = new TextureRegion(items, 0, 224, 300, 110);
        pauseMenu = new TextureRegion(items, 224, 128, 192, 48);

        highScoresRegion = new TextureRegion(Assets.items, 0, 257, 300, 110 / 3);

        ready  = atlas.createSprite(AssetNames.MenuObjects.Labels.READY);
        gameOver  = atlas.createSprite(AssetNames.MenuObjects.Labels.GAME_OVER);
        logo = atlas.createSprite(AssetNames.MenuObjects.LOGO);
        soundOff = atlas.createSprite(AssetNames.MenuObjects.Buttons.VOL_DISABLED);
        soundOn = atlas.createSprite(AssetNames.MenuObjects.Buttons.VOL_ENABLED);
        pause = atlas.createSprite(AssetNames.MenuObjects.Buttons.PAUSE);

        spring = atlas.createSprite(AssetNames.Upgrades.SPRING);
        castle = atlas.createSprite(AssetNames.CASTLE);

        coinAnim = new Animation(0.2f,
                atlas.createSprite(AssetNames.Rewards.COIN[0]),
                atlas.createSprite(AssetNames.Rewards.COIN[1]),
                atlas.createSprite(AssetNames.Rewards.COIN[2]),
                atlas.createSprite(AssetNames.Rewards.COIN[3]));

        tamadaJump = new Animation(0.2f,
                atlas.createSprite(AssetNames.Tama.JUMP[0]),
                atlas.createSprite(AssetNames.Tama.JUMP[1])
                );

        tamadaFall = new Animation(0.2f,
                atlas.createSprite(AssetNames.Tama.FALL[0]),
                atlas.createSprite(AssetNames.Tama.FALL[1])
                );

        tamadaHit = atlas.createSprite(AssetNames.Tama.HIT[0]);


        squirrelFly = new Animation(0.2f,
                atlas.createSprite(AssetNames.Enemies.SQUIRREL[0]),
                atlas.createSprite(AssetNames.Enemies.SQUIRREL[1])
        );

        platform = atlas.createSprite(AssetNames.Platforms.PLATFORM_0[0]);

        brakingPlatform = new Animation(0.2f,
                atlas.createSprite(AssetNames.Platforms.PLATFORM_0[1]),
                atlas.createSprite(AssetNames.Platforms.PLATFORM_0[2]),
                atlas.createSprite(AssetNames.Platforms.PLATFORM_0[3]),
                atlas.createSprite(AssetNames.Platforms.PLATFORM_0[4])
        );


        font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"), false);

        loadSounds();
    }
    private void loadSounds(){
        highJumpSound = Gdx.audio.newSound(Gdx.files.internal(AssetNames.Sounds.HIGHJUMP));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal(AssetNames.Sounds.GAME_OVER));
        coinSound = Gdx.audio.newSound(Gdx.files.internal(AssetNames.Sounds.COIN));
        clickSound = Gdx.audio.newSound(Gdx.files.internal(AssetNames.Sounds.CLICK));
        jumpSounds=new ArrayList<>();
        musicSounds=new ArrayList<>();

        for (int i = 0; i < AssetNames.Sounds.JUMP_SOUNDS_NUMBER; i++) {
            jumpSounds.add(Gdx.audio.newSound(
                    Gdx.files.internal(
                            AssetNames.Sounds.JUMP_PREFIX+i+
                                    AssetNames.Sounds.JUMP_POSTFIX)));
        }

        for (String file :
                AssetNames.Sounds.MUSIC) {
            addMusic(file);
        }

    }

    static class AssetNames {

        static class Files {

            public static final String ALL_OBJECTS_ATLAS = "all.pack";
        }
        static class Sounds {
            public static final String HIGHJUMP = "audio/highjump.wav";
            public static final String COIN = "audio/coin_00.wav";
            public static final String GAME_OVER = "audio/game_over.wav";
            public static final String CLICK = "audio/click.wav";

            public static final String JUMP_PREFIX = "audio/jump_0";
            public static final String JUMP_POSTFIX = ".wav";
            public static final int JUMP_SOUNDS_NUMBER=10;

            public static final String[] MUSIC = {
                    "audio/music.mp3",
                    "audio/hhavok_main.mp3",
                    "audio/jumpshot.mp3",
                    "audio/resistors.mp3",
                    "audio/dizzy_spells.mp3",
            };
        }

        public static final String CASTLE = "castle";
        public static final String PRINCESS = "princess";

        static class MenuObjects {
            class Buttons {
                public static final String PAUSE = "btn_pause";
                public static final String VOL_ENABLED = "btn_vol_enabled";
                public static final String VOL_DISABLED = "btn_vol_disabled";
            }
            class Labels {
                public static final String PLAY = "label_play";
                public static final String RESUME = "label_resume";
                public static final String QUIT = "label_quit";
                public static final String READY = "label_ready";
                public static final String GAME_OVER = "label_game_over";
            }
            public static final String LOGO = "logo";

        }

        static class BackgroundObjects {
            public static final String [] SMALL = {
                    "bg_object_small"
            };
            public static final String [] MEDIUM = {
                    "bg_object_medium0",
                    "bg_object_medium1",
            };
            public static final String [] LARGE = {
                    "bg_object_large0",
                    "bg_object_large1",
                    "bg_object_large2",
                    "bg_object_large3",
            };
        }
        static class Tama {
            public static final String [] JUMP = {
                    "tama_jump0",
                    "tama_jump1",
            };
            public static final String [] FALL = {
                    "tama_fall0",
                    "tama_fall1",
            };
            public static final String [] HIT = {
                    "tama_hit",
            };
        }
        static class Platforms {
            public static final String [] PLATFORM_0 = {
                    "platform0_normal",
                    "platform0_pulv0",
                    "platform0_pulv1",
                    "platform0_pulv2",
            };
        }

        static class Enemies {
            public static final String [] SQUIRREL = {
                "squirrel0",
                "squirrel1"
            };
        }
        static class Rewards {
            public static final String [] COIN = {
                    "coin0",
                    "coin1",
                    "coin2"
            };
        }
        static class Upgrades {
            public static final String SPRING = "spring";
        }
    }
}
