package properties;

import com.badlogic.gdx.Gdx;

public class GameProperties {
	public static int screenWidth = Gdx.graphics.getWidth();
	public static int screenHeight = Gdx.graphics.getHeight();
	public static int PIXELS_PER_METER = 15;
	public static float worldWidth = screenWidth / PIXELS_PER_METER;
	public static float worldHeight = screenHeight / PIXELS_PER_METER;
}
