package com.stc.tamajumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

import static com.stc.tamajumper.Config.VIEWPORT_HEIGHT;
import static com.stc.tamajumper.Config.VIEWPORT_WIDTH;

public class TestScreen extends ScreenAdapter {


    private final TamaJumperGame game;
    private final Random rand;
    private final Skin skin;

    private BackStage backStage;
    private OrthographicCamera camera;
    private Viewport viewport;

    public TestScreen(TamaJumperGame game) {
        this.game=game;
        rand = new Random();
        skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));
    }
    @Override
    public void show() {
        super.show();
        camera=new OrthographicCamera(VIEWPORT_WIDTH,VIEWPORT_HEIGHT);
        camera.position.set(VIEWPORT_WIDTH/ 2, VIEWPORT_HEIGHT / 2, 0);
        viewport =new FillViewport(VIEWPORT_WIDTH,VIEWPORT_HEIGHT, camera);
        backStage=new BackStage(viewport,game);
        Gdx.input.setCatchBackKey(true);
        backStage.generateObjects();
        Ufo ufo = new Ufo( VIEWPORT_HEIGHT/4,true, 3f);
        backStage.addActor(ufo);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glClearColor(2f/255f,8f/255f,36f/255f,1f);
        //camera.position.y+=VIEWPORT_HEIGHT/4*delta;
        camera.update();
        backStage.act(delta);
        backStage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        backStage.dispose();
    }
}
