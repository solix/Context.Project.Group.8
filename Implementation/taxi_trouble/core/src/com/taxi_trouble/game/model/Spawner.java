package com.taxi_trouble.game.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
    private List<Vector2> passengerspawnpoints;
    private List<Vector2> taxispawnpoints;
    private List<Vector2> destinationpoints;
    
    /**Initializes a new Spawner which can store spawn points and
     * spawn taxis, passengers and create destination points.
     * 
     */
    public Spawner() {
        passengerspawnpoints = new ArrayList<Vector2>();
        taxispawnpoints = new ArrayList<Vector2>();
        destinationpoints = new ArrayList<Vector2>();
    }
    
    /**Add a new Passenger spawn point.
     *
     * @param vec : position of the spawn point
     */
    public void addPassengerPoint(Vector2 vec){
        passengerspawnpoints.add(vec);
    }
    
    /**Add a new Taxi spawn point.
     *
     * @param vec : position of the spawn point
     */
    public void addTaxiPoint(Vector2 vec){
        taxispawnpoints.add(vec);
    }
    
    /**Add a new destination point.
     *
     * @param vec : the position of the destination point
     */
    public void addDestination(Vector2 vec) {
        destinationpoints.add(vec);
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
        Vector2 position = passengerspawnpoints.get(random);
        //Move the passenger to the center of the spawn point
        position.add(1f,1f);
        Passenger pass = new Passenger(world, 2, 2, position);
        int i = (int) (Math.random() * 3) + 1;
        //Pick a randomly chosen character sprite for the passenger
        pass.setSprite(new Sprite(new Texture("sprites/characters/character-" + i + "-standing.png")));
        return pass;
    }
}
