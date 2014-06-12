package com.taxi_trouble.game.model;

import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.taxi_trouble.game.model.powerups.PowerUp;
import com.taxi_trouble.game.model.team.Team;
import com.taxi_trouble.game.properties.ResourceManager;
import com.taxi_trouble.game.screens.NavigatorScreen;
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
    private Team team;
    private CountDownTimer timer;

    /**
     * Called when the game world is first created.
     * 
     */
    @Override
    public final void create() {
        loadResources();
        world = new World(new Vector2(0.0f, 0.0f), true);
        map = new WorldMap(ResourceManager.mapFile, world);
        team = new Team(3, map.getSpawner().spawnTaxi(world));
        world.setContactListener(new CollisionDetector(map));
        timer = new CountDownTimer(300);
        timer.startTimer();
        setScreen(new NavigatorScreen(this));

        //TODO: Make a start game method (also for initiating timer and playing starting sound)
    }

    /**
     * Loads the game resources.
     * 
     */
    public void loadResources() {
        ResourceManager.loadMap();
        ResourceManager.loadSprites();
        ResourceManager.loadFonts();
        ResourceManager.loadFx();
    }

    @Override
    public final void render() {
        super.render();
        // Spawn a new passenger if there are less than #taxis-1.
        // TODO: Instead of '3' adapt to #taxis-1 in the game.
        List<Passenger> passengers = map.getSpawner().getActivePassengers();
        if (passengers.size() < 3) {
            map.getSpawner().spawnPassenger(world);
        }

        List<PowerUp> powerups = map.getSpawner().getActivePowerUps();
        if (powerups.size() < 3) {
            map.getSpawner().spawnPowerUp(world);
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
     * Retrieves the single team.
     * 
     * @return team
     */
    public final Team getTeam() {
        return this.team;
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
        return this.map.getSpawner().getActivePassengers();
    }

    /**
     * Retrieves the active powerups on the map.
     * 
     * @return
     */
    public final List<PowerUp> getPowerUps() {
        return this.map.getSpawner().getActivePowerUps();
    }

    /**Retrieves the game countdown-timer.
     * 
     * @return timer
     */
    public final CountDownTimer getTimer() {
        return this.timer;
    }
}