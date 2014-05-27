package com.taxi_trouble.game.screens;

import static com.taxi_trouble.game.properties.GameProperties.BUTTON_CAM_HEIGHT;
import static com.taxi_trouble.game.properties.GameProperties.BUTTON_CAM_WIDTH;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taxi_trouble.game.input.ControlsUI;
import com.taxi_trouble.game.input.DriverControl;
import com.taxi_trouble.game.model.GameWorld;

/**
 * Provides the view of the game for the driver of a taxi.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class DriverScreen extends ViewObserver {
    private TaxiCamera taxiCamera;
    private OrthographicCamera virtualButtonsCamera;
    private SpriteBatch spriteBatch;
    private ControlsUI controlsUI;
    private DriverControl driverControl;

    /**
     * Constructor, creates the Driver Screen.
     * 
     * @param game
     */
    public DriverScreen(GameWorld game) {
        super(game);
    }

    /**
     * Called when the Driver Screen is set as the current screen.
     * 
     */
    @Override
    public void show() {
        super.show();

        // Initialize the spriteBatch that should be used
        spriteBatch = new SpriteBatch();

        // Initialize the virtual buttons camera
        this.virtualButtonsCamera = new OrthographicCamera();
        this.virtualButtonsCamera.setToOrtho(false, BUTTON_CAM_WIDTH,
                BUTTON_CAM_HEIGHT);

        // Initialize the taxiCamera to follow the driver its taxi
        this.taxiCamera = new TaxiCamera(taxi);

        // Load the UI for player input
        this.controlsUI = new ControlsUI();
        this.driverControl = new DriverControl(taxi, controlsUI);
        Gdx.input.setInputProcessor(driverControl);
    }

    @Override
    public void render(float delta) {
        // Specify the clear values for the color buffers and clear the buffers
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        // Update the taxiCamera's view
        taxiCamera.update(cityMap);

        // Tell the camera to update its matrices.
        spriteBatch.setProjectionMatrix(taxiCamera.combined);
        taxi.update(Gdx.app.getGraphics().getDeltaTime());
        cityMap.render(taxiCamera);

        super.render(delta);

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

    @Override
    public SpriteBatch getSpriteBatch() {
        return this.spriteBatch;
    }

}
