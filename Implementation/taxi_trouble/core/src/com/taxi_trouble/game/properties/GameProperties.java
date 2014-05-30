package com.taxi_trouble.game.properties;

import com.badlogic.gdx.Gdx;

/** Utility class defining properties of the game as the screenwidth and
 *  -height of the device, actual view-width and height of the driver etc.
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
}
