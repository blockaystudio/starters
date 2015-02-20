package com.blake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Orientation;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class splashScreen implements Screen {
	OrthographicCamera camera;
	learnGame g;
	private float sTime;

	splashScreen(learnGame game) {
		g = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(128, 0, 127.6f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sTime += Gdx.graphics.getDeltaTime();
		// camera.setToOrtho(false,Gdx.graphics.getWidth(),
		// Gdx.graphics.getHeight());
		// camera.update();
		Assets.cFrame = Assets.cAnim.getKeyFrame(sTime, true);
		// Gdx.input.setOnscreenKeyboardVisible(true);
		batch();
		getInput();

	}

	@Override
	public void resize(int width, int height) {
		Assets.reload();
		g.batch.dispose();
		g.batch = new SpriteBatch();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
		System.out.println("hiding");
	}

	@Override
	public void dispose() {
		g.batch.dispose();

	}

	public void batch() {
		g.batch.begin();
		g.batch.draw(Assets.cFrame, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		g.font = Assets.font;
		g.font.setColor(0, 100, 125, 128);
		//g.font.setScale(.6f, .6f);
		g.font.draw(g.batch, "Welcome to my game!",
				(Gdx.graphics.getWidth() / 2) - (g.font.getBounds("Welcome to my game!").width / 2),
				Gdx.graphics.getHeight() / 2 + 80);
		g.font.draw(g.batch, "Press any key to start!",
				(Gdx.graphics.getWidth() / 2) - (g.font.getBounds("Welcome to my game!").width / 2),
				Gdx.graphics.getHeight() / 2);

		g.batch.end();
	}

	public void getInput() {
		if (Gdx.input.isKeyPressed(Keys.ANY_KEY) || Gdx.input.isTouched()) {
			// Gdx.input.setOnscreenKeyboardVisible(false);
			g.setScreen(new GameScreen(g));

		}
	}
}
