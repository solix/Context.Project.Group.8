package com.taxi_trouble.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * The Character class assigns the standing and walking sprites to a Passenger.
 * 
 * @author Context group 8
 * 
 */
public class Character {

    private Sprite standing;
    private Sprite walking1;
    private Sprite walking2;

    /**
     * Initializes the Character class with the 3 sprites given.
     * 
     * @param standing
     * @param walking1
     * @param walking2
     */
    public Character(Sprite standing, Sprite walking1, Sprite walking2) {
        this.standing = standing;
        this.walking1 = walking1;
        this.walking2 = walking2;
    }

    /**
     * Returns the standing sprite.
     * 
     * @return
     */
    public Sprite getStanding() {
        return standing;
    }

    /**
     * Sets the standing sprite with the given argument.
     * 
     * @param standing
     */
    public void setStanding(Sprite standing) {
        this.standing = standing;
    }

    /**
     * Returns the first walking sprite.
     * 
     * @return
     */
    public Sprite getWalking1() {
        return walking1;
    }

    /**
     * Sets the first walking sprite to the given argument.
     * 
     * @param walking1
     */
    public void setWalking1(Sprite walking1) {
        this.walking1 = walking1;
    }

    /**
     * Returns the second walking sprite.
     * 
     * @return
     */
    public Sprite getWalking2() {
        return walking2;
    }

    /**
     * Sets the second walking sprite to the given argument.
     * 
     * @param walking2
     */
    public void setWalking2(Sprite walking2) {
        this.walking2 = walking2;
    }

}
