package com.taxi_trouble.game.model.entities;

import static com.taxi_trouble.game.properties.GameProperties.PIXELS_PER_METER;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A destination to which a passenger can be transported by a taxi.
 * After a passenger is picked up by a taxi, the team corresponding 
 * to the taxi should see the destination to bring the passenger to.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class Destination extends Entity {

    /**
     * Initializes a new destination where a passenger can be dropped off.
     * 
     * @param width
     * @param height
     */
    public Destination(float width, float height) {
        super(width, height);
    }

    /**
     * Initializes the body of this destination.
     * 
     * @param world : the world into which the body should be placed
     * @param position : the position at which the body should be placed
     */
    public void initializeBody(World world, Vector2 position) {
        initializeBody(world, position, BodyType.DynamicBody, true);
    }

    /**
     * Renders the sprite of the destination on screen.
     *
     * @param spriteBatch : the spriteBatch that is used for drawing the sprite
     */
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        getSprite().setPosition(this.getXPosition() * PIXELS_PER_METER,
                this.getYPosition() * PIXELS_PER_METER);
        getSprite().setScale(PIXELS_PER_METER);
        getSprite().draw(spriteBatch);
        spriteBatch.end();
    }
}
