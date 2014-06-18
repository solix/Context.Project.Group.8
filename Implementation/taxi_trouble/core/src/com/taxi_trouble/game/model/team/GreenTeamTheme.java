package com.taxi_trouble.game.model.team;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

import static com.taxi_trouble.game.properties.ResourceManager.getSprite;

/**GreenTeamTheme defines the theme for the green team.
 * 
 * @author Computer Games Project Group 8
 *
 */
public class GreenTeamTheme implements TeamTheme {

    /**Retrieves the color of the green team.
     *
     * @return color
     *
     */
    @Override
    public Color getTeamColor() {
        return Color.GREEN;
    }

    /**Retrieves the sprite for the taxi of the green team.
     *
     * @return taxi sprite
     *
     */
    @Override
    public Sprite getTaxiSprite() {
        return getSprite("greenTaxiSprite");
    }

}
