package com.taxi_trouble.game.screens.hud;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taxi_trouble.game.model.CountDownTimer;
import com.taxi_trouble.game.model.team.Team;

/**HUDComponent displaying a timer on screen.
 * 
 * @author Computer Games Project Group 8
 *
 */
public class TimerHUD implements HUDComponent {
    private String timerCaption;
    private int xPosition;
    private int yPosition;
    private CountDownTimer timer;

    /**Initializes a TimerHud displaying time
     * with given caption at position (xPos, yPos) of the screen.
     * 
     * @param timerCaption : caption displayed in front of the timer
     * @param xPos : x-position of the timer on screen
     * @param yPos : y-position of the timer on screen
     */
    public TimerHUD(String timerCaption, int xPos, int yPos, CountDownTimer timer) {
        this.timerCaption = timerCaption;
        this.xPosition = xPos;
        this.yPosition = yPos;
        this.timer = timer;
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
        String timerText = timerCaption + " " + timer.getMinutesFormat();
        hudFont.draw(spriteBatch, timerText, xPosition, yPosition);
        spriteBatch.end();
    }

}
