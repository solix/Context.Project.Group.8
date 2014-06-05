package com.taxi_trouble.game.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

@RunWith(MockitoJUnitRunner.class)
public class PowerUpTest {

    private PowerUp powerUp;
    
    @Mock
    private SpawnPoint spawnPoint;

    /**Initialize the powerup to be used for testing.
     *
     */
    @Before
    public final void setup() {
        powerUp = new PowerUp(spawnPoint);
        when(spawnPoint.getPosition()).thenReturn(new Vector2(1, 2));
        World world = new World(new Vector2(0, 0), false);
        powerUp.initializeBody(world);
    }

    /**Checks that the position is correctly retrieved from
     * the spawnpoint of the powerup.
     * 
     */
    @Test
    public final void retrievePositionTest() {
        assertEquals(new Vector2(1, 2), powerUp.getPosition());
    }

    /**Checks that the x-position is correctly retrieved from the
     * powerup spawnpoint.
     * 
     */
    @Test
    public final void retrieveXPositionTest() {
        assertEquals(2, powerUp.getXPosition(), 0);
    }

    /**Checks that the x-position is correctly retrieved from the
     * powerup spawnpoint.
     * 
     */
    @Test
    public final void retrieveYPositionTest() {
        assertEquals(3, powerUp.getYPosition(), 0);
    }
}
