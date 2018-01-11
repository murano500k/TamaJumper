package com.stc.tamajumper.objects;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by artem on 1/11/18.
 */


public class ParallaxBackground {

    private final float width;
    private final float height;
    private ParallaxLayer[] layers;
        private Vector2 speed = new Vector2();

        /**
         * @param layers  The  background layers
         * @param width   The screenWith
         * @param height The screenHeight
         * @param speed A Vector2 attribute to point out the x and y speed
         */
        public ParallaxBackground(ParallaxLayer[] layers,float width,float height,Vector2 speed){
            this.width=width;
            this.height=height;

            this.layers = layers;
            this.speed.set(speed);
        }

        public void render(OrthographicCamera camera, SpriteBatch batch){
            System.out.println("paraBG: cam.viewW="+camera.viewportWidth+" , cam.vH="+camera.viewportHeight);
            //this.camera.position.add(speed.x*delta,speed.y*delta, 0);
            for(ParallaxLayer layer:layers){
                System.out.println("paraBG: layer.region.getRegionWidth()="+layer.region.getRegionWidth()
                        +" ,  layer.region.getRegionHeight()="+ layer.region.getRegionHeight());
                float currentY = - camera.position.y*layer.parallaxRatio.y % ( height + layer.padding.y) ;
                do{
                    batch.draw(layer.region, -camera.viewportWidth/2 ,
                            -camera.viewportHeight/2 + currentY +layer.startPosition.y, width,height);
                    currentY += ( layer.region.getRegionHeight() + layer.padding.y );
                }while( currentY < camera.viewportHeight);

            }
        }
}
