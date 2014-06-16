package com.taxi_trouble.game.properties;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.taxi_trouble.game.sound.TaxiJukebox;

/**
 * Utility class managing all the resources that are used in the game.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public final class ResourceManager {

    public static Sprite yellowTaxiSprite;
    public static Sprite blueTaxiSprite;
    public static Sprite greenTaxiSprite;
    public static Sprite redTaxiSprite;
	public static Sprite wheelSprite;
	public static Sprite throttleSprite;
	public static Sprite brakeSprite;
	public static Sprite dPadSprite;
	public static Sprite destinationSprite;
	public static Sprite noPowerUpButtonSprite;
	public static Sprite invincibilityButtonSprite;
	public static Sprite speedBoostButtonSprite;
	public static Sprite increaseTimeButtonSprite;
	public static TiledMap mapFile;
	public static List<Sprite> charList;
    public static BitmapFont hudFont;
	public static Sprite menuBgSprite;
	public static Sprite menuPlaySprite;
	public static Sprite menuBoardSprite;
	public static Sprite menuTitleSprite;
	public static Sprite menuPlayActiveSprite;
	public static Sprite menuBoardActiveSprite;

	private ResourceManager() {
	}

	/**
	 * Loads all of the required sprites that are used in the game.
	 * 
	 */
	public static void loadSprites() {
		loadTaxiAndWheelSprites();
		loadDriverControlSprites();
		loadPowerUpControlSprites();
		loadCharSprites();
		loadMenuSprites();
		destinationSprite = new Sprite(new Texture("sprites/destination.png"));
	}

	/**
	 * Loads all sprites used in the main menu.
	 */
	public static void loadMenuSprites() {
		menuBgSprite = new Sprite(new Texture("sprites/menu-bg.png"));
		menuPlaySprite = new Sprite(new Texture("sprites/menu-play.png"));
		menuPlayActiveSprite = new Sprite(new Texture(
				"sprites/menu-play-active.png"));
		menuBoardSprite = new Sprite(new Texture("sprites/menu-board.png"));
		menuBoardActiveSprite = new Sprite(new Texture(
				"sprites/menu-board-active.png"));
		menuTitleSprite = new Sprite(new Texture("sprites/menu-title.png"));
	}

	/**
	 * Loads the sprites needed for the rendering of the powerup activation
	 * buttons.
	 */
	public static void loadPowerUpControlSprites() {
		Texture noPowerUpTexture = new Texture(
				Gdx.files.internal("sprites/no-powerup.png"));
		noPowerUpButtonSprite = new Sprite(noPowerUpTexture);
		Texture invButtonTexture = new Texture(
				Gdx.files.internal("sprites/invincibility.png"));
		invincibilityButtonSprite = new Sprite(invButtonTexture);
		Texture speedButtonTexture = new Texture(
				Gdx.files.internal("sprites/speed.png"));
		speedBoostButtonSprite = new Sprite(speedButtonTexture);
		Texture increaseTimeButtonSpriteTexture = new Texture(
			Gdx.files.internal("sprites/increasetime.png"));
			increaseTimeButtonSprite = new Sprite(increaseTimeButtonSpriteTexture);
		
	}

	/**
	 * Loads the character sprites in group of 3s.
	 * 
	 */
	public static void loadCharSprites() {
		charList = new ArrayList<Sprite>();
		for (int i = 1; i < 4; i++) {
			Sprite charSprite = new Sprite(new Texture(
					"sprites/characters/character-" + i + "-standing.png"));
			charList.add(charSprite);
		}
	}

	/**
	 * Retrieve a random character to be used by a passenger.
	 * 
	 * @return character
	 */
	public static Sprite getCharacter(int i) {
		return charList.get(i);
	}

	/**
	 * Loads the map. Call his method before using the mapFile.
	 */
	public static void loadMap() {
		mapFile = new TmxMapLoader().load("maps/citymap.tmx");
	}

    /**
     * Loads the fonts of the game.
     */
    public static void loadFonts() {
        hudFont = new BitmapFont(Gdx.files.internal("fonts/hoboStd-24.fnt"),
                Gdx.files.internal("fonts/hoboStd-24.png"), false);
    }

  /**
     * Loads the sprites needed for the rendering of the taxis. This method has
     * to be called before using the sprites.
     */
    public static void loadTaxiAndWheelSprites() {
        Texture yellowTaxiTexture = new Texture("sprites/taxi-frame-yellow.png");
        yellowTaxiSprite = new Sprite(yellowTaxiTexture);
        Texture blueTaxiTexture = new Texture("sprites/taxi-frame-blue.png");
        blueTaxiSprite = new Sprite(blueTaxiTexture);
        Texture greenTaxiTexture = new Texture("sprites/taxi-frame-green.png");
        greenTaxiSprite = new Sprite(greenTaxiTexture);
        Texture redTaxiTexture = new Texture("sprites/taxi-frame-red.png");
        redTaxiSprite = new Sprite(redTaxiTexture);
        Texture wheelTexture = new Texture("sprites/wheel.png");
        wheelSprite = new Sprite(wheelTexture);
    }

  /**
     * Loads the sprites needed for the rendering of the driver control
     * graphical interface.
     */
    public static void loadDriverControlSprites() {
        Texture throttleTexture = new Texture("sprites/throttle.png");
        throttleSprite = new Sprite(throttleTexture);
        Texture brakeTexture = new Texture("sprites/brake.png");
        brakeSprite = new Sprite(brakeTexture);
        Texture dPadTexture = new Texture("sprites/dpad.png");
        dPadSprite = new Sprite(dPadTexture);
    }


	/**
	 * Retrieve a random character to be used by a passenger.
	 * 
	 * @return character
	 */
	public static Sprite getRandomCharacter() {
		int i = (int) (Math.abs(Math.random() * charList.size()));
		return getCharacter(i);
	}

	public static int getRandomCharacterId() {
		return (int) Math.floor(Math.abs(Math.random() * charList.size()));
	}
	/**
	 * loads the sound when powerup is released
	 */
	public static void loadFx() {
		loadPowerUpSFX();
		loadTaxiSFX();
		loadPassengerSFX();
	}

	private static void loadPowerUpSFX() {
		final String powerupfx = "sound/powerupfx.ogg";
		TaxiJukebox.loadSound(powerupfx, "pufx");
		final String powerupfx2 = "sound/powerfx.ogg";
		TaxiJukebox.loadSound(powerupfx2, "powerup");

	}

	private static void loadTaxiSFX() {
		final String carcrashfx = "sound/CarCrash.ogg";
		TaxiJukebox.loadSound(carcrashfx, "carcrashfx");
		//final String revivingFx = "sound/EngineOn.ogg";
		//TaxiJukebox.loadSound(revivingFx, "EngineOn");
		
	}

	private static void loadPassengerSFX() {
		final String dropfx = "sound/dropoff.ogg";
		TaxiJukebox.loadSound(dropfx, "dropoff");
		final String yoohoofx = "sound/yoohoo.ogg";
		TaxiJukebox.loadSound(yoohoofx, "yoohoo");
	}

}
