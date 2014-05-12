package com.taxi_trouble.game.model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.taxi_trouble.game.Car;
import com.taxi_trouble.game.properties.ResourceManager;


// the rendering has to be done in this class.
public class GameWorld extends Game {
	private Car car;
	private World world;
	private SpriteBatch spriteBatch;
	private WorldMap map;
	
	public GameWorld() {
		world = new World(new Vector2(0.0f, 0.0f), true);
		car = new Car(world, 2, 4, new Vector2(10,10), (float) Math.PI, 60, 20,60);
		map = new WorldMap(ResourceManager.mapFile, world);
		spriteBatch = new SpriteBatch();
		addTaxiToWorld();
	}

	private void addTaxiToWorld() {
        // TODO Auto-generated method stub
        
    }

    @Override
	public void create() {
		
	}
    
    @Override
    public void render() {
        
    }
}
