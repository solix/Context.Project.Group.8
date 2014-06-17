package com.taxi_trouble.game.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.taxi_trouble.game.Acceleration;
import com.taxi_trouble.game.SteerDirection;
import com.taxi_trouble.game.model.entities.Destination;
import com.taxi_trouble.game.model.entities.Passenger;
import com.taxi_trouble.game.model.entities.Taxi;
import com.taxi_trouble.game.model.team.Team;


/**This class tests the functionality of the Taxi class.
 *
 * @author Computer Games Project Group 8
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class TaxiTest {

    private Taxi taxi;

    private World world;

    @Mock
    private Passenger passenger;

    @Mock
    private Destination destination;

    @Mock
    private Destination secondDestination;

    @Mock
    private WorldMap map;

    @Mock
    private Spawner spawner;

    @Mock
    private Team team;

    @Captor
    private ArgumentCaptor<Float> wheelAngle;

    /**Initialize the taxi that should be tested.
     *
     */
    @Before
    public void initTaxi() {
        taxi = spy(new Taxi(2, 4, 20, 60, 60));
        taxi.setTeam(team);
        world = new World(new Vector2(0, 0), false);
        taxi.initializeBody(world, new Vector2(1, 1), 0);

        when(map.getSpawner()).thenReturn(spawner);
        when(passenger.getDestination()).thenReturn(destination);
        //Avoid the timer from being called
        doNothing().when(taxi).triggerInvincibility(anyInt());
    }

    /**Verify that the taxi is successfully initialized.
     *
     */
    @Test
    public final void taxiSuccesfullyInitializedTest() {
        assertNotNull(taxi);
    }

    /**Check that when a passenger is picked up, the transporter of
     * the passenger is changed accordingly and the taxi has picked up
     * a passenger.
     *
     */
    @Test
    public final void pickUpPassengerTest() {
        assertFalse(taxi.pickedUpPassenger());
        taxi.pickUpPassenger(passenger);
        verify(passenger).isTransported();
        verify(passenger).setTransporter(taxi);
        verify(passenger).setUpDropOffTimer();
        assertTrue(taxi.pickedUpPassenger());
        verifyNoMoreInteractions(passenger);
    }

    /**Checks that the passenger is dropped off when the
     * taxi drops off the passenger at the right destination on the map.
     *
     */
    @Test
    public final void dropOfPassengerTest() {
        //First let the taxi pick up the passenger
        taxi.pickUpPassenger(passenger);
        assertTrue(taxi.pickedUpPassenger());
        //Drop the passenger off at the right destination of the map
        taxi.dropOffPassenger(destination, map);
        verify(passenger).deliverAtDestination(map, destination);
        verify(team).addScore(anyInt());
        assertFalse(taxi.pickedUpPassenger());
    }


    /**Given the game starts and the taxi is initialized for
     * the first time, then check that the taxi is not steered
     * and is not accelerating.
     *
     */
    @Test
    public final void initialSteeringAndAccelerationTest() {
        //Initially the taxi should not steer and not accelerate
        assertEquals(SteerDirection.STEER_NONE, taxi.getSteer());
        assertEquals(Acceleration.ACC_NONE, taxi.getAccelerate());
    }
}
