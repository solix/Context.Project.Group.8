package com.taxi_trouble.game.screens;

import static com.taxi_trouble.game.properties.GameProperties.PIXELS_PER_METER;
import static com.taxi_trouble.game.properties.GameProperties.VIRTUAL_HEIGHT;
import static com.taxi_trouble.game.properties.GameProperties.VIRTUAL_WIDTH;
import static com.taxi_trouble.game.properties.GameProperties.getScreenHeight;
import static com.taxi_trouble.game.properties.GameProperties.getScreenWidth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.taxi_trouble.game.input.NavigatorControls;
import com.taxi_trouble.game.input.NavigatorControlsUI;
import com.taxi_trouble.game.model.GameWorld;
import com.taxi_trouble.game.model.WorldMap;

/**
 * Provides the view of the game for a navigator.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class NavigatorScreen extends ViewObserver {
    private SpriteBatch spriteBatch;
    private Viewport viewport;
    private OrthographicCamera mapCamera;
    private NavigatorControls mapControl;
    private NavigatorControlsUI powerUpControlsUI;
    private float scale = 4;
    private boolean shown = false;

    /**
     * Constructor, creates the game screen.
     * 
     * @param game
     */

    public NavigatorScreen(GameWorld game) {
        super(game);
    }

    /**
     * This method is only called once at the beginning to setup the main
     * screen. It is called when the screen first shows.
     */
    @Override
    public void show() {
        super.show();

        // Initialize the camera for the navigator view.
        this.mapCamera = new OrthographicCamera();
        mapCamera.setToOrtho(false, getScreenWidth(), getScreenHeight());
        this.viewport = new StretchViewport(VIRTUAL_WIDTH * scale,
                VIRTUAL_HEIGHT * scale, mapCamera);

        // Load the MapControls to enable navigating through the map.
        powerUpControlsUI = new NavigatorControlsUI();
        mapControl = new NavigatorControls(mapCamera, this, powerUpControlsUI,
                this.team);
        Gdx.input.setInputProcessor(mapControl);

        // center the camera at the position of its teams taxi.
        mapCamera.position.set(ownTaxi.getTeam().getTaxi().getXPosition()
                * PIXELS_PER_METER, ownTaxi.getTeam().getTaxi().getYPosition()
                * PIXELS_PER_METER, 0);
    }

    @Override
    public void resume() {
        spriteBatch = new SpriteBatch();
    }

    /**
     * This method is called every cycle to render the objects.
     */
    @Override
    public void render(float delta) {
        checkSpriteBatchInitialization();

        // Specify the clear values for the color buffers and clear the buffers.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        // Update the mapCamera's view.
        mapCamera.update();
        stayInBounds(cityMap);

        // Tell the camera to update its matrices and render the citymap.
        spriteBatch.setProjectionMatrix(mapCamera.combined);
        cityMap.render(mapCamera);
        // Render the common game elements (taxis, passengers, etc.)
        super.render(delta);

        // Render the powerUp control interface.
        spriteBatch.setProjectionMatrix(hudCamera.combined);
        this.powerUpControlsUI.render(spriteBatch, ownTaxi.getTeam());
    }

    /**
     * This method is called to resize/update the viewport accordingly.
     */
    @Override
    public void resize(int width, int height) {
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
    public void dispose() {
        spriteBatch.dispose();

    }

    /**
     * This method makes it that the camera doesn't go out of bounds from the
     * map.
     * 
     * @param map
     *            Worldmap map is needed so that we can stay in bounds.
     */
    public void stayInBounds(WorldMap map) {
        int mapPixelHeight = map.getHeight();
        int mapPixelWidth = map.getWidth();

        // Check if the camera is near the left border of the map
        if (mapCamera.position.x < VIRTUAL_WIDTH * scale / 2) {
            mapCamera.position.x = VIRTUAL_WIDTH * scale / 2;
        }
        // Check if the camera is near the right border of the map
        if (mapCamera.position.x >= mapPixelWidth - VIRTUAL_WIDTH * scale / 2) {
            mapCamera.position.x = mapPixelWidth - VIRTUAL_WIDTH * scale / 2;
        }
        // Check if the camera is near the bottom border of the map
        if (mapCamera.position.y < VIRTUAL_HEIGHT * scale / 2) {
            mapCamera.position.y = VIRTUAL_HEIGHT * scale / 2;
        }
        // Check if the camera is near the top border of the map
        if (mapCamera.position.y >= mapPixelHeight - VIRTUAL_HEIGHT * scale / 2) {
            mapCamera.position.y = mapPixelHeight - VIRTUAL_HEIGHT * scale / 2;
        }
    }

    /**
     * This method changes the scale value.
     * 
     * @param sc
     *            sc is the new scale to be set.
     */
    public void setScale(float sc) {
        int mapPixelHeight = cityMap.getHeight();
        int mapPixelWidth = cityMap.getWidth();

        if (mapCamera.position.x < VIRTUAL_WIDTH * sc / 2
                || mapCamera.position.x >= mapPixelWidth - VIRTUAL_WIDTH * sc
                        / 2
                || mapCamera.position.y < VIRTUAL_HEIGHT * sc / 2
                || mapCamera.position.y >= mapPixelHeight - VIRTUAL_HEIGHT * sc
                        / 2) {
            return;
        }
        scale = sc;
    }

    /**
     * This method returns the scale value.
     * 
     * @return scale
     */
    public float getScale() {
        return scale;
    }

    /**
     * Retrieves the map.
     * 
     * @return map
     */
    public WorldMap getMap() {
        return this.cityMap;
    }

    @Override
    public SpriteBatch getSpriteBatch() {
        return this.spriteBatch;
    }

    /**
     * Initializes the spritebatch in the first render when the GL-context is
     * ready.
     */
    private void checkSpriteBatchInitialization() {
        if (!shown ) {
            this.spriteBatch = new SpriteBatch();
            shown = true;
        }
    }
}
