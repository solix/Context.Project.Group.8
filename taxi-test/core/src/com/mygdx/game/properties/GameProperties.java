package com.mygdx.game.properties;

import com.badlogic.gdx.Gdx;

public class GameProperties {
	public static int screenWidth = Gdx.graphics.getWidth();
	public static int screenHeight = Gdx.graphics.getHeight();
	public static int PIXELS_PER_METER = getPPM();
	public static float scale = getScale();
	public static float worldWidth = screenWidth / PIXELS_PER_METER;
	public static float worldHeight = screenHeight / PIXELS_PER_METER;

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
