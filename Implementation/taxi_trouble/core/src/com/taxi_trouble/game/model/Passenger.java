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
 * A passenger.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class Passenger {
    private float width, height;
    private Body body;
    private Character character;
    private SpawnPoint spawnPoint;

    /**
     * Initializes a new passenger.
     *
     * @param world
     *            : the world in which the passenger is placed
     * @param width
     *            : the width of the passenger
     * @param height
     *            : the height of the passenger
     * @param character
     *            : the character assigned to this passenger
     * @param spawnPoint
     *            : the spawnPoint of the passenger
     */
    public Passenger(World world, float width, float height, float angle,
            Character character, SpawnPoint spawnPoint) {
        this.width = width;
        this.height = height;
        this.character = character;
        this.spawnPoint = spawnPoint;
        initializeBody(world, spawnPoint.getPosition(), angle);
        setSprite(character.getStanding());
    }

    /**
     * Initialize the body of the solid passenger.
     *
     * @param world
     *            : the world in which the solid passenger is placed
     * @param position
     *            : the position at which the solid passenger is placed
     */
    private void initializeBody(World world, Vector2 position, float angle) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.angle = angle * MathUtils.degreesToRadians;
        bodyDef.fixedRotation = true;
        this.body = world.createBody(bodyDef);
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
    }

    /**
     * Retrieves the x-position of the passenger.
     * 
     * @return x-position
     */
    public float getXPosition() {
        return this.getBody().getPosition().x;
    }

    /**
     * Retrieves the y-position of the passenger.
     * 
     * @return
     */
    public float getYPosition() {
        return this.getBody().getPosition().y;
    }

    /**
     * Sets the (initial) sprite of the passenger.
     * 
     * @param passSprite
     */
    public void setSprite(Sprite passSprite) {
        passSprite.setSize(this.getWidth(), this.getHeight());
        passSprite.setOrigin(this.getWidth() / 2, this.getHeight() / 2);
        this.getBody().setUserData(passSprite);
    }

    /**
     * Resets the spawn point of the passenger.
     */
    public void resetSpawnPoint() {
        spawnPoint.setActive(false);
    }

    /**
     * Renders the sprite(s) of the passenger.
     * 
     * @param spriteBatch
     */
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        Sprite sprite = (Sprite) this.body.getUserData();
        sprite.setPosition(this.getXPosition() * PIXELS_PER_METER,
                this.getYPosition() * PIXELS_PER_METER);
        sprite.setRotation(this.getBody().getAngle()
                * MathUtils.radiansToDegrees);
        sprite.setScale(PIXELS_PER_METER);
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }
}