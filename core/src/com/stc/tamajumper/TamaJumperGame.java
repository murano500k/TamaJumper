package com.stc.tamajumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class TamaJumperGame extends com.badlogic.gdx.Game {

	public static final int MENU = 0;
	public static final int PREFERENCES = 1;
	public static final int GAME = 2;
	public static final int HELP = 3;
	public static final int HIGHSCORES = 4;
	public static final int EXIT = 5;
	public SpriteBatch batcher;
	private MenuScreen menuScreen;
	private DemoScreen gameScreen;
	private Screen preferencesScreen;
	private Prefs prefs;
	private Assets assets;
	private HighscoresScreen highscoresScreen;
	private int currentScore=0;

	@Override
	public void create () {
		batcher = new SpriteBatch();

		prefs=new Prefs();
		assets=new Assets(this);

		setScreen(getStartScreen());
	}
	public Prefs getPreferences(){
		return prefs;
	}




	public void changeScreen(int screen){
		switch(screen){
			case MENU:
				this.setScreen(new MenuScreen(this));
				break;
			case PREFERENCES:
				this.setScreen(new SettingsScreen(this));
				break;
			case HIGHSCORES:
				this.setScreen(new HighscoresScreen(this));
				break;
			case GAME:
				if(gameScreen == null || gameScreen.gameState== DemoScreen.GameState.GAME_OVER){
					currentScore=0;
				}else if(gameScreen.gameState== DemoScreen.GameState.LEVEL_END){
					currentScore=gameScreen.getCurrentScore();
				}
				gameScreen= new DemoScreen(this, currentScore);
				this.setScreen(gameScreen);
				break;
			case EXIT:
				Gdx.app.exit();
				break;
			default:
				System.err.println("not implemented");
		}
	}

	public ScreenAdapter getStartScreen(){
		return new MenuScreen(this);
	}

	@Override
	public void render() {
		super.render();
	}


	public Assets getAssets() {
		return assets;
	}
}
