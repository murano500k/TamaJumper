package com.stc.tamajumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Game extends com.badlogic.gdx.Game {

	public static final int MENU = 0;
	public static final int PREFERENCES = 1;
	public static final int GAME = 2;
	public static final int HELP = 3;
	public static final int HIGHSCORES = 4;
	public static final int EXIT = 5;
	public static final int WIN = 6;
	public SpriteBatch batcher;
	private GameScreen gameScreen;
	private Prefs prefs;
	private HighscoresScreen highscoresScreen;
	private int currentScore=0;
	private Assets assets;

	@Override
	public void create () {
		batcher = new SpriteBatch();

		prefs=new Prefs();
		assets =new Assets();
		setScreen(getStartScreen());
	}




	public void changeScreen(int screen){
		switch(screen){
			case MENU:
				this.setScreen(new MenuScreen(this));
				break;
			case PREFERENCES:
				this.setScreen(new SettingsScreen(this));
				break;
			case WIN:
				this.setScreen(new WinScreen(this));
				break;
			case HIGHSCORES:
				this.setScreen(new HighscoresScreen(this));
				break;
			case HELP:
				this.setScreen(new TestScreen(this));
				break;
			case GAME:
				if(gameScreen == null || gameScreen.gameState== GameScreen.GameState.GAME_OVER){
					currentScore=0;
				}else if(gameScreen.gameState== GameScreen.GameState.LEVEL_END){
					currentScore=gameScreen.getCurrentScore();
				}
				gameScreen= new GameScreen(this, currentScore);
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


}
