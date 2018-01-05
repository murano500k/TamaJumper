package com.stc.game2d.box2d;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.stc.game2d.Config;
import com.stc.game2d.box2d.utils.Box2DFactory;
import com.stc.game2d.utils.Sample;

public class CollisionsSample extends Sample implements ContactListener {

	/* Use Box2DDebugRenderer, which is a model renderer for debug purposes */
	private Box2DDebugRenderer debugRenderer;

	/* As always, we need a camera to be able to see the objects */
	private OrthographicCamera camera;

	/* Define a world to hold all bodies and simulate reactions between them */
	private World world;

	private Body walls;

	private boolean ballTouchedWall;
	private boolean ballTouchedBox;

	/* Fields to store previous accelerometer values in each iteration */
	private float prevAccelX;
	private float prevAccelY;
	private Body ball;
	private Body box;

	/**
	 * Main constructor used to update sample name.
	 */
	public CollisionsSample() {
		name = "Collisions";
	}

	@Override
	public void render(float delta) {
		/*
		 * Clear screen with a black background. Use red instead if the ball
		 * touched a wall or blue if touched the box.
		 */
		if (ballTouchedWall) {
			Gdx.gl.glClearColor(0.6f, 0, 0, 1);
		} else if (ballTouchedBox) {
			Gdx.gl.glClearColor(0, 0, 0.6f, 1);
		} else {
			Gdx.gl.glClearColor(0, 0, 0, 1);
		}
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		/* Check if we should change the gravity */
		processAccelerometer();

		/* Render all graphics before do physics step */
		debugRenderer.render(world, camera.combined);

		/* Step the simulation with a fixed time step of 1/60 of a second */
		world.step(1 / 60f, 6, 2);
	}

	@Override
	public void show() {
		/*
		 * This line is found in every sample but is not necessary for the
		 * sample functionality. calls Sample.show() method. That method set the
		 * sample to receive all touch and key input events. Also prevents the
		 * app from be closed whenever the user press back button and instead
		 * returns to main menu.
		 */
		super.show();

		/*
		 * Create world with a common gravity vector (9.81 m/s2 downwards force)
		 * and tell world that we want objects to sleep. This last value
		 * conserves CPU usage. As we use the accelerometer and the world
		 * gravity to change bodies positions, we can't let bodies to sleep.
		 */
		world = new World(new Vector2(0, -9.81f* Config.GRAVITY_MULTIPLIER), false);

		/* Create renderer */
		debugRenderer = new Box2DDebugRenderer();

		/*
		 * Define camera viewport. Box2D uses meters internally so the camera
		 * must be defined also in meters. We set a desired width and adjust
		 * height to different resolutions.
		 */
		camera = new OrthographicCamera(20,
				20 * (Gdx.graphics.getHeight() / (float) Gdx.graphics
						.getWidth()));

		/* Create the ball */
		Shape shape = Box2DFactory.createCircleShape(1);
		FixtureDef fixtureDef = Box2DFactory.createFixture(shape, 2.5f, 0.25f,
				0.75f, false);
		ball = Box2DFactory.createBody(world, BodyDef.BodyType.DynamicBody, fixtureDef,
				new Vector2(5, 0));

		/* Create the box */
		shape = Box2DFactory.createBoxShape(0.5f, 0.5f, new Vector2(0, 0), 0);
		fixtureDef = Box2DFactory.createFixture(shape, 1, 0.5f, 0.5f, false);
		box = Box2DFactory.createBody(world, BodyDef.BodyType.StaticBody, fixtureDef,
				new Vector2(0, 0));

		/* Create the walls */
		walls = Box2DFactory.createWalls(world, camera.viewportWidth,
				camera.viewportHeight, 1);

		world.setContactListener(this);
	}

	@Override
	public void dispose() {
		debugRenderer.dispose();
		world.dispose();
	}

	private void processAccelerometer() {

		/* Get accelerometer values */
		float y = Gdx.input.getAccelerometerY()* Config.GRAVITY_MULTIPLIER;
		float x = Gdx.input.getAccelerometerX()* Config.GRAVITY_MULTIPLIER;

		/*
		 * If accelerometer values have changed since previous processing,
		 * change world gravity.
		 */
		if (prevAccelX != x || prevAccelY != y) {

			/* Negative on the x axis but not in the y */
			world.setGravity(new Vector2(y, -x));

			/* Store new accelerometer values */
			prevAccelX = x;
			prevAccelY = y;
		}
	}

	@Override
	public void beginContact(Contact contact) {

		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();

		/*
		 * If one of the fixture contacting is an static body, and is the wall,
		 * set ballTouchedWall to true.
		 */
		boolean lastTouchedBox=ballTouchedBox;
		boolean lastTouchedWall=ballTouchedWall;
		if (fixtureA.getBody().getType() == BodyDef.BodyType.StaticBody) {
			if (fixtureA.getBody().equals(walls)) {
				ballTouchedBox = false;
				ballTouchedWall = true;
			} else {
				ballTouchedBox = true;
				ballTouchedWall = false;
			}
		} else if (fixtureB.getBody().getType() == BodyDef.BodyType.StaticBody) {
			if (fixtureA.getBody().equals(walls)) {
				ballTouchedBox = false;
				ballTouchedWall = true;
			} else {
				ballTouchedBox = true;
				ballTouchedWall = false;
			}
		}
		if(lastTouchedWall && ballTouchedWall) ballTouchedWall=false;
	}

	@Override
	public void endContact(Contact contact) {
		/*if(prevAccelX>0) prevAccelX+=Config.COLLISION_ACCELERATION;
		else prevAccelX-=Config.COLLISION_ACCELERATION;
		if(prevAccelY>0) prevAccelX+=Config.COLLISION_ACCELERATION;
		else prevAccelY-=Config.COLLISION_ACCELERATION;
		world.setGravity(new Vector2(prevAccelY, -prevAccelX));

		ball.applyLinearImpulse(new Vector2(10.0f, 10.0f), ball.getWorldCenter(),
				true);*/
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
			Vector2 pos;
			//if(ballTouchedWall)pos=box.getPosition();
			pos=ball.getPosition();
			Vector2 velocity = ball.getLinearVelocity();
			if(ballTouchedBox){

				if(velocity.x>0)velocity.x+=Config.COLLISION_ACCELERATION;
				else velocity.x-=Config.COLLISION_ACCELERATION;

				if(velocity.y>0)velocity.y+=Config.COLLISION_ACCELERATION;
				else velocity.y-=Config.COLLISION_ACCELERATION;
				velocity.x*=Config.COLLISION_MULTIPLIER;
				velocity.y*=Config.COLLISION_MULTIPLIER;
			}

			ball.applyLinearImpulse(velocity, pos , true);
	}

}
