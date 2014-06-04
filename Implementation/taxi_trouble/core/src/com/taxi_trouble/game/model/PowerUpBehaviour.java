package com.taxi_trouble.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * PowerUpBehaviour interface. Contains 2 methods. triggerEvent and render.
 * 
 * @author Lboy
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
