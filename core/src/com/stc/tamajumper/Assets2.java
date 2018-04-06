package com.stc.tamajumper;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

import static com.stc.tamajumper.Coin.COIN_GOLD_SCORE;
import static com.stc.tamajumper.Coin.COIN_RED_SCORE;
import static com.stc.tamajumper.Coin.COIN_SCORE;

public class Assets2 {
    public static final String TAMA1_ATLAS = "atlas/new.atlas";
    public static TextureAtlas atlas;

    public static Animation animProfileJump;
    public static Animation animProfileHighJump;
    public static Animation animDie;
    public static Animation animFaceJump;
    public static Animation animFaceHighJump;
    public static Animation animFaceFall;
    public static Animation animFaceHighFall;
    public static Animation animProfileFall;
    public static Animation animProfileHighFall;
    public static Animation animCoin, animCoinRed, animCoinGold;
    public static Animation animEnemyFlower, animEnemyUfo, animEnemyOctopus;
    public static Sprite texturePlatformNormal;
    public static Sprite texturePlatformMoving;
    public static Sprite texturePlatformBreakable;
    public static Sprite texturePlatformSpring;
    public static Animation animPlatformBreak;
    public static TextureRegion textureKon6;
    public static Sprite textureMenuBg;
    public static List<Sprite> bgObjects;

    public Assets2() {
        load();
    }

    public static void load(){
        atlas = new TextureAtlas(TAMA1_ATLAS);
        initAnimations();
        initTextures();
    }

    private static void initAnimations(){
        initCoins();
        initEnemies();
        animProfileJump=createJumpAnim("person-profile-jump-");
        animProfileHighJump=createHighJumpAnim("person-profile-superjump-");

        animFaceJump=createJumpAnim("person-face-jump-");
        animFaceHighJump=createHighJumpAnim("person-face-super-jump-");

        animFaceFall=createFallAnim("person-face-jump-");
        animFaceHighFall=createFallAnim("person-face-super-jump-");

        animProfileFall=createFallAnim("person-profile-jump-");
        animProfileHighFall=createFallAnim("person-profile-superjump-");

        animDie =new Animation(0.25f,
                atlas.createSprite("person-die-1"),
                atlas.createSprite("person-die-2"),
                atlas.createSprite("person-die-3"),
                atlas.createSprite("person-die-4")
        );



        Sprite breakingPlatform1=atlas.createSprite("platform_bad");
        breakingPlatform1.setAlpha(0.75f);
        Sprite breakingPlatform2=atlas.createSprite("platform_bad");
        breakingPlatform2.setAlpha(0.5f);
        Sprite breakingPlatform3=atlas.createSprite("platform_bad");
        breakingPlatform3.setAlpha(0.34f);
        Sprite breakingPlatform4=atlas.createSprite("platform_bad");
        breakingPlatform4.setAlpha(0.2f);
        animPlatformBreak =new Animation(0.1f,
                breakingPlatform1,
                breakingPlatform2,
                breakingPlatform3,
                breakingPlatform4
        );
    }

    private static Animation createJumpAnim(String imgName){
        return new Animation(0.2f,
                atlas.createSprite(imgName+"4"),
                atlas.createSprite(imgName+"5"),
                atlas.createSprite(imgName+"6"),
                atlas.createSprite(imgName+"7"),
                atlas.createSprite(imgName+"6")
                );
    }


    private static Animation createHighJumpAnim(String imgName){
        return new Animation(0.2f,
                atlas.createSprite(imgName+"4"),
                atlas.createSprite(imgName+"5"),
                atlas.createSprite(imgName+"7"),
                atlas.createSprite(imgName+"7"),
                atlas.createSprite(imgName+"7"),
                atlas.createSprite(imgName+"6")
                );
    }

    private static Animation createFallAnim(String imgName){
        return new Animation(0.2f,
                atlas.createSprite(imgName+"6"),
                atlas.createSprite(imgName+"5"),
                atlas.createSprite(imgName+"2"),
                atlas.createSprite(imgName+"1")
        );
    }

    private static void initTextures(){
        texturePlatformMoving=atlas.createSprite("platform_motion-");
        texturePlatformNormal=atlas.createSprite("platform_normal-");
        texturePlatformSpring=atlas.createSprite("platform_one-off-");
        texturePlatformBreakable=atlas.createSprite("platform_bad");
        textureKon6=new TextureRegion(new Texture( Gdx.files.internal("data/kon.png")));
        textureMenuBg=new Sprite(new Texture( Gdx.files.internal("data/bg.jpg")));

        textureMenuBg.setAlpha(0.5f);
        initBgTextures();
    }

    private static void initBgTextures(){
        bgObjects=new ArrayList<>();
        bgObjects.add(atlas.createSprite("planet-1.1"));
        bgObjects.add(atlas.createSprite("planet-1.2"));
        bgObjects.add(atlas.createSprite("planet-1"));
        bgObjects.add(atlas.createSprite("planet-2.1"));
        bgObjects.add(atlas.createSprite("planet-2"));
        bgObjects.add(atlas.createSprite("planet-3.1"));
        bgObjects.add(atlas.createSprite("planet-3"));
        bgObjects.add(atlas.createSprite("planet-4.1"));
        bgObjects.add(atlas.createSprite("planet-4"));
        bgObjects.add(atlas.createSprite("planet-5.1"));
        bgObjects.add(atlas.createSprite("planet-5"));
        bgObjects.add(atlas.createSprite("star-1(2)"));
        bgObjects.add(atlas.createSprite("star-1(4)"));
        bgObjects.add(atlas.createSprite("star-1(6)"));
        bgObjects.add(atlas.createSprite("star-1(8)"));
        bgObjects.add(atlas.createSprite("star-1(12)"));
        bgObjects.add(atlas.createSprite("star-1(14)"));
        bgObjects.add(atlas.createSprite("star-1(16)"));
        bgObjects.add(atlas.createSprite("star-1(24)"));
        bgObjects.add(atlas.createSprite("star-1(32)"));
        bgObjects.add(atlas.createSprite("platform_motion-"));


    }

    private static  void initCoins(){
        animCoin=new Animation(0.14f,
                atlas.createSprite("money-1"),
                atlas.createSprite("money-2"),
                atlas.createSprite("money-3"),
                atlas.createSprite("money-4"),
                atlas.createSprite("money-5"),
                atlas.createSprite("money-6"),
                atlas.createSprite("money-7")
        );
        animCoinRed=new Animation(0.14f,
                atlas.createSprite("money(2)-1"),
                atlas.createSprite("money(2)-2"),
                atlas.createSprite("money(2)-3"),
                atlas.createSprite("money(2)-4"),
                atlas.createSprite("money(2)-5"),
                atlas.createSprite("money(2)-6"),
                atlas.createSprite("money(2)-7")
        );
        animCoinGold=new Animation(0.14f,
                atlas.createSprite("money(3)-1"),
                atlas.createSprite("money(3)-2"),
                atlas.createSprite("money(3)-3"),
                atlas.createSprite("money(3)-4"),
                atlas.createSprite("money(3)-5"),
                atlas.createSprite("money(3)-6")
        );
    }


    public static Animation getCoinAnim(int score) {
        switch (score){
            case COIN_RED_SCORE:
                return animCoinRed;
            case COIN_GOLD_SCORE:
                return animCoinGold;
            case COIN_SCORE:
            default:
                return animCoin;
        }
    }

    public static Animation getEnemyAnim(Enemy.Type type) {
        switch (type){
            case OCTOPUS:
                return animEnemyOctopus;
            case FLOWER:
                return animEnemyFlower;
            case UFO:
            default:
                return animEnemyUfo;
        }
    }


    private static  void initEnemies(){
        animEnemyUfo=new Animation(0.14f,
                atlas.createSprite("enemy-1"),
                atlas.createSprite("enemy-2"),
                atlas.createSprite("enemy-3"),
                atlas.createSprite("enemy-4"),
                atlas.createSprite("enemy-5"),
                atlas.createSprite("enemy-6"),
                atlas.createSprite("enemy-7"),
                atlas.createSprite("enemy-8")
        );
        animEnemyOctopus=new Animation(0.14f,
                atlas.createSprite("enemy(2)-1"),
                atlas.createSprite("enemy(2)-2"),
                atlas.createSprite("enemy(2)-3"),
                atlas.createSprite("enemy(2)-4"),
                atlas.createSprite("enemy(2)-5"),
                atlas.createSprite("enemy(2)-6"),
                atlas.createSprite("enemy(2)-7")
        );
        animEnemyFlower=new Animation(0.14f,
                atlas.createSprite("enemy(3)-1"),
                atlas.createSprite("enemy(3)-2"),
                atlas.createSprite("enemy(3)-3"),
                atlas.createSprite("enemy(3)-4"),
                atlas.createSprite("enemy(3)-5"),
                atlas.createSprite("enemy(3)-6"),
                atlas.createSprite("enemy(3)-7")
        );
    }



    public static TextureRegion getBgObjectTexture(int type) {
        if(type>=bgObjects.size()){
            type-=bgObjects.size();
        }
        return bgObjects.get(type);
    }

}

/*

*/