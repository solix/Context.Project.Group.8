package com.taxi_trouble.game.properties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.taxi_trouble.game.Character;

public class ResourceManager {
    // public static String mapFile = "maps/prototype.tmx";

    public static Sprite taxiSprite;
    public static Sprite wheelSprite;
    public static TiledMap mapFile;
    public static List<Character> charList;

    /*
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
     * @throws IOException
     */
    public static void loadCharSprites() {
        charList = new ArrayList<Character>();
        // File f = new File("assets/sprites/characters");
        // System.out.println(f.getName());
        // System.out.println(f.list());
        // System.out.println(f.getCanonicalPath());
        // ArrayList<String> names = new
        // ArrayList<String>(Arrays.asList(f.list()));
        // System.out.println(names);
        for (int i = 1; i < 4; i++) {
            Sprite standing = new Sprite(new Texture(
                    "sprites/characters/character-" + i + "-standing.png"));
            Sprite walking1 = new Sprite(new Texture(
                    "sprites/characters/character-" + i + "-walk1.png"));
            Sprite walking2 = new Sprite(new Texture(
                    "sprites/characters/character-" + i + "-walk2.png"));
            // System.out.println(standing.toString());
            System.out.println(i);
            charList.add(new Character(standing, walking1, walking2));
        }
    }

    public static Character getRandomCharacter() {
        int i = (int) (Math.abs(Math.random() * charList.size() - 1));
        return charList.get(i);
    }

    /*
     * Loads the map. Call his method before using the mapFile.
     */
    public static void loadMap() {
        mapFile = new TmxMapLoader().load("maps/prototype.tmx");
    }

}
