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

/**Manages all the resources that are used in the game.
 *
 * @author Computer Games Project Group 8
 *
 */
public class ResourceManager {
    
    public static Sprite taxiSprite;
    public static Sprite wheelSprite;
    public static Sprite throttleSprite;
    public static Sprite brakeSprite;
    public static Sprite dPadSprite;
    public static TiledMap mapFile;
    public static List<Character> charList;
    public static BitmapFont scoreFont;

    /**Loads all of the required sprites that are used in the game.
     *
     */
    public static void loadSprites() {
        loadTaxiAndWheelSprites();
        loadDriverControlSprites();
        loadCharSprites();
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
            System.out.println(i);
            charList.add(new Character(standing, walking1, walking2));
        }
    }

    /**Retrieve a random character to be used by a passenger.
     *
     * @return character
     */
    public static int getRandomCharacterId() {
        return (int) (Math.abs(Math.random() * charList.size()));
       
    }
    
    public static Character getCharacter(int id) {
        return charList.get(id);
       
    }

    /**
     * Loads the map. Call his method before using the mapFile.
     */
    public static void loadMap() {
        mapFile = new TmxMapLoader().load("maps/prototype.tmx");
    }

    /**
     * Loads the fonts of the game.
     */
    public static void loadFonts() {
        scoreFont = new BitmapFont(
                Gdx.files.internal("fonts/arial-24.fnt"),
                Gdx.files.internal("fonts/arial-24.png"),
                false);
    }

}
