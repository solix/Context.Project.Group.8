package com.taxi_trouble.game.properties;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import static com.taxi_trouble.game.properties.GameProperties.screenHeight;

/**A scoreboard that keeps the score for a team.
 *
 * @author Computer Games Project Group 8
 *
 */
public class ScoreBoard {

    private int score;
	private BitmapFont scoreFont;
	private String scoreName;
	
	public ScoreBoard() {
	    score = 0;
        scoreName = "Score  "+ getScore();
        scoreFont = new BitmapFont();
	}

	public void render(){
		SpriteBatch scoreBatch = new SpriteBatch();
		scoreBatch.begin();
		scoreName = "Score  "+ getScore();
		scoreFont.setColor(0.0f, 0.0f, 1.0f, 1.0f);
		scoreFont.draw(scoreBatch, scoreName, 10, screenHeight -10);
		scoreBatch.end();
	}

	/**Retrieve the score.
	 *
	 * @return score
	 */
	public int getScore() {
		return this.score;
	}

	/**Set the score to a specified score.
	 *
	 * @param score
	 */
	public void setScore(int score) {
	    this.score = score;
	}

	/**Increment the current score.
	 *
	 */
	public void incrScore() {
	    this.score++;
	}

}
