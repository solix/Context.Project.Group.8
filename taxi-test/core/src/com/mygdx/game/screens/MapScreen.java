package com.mygdx.game.screens;

import static com.mygdx.game.properties.GameProperties.VIRTUAL_HEIGHT;
import static com.mygdx.game.properties.GameProperties.VIRTUAL_WIDTH;

import javax.swing.text.View;

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
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Car;
import com.mygdx.game.input.ControlsUI;
import com.mygdx.game.input.MapControlsUI;
import com.mygdx.game.properties.ResourceManager;
import com.mygdx.game.world.CarCamera;
import com.mygdx.game.world.WorldMap;

public class MapScreen extends BasicScreen{


	private Car car;
	private World world;
	private SpriteBatch spriteBatch;
	private ControlsUI controlsUI;
	private WorldMap map;
	private Box2DDebugRenderer debugRenderer;
	private Viewport viewport;
	private OrthographicCamera mapCamera;
	private MapControlsUI mapControl;
	public float SCALE = 4;

	@Override
	public void show() {

		//			virtualButtonsCamera.setToOrtho(false, screenWidth, screenHeight);
		this.mapCamera = new OrthographicCamera();
		mapCamera.setToOrtho(false, screenWidth, screenHeight);
		this.viewport = new StretchViewport(VIRTUAL_WIDTH * SCALE,VIRTUAL_HEIGHT * SCALE, mapCamera);
		world = new World(new Vector2(0.0f, 0.0f), true);
		map = new WorldMap(ResourceManager.mapFile, world);
		mapControl = new MapControlsUI(mapCamera, viewport, this);
		
		Gdx.input.setInputProcessor(mapControl);
		
	//	dlisten.
		
		//Gdx.input.setInputProcessor(dlisten);

		spriteBatch = new SpriteBatch();

		// Box2d World init
		

		// Load the UI for player input
//		this.controlsUI = new ControlsUI(car);
//		Gdx.input.setInputProcessor(dlisten);

		spriteBatch = new SpriteBatch();

		debugRenderer = new Box2DDebugRenderer();

		// Load the map of the game
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		stayInBounds(map);
		mapCamera.update();
		

		spriteBatch.setProjectionMatrix(mapCamera.combined);

		world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);

		world.clearForces();
		map.render(mapCamera);

		// Draw the sprites
		drawSprites();
		
		//resize((int)(viewport.getWorldWidth() * 0.5), (int)(viewport.getWorldHeight() * 0.5));

	//	virtualButtonsCamera.update();
	//	spriteBatch.setProjectionMatrix(virtualButtonsCamera.combined);
	//	controlsUI.render(spriteBatch);
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
		//viewport.update((int)(viewport.getWorldWidth() / 2), (int)(viewport.getWorldHeight() / 2));
		viewport.update(width, height);
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
	
	public void stayInBounds(WorldMap map) {
		int mapPixelHeight = map.getHeight();
		int mapPixelWidth = map.getWidth();
		
		// Check if the camera is near the left border of the map
		if (mapCamera.position.x < VIRTUAL_WIDTH * SCALE / 2) {
			mapCamera.position.x = VIRTUAL_WIDTH * SCALE / 2;
		}
		// Check if the camera is near the right border of the map
		if (mapCamera.position.x >= mapPixelWidth - VIRTUAL_WIDTH * SCALE / 2) {
			mapCamera.position.x = mapPixelWidth - VIRTUAL_WIDTH * SCALE / 2;
		}
		// Check if the camera is near the bottom border of the map
		if (mapCamera.position.y < VIRTUAL_HEIGHT * SCALE / 2) {
			mapCamera.position.y = VIRTUAL_HEIGHT * SCALE / 2;
		}
		// Check if the camera is near the top border of the map
		if (mapCamera.position.y >= mapPixelHeight - VIRTUAL_HEIGHT * SCALE / 2) {
			mapCamera.position.y = mapPixelHeight - VIRTUAL_HEIGHT * SCALE / 2;
		}
	}
	
	public void setScale(float sc){
		SCALE = sc;
	}

}
