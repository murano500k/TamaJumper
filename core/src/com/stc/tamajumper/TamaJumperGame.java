package com.stc.tamajumper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class TamaJumperGame extends com.badlogic.gdx.Game {

	public static final int MENU = 0;
	public static final int PREFERENCES = 1;
	public static final int GAME = 2;
	public SpriteBatch batcher;
	private Screen menuScreen;
	private GameScreen gameScreen;
	private Screen preferencesScreen;
	private AppPreferences prefs;
	private Assets assets;

	@Override
	public void create () {
		batcher = new SpriteBatch();

		prefs=new AppPreferences();
		assets=new Assets(this);
		setScreen(getStartScreen());
	}
	public AppPreferences getPreferences(){
		return prefs;
	}




	public void changeScreen(int screen){
		switch(screen){
			case MENU:
				if(menuScreen == null) menuScreen = new MainMenu(this);
				this.setScreen(menuScreen);
				break;
			case PREFERENCES:
				if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
				this.setScreen(preferencesScreen);
				break;
			case GAME:
				//if(gameScreen == null)
					gameScreen= new GameScreen(this);
				this.setScreen(gameScreen);
				break;
		}
	}

	public ScreenAdapter getStartScreen(){
		return new MainMenu(this);
	}

	@Override
	public void render() {
		super.render();
	}


	public Assets getAssets() {
		return assets;
	}
}
