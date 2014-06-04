package com.taxi_trouble.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * PowerUp class for handling powerups.
 * 
 * @author Context group 8
 * 
 */
public class PowerUp {

    private Body body;
    private float width;
    private float height;
    private SpawnPoint spawnPoint;
    private PowerUpBehaviour behaviour;

    /**
     * Constructor for the powerup. NOTE: the behaviour has to be set apart.
     * 
     * @param point
     */
    public PowerUp(SpawnPoint point) {
        this.width = point.getWidth();
        this.height = point.getHeight();
        this.spawnPoint = point;
    }

    /**
     * Returns the width of the powerup.
     * 
     * @return
     */
    public float getWidth() {
        return width;
    }

    /**
     * Sets the width of the powerup to the given parameter.
     * 
     * @param width
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * Returns the height of the powerup.
     * 
     * @return
     */
    public float getHeight() {
        return height;
    }

    /**
     * Sets the height of the powerup to the given one.
     * 
     * @param height
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * Initializes the body of the powerup.
     * 
     * @param world
     */
    public void initializeBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(spawnPoint.getPosition());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        this.setBody(world.createBody(bodyDef));
        initFixtureDef();
    }

    /**
     * Retrieves the fixture of the body of the powerup.
     */
    private void initFixtureDef() {
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape powerShape = new PolygonShape();
        powerShape.setAsBox(this.width / 2, this.height / 2);
        fixtureDef.shape = powerShape;
        fixtureDef.isSensor = true;
        fixtureDef.restitution = 0f;
        this.body.createFixture(fixtureDef);
        powerShape.dispose();
    }

    /**
     * Sets the body of the powerup to the given parameter.
     * 
     * @param body
     */
    public void setBody(Body body) {
        this.body = body;
        this.body.setUserData(this);
    }

    /**
     * Retrieves the body of the PowerUp.
     * 
     * @return
     */
    public Body getBody() {
        return this.body;
    }

    /**
     * Retrieves the position of this powerup.
     * 
     * @return position
     */
    public Vector2 getPosition() {
        assert (this.getBody() != null);
        return this.spawnPoint.getPosition();
    }

    /**
     * Retrieves the x-position.
     * 
     * @return x-position
     */
    public float getXPosition() {
        assert (this.getBody() != null);
        return this.spawnPoint.getPosition().x;
    }

    /**
     * Retrieves the y-position.
     * 
     * @return y-position
     */
    public float getYPosition() {
        assert (this.getBody() != null);
        return this.spawnPoint.getPosition().y;
    }

    /**
     * Sets the spawnpoint active to false, meaning it's free again.
     */
    public void resetSpawnpoint() {
        assert (this.spawnPoint != null);
        this.spawnPoint.setActive(false);

    }

    /**
     * Sets the behaviour of the powerup to the given argument.
     * 
     * @param behaviour
     */
    public void setBehaviour(PowerUpBehaviour behaviour) {
        this.behaviour = behaviour;
    }

    /**
     * Returns the behaviour of the powerup.
     * 
     * @return
     */
    public PowerUpBehaviour getBehaviour() {
        return this.behaviour;
    }

    /**
     * Render method for the powerup which calls the render method of the
     * behaviour for drawing the animation of the powerup.
     * 
     * @param spriteBatch
     */
    public void render(SpriteBatch spriteBatch) {
        this.behaviour.render(spriteBatch, getPosition());
    }
}
