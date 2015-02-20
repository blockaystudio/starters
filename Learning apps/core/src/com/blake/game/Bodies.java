package com.blake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.utils.Array;

public class Bodies extends InputAdapter implements ContactListener {

	Body bob;
	Body fred;
	Body bill;
	Body groundBody;
	World box2d = new World(new Vector2(0, -30), true);
	private PolygonShape groundBox = new PolygonShape();
	private PolygonShape dynamicBox = new PolygonShape();
	private CircleShape circle = new CircleShape();
	private BodyDef bobDef = new BodyDef();
	private BodyDef fredDef = new BodyDef();
	private BodyDef billDef = new BodyDef();
	private BodyDef groundDef = new BodyDef();
	private FixtureDef boxDef = new FixtureDef();
	private FixtureDef gndDef = new FixtureDef();
	private FixtureDef cirDef = new FixtureDef();
	Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	private Fixture myfix;
	private MouseJointDef mouseJoint = new MouseJointDef();
	static MouseJoint joint;
	private OrthographicCamera camera;
	private Vector3 touch = new Vector3();
	static Array<Fixture> fixturesToDestroy = new Array<Fixture>();
	static Array<Body> bodiesToDestroy = new Array<Body>();

	public Bodies(OrthographicCamera camera) {
		this.camera = camera;
		GameScreen.multiplexer.addProcessor(this);
		box2d.setContactListener(this);
		float h = camera.viewportHeight;
		float w = camera.viewportWidth;
		ChainShape groundShape = new ChainShape();
		Vector3 upLeft = new Vector3(0, h, 0), upRight = new Vector3(w, upLeft.y, 0);
		Vector3 botLeft = new Vector3(0, 0, 0), botRight = new Vector3(w, botLeft.y, 0);
		Vector3 test = new Vector3(Gdx.graphics.getWidth(), 0, 0);
		// Vector3 upLeft = new Vector3(0, Gdx.graphics.getHeight(), 0), upRight
		// = new Vector3(Gdx.graphics.getWidth(),
		// upLeft.y, 0);
		// Vector3 botLeft = new Vector3(0, 0, 0), botRight = new
		// Vector3(upRight.x, 0, 0);
		System.out.println(test.x + " " + test.y);
		camera.unproject(test);
		System.out.println(test.x + " " + test.y);
		// camera.unproject(botLeft);
		// camera.unproject(upRight);
		// camera.unproject(upLeft);
		System.out.println(upLeft.x + " " + upLeft.y);
		groundShape.createLoop(new float[] { upLeft.x, upLeft.y, botLeft.x, botLeft.y, botRight.x, botRight.y,
				upRight.x, upRight.y });
		bobDef.type = BodyType.DynamicBody;
		fredDef.type = BodyType.DynamicBody;
		billDef.type = BodyType.DynamicBody;
		billDef.position.set(20, 20);
		billDef.fixedRotation = true;
		bobDef.position.set(10, 20);
		bobDef.fixedRotation = true;
		fredDef.position.set(30, 31);
		fredDef.fixedRotation = true;

		dynamicBox.setAsBox(.5f, 1);
		circle.setRadius(.5f);

		boxDef.shape = dynamicBox;
		boxDef.density = .1f;
		boxDef.friction = 0f;
		boxDef.restitution = 1f;
		cirDef.shape = circle;
		cirDef.density = .1f;
		gndDef.shape = groundShape;
		gndDef.friction = .5f;
		gndDef.restitution = 0;

		// Body tom = box2d.createBody();
		// tom.setType(new BodyDef().type.DynamicBody);

		fred = box2d.createBody(fredDef);
		bob = box2d.createBody(bobDef);
		bill = box2d.createBody(billDef);
		groundBody = box2d.createBody(groundDef);
		// groundBody.setTransform(groundBody.getPosition().x,
		// groundBody.getPosition().y, 3);
		mouseJoint.bodyA = groundBody;
		mouseJoint.collideConnected = true;
		mouseJoint.maxForce = 500;
		groundDef.type = BodyType.StaticBody;
		groundDef.position.set(0, 0);

		box2d.createBody(groundDef).createFixture(gndDef);

		// groundBody.setType(BodyType.DynamicBody);
		// bob.createFixture(new FixtureDef().shape = dynamicBox, 0);
		bob.createFixture(boxDef).setUserData(new Breakable(1, 1));

		// bob.createFixture(circle, 1);
		fred.createFixture(boxDef).setUserData(new Breakable(.5f, .5f));

		bill.createFixture(cirDef).setUserData(new Breakable(.001f, .001f));
		fred.applyForce(200, 0, 0, 0, false);
		// fred.applyLinearImpulse(20, 0, fred.getLocalCenter().x,
		// fred.getLocalCenter().y, true);
		dynamicBox.dispose();
		circle.dispose();
		groundShape.dispose();

	}

	public static class Breakable {
		public float normResis;
		public float tangResis;

		public Breakable(float normResis, float tangResis) {
			this.normResis = normResis;
			this.tangResis = tangResis;
		}

	}

	private static void strain(Fixture fixture, ContactImpulse impulse) {
		if (!(fixture.getUserData() instanceof Breakable))
			return;
		// System.out.println(impulse.getNormalImpulses());
		Breakable breakable = (Breakable) fixture.getUserData();
		if (breakable.normResis > sum(impulse.getNormalImpulses())
				|| breakable.tangResis > sum(impulse.getTangentImpulses()))
			return;
		destroy(fixture);

	}

	public static float sum(float[] vals) {
		float sum = 0;
		for (float value : vals)
			sum += value;
		return sum;
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		Fixture fixtureA = contact.getFixtureA(), fixtureB = contact.getFixtureB();
		if (fixtureA.getBody().getType() == BodyType.DynamicBody) {
			strain(fixtureA, impulse);
		} else {
			strain(fixtureB, impulse);
		}
	}

	private static void destroy(Fixture fixture) {
		fixturesToDestroy.add(fixture);
		if (fixture.getBody().getFixtureList().size - 1 < 1) {
			bodiesToDestroy.add(fixture.getBody());
		}
	}

	private void destroySmaller(Fixture fixA, Fixture fixB) {
		if (fixA.getBody().getMass() > fixB.getBody().getMass())
			fixturesToDestroy.add(fixB);
		else
			fixturesToDestroy.add(fixA);
	}

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	private QueryCallback callback = new QueryCallback() {

		@Override
		public boolean reportFixture(Fixture fixture) {
			if (!fixture.testPoint(touch.x, touch.y))
				return true;
			myfix = fixture;
			mouseJoint.bodyB = fixture.getBody();
			mouseJoint.target.set(touch.x, touch.y);
			joint = (MouseJoint) box2d.createJoint(mouseJoint);
			return false;
		}

	};
	private Vector2 tmp = new Vector2();

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		camera.unproject(touch.set(screenX, screenY, 0));
		box2d.QueryAABB(callback, touch.x, touch.y, touch.x, touch.y);
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (joint == null)
			return false;
		camera.unproject(touch.set(screenX, screenY, 0));
		joint.setTarget(tmp.set(touch.x, touch.y));
		System.out.println("draggin");
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (joint == null)
			return false;
		box2d.destroyJoint(joint);
		joint = null;
		return true;
	}

}
