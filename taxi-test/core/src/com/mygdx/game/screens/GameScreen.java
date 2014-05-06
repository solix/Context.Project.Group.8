package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

	@Override
	public void show() {
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
		BoxProp pen1 = new BoxProp(world, 1, 6, new Vector2(center.x - 3,
				center.y));
		BoxProp pen2 = new BoxProp(world, 1, 6, new Vector2(center.x + 3,
				center.y));
		BoxProp pen3 = new BoxProp(world, 5, 1, new Vector2(center.x,
				center.y + 2.5f));

		// outer walls
		BoxProp wall1 = new BoxProp(world, worldWidth, 1, new Vector2(
				worldWidth / 2, 0.5f)); // bottom
		BoxProp wall2 = new BoxProp(world, 1, worldHeight - 2, new Vector2(
				0.5f, worldHeight / 2));// left
		BoxProp wall3 = new BoxProp(world, worldWidth, 1, new Vector2(
				worldWidth / 2, worldHeight - 0.5f));// top
		BoxProp wall4 = new BoxProp(world, 1, worldHeight - 2, new Vector2(
				worldWidth - 0.5f, worldHeight / 2)); // right

		Gdx.input.setInputProcessor(new TouchInputProcessor());
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

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

		/**
		 * Draw this last, so we can see the collision boundaries on top of the
		 * sprites and map.
		 */
		debugRenderer.render(world, camera.combined.scale(PIXELS_PER_METER,
				PIXELS_PER_METER, PIXELS_PER_METER));

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

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
