package com.taxi_trouble.game.screens;

import static com.taxi_trouble.game.properties.GameProperties.PIXELS_PER_METER;
import static com.taxi_trouble.game.properties.GameProperties.VIRTUAL_HEIGHT;
import static com.taxi_trouble.game.properties.GameProperties.VIRTUAL_WIDTH;
import static com.taxi_trouble.game.properties.GameProperties.screenHeight;
import static com.taxi_trouble.game.properties.GameProperties.screenWidth;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.taxi_trouble.game.model.Taxi;
import com.taxi_trouble.game.model.WorldMap;

/**Orthographic camera following a taxi in the game.
 * 
 * @author Computer Games Project Group 8
 *
 */
public class TaxiCamera extends OrthographicCamera {
    private Viewport viewport;
    private Taxi taxi;

    public TaxiCamera(Taxi taxi) {
        this.taxi = taxi;
        this.setToOrtho(false, screenWidth, screenHeight);
        this.viewport = new StretchViewport(VIRTUAL_WIDTH,
                VIRTUAL_HEIGHT, this);
    }

    public void update(WorldMap map) {
        this.followtaxi();
        this.stayInBounds(map);
        super.update();
    }

    public void followtaxi() {
        this.position.set(taxi.getXPosition() * PIXELS_PER_METER,
                taxi.getYPosition() * PIXELS_PER_METER, 0);
    }

    /**
     * Ensure the camera only shows the contents of the map and nothing
     * outside it.
     *
     */
    public void stayInBounds(WorldMap map) {
        int mapPixelHeight = map.getHeight();
        int mapPixelWidth = map.getWidth();

        // Check if the camera is near the left border of the map
        if (this.position.x < VIRTUAL_WIDTH / 2) {
            this.position.x = VIRTUAL_WIDTH / 2;
        }
        // Check if the camera is near the right border of the map
        if (this.position.x >= mapPixelWidth - VIRTUAL_WIDTH / 2) {
            this.position.x = mapPixelWidth - VIRTUAL_WIDTH / 2;
        }
        // Check if the camera is near the bottom border of the map
        if (this.position.y < VIRTUAL_HEIGHT / 2) {
            this.position.y = VIRTUAL_HEIGHT / 2;
        }
        // Check if the camera is near the top border of the map
        if (this.position.y >= mapPixelHeight - VIRTUAL_HEIGHT / 2) {
            this.position.y = mapPixelHeight - VIRTUAL_HEIGHT / 2;
        }
    }

    public void updateViewPort(int width, int height) {
        viewport.update(width, height);
    }
}
