package com.taxi_trouble.game.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.taxi_trouble.game.properties.ResourceManager;
import com.taxi_trouble.game.screens.DriverScreen;
import com.taxi_trouble.game.sound.TaxiJukebox;

/**
 * Provides the main model for all the elements of a game that is played.
 * (Please note: implementation will change when implementing multiplayer)
 *
 * @author Computer Games Project Group 8
 *
 */
public class GameWorld extends Game {
    private World world;
    private WorldMap map;
    private List<Taxi> taxis;
    // Temporary (may change when implementing multiplayer)
    private Taxi taxi;
    private List<Passenger> passengers;

    @Override
    public final void create() {
        world = new World(new Vector2(0.0f, 0.0f), true);
        ResourceManager.loadMap();
        map = new WorldMap(ResourceManager.mapFile, world);
        TaxiJukebox.loadMusic("sound/s.ogg", "sampleMusic");

        ResourceManager.loadCharSprites();
        passengers = new ArrayList<Passenger>();

        ResourceManager.loadTaxiAndWheelSprites();
        taxi = map.getSpawner().spawnTaxi(world);

        setScreen(new DriverScreen(this));
        world.setContactListener(new CollisionDetector());
    }

    @Override
    public final void render() {
        super.render();
        //Spawn a new passenger if there are less than #taxis-1.
        //TODO: Instead of '3' adapt to #taxis-1 in the game.
        if (passengers.size() < 70) {
            Passenger pas = map.getSpawner().spawnPassenger(world);
            passengers.add(pas);
        }
    }

    /**
     * Retrieves the game world map.
     *
     * @return map
     */
    public final WorldMap getMap() {
        return this.map;
    }

    /**
     * Retrieves the taxi that is steered.
     *
     * @return taxi
     */
    public final Taxi getTaxi() {
        return this.taxi;
    }

    /**
     * Retrieves the world in which the game is played.
     *
     * @return world
     */
    public final World getWorld() {
        return this.world;
    }

    /**
     * Retrieves the passengers that are currently in the game.
     *
     * @return passengers
     */
    public final List<Passenger> getPassengers() {
        return this.passengers;
    }
}