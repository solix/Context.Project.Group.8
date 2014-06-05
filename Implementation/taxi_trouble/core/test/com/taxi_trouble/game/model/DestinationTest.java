package com.taxi_trouble.game.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.taxi_trouble.game.Character;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doAnswer;


/**This class tests the functionality of the Destination class.
 *
 * @author Computer Games Project Group 8
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class DestinationTest {

    private Destination destination;

    /**Initialize the destination to be used for testing with
     * width of 1 and height of 2.
     *
     */
    @Before
    public final void setup() {
        destination = new Destination(1, 2);
        World world = new World(new Vector2(0, 0), false);
        destination.initializeBody(world, new Vector2(2, 3));
    }

    /**Checks that the position is correctly retrieved from
     * the body of the destination.
     * 
     */
    @Test
    public final void retrievePositionTest() {
        assertEquals(new Vector2(2, 3), destination.getPosition());
    }

    /**Checks that the x-position is correctly retrieved from the
     * destination body.
     * 
     */
    @Test
    public final void retrieveXPositionTest() {
        assertEquals(2, destination.getXPosition(), 0);
    }

    /**Checks that the x-position is correctly retrieved from the
     * destination body.
     * 
     */
    @Test
    public final void retrieveYPositionTest() {
        assertEquals(3, destination.getYPosition(), 0);
    }
}
