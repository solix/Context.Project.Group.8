package com.taxi_trouble.game.input;

import static com.taxi_trouble.game.properties.GameProperties.BUTTON_CAM_WIDTH;
import static com.taxi_trouble.game.properties.GameProperties.VIRTUAL_HEIGHT;
import static com.taxi_trouble.game.properties.GameProperties.VIRTUAL_WIDTH;
import static com.taxi_trouble.game.properties.GameProperties.translateScreenX;
import static com.taxi_trouble.game.properties.GameProperties.translateScreenY;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.taxi_trouble.game.model.team.Team;
import com.taxi_trouble.game.screens.NavigatorScreen;

/**
 * This is the controller class for the NavigatorScreen.
 * 
 * @author Computer Games Project Group 8
 * 
 */
/**
 * This is the controller class for MapScreen.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class NavigatorControls implements InputProcessor {

    private NavigatorControlsUI powerUpControlsUI;
    private Team team;

    private OrthographicCamera mapCamera;
    private int old_x;
    private int old_y;
    private float last_dist;
    private NavigatorScreen mapscreen;
    private float old_factor = 1;
    private float ZOOM = 1;

    /**
     * Constructor method for MapControlsUI.
     * 
     * @param cam
     * @param mapscreen
     */
    public NavigatorControls(OrthographicCamera cam, NavigatorScreen mapscreen,
            NavigatorControlsUI powerUpControlsUI, Team team) {
        this.mapCamera = cam;
        this.mapscreen = mapscreen;
        this.powerUpControlsUI = powerUpControlsUI;
        this.team = team;
    }

    @Override
    public boolean keyDown(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * This method is called every time the screen is touched.
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // If the index of the touch is 0 then initialize/ modify the value of
        // old_x and old_y.
        if (pointer == 0) {
            old_x = screenX;
            old_y = screenY;
        }
        // If there are 2 touches then initialize/ modify last_dist.
        if (Gdx.input.isTouched(1) && Gdx.input.isTouched(0)) {
            Vector2 pointA = new Vector2(Gdx.input.getX(0), Gdx.input.getY(0));
            Vector2 pointB = new Vector2(Gdx.input.getX(1), Gdx.input.getY(1));
            last_dist = pyth(pointA, pointB);

        }
        if (Gdx.input.isTouched(1) && !Gdx.input.isTouched(0)) {
            mapCamera.position.set(0, 0, 0);
        }
        checkPowerUpButtonPressed(screenX, screenY, button);

        return true;
    }

    private void checkPowerUpButtonPressed(int screenX, int screenY, int button) {
        screenX = translateScreenX(screenX, BUTTON_CAM_WIDTH);
        screenY = translateScreenY(screenY, BUTTON_CAM_WIDTH);

        if (this.powerUpControlsUI.buttonPressed(screenX, screenY, button)) {
            team.usePowerUp();

        }
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * This method is called every time you drag your finger on the screen. For
     * one finger it scrolls through the map and for two fingers it starts
     * zooming in and out.
     * 
     * @param x
     *            This is the x-coordinate of the finger touching the map.
     * @param y
     *            This is the y coordinate of the finger touching the map.
     * @param pointer
     *            This is the index no. of the finger touching the map.
     */
    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        // If the pointer == 0 then scroll through the map.
        if (Gdx.input.isTouched(0) && pointer == 0) {
            // Calculate the difference between the old and the new coordinate *
            // ZOOM for scaling purposes.
            float deltaX = (old_x - x) * -1 * ZOOM;
            float deltaY = (old_y - y) * ZOOM;
            // Get the coordinates of the new position for the camera.
            Vector3 newCamPos = mapCamera.position.sub(deltaX, deltaY, 0);
            // Set the camera position to the new position.
            mapCamera.position.set(newCamPos);
            // Replace the old x and y coordinates.
            old_x = x;
            old_y = y;
            return true;
            // Else if 2 fingers are touching we start scaling/zooming through
            // the map.
        } else if (Gdx.input.isTouched(1) && Gdx.input.isTouched(0)) {
            Vector2 pointA = new Vector2(Gdx.input.getX(0), Gdx.input.getY(0));
            Vector2 pointB = new Vector2(Gdx.input.getX(1), Gdx.input.getY(1));
            // Calculate the distance between the 2 touches.
            float dist = pyth(pointA, pointB);
            // Calculate the factor between the old touch and the new touch.
            float factor = last_dist / dist;
            last_dist = dist;
            // Call the zoom method.
            zoom(mapCamera, factor);
            old_factor = factor;
            return true;
        } else {
            return false;
        }

    }

    /**
     * This method returns the distance between 2 points on the map using the
     * pythagoras method.
     * 
     * @param v1
     *            This is the first vector provided.
     * @param v2
     *            This is the second vector provided.
     * @return
     */
    public float pyth(Vector2 v1, Vector2 v2) {
        float x = Math.abs(v1.x - v2.x);
        float y = Math.abs(v1.y - v2.y);
        x = x * x;
        y = y * y;
        return (float) Math.sqrt(x + y);
    }

    //TODO: Refactor this code!
    /**
     * This method alters the camera.zoom value to zoom in and out of the map.
     * 
     * @param cam
     *            The camera to be zoomed.
     * @param factor
     *            The factor at which the camera needs to be zoomed.
     */
    public void zoom(OrthographicCamera cam, float factor) {
        float factorThresh = Math.abs(factor - 1);
        float factorOldTresh = Math.abs(old_factor - 1);
        float dif = Math.abs(factorOldTresh - factorThresh);

        int mapPixelHeight = mapscreen.getMap().getHeight();
        int mapPixelWidth = mapscreen.getMap().getWidth();

        // If the distance between the 2 finger hasn't changed then do nothing.
        if (dif == 0) {
            return;
        }
        if (factor > 1) {
            factor = (float) (1 + factorThresh * 0.8);
        } else if (factor < 1) {
            factor = (float) (1 - factorThresh * 0.8);
        }
        // If after the zoom * factor camera.zoom < 0.5 or > 1.5 then do
        // nothing.
        if (cam.zoom * factor > 1.5
                || ((mapCamera.position.x < VIRTUAL_WIDTH
                        * mapscreen.getScale() / 2
                        || mapCamera.position.x >= mapPixelWidth
                                - VIRTUAL_WIDTH * mapscreen.getScale() / 2
                        || mapCamera.position.y < VIRTUAL_HEIGHT
                                * mapscreen.getScale() / 2 || mapCamera.position.y >= mapPixelHeight
                        - VIRTUAL_HEIGHT * mapscreen.getScale() / 2) && factor > 1)
                || cam.zoom * factor < 0.5) {
            return;
        }
        cam.zoom = cam.zoom * factor;
        ZOOM = cam.zoom;
        mapscreen.setScale(factor * mapscreen.getScale());
    }
}