package com.stc.tamajumper.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.stc.tamajumper.TamaJumperGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "MyGame";
		//config.useGL20 = false;
		config.height = 1280;
		config.width = 720;
		new LwjglApplication(new TamaJumperGame(), config);
	}
}
