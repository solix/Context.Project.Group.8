package com.taxi_trouble.game.properties;

import static com.taxi_trouble.game.properties.GameProperties.VIRTUAL_HEIGHT;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * A scoreboard that keeps the score for a team.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class ScoreBoard {

    private int score;
    private BitmapFont scoreFont;
    private String scoreName;
    final static int TEN = 10;

    public ScoreBoard() {
        score = 0;
        scoreName = "Score  " + getScore();
        scoreFont = new BitmapFont();
    }

    public void render(SpriteBatch scoreBatch) {
        scoreBatch.begin();
        scoreName = "Score  " + getScore();
        scoreFont.setColor(0.0f, 0.0f, 1.0f, 1.0f);
        scoreFont.draw(scoreBatch, scoreName, TEN, VIRTUAL_HEIGHT + 150);
        scoreBatch.end();
    }

    /**
     * Retrieve the score.
     * 
     * @return score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Set the score to a specified score.
     * 
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Increment the current score.
     * 
     */
    public void incrScore() {
        this.score++;
    }

}
