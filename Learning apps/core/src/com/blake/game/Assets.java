package com.blake.game;

import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Assets {
	static Box2DSprite bob;
	static Box2DSprite bill;
	static Box2DSprite fred;
	private static Sprite billPic;
	private static Sprite bobPic;
	public static Sprite pauset;
	static Texture tPause;
	static Texture tBob;
	static Texture tSheet;
	static Texture tBill;
	static TextureRegion[] sFrames;
	static TextureRegion cFrame;
	static Animation cAnim;
	public static Sound firstSound;
	public static BitmapFont font;
	public static Preferences settings;
	public static Texture img;

	public static String name;
	public static Sprite fredPic;

	public static void load() {

		firstSound = Gdx.audio.newSound(Gdx.files.internal("sounds/firstsound.wav"));

		tBill = new Texture(Gdx.files.internal("chars/bill.png"));
		tBob = new Texture(Gdx.files.internal("chars/bob.png"));
		tPause = new Texture(Gdx.files.internal("pics/Pause1.png"));
		tSheet = new Texture(Gdx.files.internal("pics/loadingsheet.png"));
		pauset = new Sprite(new Texture(Gdx.files.internal("pics/Pause1.png")));

		fredPic = new Sprite(tBob);
		billPic = new Sprite(tBill);
		bobPic = new Sprite(tBob);
		bob = new Box2DSprite(Assets.bobPic);
		bill = new Box2DSprite(billPic);
		fred = new Box2DSprite(fredPic);

		reload();

		settings = Gdx.app.getPreferences("settings");
		name = settings.getString("name", "No name found.");

		TextureRegion[][] temp = TextureRegion.split(tSheet, 16, 16);
		sFrames = new TextureRegion[12];

		int index = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				sFrames[index++] = temp[i][j];

			}
		}
		cAnim = new Animation(0.25F, sFrames);

	}

	public static void reload() {
		double math = (Gdx.graphics.getWidth() * .1f) + (Gdx.graphics.getHeight() * .1f);
		float ssize = (Gdx.graphics.getDensity() * 150);
		int fontSize = Math.round(ssize);
		System.out.println(fontSize);
		// final float gooddWidth = Gdx.graphics.getWidth() * .09f;
		// final float goodHeight = Gdx.graphics.getHeight() * .13f;
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/GABRIOLA.TTF"));
		font = gen.generateFont(fontSize);
		gen.dispose();
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font.setColor(Color.valueOf("7a7d7d"));
		/*
		 * approximate 1920x1080 aspect ratio..1920/1080 or 1080/8 =
		 * 137.5....1920/x = 137.5
		 */

		// 1366 x 768 :: 1024 x 768
		// Assets.bob.scale(-.1f * ASPECT_RATIO);
		Assets.bill.setSize(12, 12);
		Assets.bob.setSize(12, 12);
		// bob.setSize(gooddWidth, goodHeight);
		// bill.setSize(gooddWidth, goodHeight);
	}

	public static void dispose() {
		firstSound.dispose();
		tBill.dispose();
		tBob.dispose();
		pauset.getTexture().dispose();
		tSheet.dispose();
		System.out.println("disp");
	}
}
