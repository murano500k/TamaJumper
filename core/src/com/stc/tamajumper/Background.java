/*
package com.stc.tamajumper;

import com.badlogic.gdx.scenes.scene2d.Group;

import static com.stc.tamajumper.Config.PIXELS.WORLD_HEIGHT;
import static com.stc.tamajumper.Config.PIXELS.WORLD_WIDTH;

*/
/**
 * Created by artem on 2/2/18.
 *//*


public class Background extends Group {

    private void init(){

        while (y < WORLD_HEIGHT - WORLD_WIDTH / 2) {
            Platform platform = Platform.generatePlatform(y,rand);
            platforms.addActor(platform);



            if (y > WORLD_HEIGHT / 5 && rand.nextFloat() > (1-Config.ENEMY_GENERATION_PROBABILITY)) {
                Enemy enemy = new Enemy(rand.nextFloat()*(WORLD_WIDTH- Enemy.WIDTH), platform.getY()
                        + rand.nextFloat() * 2 *Config.PIXELS.PLAYER_DIMEN, rand.nextBoolean());
                enemies.addActor(enemy);
            }

            if (platform.getType()== Platform.Type.NORMAL && rand.nextFloat() > (1-Config.COIN_GENERATION_PROBABILITY)) {
                Coin coin = new Coin(platform.getX()+ Coin.WIDTH/2
                        , platform.getY()+ Platform.HEIGHT);
                coins.addActor(coin);
            }



            y += (maxJumpHeight*0.9f - 0.5f);
            float levelProgressDifficulty=(WORLD_HEIGHT-y)/WORLD_HEIGHT;

            float randomSeed= rand.nextFloat();

            if(randomSeed<0.5f) randomSeed*=2;
            else if(randomSeed<0.75f)randomSeed/=1.2f;
            else randomSeed/=2;
            y -= (randomSeed) * maxJumpHeight*levelProgressDifficulty;
        }
    }


}
*/
