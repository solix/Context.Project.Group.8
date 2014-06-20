package com.taxi_trouble.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taxi_trouble.game.input.DriverControls;
import com.taxi_trouble.game.input.DriverControlsUI;
import com.taxi_trouble.game.model.GameWorld;

/**
 * Provides the view of the game for the driver of a taxi.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class DriverScreen extends ViewObserver {
    private TaxiCamera taxiCamera;
    private SpriteBatch spriteBatch;
    private DriverControls driverControl;
    private DriverControlsUI controlsUI;
    private double lastCarUpdate;
    private boolean shown = false;

    /**
     * Constructor, creates the Driver Screen.
     * 
     * @param game
     */
    public DriverScreen(GameWorld game) {
        super(game);
        lastCarUpdate = System.currentTimeMillis();
    }

    /**
     * Called when the Driver Screen is set as the current screen.
     * 
     */
    @Override
    public void show() {
        super.show();

        // Initialize the taxiCamera to follow the driver its taxi
        this.taxiCamera = new TaxiCamera(ownTaxi);

        // Load the UI for player input
        this.controlsUI = new DriverControlsUI();
        this.driverControl = new DriverControls(ownTaxi, controlsUI);
        Gdx.input.setInputProcessor(driverControl);
    }

    @Override
    public void resume() {
        shown = false;
    }

    @Override
    public void render(float delta) {
        checkSpriteBatchInitialization();

        // Specify the clear values for the color buffers and clear the buffers
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update the taxiCamera's view
        taxiCamera.update(cityMap);

        // Tell the camera to update its matrices.
        spriteBatch.setProjectionMatrix(taxiCamera.combined);
        ownTaxi.update(Gdx.app.getGraphics().getDeltaTime());
        cityMap.render(taxiCamera);

        if (System.currentTimeMillis() - lastCarUpdate > 50) {
            taxigame.getMultiplayerInterface().sendCarLocation(
                    ownTaxi.networkMessage());
            lastCarUpdate = System.currentTimeMillis();
        }
        super.render(delta);
        spriteBatch.setProjectionMatrix(hudCamera.combined);
        controlsUI.render(spriteBatch);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
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

    /**
     * Initializes the spritebatch in the first render
     * when the GL-context is ready.
     */
    private void checkSpriteBatchInitialization() {
        if(!shown) {
            this.spriteBatch = new SpriteBatch();
            shown = true;
        }
    }
}
