package com.taxi_trouble.game.model.powerups;

import static com.taxi_trouble.game.properties.ResourceManager.invincibilityButtonSprite;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.taxi_trouble.game.model.Taxi;

public class IncreaseTimeBehaviour implements PowerUpBehaviour {

    private PowerUpAnimation powerAnim;
    private final int POWERUPTIME = 10;

    /**
     * Constructor for the behaviour of the IncreaseTimerPowerup. Activating
     * this timer increases the time left to drop off a passenger
     * 
     * @param anim
     *            : the animation to be used for the powerup
     */
    public IncreaseTimeBehaviour(PowerUpAnimation anim) {
        this.powerAnim = anim;
    }

    /**
     * Increase the time left to drop off the passenger if the taxi has a
     * passenger, else do nothing.
     */
    @Override
    public void triggerEvent(Taxi taxi) {
        if (taxi.pickedUpPassenger()) {
            taxi.getPassenger().getDropOffTimer().increaseTime(POWERUPTIME);
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch, Vector2 location) {
        this.powerAnim.render(spriteBatch, location);
    }

    @Override
    public Sprite getActivationButtonSprite() {
        return invincibilityButtonSprite;
    }

}
