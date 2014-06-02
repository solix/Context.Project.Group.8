package com.taxi_trouble.game.model;

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

    private String type;
    private Body body;
    private float width;
    private float height;
    private SpawnPoint point;

    /**
     * Initializes a new PowerUp with the given parameters.
     * 
     * @param type
     * @param point
     */
    public PowerUp(String type, SpawnPoint point) {
        this.type = type;
        this.width = point.getWidth();
        this.height = point.getHeight();
        this.point = point;
    }

    /**
     * Retrieves the type of the powerup.
     * 
     * @return
     */
    public String getType() {
        return this.type;
    }

    /**
     * Initializes the body of the powerup.
     * 
     * @param world
     * @param position
     */
    public void initializeBody(World world, Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
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
     * Retrieves the position of the PowerUp.
     * 
     * @return
     */
    public Vector2 getPosition() {
        return this.point.getPosition();
    }
}
