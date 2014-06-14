package com.taxi_trouble.game.model;

import com.taxi_trouble.game.model.powerups.PowerUp;
import com.taxi_trouble.game.model.team.BlueTeamTheme;
import com.taxi_trouble.game.model.team.GreenTeamTheme;
import com.taxi_trouble.game.model.team.RedTeamTheme;
import com.taxi_trouble.game.model.team.Team;
import com.taxi_trouble.game.model.team.YellowTeamTheme;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;


/**This class tests the functionality of the Team class.
 *
 * @author Computer Games Project Group 8
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class TeamTest {

    private Team team0;
    private Team team1;
    private Team team2;
    private Team team3;
    
    @Mock
    private Taxi taxi;

    @Mock
    private PowerUp powerUp;

    /**Initialize the teams that should be used
     * for the tests.
     * 
     */
    @Before
    public void initTeam() {
        team0 = new Team(0, taxi);
        team1 = new Team(1, taxi);
        team2 = new Team(2, taxi);
        team3 = new Team(3, taxi);
    }

    /**Check that if the current score, which is initially zero,
     * is incremented, that the score becomes one more.
     * 
     */
    @Test
    public void incrementScoreTest() {
       assertEquals(0, team0.getScore());
       team0.incScore();
       assertEquals(1, team0.getScore());
    }

    /**Check that if the current score is changed to a different
     * score, the score will be updated accordingly.
     * 
     */
    @Test
    public void changeScoreTest() {
       assertEquals(0, team0.getScore());
       team0.setScore(10);
       assertEquals(10, team0.getScore());
    }

    /**Check that the team initially does not have a power-up.
     * 
     */
    @Test
    public void noPowerUpTest() {
        assertFalse(team0.hasPowerUp());
    }

    /**Checks that if a power-up is set (power-up is picked up)
     * the team has a power-up.
     * 
     */
    @Test
    public void setPowerUpTest() {
        assertFalse(team0.hasPowerUp());
        team0.setPowerUp(powerUp);
        assertTrue(team0.hasPowerUp());
    }

    /**Checks that if a power-up is used, the team loses the power-up
     * and the power-up is activated.
     * 
     */
    @Test
    public final void usePowerUpTest() {
        team0.setPowerUp(powerUp);
        assertTrue(team0.hasPowerUp());
        team0.usePowerUp();
        verify(powerUp).activatePowerUp(taxi);
        assertFalse(team0.hasPowerUp());
    }

    /**Checks that team with id equal to 0 gets the yellow theme.
     * 
     */
    @Test
    public final void teamZeroGetsYellowTheme() {
        assertTrue(team0.getTeamTheme() instanceof YellowTeamTheme);
    }

    /**Checks that team with id equal to 1 gets the blue theme.
     * 
     */
    @Test
    public final void teamOneGetsBlueTheme() {
        assertTrue(team1.getTeamTheme() instanceof BlueTeamTheme);
    }

    /**Checks that the team with id equal to 2 gets the green theme.
     * 
     */
    @Test
    public final void teamTwoGetsGreenTheme() {
        assertTrue(team2.getTeamTheme() instanceof GreenTeamTheme);
    }

    /**Checks that team with id equal to 3 gets the red theme.
     * 
     */
    @Test
    public final void teamThreeGetsRedTheme() {
        assertTrue(team3.getTeamTheme() instanceof RedTeamTheme);
    }
}
