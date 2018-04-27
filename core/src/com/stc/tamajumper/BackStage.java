package com.stc.tamajumper;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

import static com.stc.tamajumper.Config.PIXELS.WORLD_HEIGHT;
import static com.stc.tamajumper.Config.PIXELS.WORLD_WIDTH;

public class BackStage extends MyStage {
    private Group objects;
    Random random;

    public BackStage(Viewport viewport, TamaJumperGame game) {
        super(viewport, game);
        random=new Random();
    }

    public void generateObjects() {
        objects = new Group();
        generateStars();
        generatePlanets();
        addActor(objects);
    }

    private void generatePlanets() {
        float y = Platform.HEIGHT / 2;
        float maxJumpHeight = Tama.JUMP_VELOCITY * Tama.JUMP_VELOCITY / (2 * -Config.GRAVITY.y);
        while (y < WORLD_HEIGHT - WORLD_WIDTH / 2) {
            BgPlanet bgPlanet = BgPlanet.createBgPlanet(y);
            objects.addActor(bgPlanet);
            y += maxJumpHeight*0.8f;
            float levelProgressDifficulty=(WORLD_HEIGHT-y)/WORLD_HEIGHT;

            float randomSeed= new Random().nextFloat();

            if(randomSeed<0.5f) randomSeed*=2;
            else if(randomSeed<0.75f)randomSeed/=1.2f;
            else randomSeed/=2;
            y -= (randomSeed) * maxJumpHeight*levelProgressDifficulty;
        }
    }

    private void generateStars(){
        float y=0;
        float maxJumpHeight = Tama.JUMP_VELOCITY * Tama.JUMP_VELOCITY / (2 * -Config.GRAVITY.y);
        while (y < WORLD_HEIGHT){
            BgStar bgStar = BgStar.createBgStar(y);
            objects.addActor(bgStar);
            y+=maxJumpHeight*0.05f;
        }
    }
}
