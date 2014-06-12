package com.taxi_trouble.game.screens.hud;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taxi_trouble.game.model.team.Team;

/**HUDComponent displaying the winning team and their score.
 *
 * @author Computer Games Project Group 8
 *
 */
public class EndGameHUD implements HUDComponent {

    private Team team;
    private int xPosition;
    private int yPosition;

    /**Initializes an EndGameHUD displaying a message with winning team
     * and their score on screen at position (xPos, yPos).
     *
     * @param winner : the winning team
     * @param xPos : x-position of the hud-component
     * @param yPos : y-position of the hud-component
     */
    public EndGameHUD(Team winner, int xPos, int yPos) {
        this.team = winner;
        this.xPosition = xPos;
        this.yPosition = yPos;
    }

    /**Renders the text of who won on screen with teamId and score.
     *
     */
    @Override
    public void render(SpriteBatch spriteBatch, BitmapFont hudFont, Team team) {
        render(spriteBatch, hudFont);
    }
    
    public void render(SpriteBatch spriteBatch, BitmapFont hudFont) {
        spriteBatch.begin();
        String winnerText = String.format("Team %d won!", this.team.getTeamId() + 1);
        String winnerScore = String.format("Score: %d", this.team.getScore());
        hudFont.draw(spriteBatch, winnerText, xPosition, yPosition);
        hudFont.draw(spriteBatch, winnerScore, xPosition, yPosition - hudFont.getCapHeight());
        spriteBatch.end();
    }

}
