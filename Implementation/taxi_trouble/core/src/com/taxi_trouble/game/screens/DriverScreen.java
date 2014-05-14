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
	GameWorld game;
	TaxiCamera taxiCamera;
	Taxi taxi;
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
		this.game = game;
	}
	
    @Override
    public void show() {
        this.virtualButtonsCamera = new OrthographicCamera();
        virtualButtonsCamera.setToOrtho(false, screenWidth, screenHeight);
        spriteBatch = new SpriteBatch();
        debugRenderer = new Box2DDebugRenderer();

        // Initialize the taxi
        this.taxi = game.taxi;
        
        this.taxiCamera = new TaxiCamera(taxi);

        // Load the UI for player input
        this.controlsUI = new ControlsUI(taxi);
        Gdx.input.setInputProcessor(controlsUI);
        // Load the map of the game
        cityMap = new WorldMap(ResourceManager.mapFile, game.world);

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
		game.world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);

		game.world.clearForces();
		cityMap.render(taxiCamera);

		// Draw the sprites
		drawSprites();
		
		debugRenderer.render(game.world, taxiCamera.combined.scale(PIXELS_PER_METER,
		      PIXELS_PER_METER, PIXELS_PER_METER));
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
		game.world.getBodies(tmpBodies);
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
