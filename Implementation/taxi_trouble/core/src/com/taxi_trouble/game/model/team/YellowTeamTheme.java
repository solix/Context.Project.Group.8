package com.taxi_trouble.game.model.team;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

import static com.taxi_trouble.game.properties.ResourceManager.yellowTaxiSprite;

/**YellowTeamTheme defines the theme for the yellow team.
 * 
 * @author Computer Games Project Group 8
 *
 */
public class YellowTeamTheme implements TeamTheme {

    /**Retrieves the color of the yellow team.
     *
     * @return color
     *
     */
    @Override
    public Color getTeamColor() {
        return Color.YELLOW;
    }

    /**Retrieves the sprite for the taxi of the yellow team.
     *
     * @return taxi sprite
     *
     */
    @Override
    public Sprite getTaxiSprite() {
        return yellowTaxiSprite;
    }

}
