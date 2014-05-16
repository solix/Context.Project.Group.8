package com.taxi_trouble.game.properties;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class ResourceManager {
	//public static String mapFile = "maps/prototype.tmx";
	
	
	public static Sprite taxiSprite;
	public static Sprite wheelSprite;
	public static TiledMap mapFile;
	
	/*
	 * Loads the sprites needed for the rendering of the taxi. 
	 * This method has to be called before using the sprites.
	 */
	public static void loadTaxiAndWheelSprites(){
		Texture taxiTexture = new Texture("sprites/taxi_frame.png");
		taxiSprite = new Sprite(taxiTexture);
		Texture wheelTexture = new Texture("sprites/wheel.png");
		wheelSprite = new Sprite(wheelTexture);
	}
	
	/*
	 * Loads the map. Call his method before using the mapFile.
	 */
	public static void loadMap(){
		mapFile = new TmxMapLoader().load("maps/prototype.tmx");
	}
}
