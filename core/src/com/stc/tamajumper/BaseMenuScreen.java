package com.stc.tamajumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FillViewport;

import static com.stc.tamajumper.Config.VIEWPORT_HEIGHT;
import static com.stc.tamajumper.Config.VIEWPORT_WIDTH;

/**
 * Created by artem on 2/19/18.
 */

public class BaseMenuScreen extends ScreenAdapter {
    protected final Game game;
    protected final Skin skin;
    protected MyStage stage;
    protected OrthographicCamera camera;
    protected FillViewport viewport;

    public BaseMenuScreen(Game game) {
        this.game = game;
        this.skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));

    }

    @Override
    public void show() {
        super.show();

        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(VIEWPORT_WIDTH / 2, VIEWPORT_HEIGHT / 2, 0);
        viewport = new FillViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, camera);
        stage = new MyStage(viewport, game);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);
        addBg();

    }

    private void addBg() {
        BackgroundImage backgroundImage= new BackgroundImage(0, 0,VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        stage.addActor(backgroundImage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }
    @Override
    public void dispose() {
        // dispose of assets when not needed anymore
        stage.dispose();
    }

}
