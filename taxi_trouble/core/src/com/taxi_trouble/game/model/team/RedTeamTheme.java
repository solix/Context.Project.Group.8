package com.taxi_trouble.game.model.team;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

import static com.taxi_trouble.game.properties.ResourceManager.redTaxiSprite;

/**RedTeamTheme defines the theme for the red team.
 * 
 * @author Computer Games Project Group 8
 *
 */
public class RedTeamTheme implements TeamTheme {

    /**Retrieves the color of the red team.
     *
     * @return color
     *
     */
    @Override
    public Color getTeamColor() {
        return Color.RED;
    }

    /**Retrieves the sprite for the taxi of the red team.
     *
     * @return taxi sprite
     *
     */
    @Override
    public Sprite getTaxiSprite() {
        return redTaxiSprite;
    }

}
