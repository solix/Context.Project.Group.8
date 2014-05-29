package com.taxi_trouble.game.properties;

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
    private final static int SCORE_XPOSITION = 10;
    private final static int SCORE_YPOSITION = 470;

    /**Initializes a new ScoreBoard.
     * The score will be initialized to zero.
     *
     */
    public ScoreBoard(BitmapFont scoreBoardFont) {
        this.score = 0;
        this.scoreName = "Score  " + getScore();
        this.scoreBoardFont = scoreBoardFont;
        this.scoreBoardFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    /**Renders the score board on screen using the given spriteBatch.
     *
     * @param spriteBatch
     */
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        scoreName = "Score  " + getScore();
        scoreBoardFont.draw(spriteBatch, scoreName, SCORE_XPOSITION, SCORE_YPOSITION);
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
