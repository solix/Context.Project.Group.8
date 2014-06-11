package com.taxi_trouble.game.model.powerups;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.taxi_trouble.game.model.Taxi;

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

    /**
     * Retrieves the right buttonSprite for activating the button in the navigator screen.
     *
     * @return 
     */
    public Sprite getActivationButtonSprite();
    
    /**
     * This function will be the same for every implementation, consider making this an abstract class instead of an interface.
     * @return
     * behaviour ID
     */
	public int getId(); 
}
