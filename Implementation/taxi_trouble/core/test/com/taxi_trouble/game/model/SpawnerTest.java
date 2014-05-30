package com.taxi_trouble.game.model;

import static com.taxi_trouble.game.properties.ResourceManager.charList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.taxi_trouble.game.Character;

@RunWith(MockitoJUnitRunner.class)
public class SpawnerTest {

    private Spawner spawner;
    private Vector2 vector2;
    // private World world;

    @Mock
    private SpawnPoint spawnpoint;

    @Mock
    private Passenger passenger;

    @Mock
    private Character character;

    @Mock
    private Sprite sprite;

    @Mock
    private List<Character> charlist;

    @Mock
    private Destination destination;

    @Before
    public void setup() {
        spawner = new Spawner();
        charList = new ArrayList<Character>();
        vector2 = new Vector2();
        // world = new World(vector2, true);
        when(spawnpoint.getPosition()).thenReturn(vector2);
        // LwjglApplicationConfiguration config = new
        // LwjglApplicationConfiguration();
        // new LwjglApplication(new GameWorld(), config);
        // when(spawner.spawnPassenger(world)).thenReturn(passenger);
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

    // @Test
    // public void spawnPassengerTest() {
    //
    // spawner.addPassengerPoint(spawnpoint);
    // spawner.getDestinationpoints().add(spawnpoint);
    // charList = charlist;
    // when(charList.size()).thenReturn(0);
    // when(getRandomCharacter()).thenReturn(character);
    // when(character.getStanding()).thenReturn(sprite);
    // // when(spawner.randomDestination(world)).thenReturn(destination);
    //
    // Passenger pas = spawner.spawnPassenger(world);
    //
    // }

    @Test
    public void despawnPassengerTest() {
        spawner.getActivePassengers().add(passenger);
        assertEquals(1, spawner.getActivePassengers().size());
        spawner.despawnPassenger(passenger);
        assertEquals(0, spawner.getActivePassengers().size());
    }
}
