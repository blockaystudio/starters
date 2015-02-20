package com.blake.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Orientation;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen { // ScreenAdapter

	/*
	 * short, long, float, double, char, int, byte, boolean Vector2,3 ArrayList
	 * LinkedList Array List Casted Array<Bodies>
	 */

	private learnGame game;
	OrthographicCamera camera;
	private Bodies world;
	Random rand;
	Buttons buttons;
	Vector3 touch;
	static InputMultiplexer multiplexer = new InputMultiplexer();
	/*
	 * InputMultiplexer multiplexer = new InputMultiplexer(); multiple
	 * inputprocessors!!!
	 */
	float sTime = 0f;
	float delta;
	private static boolean killBody;
	private static boolean paused;

	public GameScreen(learnGame learngame) {
		this.game = learngame;

		camera = new OrthographicCamera();
		Gdx.input.setInputProcessor(multiplexer);
		buttons = new Buttons();
		world = new Bodies(camera);
		touch = new Vector3();
		game.batch = new SpriteBatch();
		rand = new Random();
		camera.setToOrtho(false, 80, 48);
		camera.update();

		System.out.println("Aspect ratio:" + (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight() + "     "
				+ Gdx.graphics.getWidth() + "x" + Gdx.graphics.getHeight());
		/*
		 * switch (Gdx.app.getType()) { case Desktop: screenButtons = false;
		 * break; case Default: //For ANDROID!! screenButtons = true; break;
		 */
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.delta = delta;

		/*
		 * fred.setTransform(fred.getLocalCenter().x, fred.getLocalCenter().y,
		 * 2); //fred.applyAngularImpulse(20, true); //fred.applyTorque(5000,
		 * true); //fred.setTransform(fred.getPosition().x,
		 * fred.getPosition().y, inte++); //
		 * body.applyForce(1,3,body.getPosition().x,body.getPosition().y,true);
		 * // fred.applyForceToCenter(5,2,true); /
		 * body.applyLinearImpulse(10,10,
		 * body.getPosition().x,body.getPosition().y,true);
		 */

		sTime += Gdx.graphics.getDeltaTime();
		Assets.cFrame = Assets.cAnim.getKeyFrame(sTime, true);

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();

		if (GameScreen.paused) {
			game.batch.draw(Assets.pauset, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

			if (Gdx.input.isKeyJustPressed(Keys.SPACE))
				GameScreen.paused = false;

		} else {
			/*
			 * for (Body bod : bodies) { if (bod.getUserData() instanceof
			 * Sprite) { Sprite sprite = (Sprite) bod.getUserData();
			 * sprite.setOriginCenter(); sprite.setRotation((float)
			 * Math.toDegrees(bod.getAngle()));
			 * 
			 * sprite.setCenter(bod.getPosition().x, bod.getPosition().y);
			 * sprite.draw(game.batch); } }
			 */
			Assets.font.draw(game.batch, Assets.name, 400, 400);
			game.batch.draw(Assets.cFrame, 0, 0);
			Assets.bob.draw(game.batch, world.bob);
			Assets.fred.draw(game.batch, world.fred);
			Assets.bill.draw(game.batch, world.bill);

			// Assets.bob.setOriginCenter();
			// Assets.bill.setRotation((float)
			// Math.toDegrees(world.fred.getAngle()));
			// Assets.bob.setCenter(world.fred.getPosition().x,
			// world.fred.getPosition().y);

			/*
			 * Create as many images as you need!! no modifying the image though
			 * // game.batch.draw(Assets.bill, body.getPosition().x - 64,
			 * body.getPosition().y - 64);
			 */
		}
		game.batch.end();
		Buttons.stage.draw();
		world.box2d.step(1 / 60f, 8, 3);
		for (Fixture fixture : Bodies.fixturesToDestroy) {
			if (Bodies.joint != null) {
				world.box2d.destroyJoint(Bodies.joint);
				Bodies.joint = null;
			}
			fixture.getBody().destroyFixture(fixture);
			Bodies.fixturesToDestroy.removeValue(fixture, true);
		}
		for (Body body : Bodies.bodiesToDestroy) {
			world.box2d.destroyBody(body);
			Bodies.bodiesToDestroy.removeValue(body, true);
		}
		removeBodySafely(world.groundBody);

		world.debugRenderer.render(world.box2d, camera.combined);
		generalUpdate(delta, touch, camera);

	}

	// /Gdx.input.setOnscreenKeyboardVisible(true)///
	public void generalUpdate(float delta, Vector3 touch, OrthographicCamera camera) {

		if (Gdx.input.justTouched()) {
			Gdx.input.vibrate(new long[] { 200, 200 }, 1);
		}
		if (Gdx.input.isTouched()) {
			touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touch);
			// world.bill.setTransform(touch.x, touch.y, 0);
		}

		if (Gdx.input.isKeyJustPressed(Keys.ALT_RIGHT) || Gdx.input.isTouched(1)) {
			switch (rand.nextInt(3)) {
			case 0:
				Assets.settings.putString("name", "blake");
				break;
			case 1:
				Assets.settings.putString("name", "kaleb");
				break;
			case 2:
				Assets.settings.putString("name", "tom");
				break;
			}
			Assets.settings.flush();
			Assets.name = Assets.settings.getString("name", "No name found.");
		}

		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			GameScreen.paused = true;
		}
		if (Gdx.input.isKeyPressed(Keys.ALT_LEFT)) {

			game.setScreen(new splashScreen(game));
		}

		moveSprites();

	}

	@Override
	public void show() {
		Assets.firstSound.play();

	}

	@Override
	public void resize(int width, int height) {
		// killBody = true;
		Assets.reload();
		// should create a 800x480 on all devices, maybe not lower resolutions.
		// PPU pixels per unit
		// Camera height / world height pixels per world unit

		System.out.println(Gdx.graphics.getDensity());
		// Array<Fixture> dthis = groundBody.getFixtureList();

		// world.groundBody.createFixture(world.groundBox, 0f);
		// world.groundBody.setAwake(false);

		game.batch.dispose();
		game.batch = new SpriteBatch();

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		world.box2d.dispose();
		game.batch.dispose();
		Buttons.stage.dispose();
		System.out.println("disposed");
	}

	public void removeBodySafely(Body body) {
		if (GameScreen.killBody) {
			// to prevent some obscure c assertion that happened randomly once
			// in a blue moon
			final Array<JointEdge> list = body.getJointList();
			while (list.size > 0) {
				world.box2d.destroyJoint(list.get(0).joint);
			}
			// actual remove
			world.box2d.destroyBody(body);
			GameScreen.killBody = false;
		}
	}

	public void moveSprites() {
		if (Gdx.input.isKeyPressed(Keys.A) || (Gdx.input.isKeyPressed(Keys.LEFT))) {
			world.bob.setLinearVelocity(-5, 0);
			camera.position.x -= 1;
			// bill.bounds.x -= 150 * delta;
		}
		if (Gdx.input.isKeyPressed(Keys.D) || (Gdx.input.isKeyPressed(Keys.RIGHT))) {
			world.bob.setLinearVelocity(5, 0);
			camera.position.x += 1;
			// bill.bounds.x += 150 * delta;
		}
		if (Gdx.input.isKeyPressed(Keys.W) || (Gdx.input.isKeyPressed(Keys.UP))) {
			world.bob.setLinearVelocity(0, 5);
			camera.position.y += 1;
			// bill.bounds.y -= 150 * delta;
		}
		if (Gdx.input.isKeyPressed(Keys.S) || (Gdx.input.isKeyPressed(Keys.DOWN))) {
			// bill.bounds.y += 150 * delta;
			world.bob.setLinearVelocity(0, -5);
			camera.position.y -= 1;
		}
		camera.update();
		Gdx.input.getNativeOrientation();
		if (Orientation.Portrait != null) {
			// world.body.setLinearVelocity(vX, vY); += 1.5 *
			// Gdx.input.getAccelerometerX();
			// bob.bounds.y -= 1.5 * Gdx.input.getAccelerometerY();
		} else {
			world.bob.setLinearVelocity(0, 2 * Gdx.input.getAccelerometerX());
			// bob.bounds.x += 1.5 * Gdx.input.getAccelerometerX();
			// bob.bounds.y -= 1.5 * Gdx.input.getAccelerometerY();
		}
	}
}
