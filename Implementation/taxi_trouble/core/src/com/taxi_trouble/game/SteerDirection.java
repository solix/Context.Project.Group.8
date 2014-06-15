package com.taxi_trouble.game;

/**An enumeration of the directions in which a car can be steered.
 *
 * @author Computer Games Project Group 8
 *
 */
public enum SteerDirection {
    /**
     * Steering left.
     */
    STEER_LEFT,

    /**
     * Steering right.
     */
    STEER_RIGHT,

    /**
     * Steering in no direction.
     */
    STEER_NONE;
    
    /**
     * Storing the values so they don't have to be generated everytime SteerDirection.values() is needed. 
     * Steerdirection.values can now be used instead.
     */
    public static SteerDirection[] values = SteerDirection.values();
}
