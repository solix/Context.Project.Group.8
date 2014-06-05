package com.taxi_trouble.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * This behaviour defines the behaviour for the speed powerup.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class SpeedBehaviour implements PowerUpBehaviour {

    private PowerUpAnimation powerAnim;
    private float increasedMaxSpeed = 80;
    private final int POWERUPTIME = 5;

    /**
     * Constructor for the speed powerup behaviour.
     * Activating this powerup temporarily increases the maximum
     * speed of a taxi, i.e. gives it a speed boost.
     * 
     * @param anim : the animation to be used for the powerup
     */
    public SpeedBehaviour(PowerUpAnimation anim) {
        powerAnim = anim;
    }

    /**Activate the speed powerup which temporarily increases
     * the maximum speed of the taxi.
     * 
     */
    @Override
    public void triggerEvent(final Taxi taxi) {
        final float originalSpeed = taxi.getMaxSpeed();
        taxi.setMaxSpeed(increasedMaxSpeed);
        Timer.schedule(new Task() {
            @Override
            public void run() {
                taxi.setMaxSpeed(originalSpeed);
            }
        }, POWERUPTIME);
    }

    /**Renders the corresponding animation of the powerup.
     * 
     */
    @Override
    public void render(SpriteBatch spriteBatch, Vector2 location) {
        powerAnim.render(spriteBatch, location);
    }
}
