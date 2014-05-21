package com.taxi_trouble.game.model;

import static com.taxi_trouble.game.properties.ResourceManager.getRandomCharacter;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.physics.box2d.World;
import com.taxi_trouble.game.Character;
import com.taxi_trouble.game.properties.ResourceManager;

/**
 * A spawner which can be called for spawning new passengers, taxis and setting
 * destination/deliver points for a taxi.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class Spawner {
    private List<SpawnPoint> passengerspawnpoints;
    private List<SpawnPoint> taxispawnpoints;
    private List<SpawnPoint> destinationpoints;

    /**
     * Initializes a new Spawner which can store spawn points and spawn taxis,
     * passengers and create destination points.
     * 
     */
    public Spawner() {
        passengerspawnpoints = new ArrayList<SpawnPoint>();
        taxispawnpoints = new ArrayList<SpawnPoint>();
        destinationpoints = new ArrayList<SpawnPoint>();
    }

    /**
     * Add a new Passenger spawn point.
     * 
     * @param spawnPoint
     *            : position of the spawn point
     */
    public void addPassengerPoint(SpawnPoint spawnPoint) {
        spawnPoint.getPosition().add(1f, 1f);
        passengerspawnpoints.add(spawnPoint);
    }

    /**
     * Add a new Taxi spawn point.
     * 
     * @param spawnPoint
     *            : position of the spawn point
     */
    public void addTaxiPoint(SpawnPoint spawnPoint) {
        taxispawnpoints.add(spawnPoint);
    }

    /**
     * Add a new destination point.
     * 
     * @param spawnPoint
     *            : the position of the destination point
     */
    public void addDestination(SpawnPoint spawnPoint) {
        destinationpoints.add(spawnPoint);
    }

    /**
     * Spawn a new passenger into a specified world at a randomly chosen spawn
     * point.
     * 
     * @param world
     *            : the world into which the passenger should be spawned
     * @return the spawned passenger
     */
    public Passenger spawnPassenger(World world) {
        // Pick a random passenger spawn point as location to spawn a passenger
        int random = (int) (Math.abs(Math.random()
                * passengerspawnpoints.size() - 1));
        while (passengerspawnpoints.get(random).isActive()) {
            random = (int) (Math.abs(Math.random()
                    * passengerspawnpoints.size() - 1));
        }
        SpawnPoint spawnPoint = passengerspawnpoints.get(random);
        // Assign a random character to the passenger
        Character character = getRandomCharacter();
        Passenger pass = new Passenger(world, 2, 2, spawnPoint.getAngle(),
                character, spawnPoint);
        return pass;
    }

    /**
     * Spawn a new taxi into a specified world at a randomly chosen spawn point.
     * 
     * @param world
     *            : the world into which the passenger should be spawned
     * @return
     */
    public Taxi spawnTaxi(World world) {
        //Pick a random taxi spawn point as location to spawn a taxi.
        int random = (int) (Math.abs(Math.random() 
                * taxispawnpoints.size() - 1));
        while (taxispawnpoints.get(random).isActive()) {
            random = (int) (Math
                    .abs(Math.random() * taxispawnpoints.size() - 1));
        }
        SpawnPoint spawnPoint = taxispawnpoints.get(random);
        spawnPoint.setActive(true);
        //Initialize the new Taxi spawned at the randomly chosen location
        Taxi taxi = new Taxi(2, 4, 20, 60, 60);
        taxi.createBody(world, spawnPoint.getPosition(), spawnPoint.getAngle());
        taxi.setSprite(ResourceManager.taxiSprite,ResourceManager.wheelSprite);
        return taxi;
    }
}