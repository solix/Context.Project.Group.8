package com.taxi_trouble.game.properties;

import static com.taxi_trouble.game.properties.ResourceManager.getSpriteSheet;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.taxi_trouble.game.model.entities.powerups.IncreaseTimeBehaviour;
import com.taxi_trouble.game.model.entities.powerups.InvincibilityBehaviour;
import com.taxi_trouble.game.model.entities.powerups.PowerUpAnimation;
import com.taxi_trouble.game.model.entities.powerups.PowerUpBehaviour;
import com.taxi_trouble.game.model.entities.powerups.SpeedBehaviour;

/**
 * Utility class defining properties of the game as the screenwidth and -height
 * of the device, actual view-width and height of the driver etc.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public final class GameProperties {

    public static int PIXELS_PER_METER = getPPM();
    public static int VIRTUAL_WIDTH = 480;
    public static int VIRTUAL_HEIGHT = 320;
    public static int BUTTON_CAM_HEIGHT = 480;
    public static int BUTTON_CAM_WIDTH = 800;
    public static int UI_HEIGHT = 720;
    public static int UI_WIDTH = 1280;

    private GameProperties() {
    }

    /**
     * Retrieves the pixels-per-meter ratio of the game.
     * 
     * @return pixels-per-meter
     */
    public static int getPPM() {
        return 15;
    }

    /**
     * Retrieves the scale used in the game.
     * 
     * @return
     */
    public static float getScale() {
        return 2.0f;
    }

    /**
     * Retrieves the screen-width of the device that is used to play the game.
     * 
     * @return screen-width
     */
    public static int getScreenWidth() {
        if (Gdx.graphics != null) {
            return Gdx.graphics.getWidth();
        }
        return 0;
    }

    /**
     * Retrieves the screen-height of the device that is used to play the game.
     * 
     * @return screen-height
     */
    public static int getScreenHeight() {
        if (Gdx.graphics != null) {
            return Gdx.graphics.getHeight();
        }
        return 0;
    }

    /**
     * Retrieve the list of available power-up behaviours.
     * 
     * @return power-up behaviours
     */
    public static List<PowerUpBehaviour> getPowerUpBehaviours() {
        // Add the powerup behaviours
        List<PowerUpBehaviour> behaviours = new ArrayList<PowerUpBehaviour>();
        behaviours.add(new SpeedBehaviour(getSpeedAnimation()));
        behaviours.add(new InvincibilityBehaviour(getInvincibleAnimation()));
        behaviours.add(new IncreaseTimeBehaviour(getTimerAnimation()));
        return behaviours;
    }

    /**
     * Translates an x-coordinate of the screen to an x-coordinate of the
     * virtual screen.
     * 
     * @param screenX
     *            x-coordinate of the screen
     * @return x-coordinate of the virtual screen
     */
    public static int translateScreenX(int screenX, int virtual) {
        return (int) (screenX * ((float) virtual / getScreenWidth()));
    }

    /**
     * Translates an y-coordinate of the screen to an y-coordinate of the
     * virtual screen.
     * 
     * @param screenY
     *            y-coordinate of the screen
     * @return y-coordinate of the virtual screen
     */
    public static int translateScreenY(int screenY, int virtual) {
        int translated = (int) (screenY * ((float) virtual / getScreenHeight()));
        int flipped = (int) (virtual - translated);
        return flipped;
    }

    /**
     * Retrieves the animation for the invincibility power-up.
     * 
     * @return invincibility power-up animation
     */
    private static PowerUpAnimation getInvincibleAnimation() {
        return new PowerUpAnimation(getSpriteSheet("invincibleSpriteSheet"));
    }

    /**
     * Retrieves the animation for the speed power-up.
     * 
     * @return speed boost power-up animation
     */
    private static PowerUpAnimation getSpeedAnimation() {
        return new PowerUpAnimation(getSpriteSheet("speedSpriteSheet"));
    }

    /**
     * Retrieves the animation for the extra drop-off time power-up.
     * 
     * @return increase drop-off time power-up animation
     */
    private static PowerUpAnimation getTimerAnimation() {
        return new PowerUpAnimation(getSpriteSheet("increaseTimeSpriteSheet"));
    }
}
