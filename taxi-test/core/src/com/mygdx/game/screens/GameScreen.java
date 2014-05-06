package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.BoxProp;
import com.mygdx.game.Car;
import com.mygdx.game.input.TouchInputProcessor;

public class GameScreen extends BasicScreen implements Screen {
	private Car car;
	private World world;
	private OrthographicCamera camera;
	private SpriteBatch spriteBatch;
	private Box2DDebugRenderer debugRenderer;
	private Stage stage;
	private InputMultiplexer multiplexer;
	private TouchInputProcessor touchInput;
	private TiledMap tileMap;
	private OrthogonalTiledMapRenderer tmrenderer;

	@Override
	public void show() {
		multiplexer = new InputMultiplexer();
		touchInput = new TouchInputProcessor();
		stage = new Stage();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(touchInput);
		Gdx.input.setInputProcessor(multiplexer);

		// Box2d World init
		world = new World(new Vector2(0.0f, 0.0f), true);

		this.car = new Car(world, 2, 4, new Vector2(10, 10), (float) Math.PI,
				60, 20, 60);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, screenWidth, screenHeight);
		spriteBatch = new SpriteBatch();

		debugRenderer = new Box2DDebugRenderer();

		Vector2 center = new Vector2(worldWidth / 2, worldHeight / 2);

		// pen in the center
		/*BoxProp pen1 = new BoxProp(world, 1, 6, new Vector2(center.x - 3,
				center.y));
		BoxProp pen2 = new BoxProp(world, 1, 6, new Vector2(center.x + 3,
				center.y));
		BoxProp pen3 = new BoxProp(world, 5, 1, new Vector2(center.x,
				center.y + 2.5f));*/

		// outer walls
		/*BoxProp wall1 = new BoxProp(world, worldWidth, 1, new Vector2(
				worldWidth / 2, 0.5f)); // bottom
		BoxProp wall2 = new BoxProp(world, 1, worldHeight - 2, new Vector2(
				0.5f, worldHeight / 2));// left
		BoxProp wall3 = new BoxProp(world, worldWidth, 1, new Vector2(
				worldWidth / 2, worldHeight - 0.5f));// top
		BoxProp wall4 = new BoxProp(world, 1, worldHeight - 2, new Vector2(
				worldWidth - 0.5f, worldHeight / 2)); // right
*/		
		// load the map
		tileMap = new TmxMapLoader().load("maps/test_map.tmx");
		tmrenderer = new OrthogonalTiledMapRenderer(tileMap);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		// set the camera to follow the taxi
		camera.position.set(car.body.getPosition().x * PIXELS_PER_METER, car.body.getPosition().y * PIXELS_PER_METER,0);
		
		/**Ensure the camera only shows the contents of the map and nothing outside it.
		 * 
		 */
		int mapPixelWidth = tileMap.getProperties().get("width", Integer.class)*tileMap.getProperties().get("tilewidth", Integer.class);
		int mapPixelHeight = tileMap.getProperties().get("height", Integer.class)*tileMap.getProperties().get("tileheight", Integer.class);
		
		//Check if the camera is near the left border of the map
		if(camera.position.x < Gdx.graphics.getWidth()/2) {
			camera.position.x = Gdx.graphics.getWidth()/2;
		}
		//Check if the camera is near the right border of the map
		if(camera.position.x >= mapPixelWidth - Gdx.graphics.getWidth()/2) {
			camera.position.x = mapPixelWidth - Gdx.graphics.getWidth()/2;
		}
		//Check if the camera is near the bottom border of the map
		if(camera.position.y < Gdx.graphics.getHeight()/2) {
			camera.position.y = Gdx.graphics.getHeight()/2;
		}
		//Check if the camera is near the top border of the map
		if(camera.position.y >= mapPixelHeight - Gdx.graphics.getHeight()/2) {
			camera.position.y = mapPixelHeight - Gdx.graphics.getHeight()/2;
		}
		
		// tell the camera to update its matrices.
		camera.update();

		spriteBatch.setProjectionMatrix(camera.combined);

		if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP))
			car.accelerate = Car.ACC_ACCELERATE;
		else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN))
			car.accelerate = Car.ACC_BRAKE;
		else
			car.accelerate = Car.ACC_NONE;

		if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT))
			car.steer = Car.STEER_LEFT;
		else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT))
			car.steer = Car.STEER_RIGHT;
		else
			car.steer = Car.STEER_NONE;

		car.update(Gdx.app.getGraphics().getDeltaTime());

		/**
		 * Have box2d update the positions and velocities (and etc) of all
		 * tracked objects. The second and third argument specify the number of
		 * iterations of velocity and position tests to perform -- higher is
		 * more accurate but is also slower.
		 */
		world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);

		world.clearForces();
		
		//draw the map
		tmrenderer.setView(camera);
		tmrenderer.render();
		
		//draw the sprites
		drawSprites();

		/**
		 * Draw this last, so we can see the collision boundaries on top of the
		 * sprites and map.
		 */
		//debugRenderer.render(world, camera.combined.scale(PIXELS_PER_METER,
		//		PIXELS_PER_METER, PIXELS_PER_METER));

	}
	
	public void drawSprites() {
		spriteBatch.begin();
		Array<Body> tmpBodies = new Array<Body>();
		world.getBodies(tmpBodies);
		for(Body body : tmpBodies) {
			if(body.getUserData() != null && body.getUserData() instanceof Sprite) {
				Sprite sprite = (Sprite) body.getUserData();
				sprite.setPosition(body.getPosition().x*PIXELS_PER_METER, body.getPosition().y*PIXELS_PER_METER);
				sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
				sprite.setScale(PIXELS_PER_METER);
				sprite.draw(spriteBatch);
			}
		}
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = screenWidth;
		camera.viewportHeight = screenHeight;
		camera.update();
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