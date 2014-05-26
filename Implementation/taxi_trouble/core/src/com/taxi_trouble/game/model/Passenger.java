package com.taxi_trouble.game.model;

import static com.taxi_trouble.game.properties.GameProperties.PIXELS_PER_METER;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
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
        this.character = character;
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
        InitFixtureDef();
    }

    /**
     * Retrieves the fixture for the body of the solid passenger.
     *
     */
    private void InitFixtureDef() {
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
     * Retrieves the current angle under which the passenger is placed in the
     * world.
     *
     * @return angle
     */
    public float getAngle() {
        assert (this.getBody() != null);
        return this.getBody().getAngle();
    }

    /**
     * Sets the angle of the passenger.
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
        if (this.getDestination().equals(destination)) {
            map.getSpawner().despawnPassenger(this);
            resetSpawnPoint();
            //Deactivate the body of the passenger
            map.getWorld().step(0, 0, 0);
            this.getBody().setActive(false);
        }
    }

    /**
     * Resets the spawn point of the passenger.
     */
    public void resetSpawnPoint() {
        assert (this.spawnPoint != null);
        spawnPoint.setActive(true);
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

    public boolean isDelivered() {
        assert (this.spawnPoint != null);
        return this.spawnPoint.isActive();
    }

    /**
     * Renders the sprite(s) of the passenger.
     *
     * @param spriteBatch
     */
    public void render(SpriteBatch spriteBatch) {
        if (this.transporter != null) {
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