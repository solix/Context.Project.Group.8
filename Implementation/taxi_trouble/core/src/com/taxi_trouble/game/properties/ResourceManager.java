package com.taxi_trouble.game.properties;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.taxi_trouble.game.Character;

/**
 * This is the ResourceManager which handles and manages all the resources.
 * 
 * @author Context group 8
 * 
 */
public class ResourceManager {
    // public static String mapFile = "maps/prototype.tmx";

    public static Sprite taxiSprite;
    public static Sprite wheelSprite;
    public static TiledMap mapFile;
    public static List<Character> charList;
    final static int AMOUNT_OF_CHARS = 4;

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
     * Loads the character sprites in group of 3s.
     * 
     */
    public static void loadCharSprites() {
        charList = new ArrayList<Character>();
        for (int i = 1; i < AMOUNT_OF_CHARS; i++) {
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

    /**
     * Returns a randomly selected Character.
     * 
     * @return
     */
    public static Character getRandomCharacter() {
        int i = (int) (Math.abs(Math.random() * charList.size() - 1));
        return charList.get(i);
    }

    /**
     * Loads the map. Call his method before using the mapFile.
     */
    public static void loadMap() {
        mapFile = new TmxMapLoader().load("maps/prototype.tmx");
    }

}
