package com.taxi_trouble.game.screens;

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
import com.badlogic.gdx.utils.viewport.Viewport;
import com.taxi_trouble.game.input.ControlsUI;
/**
 * This Class will create screen viewer for driver It follows the same dimension screen as its ViewObserver
 */
import com.taxi_trouble.game.model.GameWorld;
import com.taxi_trouble.game.model.Taxi;
import com.taxi_trouble.game.model.WorldMap;
import com.taxi_trouble.game.properties.ResourceManager;

public class DriverScreen extends ViewObserver {
	Taxi taxi;
	World world;
	TaxiCamera taxiCamera;
	OrthographicCamera virtualButtonsCamera;
	SpriteBatch spriteBatch;
	ControlsUI controlsUI;
	WorldMap cityMap;
	Box2DDebugRenderer debugRenderer;
	Viewport viewport;

	/**
	 * Constructor creates game screen and adds camera to follow taxi.
	 * 
	 * @param game
	 */
	public DriverScreen(GameWorld game) {
		super(game);
		this.taxiCamera = new TaxiCamera(taxi);
		spriteBatch = new SpriteBatch();
		debugRenderer = new Box2DDebugRenderer();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		// Tell the camera to update its matrices.
		taxiCamera.update(cityMap);
		spriteBatch.setProjectionMatrix(taxiCamera.combined);
		taxi.update(Gdx.app.getGraphics().getDeltaTime());
		/**
		 * Have box2d update the positions and velocities (and etc) of all
		 * tracked objects. The second and third argument specify the number of
		 * iterations of velocity and position tests to perform -- higher is
		 * more accurate but is also slower.
		 */
		world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);

		world.clearForces();
		cityMap.render(taxiCamera);

		// Draw the sprites
		drawSprites();
	}

	@Override
	public void show() {
		this.virtualButtonsCamera = new OrthographicCamera();
		virtualButtonsCamera.setToOrtho(false, screenWidth, screenHeight);
		spriteBatch = new SpriteBatch();

		// Box2d World init
		world = new World(new Vector2(0.0f, 0.0f), true);

		// Initialize the taxi
		this.taxi = new Taxi(world, 2, 4, new Vector2(10, 10), (float) Math.PI,
				60, 20, 60);

		// Load the UI for player input
		this.controlsUI = new ControlsUI(taxi);
		Gdx.input.setInputProcessor(controlsUI);
		// Load the map of the game
		cityMap = new WorldMap(ResourceManager.mapFile, world);

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

	@Override
	public void resize(int width, int height) {
		taxiCamera.updateViewPort(width, height);

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

}
