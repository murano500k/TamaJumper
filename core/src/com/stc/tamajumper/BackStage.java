package com.stc.tamajumper;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

import static com.stc.tamajumper.Config.PIXELS.WORLD_HEIGHT;
import static com.stc.tamajumper.Config.PIXELS.WORLD_WIDTH;

public class BackStage extends MyStage {
    private Group objects;

    public BackStage(Viewport viewport, TamaJumperGame game) {
        super(viewport, game);
    }

    public void generateObjects() {
        float y = Platform.HEIGHT / 2;
        float maxJumpHeight = Tama.JUMP_VELOCITY * Tama.JUMP_VELOCITY / (2 * -Config.GRAVITY.y);
        objects = new Group();


        while (y < WORLD_HEIGHT - WORLD_WIDTH / 2) {
            BgObject bgObject = BgObject.createBgObject(y);
            objects.addActor(bgObject);


            y += (maxJumpHeight*0.8f - 0.5f);
            float levelProgressDifficulty=(WORLD_HEIGHT-y)/WORLD_HEIGHT;

            float randomSeed= new Random().nextFloat();

            if(randomSeed<0.5f) randomSeed*=2;
            else if(randomSeed<0.75f)randomSeed/=1.2f;
            else randomSeed/=2;
            y -= (randomSeed) * maxJumpHeight*levelProgressDifficulty;
        }
        addActor(objects);
    }
}
