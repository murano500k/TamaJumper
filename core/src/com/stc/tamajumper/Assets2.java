package com.stc.tamajumper;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets2 {
    public static final String TAMA1_ATLAS = "atlas/tama1.atlas";
    public static TextureAtlas atlas;

    public static Animation animProfileJump;
    public static Animation animProfileHighJump;
    public static Animation animDie;
    public static Animation animFaceJump;
    public static Animation animFaceHighJump;
    public static Animation animFaceFall;
    public static Animation animProfileFall;
    public static Animation animCoin;
    public static Sprite texturePlatformNormal;
    public static Sprite texturePlatformMoving;
    public static Sprite texturePlatformBreakable;
    public static Sprite texturePlatformSpring;
    public static Animation animPlatformBreak;

    public Assets2() {
        load();
    }

    public static void load(){
        atlas = new TextureAtlas(TAMA1_ATLAS);
        initAnimations();
        initTextures();
    }

    private static void initAnimations(){
        animProfileJump=createJumpAnim("person-profile-jump-");
        animProfileHighJump=createHighJumpAnim("person-profile-superjump-");

        animFaceJump=createJumpAnim("person-face-jump-");
        animFaceHighJump=createHighJumpAnim("person-face-super-jump-");

        animFaceFall=createFallAnim("person-face-jump-");
        animProfileFall=createFallAnim("person-profile-jump-");

        animDie =new Animation(0.5f,
                atlas.createSprite("person-die-1"),
                atlas.createSprite("person-die-2"),
                atlas.createSprite("person-die-3"),
                atlas.createSprite("person-die-4")
        );

        animCoin=new Animation(0.14f,
                atlas.createSprite("money-1"),
                atlas.createSprite("money-2"),
                atlas.createSprite("money-3"),
                atlas.createSprite("money-4"),
                atlas.createSprite("money-5"),
                atlas.createSprite("money-6"),
                atlas.createSprite("money-7")
        );

        Sprite breakingPlatform1=atlas.createSprite("platform_bad");
        Sprite breakingPlatform2=atlas.createSprite("platform_bad");
        breakingPlatform2.setAlpha(0.75f);
        Sprite breakingPlatform3=atlas.createSprite("platform_bad");
        breakingPlatform3.setAlpha(0.5f);
        Sprite breakingPlatform4=atlas.createSprite("platform_bad");
        breakingPlatform4.setAlpha(0.25f);
        animPlatformBreak =new Animation(0.15f,
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
    }




}


/*/home/artem/projects/TamaJumper/android/assets/forArtem/money-1.png
/home/artem/projects/TamaJumper/android/assets/forArtem/money-2.png
/home/artem/projects/TamaJumper/android/assets/forArtem/money-3.png
/home/artem/projects/TamaJumper/android/assets/forArtem/money-4.png
/home/artem/projects/TamaJumper/android/assets/forArtem/money-5.png
/home/artem/projects/TamaJumper/android/assets/forArtem/money-6.png
/home/artem/projects/TamaJumper/android/assets/forArtem/money-7.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-die-1.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-die-2.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-die-3.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-die-4.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-jump-1.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-jump-2.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-jump-3.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-jump-4.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-jump-5.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-jump-6.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-jump-7.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-super-jump-1.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-super-jump-2.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-super-jump-3.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-super-jump-4.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-super-jump-5.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-super-jump-6.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-face-super-jump-7.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-jump-1.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-jump-2.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-jump-3.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-jump-4.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-jump-5.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-jump-6.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-jump-7.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-superjump-1.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-superjump-2.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-superjump-3.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-superjump-4.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-superjump-5.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-superjump-6.png
/home/artem/projects/TamaJumper/android/assets/forArtem/person-profile-superjump-7.png
/home/artem/projects/TamaJumper/android/assets/forArtem/platform_bad.png
/home/artem/projects/TamaJumper/android/assets/forArtem/platform_motion-.png
/home/artem/projects/TamaJumper/android/assets/forArtem/platform_normal-.png
/home/artem/projects/TamaJumper/android/assets/forArtem/platform_one-off-.png*/