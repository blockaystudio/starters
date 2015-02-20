package com.blake.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//public class learnGame extends ApplicationAdapter {
public class learnGame extends Game {

	// public GameScreen gameScreen;
	public SpriteBatch batch;
	public BitmapFont font;

	public void create() {
		Assets.load();
		batch = new SpriteBatch();
		font = new BitmapFont();

		setScreen(new splashScreen(this));

	}

	public void dispose() {
		batch.dispose();
		font.dispose();
		Assets.dispose();
	}

}
/*
 * public void render() { /*Gdx.gl.glClearColor(1, 0, 0, 1);
 * Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); batch.begin();
 * batch.draw(Assets.img, 0,
 * 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight()); batch.end();
 */

/*
 * SpriteBatch batch; Texture img;
 * 
 * @Override public void create () { batch = new SpriteBatch(); img = new
 * Texture("badlogic.jpg"); }
 * 
 * @Override public void render () { Gdx.gl.glClearColor(1, 0, 0, 1);
 * Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); batch.begin(); batch.draw(img, 0,
 * 0); batch.end(); } }
 */
