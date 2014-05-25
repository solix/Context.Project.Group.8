package com.taxi_trouble.game.model;

import com.taxi_trouble.game.model.Destination;
import com.taxi_trouble.game.model.Passenger;
import com.taxi_trouble.game.model.Spawner;
import com.taxi_trouble.game.model.Taxi;
import com.taxi_trouble.game.model.Team;
import com.taxi_trouble.game.model.WorldMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
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

    /**Initialize the taxi that should be tested.
     *
     */
    @Before
    public void initTaxi() {
        taxi = new Taxi(2, 4, 20, 60, 60);
        taxi.setTeam(team);
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
     * the passenger is changed accordingly.
     *
     */
    @Test
    public final void pickUpPassengerTest() {
        taxi.pickUpPassenger(passenger);
        verify(passenger).setTransporter(taxi);
        assertEquals(true, taxi.pickedUpPassenger());
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
        assertEquals(true, taxi.pickedUpPassenger());

        //Drop the passenger off at the right destination of the map
        taxi.dropOffPassenger(destination, map);
        verify(passenger).getDestination();
        verify(spawner).despawnPassenger(passenger);
        verify(team).incScore();
        assertEquals(false, taxi.pickedUpPassenger());
    }

    /**Checks that the passenger can not be dropped off at the
     * wrong destination.
     *
     */
    @Test
    public final void dropOffPassengerWrongDestination() {
        taxi.pickUpPassenger(passenger);

        taxi.dropOffPassenger(mock(Destination.class), map);
        verifyNoMoreInteractions(map);
        verifyNoMoreInteractions(spawner);
    }
}
