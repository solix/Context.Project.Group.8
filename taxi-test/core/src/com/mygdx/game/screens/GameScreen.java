package com.mygdx.game.screens;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.BoxProp;
import com.mygdx.game.Car;
import com.mygdx.game.ui.ControlsUI;

public class GameScreen extends BasicScreen {
	private Car car;
	private World world;
	private OrthographicCamera carCamera;
	private OrthographicCamera virtualButtonsCamera;
	private SpriteBatch spriteBatch;
	private Box2DDebugRenderer debugRenderer;
	private ControlsUI controlsUI;
	private TiledMap tileMap;
	private OrthogonalTiledMapRenderer tmrenderer;

	@Override
	public void show() {
		this.virtualButtonsCamera = new OrthographicCamera();
		virtualButtonsCamera.setToOrtho(false, screenWidth, screenHeight);
		spriteBatch = new SpriteBatch();

		// Box2d World init
		world = new World(new Vector2(0.0f, 0.0f), true);

		// Initialize the taxi
		this.car = new Car(world, 2, 4, new Vector2(10, 10), (float) Math.PI,
				60, 20, 60);

		this.controlsUI = new ControlsUI(car);
		Gdx.input.setInputProcessor(controlsUI);

		carCamera = new OrthographicCamera();
		carCamera.setToOrtho(false, screenWidth, screenHeight);
		spriteBatch = new SpriteBatch();

		debugRenderer = new Box2DDebugRenderer();

		// load the map
		tileMap = new TmxMapLoader().load("maps/test_map2.tmx");
		tmrenderer = new OrthogonalTiledMapRenderer(tileMap);

		// load the map objects
		MapLayer layer = tileMap.getLayers().get("box2D");
		MapObjects objects = layer.getObjects();

		Iterator<MapObject> obj_iterator = objects.iterator();
		while (obj_iterator.hasNext()) {
			MapObject obj = obj_iterator.next();
			if (obj instanceof RectangleMapObject) {
				Rectangle r = ((RectangleMapObject) obj).getRectangle();
				BoxProp solid = new BoxProp(world, (r.getWidth())
						/ PIXELS_PER_METER, (r.getHeight()) / PIXELS_PER_METER,
						new Vector2(r.getX() / PIXELS_PER_METER + r.getWidth()
								/ (2 * PIXELS_PER_METER), r.getY()
								/ PIXELS_PER_METER + r.getHeight()
								/ (2 * PIXELS_PER_METER)));
			}
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		// set the camera to follow the taxi
		carCamera.position.set(car.body.getPosition().x * PIXELS_PER_METER,
				car.body.getPosition().y * PIXELS_PER_METER, 0);

		/**
		 * Ensure the camera only shows the contents of the map and nothing
		 * outside it.
		 * 
		 */
		int mapPixelWidth = tileMap.getProperties().get("width", Integer.class)
				* tileMap.getProperties().get("tilewidth", Integer.class);
		int mapPixelHeight = tileMap.getProperties().get("height",
				Integer.class)
				* tileMap.getProperties().get("tileheight", Integer.class);

		// Check if the camera is near the left border of the map
		if (carCamera.position.x < Gdx.graphics.getWidth() / 2) {
			carCamera.position.x = Gdx.graphics.getWidth() / 2;
		}
		// Check if the camera is near the right border of the map
		if (carCamera.position.x >= mapPixelWidth - Gdx.graphics.getWidth() / 2) {
			carCamera.position.x = mapPixelWidth - Gdx.graphics.getWidth() / 2;
		}
		// Check if the camera is near the bottom border of the map
		if (carCamera.position.y < Gdx.graphics.getHeight() / 2) {
			carCamera.position.y = Gdx.graphics.getHeight() / 2;
		}
		// Check if the camera is near the top border of the map
		if (carCamera.position.y >= mapPixelHeight - Gdx.graphics.getHeight()
				/ 2) {
			carCamera.position.y = mapPixelHeight - Gdx.graphics.getHeight()
					/ 2;
		}

		// tell the camera to update its matrices.
		carCamera.update();

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

		// draw the map
		tmrenderer.setView(carCamera);
		tmrenderer.render();

		// draw the sprites
		drawSprites();

		/**
		 * Draw this last, so we can see the collision boundaries on top of the
		 * sprites and map.
		 */
		debugRenderer.render(world, carCamera.combined.scale(PIXELS_PER_METER,
				PIXELS_PER_METER, PIXELS_PER_METER));

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
		carCamera.viewportWidth = screenWidth;
		carCamera.viewportHeight = screenHeight;
		carCamera.update();
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
