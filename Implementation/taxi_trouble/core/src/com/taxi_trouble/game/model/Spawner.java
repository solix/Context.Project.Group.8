package com.taxi_trouble.game.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A spawner which can be called for spawning new passengers, taxis and
 * setting destination/deliver points for a taxi.
 *
 * @author Computer Games Project Group 8
 *
 */
public class Spawner {
    private List<SpawnPoint> passengerspawnpoints;
    private List<SpawnPoint> taxispawnpoints;
    private List<SpawnPoint> destinationpoints;
    
    /**Initializes a new Spawner which can store spawn points and
     * spawn taxis, passengers and create destination points.
     * 
     */
    public Spawner() {
        passengerspawnpoints = new ArrayList<SpawnPoint>();
        taxispawnpoints = new ArrayList<SpawnPoint>();
        destinationpoints = new ArrayList<SpawnPoint>();
    }
    
    /**Add a new Passenger spawn point.
     *
     * @param spawnPoint : position of the spawn point
     */
    public void addPassengerPoint(SpawnPoint spawnPoint){
        spawnPoint.getPosition().add(1f,1f);
        passengerspawnpoints.add(spawnPoint);
    }
    
    /**Add a new Taxi spawn point.
     *
     * @param spawnPoint : position of the spawn point
     */
    public void addTaxiPoint(SpawnPoint spawnPoint){
        taxispawnpoints.add(spawnPoint);
    }
    
    /**Add a new destination point.
     *
     * @param spawnPoint : the position of the destination point
     */
    public void addDestination(SpawnPoint spawnPoint) {
        destinationpoints.add(spawnPoint);
    }
    
    /**Spawn a new passenger into a specified world at a randomly chosen
     * spawn point.
     *
     * @param world : the world into which the passenger should be spawned
     * @return the spawned passenger
     */
    public Passenger spawnPassenger(World world){
        //Pick a random passenger spawn point as location to spawn a passenger
        int random = (int) (Math.abs(Math.random() * passengerspawnpoints.size() - 1));
        SpawnPoint spawnPoint = passengerspawnpoints.get(random);
        //Move the passenger to the center of the spawn point
        //position.add(1f,1f);
        Passenger pass = new Passenger(world, 2, 2, spawnPoint.getPosition(), spawnPoint.getAngle());
        int i = (int) (Math.random() * 3) + 1;
        //Pick a randomly chosen character sprite for the passenger
        pass.setSprite(new Sprite(new Texture("sprites/characters/character-" + i + "-standing.png")));
        return pass;
    }
}
