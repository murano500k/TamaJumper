package com.stc.tamajumper;



public class Castle extends DynamicGameObject {
    public static float CASTLE_WIDTH = 1.7f;
    public static float CASTLE_HEIGHT = 1.7f;

    public Castle (float x, float y) {
        super(x, y, CASTLE_WIDTH, CASTLE_HEIGHT);
    }

    @Override
    void update(float deltaTime) {

    }
}