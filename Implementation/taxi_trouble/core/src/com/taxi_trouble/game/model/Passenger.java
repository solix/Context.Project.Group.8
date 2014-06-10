package com.taxi_trouble.game.model;

import static com.taxi_trouble.game.properties.GameProperties.PIXELS_PER_METER;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.taxi_trouble.game.Character;

/**
 * A passenger which can be transported by a taxi to a certain destination when
 * picked up.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class Passenger {
    private float width, height;
    private Body body;
    private Character character;
    private SpawnPoint spawnPoint;
    private Sprite passengerSprite;
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
     * @param character
     *            : the character assigned to this passenger
     */
    public Passenger(float width, float height, Character character) {
        this.width = width;
        this.height = height;
        this.setCharacter(character);
        setSprite(character.getStanding());
    }

    /**
     * Initialize the body of the solid passenger.
     * 
     * @param world
     *            : the world in which the solid passenger is placed
     * @param spawnPoint
     *            : the spawnPoint of the passenger
     */
    public void initializeBody(World world, SpawnPoint spawnPoint) {
        this.spawnPoint = spawnPoint;
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(spawnPoint.getPosition());
        bodyDef.angle = spawnPoint.getAngle() * MathUtils.degreesToRadians;
        bodyDef.fixedRotation = true;
        this.setBody(world.createBody(bodyDef));
        initFixtureDef();
    }

    /**
     * Retrieves the fixture for the body of the solid passenger.
     * 
     */
    private void initFixtureDef() {
        FixtureDef fixtureDef = new FixtureDef();

        PolygonShape passengerShape = new PolygonShape();
        passengerShape.setAsBox(this.width / 2, this.height / 2);
        fixtureDef.shape = passengerShape;
        fixtureDef.isSensor = true;
        fixtureDef.restitution = 0f;
        this.body.createFixture(fixtureDef);
        passengerShape.dispose();
    }

    /**
     * Retrieves the width of the passenger.
     * 
     * @return width
     */
    public float getWidth() {
        return this.width;
    }

    /**
     * Retrieves the height of the passenger.
     * 
     * @return height
     */
    public float getHeight() {
        return this.height;
    }

    /**
     * Retrieves the body of the passenger.
     * 
     * @return body
     */
    public Body getBody() {
        return this.body;
    }

    /**
     * Changes the body of the passenger to the specified body.
     * 
     * @param body
     */
    public void setBody(Body body) {
        this.body = body;
        body.setUserData(this);
    }

    /**
     * Retrieves the x-position of the passenger.
     * 
     * @return x-position
     */
    public float getXPosition() {
        assert (this.getBody() != null);
        return this.getBody().getPosition().x;
    }

    /**
     * Retrieves the y-position of the passenger.
     * 
     * @return y-position
     */
    public float getYPosition() {
        assert (this.getBody() != null);
        return this.getBody().getPosition().y;
    }

    /**
     * Sets the position of a passenger.
     * 
     * @param position
     *            : the position of the passenger
     */
    public void setPosition(Vector2 position) {
        assert (this.getBody() != null);
        this.getBody().setTransform(position, this.body.getAngle());
    }

    /**
     * Retrieves the current position of the passenger.
     * 
     * @return current position
     */
    public Vector2 getPosition() {
        assert (this.getBody() != null);
        return this.getBody().getPosition();
    }

    /**
     * Retrieves the current angle in radians under which the passenger is
     * placed in the world.
     * 
     * @return angle
     */
    public float getAngle() {
        assert (this.getBody() != null);
        return this.getBody().getAngle();
    }

    /**
     * Sets the angle in radians of the passenger.
     * 
     * @param angle
     *            : the new angle
     */
    public void setAngle(float angle) {
        assert (this.getBody() != null);
        this.getBody().setTransform(getPosition(), angle);
    }

    /**
     * Sets the (initial) sprite of the passenger.
     * 
     * @param passSprite
     */
    public void setSprite(Sprite passSprite) {
        passSprite.setSize(this.getWidth(), this.getHeight());
        passSprite.setOrigin(this.getWidth() / 2, this.getHeight() / 2);
        this.passengerSprite = passSprite;
    }

    /**
     * Set the transporter of the passenger.
     * 
     */
    public void setTransporter(Taxi transporter) {
        this.transporter = transporter;
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
        // Check if the destination is the right location
        if (this.getDestination().equals(destination) && isTransported()) {
            // Despawn the passenger and remove it from the world
            Spawner spawner = map.getSpawner();
            spawner.despawnPassenger(this);
            removePassengerFromWorld(map.getWorld());
            cancelTransport();
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

    /**
     * Retrieves the character of the passenger.
     * 
     * @return character
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * Sets the character of the passenger to the given argument.
     * 
     * @param character
     */
    public void setCharacter(Character character) {
        this.character = character;
    }

    /**Sets up the dropoff timer for the passenger indicating the remaining time
     * for dropping off the passenger.
     * 
     */
    public void setUpDropOffTimer() {
        if(this.dropOffTimer == null) {
            this.dropOffTimer = new CountDownTimer(30);
            dropOffTimer.startTimer();
        }
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
        if (this.isTransported()) {
            setPosition(transporter.getPosition());
            setAngle(transporter.getAngle());
        }
        spriteBatch.begin();
        passengerSprite.setPosition(this.getXPosition() * PIXELS_PER_METER,
                this.getYPosition() * PIXELS_PER_METER);
        passengerSprite.setRotation(this.getBody().getAngle()
                * MathUtils.radiansToDegrees);
        passengerSprite.setScale(PIXELS_PER_METER);
        passengerSprite.draw(spriteBatch);
        spriteBatch.end();
    }
}