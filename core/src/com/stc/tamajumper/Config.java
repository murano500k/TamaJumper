package com.stc.tamajumper;



public class Config {



    public static final int WORLD_STATE_RUNNING = 0;
    public static final int WORLD_STATE_NEXT_LEVEL = 1;
    public static final int WORLD_STATE_GAME_OVER = 2;
    public static final int PLATFORM_SCORE = 1;

    public static final int VIEWPORT_WIDTH = 720;
    public static final int VIEWPORT_HEIGHT = 1280;


    public static final int TAMADA_STATE_JUMP = 0;
    public static final int TAMADA_STATE_FALL = 1;
    public static final int TAMADA_STATE_HIT = 2;
    public static final float TAMADA_JUMP_VELOCITY = 11;
    public static final float TAMADA_MOVE_VELOCITY = 20;
    public static final float TAMADA_WIDTH = 0.8f;
    public static final float TAMADA_HEIGHT = 0.8f;


    public static final float PLATFORM_WIDTH = 2;
    public static final float PLATFORM_HEIGHT = 0.5f;
    public static final int PLATFORM_TYPE_STATIC = 0;
    public static final int PLATFORM_TYPE_MOVING = 1;
    public static final int PLATFORM_STATE_NORMAL = 0;
    public static final int PLATFORM_STATE_PULVERIZING = 1;
    public static final float PLATFORM_PULVERIZE_TIME = 0.2f * 4;
    public static final float PLATFORM_VELOCITY = 2;



    public static final float BG_OBJECT_JUMP_RATIO = 0.1f;
    public static final float BG_OBJECT_MOVE_RATIO = 0.1f;
    public static final float BG_OBJECT_MOVE_VELOCITY = 1;
    public static final int BG_OBJECTS_TYPES_COUNT = 3;
    public static final float SMALL_BG_OBJECT_DIMEN = 1;
    public static final float MEDIUM_BG_OBJECT_DIMEN = 2;
    public static final float LARGE_BG_OBJECT_DIMEN = 3;


    public class PIXELS {
        public static final int PLAYER_DIMEN = 32;
        public static final int DOUBLE_DIMEN = PLAYER_DIMEN * 2;
        public static final int HALF_DIMEN = PLAYER_DIMEN / 2;

        public static final int PLATFORM_W = DOUBLE_DIMEN;
        public static final int PLATFORM_H = HALF_DIMEN;
        static final int FRUSTUM_WIDTH = 10;
        static final int FRUSTUM_HEIGHT = 15;
        public static final float WORLD_WIDTH = FRUSTUM_WIDTH;
        public static final float LEVEL_HEIGHT_SCREENS = 20;
        public static final float WORLD_HEIGHT = FRUSTUM_HEIGHT * LEVEL_HEIGHT_SCREENS;
    }
}
