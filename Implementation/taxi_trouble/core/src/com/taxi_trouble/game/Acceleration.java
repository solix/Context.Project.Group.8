package com.taxi_trouble.game;

/**An enumeration of the ways in which a taxi can accelerate.
 *
 * @author Computer Games Project Group 8
 *
 */
public enum Acceleration {
    /**
     * Accelerate / speed up the taxi.
     */
    ACC_ACCELERATE,

    /**
     * Brake the taxi.
     */
    ACC_BRAKE,

    /**
     * Do not accelerate or brake.
     */
    ACC_NONE;
    
    /**
     * Storing the values so they don't have to be generated everytime Acceleration.values() is needed. 
     * Acceleration.values can now be used instead.
     */
    public static Acceleration[] values = Acceleration.values();
}


