package com.stc.game2d;

import com.badlogic.gdx.Game;
import com.stc.game2d.box2d.CollisionsSample;

public class Codelabs extends Game {

	public static final String TITLE = "Codelabs";
	public static final String VERSION = "v0.6.0";
	
	public static final float TARGET_WIDTH = 800;
			
	@Override
	public void create() {
		setScreen(new CollisionsSample());
	}
	
}
