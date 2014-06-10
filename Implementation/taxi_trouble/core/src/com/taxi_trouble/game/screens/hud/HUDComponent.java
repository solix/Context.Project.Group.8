package com.taxi_trouble.game.screens.hud;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taxi_trouble.game.model.team.Team;

/**A HUDComponent is a component of the Head-up display which can be displayed
 * on the screen of a player device.
 * 
 * @author Computer Games Project Group 8
 *
 */
public interface HUDComponent {

    /**Renders the hud component with a given sprite batch and font for a specified team.
     * 
     * @param spriteBatch
     * @param hudFont
     * @param team
     */
    public void render(SpriteBatch spriteBatch, BitmapFont hudFont, Team team);
}
