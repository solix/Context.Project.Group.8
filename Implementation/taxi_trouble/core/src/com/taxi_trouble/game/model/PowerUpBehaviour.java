package com.taxi_trouble.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * PowerUpBehaviour interface that can be used to define various behaviours
 * for powerups. As well defines the rendering of the powerup.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public interface PowerUpBehaviour {

    /**
     * Triggers the event belonging to the type of powerup
     * 
     * @param taxi
     */
    public void triggerEvent(Taxi taxi);

    /**
     * Renders the powerupAnimation
     * 
     * @param spriteBatch
     * @param location
     */
    public void render(SpriteBatch spriteBatch, Vector2 location);

}
