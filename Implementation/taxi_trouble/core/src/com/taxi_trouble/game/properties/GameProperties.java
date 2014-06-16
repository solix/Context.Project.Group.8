package com.taxi_trouble.game.properties;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.taxi_trouble.game.model.powerups.IncreaseTimeBehaviour;
import com.taxi_trouble.game.model.powerups.InvincibilityBehaviour;
import com.taxi_trouble.game.model.powerups.PowerUpAnimation;
import com.taxi_trouble.game.model.powerups.PowerUpBehaviour;
import com.taxi_trouble.game.model.powerups.SpeedBehaviour;

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
	public static int PIXELS_PER_METER = 15;
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

	/**
	 * Translates an x-coordinate of the screen to an x-coordinate of the
	 * virtual screen.
	 * 
	 * @param screenX
	 *            x-coordinate of the screen
	 * @return x-coordinate of the virtual screen
	 */
	public static int translateScreenX(int screenX) {
		return (int) (screenX * ((float) BUTTON_CAM_WIDTH / screenWidth));
	}

	/**
	 * Translates an y-coordinate of the screen to an y-coordinate of the
	 * virtual screen.
	 * 
	 * @param screenY
	 *            y-coordinate of the screen
	 * @return y-coordinate of the virtual screen
	 */
	public static int translateScreenY(int screenY) {
		int translated = (int) (screenY * ((float) GameProperties.BUTTON_CAM_HEIGHT / GameProperties.screenHeight));
		int flipped = (int) (GameProperties.BUTTON_CAM_HEIGHT - translated);
		return flipped;
	}

	public static List<PowerUpBehaviour> getPowerUpBehaviours() {
		List<PowerUpBehaviour> behaviours = new ArrayList<PowerUpBehaviour>();
		// Add the powerup behaviours
		behaviours.add(new SpeedBehaviour(getSpeedAnimation()));
		behaviours.add(new InvincibilityBehaviour(getInvincibleAnimation()));
		behaviours.add(new IncreaseTimeBehaviour(getTimerAnimation()));

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

	private static PowerUpAnimation getTimerAnimation() {
		PowerUpAnimation animation = new PowerUpAnimation(new Texture(
				Gdx.files.internal("sprites/powerups/timer-spritesheet.png")));
		return animation;
	}
}
