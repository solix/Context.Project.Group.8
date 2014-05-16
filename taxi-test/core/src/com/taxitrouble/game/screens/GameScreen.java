package com.taxitrouble.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
<<<<<<< HEAD:taxi-test/core/src/com/mygdx/game/screens/GameScreen.java
import com.mygdx.game.Car;
import com.mygdx.game.input.ControlsUI;
import com.mygdx.game.input.DriverControl;
import com.mygdx.game.properties.GameProperties;
import com.mygdx.game.properties.ResourceManager;
import com.mygdx.game.world.CarCamera;
import com.mygdx.game.world.WorldMap;
=======
import com.badlogic.gdx.utils.viewport.Viewport;
import com.taxitrouble.game.Car;
import com.taxitrouble.game.input.ControlsUI;
import com.taxitrouble.game.properties.ResourceManager;
import com.taxitrouble.game.world.CarCamera;
import com.taxitrouble.game.world.WorldMap;
>>>>>>> sprint2_refactor:taxi-test/core/src/com/taxitrouble/game/screens/GameScreen.java

public class GameScreen extends BasicScreen {
	private Car car;
	private World world;
	private CarCamera carCamera;
	private OrthographicCamera virtualButtonsCamera;
	private SpriteBatch spriteBatch;
	private ControlsUI controlsUI;
	private WorldMap map;
	private Box2DDebugRenderer debugRenderer;
	private DriverControl driverControl;

	@Override
	public void show() {
		this.virtualButtonsCamera = new OrthographicCamera();

		this.virtualButtonsCamera.setToOrtho(false,
				GameProperties.BUTTON_CAM_WIDTH,
				GameProperties.BUTTON_CAM_HEIGHT);

		spriteBatch = new SpriteBatch();

		// Box2d World init
		world = new World(new Vector2(0.0f, 0.0f), true);

		// Initialize the taxi
		this.car = new Car(world, 2, 4, new Vector2(10, 10), (float) Math.PI,
				60, 20, 60);

		// Load the UI for player input
		this.controlsUI = new ControlsUI();
		this.driverControl = new DriverControl(car, controlsUI);
		Gdx.input.setInputProcessor(driverControl);

		carCamera = new CarCamera(car);
		spriteBatch = new SpriteBatch();

		debugRenderer = new Box2DDebugRenderer();

		// Load the map of the game
		map = new WorldMap(ResourceManager.mapFile, world);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		// Tell the camera to update its matrices.
		carCamera.update(map);

		spriteBatch.setProjectionMatrix(carCamera.combined);

		// if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP))
		// car.accelerate = Car.ACC_ACCELERATE;
		// else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN))
		// car.accelerate = Car.ACC_BRAKE;
		// else
		// car.accelerate = Car.ACC_NONE;
		//
		// if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT))
		// car.steer = Car.STEER_LEFT;
		// else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT))
		// car.steer = Car.STEER_RIGHT;
		// else
		// car.steer = Car.STEER_NONE;

		car.update(Gdx.app.getGraphics().getDeltaTime());

		/**
		 * Have box2d update the positions and velocities (and etc) of all
		 * tracked objects. The second and third argument specify the number of
		 * iterations of velocity and position tests to perform -- higher is
		 * more accurate but is also slower.
		 */
		world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);

		world.clearForces();
		map.render(carCamera);

		// Draw the sprites
		drawSprites();

		/**
		 * Draw this last, so we can see the collision boundaries on top of the
		 * sprites and map.
		 */
		// debugRenderer.render(world,
		// carCamera.combined.scale(PIXELS_PER_METER,
		// PIXELS_PER_METER, PIXELS_PER_METER));

		virtualButtonsCamera.update();
		spriteBatch.setProjectionMatrix(virtualButtonsCamera.combined);
		controlsUI.render(spriteBatch);
	}

	public void drawSprites() {
		spriteBatch.begin();
		Array<Body> tmpBodies = new Array<Body>();
		world.getBodies(tmpBodies);
		for (Body body : tmpBodies) {
			if (body.getUserData() != null
					&& body.getUserData() instanceof Sprite) {
				Sprite sprite = (Sprite) body.getUserData();
				sprite.setPosition(body.getPosition().x * PIXELS_PER_METER,
						body.getPosition().y * PIXELS_PER_METER);
				sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
				sprite.setScale(PIXELS_PER_METER);
				sprite.draw(spriteBatch);
			}
		}
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		carCamera.updateViewPort(width, height);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

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
	public void dispose() {
		spriteBatch.dispose();
	}
}
