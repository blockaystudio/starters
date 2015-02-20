package com.blake.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.blake.game.learnGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new learnGame(), config);
		//TexturePacker.Settings settins = new TexturePacker.Settings();
		//settins.pot = true;
		//settins.useIndexes = true;
      // TexturePacker.process(settins, "../asses/", "./assest/", "packs.atlas");

	}
}
