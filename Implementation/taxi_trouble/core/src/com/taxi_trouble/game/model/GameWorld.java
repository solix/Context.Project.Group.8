package com.taxi_trouble.game.model;

import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.taxi_trouble.game.properties.ResourceManager;
import com.taxi_trouble.game.screens.DriverScreen;


// the rendering has to be done in this class.
public class GameWorld extends Game {
	public World world;
	private SpriteBatch spriteBatch;
	private WorldMap map;
	private List<Taxi> taxis;
	public Taxi taxi;

    @Override
	public void create() {
        world = new World(new Vector2(0.0f, 0.0f), true);
        map = new WorldMap(ResourceManager.mapFile, world);
        taxi = new Taxi(2, 4, 20, 60, 60);
        taxi.createBody(world, new Vector2(10,10), (float) Math.PI);
        taxi.setSprite(new Sprite(new Texture(ResourceManager.taxiSprite)));
        taxi.setWheelSprite(new Sprite(new Texture(ResourceManager.wheelSprite)));
        setScreen(new DriverScreen(this));
	}
    
    @Override
    public void render() {
        super.render();
    }
}
