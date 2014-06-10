package com.taxi_trouble.game.screens.hud;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taxi_trouble.game.model.team.Team;

/**HUDComponent displaying in which team one is.
 * 
 * @author Computer Games Project Group 8
 *
 */
public class TeamHUD implements HUDComponent {
    private String teamCaption;
    private int xPosition;
    private int yPosition;

    /**Initializes a TeamHUD displaying the team
     * with given caption at position (xPos, yPos) of the screen.
     * 
     * @param teamCaption : caption displayed in front of the team no.
     * @param xPos : x-position of the team no. on screen
     * @param yPos : y-position of the team no. on screen
     */
    public TeamHUD(String teamCaption, int xPos, int yPos) {
        this.teamCaption = teamCaption;
        this.xPosition = xPos;
        this.yPosition = yPos;
    }

    /**Renders the team no. with the defined teamCaption and position on screen.
     * 
     * @param spriteBatch : batch to be used to render
     * @param hudFont : font to be used to display the caption and team no.
     * @param team : the team
     * 
     */
    @Override
    public void render(SpriteBatch spriteBatch, BitmapFont hudFont, Team team) {
        spriteBatch.begin();
        String teamText = String.format(teamCaption + " %d", (team.getTeamId() + 1));
        hudFont.draw(spriteBatch, teamText, xPosition, yPosition);
        spriteBatch.end();
    }

}
