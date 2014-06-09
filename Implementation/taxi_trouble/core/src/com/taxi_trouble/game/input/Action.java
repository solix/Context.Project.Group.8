package com.taxi_trouble.game.input;

/**An enumeration of the actions that can be performed pressing buttons.
 *
 * @author Computer Games Project Group 8
 *
 */
public enum Action {
    /**
     * Accelerate the taxi of a team.
     */
    ACCELERATE,

    /**
     * Brake the taxi of a team.
     */
    BRAKE,

    /**
     * Steer the taxi of a team to the left.
     */
    LEFT,

    /**
     * Steer the taxi of a team to the right.
     */
    RIGHT,

    /**
     * The default action of the DPad.
     */
    DPAD_DEFAULT,

    /**
     * Not active button.
     */
    NOT_ACTIVE,

    /**
     * Activating a powerUp.
     */
    ACTIVATE_POWERUP
}
