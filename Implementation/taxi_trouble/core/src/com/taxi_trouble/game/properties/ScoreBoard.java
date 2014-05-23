package com.taxi_trouble.game.properties;

import static com.taxi_trouble.game.properties.GameProperties.screenHeight;
import static com.taxi_trouble.game.properties.ResourceManager.scoreFont;

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
    private BitmapFont scoreBoardFont;
    private String scoreName;
    private final static int TEN = 10;

    /**
     * Initializes a new ScoreBoard. The score will be initialized to zero.
     * 
     */
    public ScoreBoard() {
        this.score = 0;
        this.scoreName = "Score  " + getScore();
        this.scoreBoardFont = scoreFont;
        this.scoreBoardFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    /**
     * Renders the score board on screen using the given spriteBatch.
     * 
     * @param spriteBatch
     *            Spritebatch to be rendered.
     */
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        scoreName = "Score  " + getScore();
        scoreBoardFont.draw(spriteBatch, scoreName, TEN, screenHeight);
        spriteBatch.end();
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
     *            Score to be set.
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
