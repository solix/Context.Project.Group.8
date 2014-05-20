package com.taxi_trouble.game.screens;

import static com.taxi_trouble.game.properties.GameProperties.VIRTUAL_HEIGHT;
import static com.taxi_trouble.game.properties.GameProperties.VIRTUAL_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.taxi_trouble.game.input.MapControls;
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
    private MapControls mapControl;
    private float SCALE = 4;

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
     * screen.
     */
    @Override
    public void show() {
        super.show();

        // Initialize the sprite batch that should be used.
        spriteBatch = new SpriteBatch();

        // Initialize the camera for the navigator view.
        this.mapCamera = new OrthographicCamera();
        mapCamera.setToOrtho(false, screenWidth, screenHeight);
        this.viewport = new StretchViewport(VIRTUAL_WIDTH * SCALE,
                VIRTUAL_HEIGHT * SCALE, mapCamera);

        // Load the MapControls to enable navigating through the map.
        mapControl = new MapControls(mapCamera, this);
        Gdx.input.setInputProcessor(mapControl);
    }

    /**
     * This method is called every cycle to render the objects.
     */
    @Override
    public void render(float delta) {
        // Specify the clear values for the color buffers and clear the buffers
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        // Update the mapCamera's view.
        mapCamera.update();
        stayInBounds(cityMap);

        // Tell the camera to update its matrices.
        spriteBatch.setProjectionMatrix(mapCamera.combined);
        cityMap.render(mapCamera);

        super.render(delta);
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
    public void resume() {
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

    /**
     * This method changes the SCALE value.
     * 
     * @param sc
     *            sc is the new SCALE to be set.
     */
    public void setScale(float sc) {

        int mapPixelHeight = cityMap.getHeight();
        int mapPixelWidth = cityMap.getWidth();

        if (mapCamera.position.x < VIRTUAL_WIDTH * sc / 2
                || mapCamera.position.x >= mapPixelWidth - VIRTUAL_WIDTH * sc / 2
                || mapCamera.position.y < VIRTUAL_HEIGHT * sc / 2
                || mapCamera.position.y >= mapPixelHeight - VIRTUAL_HEIGHT * sc / 2) {
            return;
        }
        SCALE = sc;
    }

    /**
     * This method returns the SCALE value.
     * 
     * @return
     */
    public float getScale() {
        return SCALE;
    }

    public WorldMap getMap() {
        return this.cityMap;
    }

    @Override
    public SpriteBatch getSpriteBatch() {
        return this.spriteBatch;
    }

}
