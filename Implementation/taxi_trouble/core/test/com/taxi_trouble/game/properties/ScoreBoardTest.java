package com.taxi_trouble.game.properties;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;


/**This class tests the functionality of the ScoreBoard class which
 * keeps the score of teams.
 *
 * @author Computer Games Project Group 8
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ScoreBoardTest {

    private ScoreBoard scoreBoard;

    @Mock
    private BitmapFont scoreFont;

    /**Initialize the score board that should be used
     * for the tests.
     * 
     */
    @Before
    public void initScoreBoard() {
        scoreBoard = new ScoreBoard(scoreFont);
    }

    /**Check that if the current score, which is initially zero,
     * is incremented, that the score becomes one more.
     * 
     */
    @Test
    public void incrementScoreTest() {
       assertEquals(0, scoreBoard.getScore());
       scoreBoard.incrScore();
       assertEquals(1, scoreBoard.getScore());
    }

    /**Check that if the current score is changed to a different
     * score, the score will be updated accordingly.
     * 
     */
    @Test
    public void changeScoreTest() {
       assertEquals(0, scoreBoard.getScore());
       scoreBoard.setScore(10);
       assertEquals(10, scoreBoard.getScore());
    }
}
