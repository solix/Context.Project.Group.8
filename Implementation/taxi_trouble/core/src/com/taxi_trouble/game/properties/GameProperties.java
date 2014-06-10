package com.taxi_trouble.game.properties;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.taxi_trouble.game.model.InvincibilityBehaviour;
import com.taxi_trouble.game.model.PowerUpAnimation;
import com.taxi_trouble.game.model.PowerUpBehaviour;
import com.taxi_trouble.game.model.SpeedBehaviour;

/**
 * Utility class defining properties of the game as the screenwidth and -height
 * of the device, actual view-width and height of the driver etc.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public final class GameProperties {
	public static int screenWidth = Gdx.graphics.getWidth();
	public static int screenHeight = Gdx.graphics.getHeight();
	public static int PIXELS_PER_METER = getPPM();
	public static float scale = getScale();
	public static int VIRTUAL_WIDTH = 480;
	public static int VIRTUAL_HEIGHT = 320;
	public static int BUTTON_CAM_HEIGHT = 480;
	public static int BUTTON_CAM_WIDTH = 800;
	public static int CONTROLS_HEIGHT = 480;
	public static int CONTROLS_WIDTH = 800;
	public static int UI_HEIGHT = 720;
	public static int UI_WIDTH = 1280;

    private GameProperties() {
    }

    private static int getPPM() {
        switch (Gdx.app.getType()) {
        case Android:
            return 15;
        case Desktop:
            return 15;
        default:
            return 15;
        }
    }

    private static float getScale() {
        switch (Gdx.app.getType()) {
        case Android:
            return 2.0f;
        case Desktop:
            return 1.0f;
        default:
            return 1.0f;
        }
    }

    public static List<PowerUpBehaviour> getPowerUpBehaviours() {
        List<PowerUpBehaviour> behaviours = new ArrayList<PowerUpBehaviour>();
        // Add the powerup behaviours
        behaviours.add(new SpeedBehaviour(getSpeedAnimation()));
        behaviours.add(new InvincibilityBehaviour(getInvincibleAnimation())); 

        return behaviours;

    }

	/**
	 * Retrieves the animation for the invincibility powerup.
	 * 
	 * @return
	 */
	private static PowerUpAnimation getInvincibleAnimation() {
		PowerUpAnimation animation = new PowerUpAnimation(
				new Texture(
						Gdx.files
								.internal("sprites/powerups/invincible-spritesheet.png")));
		return animation;
	}

	/**
	 * Retrieves the animation for the speed powerup.
	 * 
	 * @return
	 */
	private static PowerUpAnimation getSpeedAnimation() {
		PowerUpAnimation animation = new PowerUpAnimation(new Texture(
				Gdx.files.internal("sprites/powerups/speed-spritesheet.png")));
		return animation;
	}
}
