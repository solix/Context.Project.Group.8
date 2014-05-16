package com.taxi_trouble.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.taxi_trouble.game.input.ControlsUI;
import com.taxi_trouble.game.model.GameWorld;
import com.taxi_trouble.game.model.Taxi;
import com.taxi_trouble.game.model.WorldMap;
import com.taxi_trouble.game.properties.ResourceManager;

/**Provides the view of the game for the driver of a taxi.
 *
 * @author Computer Games Project Group 8
 *
 */
public class DriverScreen extends ViewObserver {
    private TaxiCamera taxiCamera;
    private Taxi taxi;
    private OrthographicCamera virtualButtonsCamera;
    private SpriteBatch spriteBatch;
    private ControlsUI controlsUI;
    private WorldMap cityMap;
    private Box2DDebugRenderer debugRenderer;

    /**
     * Constructor creates game screen and adds camera to follow taxi.
     * 
     * @param game
     */
    public DriverScreen(GameWorld game) {
        super(game);
    }

    @Override
    public void show() {
        this.virtualButtonsCamera = new OrthographicCamera();
        virtualButtonsCamera.setToOrtho(false, screenWidth, screenHeight);
        spriteBatch = new SpriteBatch();
        debugRenderer = new Box2DDebugRenderer();

        // Initialize the taxi
        this.taxi = taxigame.getTaxi();
        this.taxiCamera = new TaxiCamera(taxi);

        // Load the UI for player input
        this.controlsUI = new ControlsUI(taxi);
        Gdx.input.setInputProcessor(controlsUI);
        // Load the map of the game
        cityMap = taxigame.getMap();
        
        //Load the Sprites
        ResourceManager.loadTaxiAndWheelSprites();
        taxi.setSprite(ResourceManager.taxiSprite,ResourceManager.wheelSprite);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        //Update the taxiCamera's view
        taxiCamera.update(cityMap);

        // Tell the camera to update its matrices.
        spriteBatch.setProjectionMatrix(taxiCamera.combined);
        taxi.update(Gdx.app.getGraphics().getDeltaTime());

        taxigame.getWorld().step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);
        taxigame.getWorld().clearForces();
        
        cityMap.render(taxiCamera);
        taxi.render(spriteBatch);

        spriteBatch.setProjectionMatrix(virtualButtonsCamera.combined);
        controlsUI.render(spriteBatch);
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
}
