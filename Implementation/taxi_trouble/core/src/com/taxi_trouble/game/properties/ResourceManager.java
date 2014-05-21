package com.taxi_trouble.game.properties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.taxi_trouble.game.Character;

public class ResourceManager {
    // public static String mapFile = "maps/prototype.tmx";

    public static Sprite taxiSprite;
    public static Sprite wheelSprite;
    public static TiledMap mapFile;
    public static List<Character> charList;
    
    //animation
    public static Texture passenger_sheet;
    public static TextureRegion[] passenger_sheet_frames;
    public static TextureRegion current_passenger_frame;
    public static Animation loading_passenger_animation;
    public static final int SHEET_TILE_WIDTH=128;
    public static final int SHEET_TILE_HEIGHT=90;
    public static int numberOfImageInSheet=3;

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
    	
    /**
     * Loads Animation 
     * NOTE: argument inside method need to be changed to Passenger later on
     */
    public static void loadPassengerAnimation(FileHandle sheetfile){
    	
    	passenger_sheet=new Texture(sheetfile);
    	TextureRegion[][] temp =TextureRegion.split(passenger_sheet, SHEET_TILE_WIDTH, SHEET_TILE_HEIGHT); 
    	passenger_sheet_frames=new TextureRegion[numberOfImageInSheet];
    	int index =0;
    	for(int i=0;i<2;i++){
    		for(int j=0;i<2;i++){
    			passenger_sheet_frames[index++]=temp[i][j];
    		}
    	}
    	//for(int i=0;i<2*2;i++){
    		//passenger_sheet_frames[i].flip(false, true);
    	//}
    	loading_passenger_animation=new Animation(0.2f, passenger_sheet_frames);
    }
}
