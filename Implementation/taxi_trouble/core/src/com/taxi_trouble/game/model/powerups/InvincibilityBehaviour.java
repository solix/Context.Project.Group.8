package com.taxi_trouble.game.model.powerups;

import static com.taxi_trouble.game.properties.ResourceManager.invincibilityButtonSprite;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.taxi_trouble.game.model.Taxi;

/**
 * This behaviour defines the behaviour for invincibility.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class InvincibilityBehaviour implements PowerUpBehaviour {

    private PowerUpAnimation powerAnim;
    private final int POWERUPTIME = 10;
    private final int id = 1;

    /**
     * Constructor for the behaviour of the invicibility powerup.
     * Activating the powerup prevents passenger stealing from a
     * taxi for a short time.
     * 
     * @param anim : the animation to be used for the powerup
     */
    public InvincibilityBehaviour(PowerUpAnimation anim) {
        this.powerAnim = anim;
    }

    /**Activate the invicibility powerup which prevents passenger
     * stealing from this taxi for a short period of time.
     * 
     */
    @Override
    public void triggerEvent(Taxi taxi) {
    	System.out.println("INVINCIBLE"); 
        taxi.triggerInvincibility(POWERUPTIME);
    }

    /**Renders the corresponding animation of the powerup.
     * 
     */
    @Override
    public void render(SpriteBatch spriteBatch, Vector2 location) {
        this.powerAnim.render(spriteBatch, location);
    }

    @Override
    public Sprite getActivationButtonSprite() {
        return invincibilityButtonSprite; 
    }
    
    @Override
    public int getId(){
    	return this.id;
    }

}
