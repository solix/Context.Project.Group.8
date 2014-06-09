package com.taxi_trouble.game.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.taxi_trouble.game.model.powerups.PowerUp;

/**This class tests the functionality of the PowerUp class.
*
* @author Computer Games Project Group 8
*
*/
@RunWith(MockitoJUnitRunner.class)
public class PowerUpTest {

    private PowerUp powerUp;
    
    @Mock
    private SpawnPoint spawnPoint;

    /**Initialize the powerUp to be used for testing.
     *
     */
    @Before
    public final void setup() {
        powerUp = new PowerUp(spawnPoint);
        when(spawnPoint.getPosition()).thenReturn(new Vector2(1, 2));
        World world = new World(new Vector2(0, 0), false);
        powerUp.initializeBody(world);
    }

    /**Verify that the powerUp is successfully initialized.
    *
    */
    @Test
    public void powerUpSuccessFullyInitializedTest() {
        assertNotNull(powerUp);
    }

    /**Checks that the position is correctly retrieved from
     * the spawnpoint of the powerUp.
     * 
     */
    @Test
    public final void retrievePositionTest() {
        assertEquals(new Vector2(1, 2), powerUp.getPosition());
    }

    /**Checks that the x-position is correctly retrieved from the
     * powerUp spawnpoint.
     * 
     */
    @Test
    public final void retrieveXPositionTest() {
        assertEquals(1, powerUp.getXPosition(), 0);
    }

    /**Checks that the x-position is correctly retrieved from the
     * powerUp spawnpoint.
     * 
     */
    @Test
    public final void retrieveYPositionTest() {
        assertEquals(2, powerUp.getYPosition(), 0);
    }
}
