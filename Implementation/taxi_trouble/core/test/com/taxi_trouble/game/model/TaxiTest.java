package com.taxi_trouble.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.taxi_trouble.game.Acceleration;
import com.taxi_trouble.game.SteerDirection;
import com.taxi_trouble.game.model.Destination;
import com.taxi_trouble.game.model.Passenger;
import com.taxi_trouble.game.model.Spawner;
import com.taxi_trouble.game.model.Taxi;
import com.taxi_trouble.game.model.Team;
import com.taxi_trouble.game.model.WorldMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;


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
        taxi = new Taxi(2, 4, 20, 60, 60);
        taxi.setTeam(team);
        world = new World(new Vector2(0, 0), false);
        taxi.initializeBody(world, new Vector2(1, 1), 0);

        when(map.getSpawner()).thenReturn(spawner);
        when(passenger.getDestination()).thenReturn(destination);
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
        verify(passenger).setTransporter(taxi);
        assertTrue(taxi.pickedUpPassenger());
        verifyNoMoreInteractions(passenger);
    }

    /**Checks that the passenger is dropped off when the
     * taxi drops off the passenger at the right destination on the map.
     *
     */
    @Test
    public final void dropOffPassengerRightDestinationTest() {
        //First let the taxi pick up the passenger
        taxi.pickUpPassenger(passenger);
        assertTrue(taxi.pickedUpPassenger());

        //Drop the passenger off at the right destination of the map
        taxi.dropOffPassenger(destination, map);
        verify(passenger).getDestination();
        verify(passenger).deliverAtDestination(map, destination);
        verify(team).incScore();
        assertFalse(taxi.pickedUpPassenger());
    }

    /**Given the taxi has a passenger, check that the passenger
     * can not be dropped off at the wrong destination.
     *
     */
    @Test
    public final void dropOffPassengerWrongDestination() {
        //First the taxi should have picked up a passenger
        taxi.pickUpPassenger(passenger);
        assertTrue(taxi.pickedUpPassenger());

        taxi.dropOffPassenger(mock(Destination.class), map);
        verifyNoMoreInteractions(map);
        verifyNoMoreInteractions(spawner);
        assertTrue(taxi.pickedUpPassenger());
    }

/*    @Test
    public final void changeTaxiSpeedTest() {
        //Initially the speed should be zero
        assertEquals(0f, taxi.getSpeedKMH(), 0);
        taxi.setSpeedKMH(42f);
        assertEquals(42f, taxi.getSpeedKMH(), 0);
    }*/

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

    @Test
    public final void steerRightTest() {
        taxi.setSteer(SteerDirection.STEER_RIGHT);
        assertEquals(SteerDirection.STEER_RIGHT, taxi.getSteer());
        taxi.update(20);
        for(Wheel wheel : taxi.getRevolvingWheels()) {
            //verify(wheel).setAngle(wheelAngle.capture());
        }
        //assertEquals(20f, wheelAngle.getValue(), 0f);
    }
}
