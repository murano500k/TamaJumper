package com.stc.tamajumper.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.stc.tamajumper.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "MyGame";
		System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");

		//config.useGL20 = false;
		config.height = 1280;
		config.width = 720;
		new LwjglApplication(new Game(), config);
	}
}
