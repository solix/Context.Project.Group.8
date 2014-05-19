package com.taxi_trouble.game.screens;

import static com.taxi_trouble.game.properties.GameProperties.BUTTON_CAM_HEIGHT;
import static com.taxi_trouble.game.properties.GameProperties.BUTTON_CAM_WIDTH;

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
import com.taxi_trouble.game.input.ControlsUI;
import com.taxi_trouble.game.input.DriverControl;
import com.taxi_trouble.game.model.Taxi;
import com.taxi_trouble.game.model.WorldMap;
import com.taxi_trouble.game.properties.ResourceManager;

//THIS CLASS IS NO LONGER USED BUT IS KEPT FOR REFERENCE
public class GameScreen extends BasicScreen {
	private Taxi taxi;
	private World world;
	private TaxiCamera taxiCamera;
	private OrthographicCamera virtualButtonsCamera;
	private SpriteBatch spriteBatch;
	private ControlsUI controlsUI;
	private WorldMap map;
	private DriverControl driverControl;
	private Box2DDebugRenderer debugRenderer;

	@Override
	public void show() {
		this.virtualButtonsCamera = new OrthographicCamera();
		this.virtualButtonsCamera.setToOrtho(false, BUTTON_CAM_WIDTH, BUTTON_CAM_HEIGHT);
		spriteBatch = new SpriteBatch();

		// Box2d World init
		world = new World(new Vector2(0.0f, 0.0f), true);

		// Initialize the taxi
		this.taxi = new Taxi(2, 4, 20, 60, 60);
		taxi.createBody(world, new Vector2(10, 10), (float) Math.PI);
		
		//Load the Sprites
        ResourceManager.loadTaxiAndWheelSprites();
        taxi.setSprite(ResourceManager.taxiSprite,ResourceManager.wheelSprite);

        // Load the UI for player input
        this.controlsUI = new ControlsUI();
        this.driverControl = new DriverControl(taxi, controlsUI);
        Gdx.input.setInputProcessor(driverControl);

		taxiCamera = new TaxiCamera(taxi);
		spriteBatch = new SpriteBatch();

		debugRenderer = new Box2DDebugRenderer();

		// Load the map of the game
		ResourceManager.loadMap();
		map = new WorldMap(ResourceManager.mapFile, world);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		// Tell the camera to update its matrices.
		taxiCamera.update(map);

		spriteBatch.setProjectionMatrix(taxiCamera.combined);

		/*if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP))
			taxi.setAccelerate(Acceleration.ACC_ACCELERATE);
		else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN))
			taxi.setAccelerate(Acceleration.ACC_BRAKE);
		else
			taxi.setAccelerate(Acceleration.ACC_NONE);

		if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT))
			taxi.setSteer(SteerDirection.STEER_LEFT);
		else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT))
			taxi.setSteer(SteerDirection.STEER_RIGHT);
		else
			taxi.setSteer(SteerDirection.STEER_NONE);*/

		taxi.update(Gdx.app.getGraphics().getDeltaTime());

		world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);

		world.clearForces();
		map.render(taxiCamera);

		// Draw the sprites
		taxi.render(spriteBatch);

		/**
		 * Draw this last, so we can see the collision boundaries on top of the
		 * sprites and map.
		 */
		// debugRenderer.render(world,
		// taxiCamera.combined.scale(PIXELS_PER_METER,
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
		taxiCamera.updateViewPort(width, height);
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
