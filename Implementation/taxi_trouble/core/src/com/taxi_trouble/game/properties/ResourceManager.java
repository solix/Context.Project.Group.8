package com.taxi_trouble.game.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static Map<String, Sprite> spriteStore;
    private static Map<String, Texture> spriteSheetStore;
    private static List<Sprite> characterSpriteList;
    private static TiledMap mapFile;
    private static BitmapFont hudFont;

    static {
        spriteStore = new HashMap<String, Sprite>();
        spriteSheetStore = new HashMap<String, Texture>();
    }

    private ResourceManager() {
    }

    /**
     * Loads/Imports the required resources that are used in the game.
     * 
     */
    public static void loadResources() {
        loadSprites();
        loadSpriteSheets();
        loadMap();
        loadFonts();
        loadFx();
    }

    /**
     * Loads/Imports all of the required sprites that are used in the game.
     * 
     */
    public static void loadSprites() {
        loadTaxiAndWheelSprites();
        loadDriverControlSprites();
        loadPowerUpControlSprites();
        loadCharacterSprites();
        loadMenuSprites();
        loadSprite("sprites/destination.png", "destinationSprite");
    }

    public static void loadSpriteSheets() {
        loadSpriteSheet("sprites/powerups/speed-spritesheet.png", "speedSpriteSheet");
        loadSpriteSheet("sprites/powerups/invincible-spritesheet.png", "invincibleSpriteSheet");
        loadSpriteSheet("sprites/powerups/timer-spritesheet.png", "increaseTimeSpriteSheet");
    }

    /**
     * Imports a sprite from specified path and store it with the
     * given identifier name.
     *
     * @param path : path to the sprite resource
     * @param name : String identifier of the sprite resource
     */
    public static void loadSprite(String path, String name) {
        if(Gdx.files != null) {
            Texture texture = new Texture(Gdx.files.internal(path));
            Sprite sprite = new Sprite(texture);
            spriteStore.put(name, sprite);
        }
    }

    /**
     * Imports a spritesheet from specified path and store it with
     * the given identifier name.
     * 
     * @param path : path to the spritesheet resource
     * @param name : String identifier of the spritesheet resource
     */
    public static void loadSpriteSheet(String path, String name) {
        if(Gdx.files != null) {
            Texture texture = new Texture(Gdx.files.internal(path));
            spriteSheetStore.put(name, texture);
        }
    }

    /**
     * Retrieves the sprite from the loaded sprite resources.
     *
     * @param name : String identifier of the sprite resource
     * @return sprite
     */
    public static Sprite getSprite(String name) {
        return spriteStore.get(name);
    }

    /**
     * Retrieves the spriteSheet from the loaded spritesheet resources.
     *
     * @param name : String identifier of the spritesheet resource
     * @return spritesheet texture
     */
    public static Texture getSpriteSheet(String name) {
        return spriteSheetStore.get(name);
    }

    /**
     * Imports all sprites used in the main menu.
     */
    public static void loadMenuSprites() {
        loadSprite("sprites/menu-bg.png", "menuBgSprite");
        loadSprite("sprites/menu-play.png", "menuPlaySprite");
        loadSprite("sprites/menu-play-active.png", "menuPlayActiveSprite");
        loadSprite("sprites/menu-board.png", "menuBoardSprite");
        loadSprite("sprites/menu-board-active.png", "menuBoardActiveSprite");
        loadSprite("sprites/menu-title.png", "menuTitleSprite");
    }

    /**
     * Imports the sprites needed for the rendering of the powerup activation
     * buttons.
     */
    public static void loadPowerUpControlSprites() {
        loadSprite("sprites/no-powerup.png", "noPowerUpButtonSprite");
        loadSprite("sprites/invincibility.png", "invincibilityButtonSprite");
        loadSprite("sprites/speed.png", "speedBoostButtonSprite");
        loadSprite("sprites/increasetime.png", "increaseTimeButtonSprite");
    }

    /**
     * Imports the character sprites that can be used by a passenger.
     * 
     */
    public static void loadCharacterSprites() {
        characterSpriteList = new ArrayList<Sprite>();
        for (int i = 1; i < 4; i++) {
            Sprite charSprite = new Sprite(new Texture(
                    "sprites/characters/character-" + i + ".png"));
            addCharacterSprite(charSprite);
        }
    }

    /**Adds a new passenger character sprite to the list of
     * passenger sprites.
     *
     * @param sprite : new character sprite
     */
    public static void addCharacterSprite(Sprite sprite) {
        characterSpriteList.add(sprite);
    }

    /**
     * Retrieve a random character to be used by a passenger.
     * 
     * @return character
     */
    public static Sprite getCharacter(int i) {
        return characterSpriteList.get(i);
    }

    /**
     * Retrieve a random character to be used by a passenger.
     * 
     * @return character
     */
    public static Sprite getRandomCharacter() {
        int i = (int) (Math.abs(Math.random() * characterSpriteList.size()));
        return getCharacter(i);
    }

    /**
     * Retrieve a random index for a passenger character sprite.
     *
     * @return
     */
    public static int getRandomCharacterId() {
        return (int) Math.floor(Math.abs(Math.random() * characterSpriteList.size()));
    }

    /**
     * Loads the map. Call this method before using the mapFile.
     */
    public static void loadMap() {
        mapFile = new TmxMapLoader().load("maps/citymap.tmx");
    }

    /**
     * Retrieves the tiled map used as map in the game.
     * 
     * @return tiled map
     */
    public static TiledMap getTiledMap() {
        return mapFile;
    }

    /**
     * Loads the fonts of the game.
     */
    public static void loadFonts() {
        hudFont = new BitmapFont(Gdx.files.internal("fonts/hoboStd-24.fnt"),
                Gdx.files.internal("fonts/hoboStd-24.png"), false);
    }

    /**Retrieves the imported font for the game hud.
     * 
     * @return hud font
     */
    public static BitmapFont getHudFont() {
        return hudFont;
    }

    /**
     * Loads/Imports the sprites needed for the rendering of the taxis. 
     */
    private static void loadTaxiAndWheelSprites() {
        loadSprite("sprites/taxi-frame-yellow.png", "yellowTaxiSprite");
        loadSprite("sprites/taxi-frame-blue.png", "blueTaxiSprite");
        loadSprite("sprites/taxi-frame-green.png", "greenTaxiSprite");
        loadSprite("sprites/taxi-frame-red.png", "redTaxiSprite");
        loadSprite("sprites/wheel.png", "wheelSprite");
    }

    /**
     * Loads/Imports the sprites needed for the rendering of the 
     * driver control graphical interface.
     */
    private static void loadDriverControlSprites() {
        loadSprite("sprites/throttle.png", "throttleSprite");
        loadSprite("sprites/brake.png", "brakeSprite");
        loadSprite("sprites/dpad.png", "dPadSprite");
    }

    /**
     * Loads the sound effects of the game.
     */
    public static void loadFx() {
        loadPowerUpSFX();
        loadTaxiSFX();
        loadPassengerSFX();
    }

    /**
     * Loads the power-up sound effects.
     */
    private static void loadPowerUpSFX() {
        final String powerupfx = "sound/powerupfx.ogg";
        TaxiJukebox.loadSound(powerupfx, "pufx");
        final String powerupfx2 = "sound/powerfx.ogg";
        TaxiJukebox.loadSound(powerupfx2, "powerup");

    }

    /**
     * Loads the taxi sound effects.
     */
    private static void loadTaxiSFX() {
        final String carcrashfx = "sound/CarCrash.ogg";
        TaxiJukebox.loadSound(carcrashfx, "carcrashfx");
    }

    /**
     * Loads the passenger sound effects.
     */
    private static void loadPassengerSFX() {
        final String dropfx = "sound/dropoff.ogg";
        TaxiJukebox.loadSound(dropfx, "dropoff");
        final String yoohoofx = "sound/yoohoo.ogg";
        TaxiJukebox.loadSound(yoohoofx, "yoohoo");
    }
}