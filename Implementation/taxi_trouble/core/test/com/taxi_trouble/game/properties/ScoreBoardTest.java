package com.taxi_trouble.game.properties;

import static org.junit.Assert.*;

import com.badlogic.gdx.Gdx;
import com.taxi_trouble.game.model.GameWorld;
import com.taxi_trouble.game.properties.ResourceManager;

import org.junit.Before;
import org.junit.Test;


/**This class tests the functionality of the ScoreBoard class which
 * keeps the score of teams.
 *
 * @author Computer Games Project Group 8
 *
 */
public class ScoreBoardTest {

    private ScoreBoard scoreBoard;

    @Before
    public void initScoreBoard() {
        //Temporarily disabled
        //ResourceManager.loadFonts();
        //scoreBoard = new ScoreBoard();
    }

    @Test
    public void incrementScoreTest() {
        //assertEquals(0, scoreBoard.getScore());
    }
}
