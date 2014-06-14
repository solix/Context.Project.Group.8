package com.taxi_trouble.game.model;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**This class tests the functionality of the Passenger class.
*
* @author Computer Games Project Group 8
*
*/
@RunWith(MockitoJUnitRunner.class)
public class CollisionDetectorTest {

    private CollisionDetector collisionDetector;

    @Mock
    private Taxi taxi;

    @Mock
    private Taxi secondTaxi;

    @Mock
    private Passenger passenger;

    @Mock
    private Destination destination;

    @Mock
    private WorldMap map;

    /**Initializes the collision detector that should be used to test
     * the different types of collisions that may occur.
     *
     */
    @Before
    public final void setup() {
        collisionDetector = new CollisionDetector(map);
    }

    /**Verify that the collision detector is successfully initialized.
    *
    */
    @Test
    public void collisionDetectorSuccessFullyInitializedTest() {
        assertNotNull(collisionDetector);
    }

    /**Check that when a taxi drives into a passenger, the passenger
     * is picked up by the taxi (if this applies, i.e. if the taxi
     * does not have a passenger yet).
     *
     */
    @Test
    public final void collideEmptyTaxiWithPassenger() {
        collisionDetector.collide(taxi, passenger);
        verify(taxi).pickUpPassenger(passenger);
    }

    /**Check that when a taxi drives into a destination, the taxi will
     * drop off its passenger (if this applies, i.e. if the taxi has
     * a passenger and the destination is right)
     *
     */
    @Test
    public final void collideTaxiWithDestination() {
        collisionDetector.collide(taxi, destination);
        verify(taxi).dropOffPassenger(destination, map);
    }

    /**Check that when a taxi drives into another taxi, the taxi will
     * steal the passenger from the other passenger (if this applies).
     *
     */
    @Test
    public final void collideTaxiWithOtherTaxi() {
        collisionDetector.collide(taxi, secondTaxi);
        verify(taxi).stealPassenger(secondTaxi);
    }
}
