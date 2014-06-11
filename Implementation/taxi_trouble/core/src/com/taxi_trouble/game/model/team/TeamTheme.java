package com.taxi_trouble.game.model.team;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**Interface for defining the properties of a theme.
 * 
 * @author Computer Games Project Group 8
 *
 */
public interface TeamTheme {

    /**Retrieve the color corresponding to the theme.
     * 
     * @return teamcolor
     */
    public Color getTeamColor();

    /**Retrieve the sprite for the taxi of the team.
     * 
     * @return taxi sprite
     */
    public Sprite getTaxiSprite();
}
