package com.taxi_trouble.game.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.taxi_trouble.game.model.entities.Destination;
import com.taxi_trouble.game.model.entities.Passenger;
import com.taxi_trouble.game.model.entities.powerups.PowerUpBehaviour;

/**This class tests the functionality of the Spawner class.
*
* @author Computer Games Project Group 8
*
*/
@RunWith(MockitoJUnitRunner.class)
public class SpawnerTest {

    private Spawner spawner;
    private Vector2 vector2;

    @Mock
    private SpawnPoint spawnpoint;

    @Mock
    private Passenger passenger;

    @Mock
    private List<Sprite> charlist;

    @Mock
    private Destination destination;
    
    @Mock
    private List<PowerUpBehaviour> powerUpBehaviours;

    @Before
    public void setup() {
        spawner = spy(new Spawner());
        vector2 = new Vector2();
        when(spawnpoint.getPosition()).thenReturn(vector2);
    }

    @Test
    public void checkSetup() {
        assertNotNull(spawner);
    }

    @Test
    public void checkAddPassengerPoint() {
        spawner.addPassengerPoint(spawnpoint);
        assertEquals(1, spawner.getPassengerspawnpoints().size());
        spawner.addPassengerPoint(spawnpoint);
        assertEquals(2, spawner.getPassengerspawnpoints().size());
    }

    @Test
    public void checkAddDestination() {
        spawner.addDestination(spawnpoint);
        assertEquals(1, spawner.getDestinationpoints().size());
        spawner.addDestination(spawnpoint);
        assertEquals(2, spawner.getDestinationpoints().size());
    }

    @Test
    public void checkAddTaxiPoint() {
        spawner.addTaxiPoint(spawnpoint);
        assertEquals(1, spawner.getTaxispawnpoints().size());
        spawner.addTaxiPoint(spawnpoint);
        assertEquals(2, spawner.getTaxispawnpoints().size());
    }

    @Test
    public void despawnPassengerTest() {
        spawner.getActivePassengers().put(0, passenger);
        assertEquals(1, spawner.getActivePassengers().size());
        spawner.despawnPassenger(passenger);
        assertEquals(0, spawner.getActivePassengers().size());
    }
}
