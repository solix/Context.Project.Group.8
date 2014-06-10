package com.taxi_trouble.game.properties;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.taxi_trouble.game.Character;
import com.taxi_trouble.game.sound.JukeBoxTest;
import com.taxi_trouble.game.sound.TaxiJukebox;

/**
 * Utility class managing all the resources that are used in the game.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public final class ResourceManager {

    public static Sprite taxiSprite;
    public static Sprite wheelSprite;
    public static Sprite throttleSprite;
    public static Sprite brakeSprite;
    public static Sprite dPadSprite;
    public static Sprite destinationSprite;
    public static Sprite noPowerUpButtonSprite;
    public static Sprite invincibilityButtonSprite;
    public static Sprite speedBoostButtonSprite;
    public static TiledMap mapFile;
    public static List<Character> charList;
    public static BitmapFont scoreFont;
    

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
        destinationSprite = new Sprite(new Texture("sprites/destination.png"));
    }

    /**
     * Loads the sprites needed for the rendering of the taxi. This method has
     * to be called before using the sprites.
     */
    public static void loadTaxiAndWheelSprites() {
        Texture taxiTexture = new Texture("sprites/taxi_frame.png");
        taxiSprite = new Sprite(taxiTexture);
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
     * Loads the sprites needed for the rendering of the powerup activation
     * buttons.
     */
    public static void loadPowerUpControlSprites() {
        Texture noPowerUpTexture = new Texture(Gdx.files.internal("sprites/no-powerup.png"));
        noPowerUpButtonSprite = new Sprite(noPowerUpTexture);
        Texture invButtonTexture = new Texture(Gdx.files.internal("sprites/invincibility.png"));
        invincibilityButtonSprite = new Sprite(invButtonTexture);
        Texture speedButtonTexture = new Texture(Gdx.files.internal("sprites/speed.png"));
        speedBoostButtonSprite = new Sprite(speedButtonTexture);
    }

    /**
     * Loads the character sprites in group of 3s.
     * 
     */
    public static void loadCharSprites() {
        charList = new ArrayList<Character>();
        for (int i = 1; i < 4; i++) {
            Sprite standing = new Sprite(new Texture(
                    "sprites/characters/character-" + i + "-standing.png"));
            Sprite walking1 = new Sprite(new Texture(
                    "sprites/characters/character-" + i + "-walk1.png"));
            Sprite walking2 = new Sprite(new Texture(
                    "sprites/characters/character-" + i + "-walk2.png"));
            charList.add(new Character(standing, walking1, walking2));
        }
    }

    /**
     * Retrieve a random character to be used by a passenger.
     * 
     * @return character
     */
    public static Character getRandomCharacter() {
        int i = (int) (Math.abs(Math.random() * charList.size()));
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
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/hoboStd-24.fnt"),
                Gdx.files.internal("fonts/hoboStd-24.png"), false);
    }
 
     /**
     * loads the sound when powerup is released
     */
     public static void loadFx(){
    	 loadPowerUpSFX();
    	 loadTaxiSFX();
    	 loadPassengerSFX();     }
     
     
     private static void loadPowerUpSFX(){
        final String powerupfx="sound/powerupfx.ogg";
        TaxiJukebox.loadSound(powerupfx, "pufx");
        final String powerupfx2="sound/powerfx.ogg";
        TaxiJukebox.loadSound(powerupfx2, "powerup");

    }
    
     private static void loadTaxiSFX(){
    	final String carcrashfx="sound/CarCrash.ogg";
        TaxiJukebox.loadSound(carcrashfx, "carcrashfx");
        final String revivingFx="sound/EngineOn.ogg";
        TaxiJukebox.loadSound(revivingFx, "reviving");
        final String startEngine="sound/startengine.ogg";
        TaxiJukebox.loadSound(startEngine, "startEngine");
    	
    }
     private static void loadPassengerSFX(){
	final String passengerfx="sound/yohoo.ogg";
    TaxiJukebox.loadSound(passengerfx, "yohoo");
    
    final String dropfx="sound/dropoff.ogg";
    TaxiJukebox.loadSound(dropfx, "dropoff");
    
    	
    }
    /**
     * load fx passanger calling taxi when taxi approach
     */
    public static void loadYohooFx(){
        

    }

}
