package com.taxi_trouble.game.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.taxi_trouble.game.properties.ResourceManager;
import com.taxi_trouble.game.screens.DriverScreen;
import com.taxi_trouble.game.screens.GameScreen;
import com.taxi_trouble.game.screens.NavigatorScreen;
import com.taxi_trouble.game.sound.TaxiJukebox;

/**Provides the main model for all the elements of a game that is played.
 * (Please note: implementation will change when implementing multiplayer)
 * 
 * @author Computer Games Project Group 8
 *
 */
public class GameWorld extends Game {
    private World world;
    private WorldMap map;
    private List<Taxi> taxis;
    //Temporary (may change when implementing multiplayer)
    private Taxi taxi;
    private List<Passenger> passengers;

    @Override
	public void create() {
        world = new World(new Vector2(0.0f, 0.0f), true);
        ResourceManager.loadMap();
        map = new WorldMap(ResourceManager.mapFile, world);
        taxi = new Taxi(2, 4, 20, 60, 60);
        taxi.createBody(world, new Vector2(10,10), (float) Math.PI);
        setScreen(new NavigatorScreen(this));
        TaxiJukebox.loadMusic("sound/s.ogg", "sampleMusic");
        passengers = new ArrayList<Passenger>();
    }

    @Override
    public void render() {
        super.render();
        if(passengers.size() < 3) {
            Passenger pas = map.getSpawner().spawnPassenger(world);
            passengers.add(pas);
        }
        
    }

    /**Retrieves the game world map.
     * 
     * @return map
     */
    public WorldMap getMap(){
        return this.map;
    }
    
    /**Retrieves the taxi that is steered.
     * 
     * @return taxi
     */
    public Taxi getTaxi(){
    	return this.taxi;
    }
    
    /**Retrieves the world in which the game is played.
     * 
     * @return world
     */
    public World getWorld(){
    	return this.world;
    }
    
    public List<Passenger> getPassengers() {
        return this.passengers;
    }
}