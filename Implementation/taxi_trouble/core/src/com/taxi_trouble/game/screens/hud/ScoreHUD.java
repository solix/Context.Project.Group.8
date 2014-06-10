package com.taxi_trouble.game.screens.hud;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taxi_trouble.game.model.team.Team;

/**HUDComponent displaying a teams score on screen.
 * 
 * @author Computer Games Project Group 8
 *
 */
public class ScoreHUD implements HUDComponent {
    private String scoreCaption;
    private int xPosition;
    private int yPosition;

    /**Initializes a ScoreHUD displaying the team score
     * with given caption at position (xPos, yPos) of the screen.
     * 
     * @param scoreCaption : caption displayed in front of the score value
     * @param xPos : x-position of the score on screen
     * @param yPos : y-position of the score on screen
     */
    public ScoreHUD(String scoreCaption, int xPos, int yPos) {
        this.scoreCaption = scoreCaption;
        this.xPosition = xPos;
        this.yPosition = yPos;
    }

    /**Renders the score with the defined scoreCaption and position on screen.
     * 
     * @param spriteBatch : batch to be used to render
     * @param hudFont : font to be used to display the caption and score
     * @param team : team for which the score is displayed
     * 
     */
    @Override
    public void render(SpriteBatch spriteBatch, BitmapFont hudFont, Team team) {
        spriteBatch.begin();
        String scoreText = String.format(scoreCaption + " %d", team.getScore());
        hudFont.draw(spriteBatch, scoreText, xPosition, yPosition);
        spriteBatch.end();
    }

}
