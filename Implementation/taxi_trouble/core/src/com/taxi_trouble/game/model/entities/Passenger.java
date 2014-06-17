package com.taxi_trouble.game.model.entities;

import static com.taxi_trouble.game.properties.GameProperties.PIXELS_PER_METER;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.taxi_trouble.game.model.CountDownTimer;
import com.taxi_trouble.game.model.SpawnPoint;
import com.taxi_trouble.game.model.Spawner;
import com.taxi_trouble.game.model.WorldMap;

/**
 * A passenger which can be transported by a taxi to a certain destination when
 * picked up.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class Passenger extends Entity {
    private SpawnPoint spawnPoint;
    private Taxi transporter;
    private Destination destination;
	private CountDownTimer dropOffTimer;

    /**
     * Initializes a new passenger.
     * 
     * @param width
     *            : the width of the passenger
     * @param height
     *            : the height of the passenger
     * @param id
     *            : the identifier of the passenger
     */
    public Passenger(SpawnPoint point, int id) {
        super(id, point.getWidth(), point.getHeight());
        this.spawnPoint = point;
    }

    /**
     * Initialize the body of the solid passenger.
     * 
     * @param world
     *            : the world in which the solid passenger is placed
     * @param spawnPoint
     *            : the spawnPoint of the passenger
     */
    public void initializeBody(World world) {
        initializeBody(world, spawnPoint.getPosition(), 
                spawnPoint.getAngle() * MathUtils.degreesToRadians, 
                BodyType.StaticBody, true, true);
    }

    /**
     * Set the transporter of the passenger.
     * 
     */
    public void setTransporter(Taxi transporter) {
        this.transporter = transporter;
    }

    /**Retrieves the transporter of the passenger.
     * 
     * @return transporting taxi
     */
    public Taxi getTransporter(){
    	return this.transporter;
    }

    /**
     * Make the passenger lose its transporter, i.e. get out of the taxi.
     * 
     */
    public void cancelTransport() {
        this.transporter = null;
    }

    /**
     * Retrieves whether the passenger is transported, i.e. it has a
     * transporter/taxi.
     * 
     * @return boolean indicating whether the passenger is transported
     */
    public boolean isTransported() {
        return this.transporter != null;
    }

    /**
     * Deliver the passenger at its destination.
     * 
     * @param map
     *            : the map to remove the passenger from
     * @param destination
     *            : the destination to deliver the passenger
     * 
     */
    public void deliverAtDestination(WorldMap map, Destination destination) {
        assert (this.getBody() != null);
        if (destination == this.destination){
        	// Despawn the passenger and remove it from the world
            Spawner spawner = map.getSpawner();
            spawner.despawnPassenger(this);
            removePassengerFromWorld(map.getWorld());
            cancelTransport();
        }
        else{
        	System.out.println("POSSIBLE DESYNC: TRIED TO DROP OF PASSENGER AT WRONG DESTINATION");
        }
        
    }

    /**
     * Removes the passenger from the world.
     * 
     * @param world
     *            : the world from which the passenger should be removed
     */
    private void removePassengerFromWorld(World world) {
        world.step(0, 0, 0);
        world.destroyBody(this.getBody());
    }

    /**
     * Resets the spawn point of the passenger.
     */
    public void resetSpawnPoint() {
        assert (this.spawnPoint != null);
        this.spawnPoint.setActive(false);
    }

    /**
     * Set the passengers destination.
     * 
     * @param dest
     *            : the destination to be set
     */
    public void setDestination(Destination dest) {
        // The destination of a passenger can only be set once
        if (this.destination == null) {
            this.destination = dest;
        }
    }

    /**
     * Retrieves the passenger its destination.
     * 
     * @return destination : the passenger destination
     */
    public Destination getDestination() {
        return this.destination;
    }

    /**
     * Retrieves the startposition of the passenger from its spawnpoint.
     * 
     * @return startposition
     */
    public Vector2 getStartPosition() {
        assert (this.spawnPoint != null);
        return this.spawnPoint.getPosition();
    }

    /**Sets up the dropoff timer for the passenger indicating the remaining time
     * for dropping off the passenger.
     * 
     */
    public void setUpDropOffTimer() {
        if(this.dropOffTimer == null) {
            this.dropOffTimer = new CountDownTimer(computeDropOffTimeLimit());
            dropOffTimer.startTimer();
        }
    }

    /**Computes the drop-off time limit for this passenger to its destination.
     * 
     * @return drop-off time limit
     */
    private int computeDropOffTimeLimit() {
        float dist = this.getPosition().dst(this.destination.getPosition());
        int timeLimit = (int) (dist / 5) + 15;
        return timeLimit;
    }

    /**Retrieves the remaining time to drop-off the passenger.
     * 
     * @return remaining drop-off time in seconds
     */
    public int remainingDropOffTime() {
        return this.dropOffTimer.getTimeRemaining();
    }

    /**Retrieves the drop-off timer indicating the remaining time
     * to drop off the passenger.
     * 
     * @return drop-off Timer
     */
    public CountDownTimer getDropOffTimer() {
        return this.dropOffTimer;
    }

    /**
     * Renders the sprite(s) of the passenger.
     * 
     * @param spriteBatch
     */
    public void render(SpriteBatch spriteBatch) {
        if(this.getBody() != null){
            if (this.isTransported()) {
                setPosition(transporter.getPosition());
                setAngle(transporter.getAngle());
                System.out.println(this.getAngle());
            }
            spriteBatch.begin();
            getSprite().setPosition(getXPosition() * PIXELS_PER_METER,
                    getYPosition() * PIXELS_PER_METER);
            getSprite().setRotation(getBody().getAngle() * MathUtils.radiansToDegrees);
            getSprite().draw(spriteBatch);
            spriteBatch.end();
        }
   }

   @Override
   public void addBodyToWorld(World world){
       this.initializeBody(world);
   }
}