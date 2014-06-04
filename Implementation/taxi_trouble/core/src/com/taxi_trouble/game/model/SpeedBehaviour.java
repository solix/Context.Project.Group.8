package com.taxi_trouble.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * This behaviour defines the behaviour for the speed powerup
 * 
 * @author Context group 8
 * 
 */
public class SpeedBehaviour implements PowerUpBehaviour {

    private PowerUpAnimation powerAnim;

    /**
     * Constructor for the behaviour.
     * 
     * @param anim
     */
    public SpeedBehaviour(PowerUpAnimation anim) {
        powerAnim = anim;
    }

    @Override
    public void triggerEvent(Taxi taxi) {
        taxi.triggerSpeed();

    }

    @Override
    public void render(SpriteBatch spriteBatch, Vector2 location) {
        powerAnim.render(spriteBatch, location);

    }
}
