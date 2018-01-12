package com.stc.tamajumper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class GameStarter extends Game {

	public SpriteBatch batcher;
	@Override
	public void create () {
		batcher = new SpriteBatch();
		Settings.load();
		Assets.load();
		setScreen(getStartScreen());
	}
	public ScreenAdapter getStartScreen(){
		return new MainMenu(this);
	}

	@Override
	public void render() {
		super.render();
	}


}
