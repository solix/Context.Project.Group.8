package com.taxi_trouble.game.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.taxi_trouble.game.model.Passenger;
import com.taxi_trouble.game.Character;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;


/**This class tests the functionality of the Passenger class.
 *
 * @author Computer Games Project Group 8
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PassengerTest {

    private Passenger passenger;

    private World world;

    private SpawnPoint spawnPoint;

    @Mock
    private Destination destination;

    @Mock
    private Taxi transporter;

    @Mock
    private WorldMap map;

    @Mock
    private Spawner spawner;

    @Mock
    private Character character;

    //Should this mock be necessary? Don't think so. -> Adapt the implementation
    @Mock
    private Sprite sprite;

    /**Initialize the passenger that should be tested.
    *
    */
   @Before
   public void initPassenger() {
       when(character.getStanding()).thenReturn(sprite);

       passenger = new Passenger(2, 2, character);
       world = new World(new Vector2(0, 0), false);
       spawnPoint = new SpawnPoint(0, 0, 0);
       passenger.initializeBody(world, spawnPoint);
       passenger.setDestination(destination);

       when(map.getSpawner()).thenReturn(spawner);
       when(map.getWorld()).thenReturn(world);
   }

   /**Tests that the position of the body of the passenger in the world
    * is correctly updated when the position is adapted.
    *
    */
   @Test
   public final void changePositionTest() {
       assertEquals(new Vector2(0, 0), passenger.getPosition());
       passenger.setPosition(new Vector2(1, 1));
       assertEquals(new Vector2(1, 1), passenger.getPosition());
   }

   /**Tests that the angle of the body of the passenger in the world
    * is correctly updated when the angle is adapted.
    *
    */
   @Test
   public final void changeAngleTest() {
       assertEquals(0f, passenger.getAngle(), 0);
       passenger.setAngle(90 * MathUtils.degreesToRadians);
       assertEquals(90 * MathUtils.degreesToRadians, passenger.getAngle(), 0);
   }

   /**Given the passenger initially has no passenger and the transporter
    * of the passenger is set, the passenger should be transported.
    *
    */
   @Test
   public final void transportPassengerTest() {
       //Initially the passenger should be being transported.
       assertFalse(passenger.isTransported());
       passenger.setTransporter(transporter);
       //After the taxi has been set the passenger should be being transported.
       assertTrue(passenger.isTransported());
   }

   /**Given the passenger is being transported and the transport is cancelled
    * than the transport should be cancelled, i.e. there is no current
    * transporter.
    *
    */
   @Test
   public final void cancelPassengerTransportTest() {
       //First the passenger should have a transporter.
       passenger.setTransporter(transporter);
       assertTrue(passenger.isTransported());
       //When the transport is cancelled the passenger is no longer transported.
       passenger.cancelTransport();
       assertFalse(passenger.isTransported());
   }

   /**Given the passenger is transported by a passenger, when the passenger
    * is delivered at the right destination it will be delivered.
    *
    */
   @Test
   public final void deliverAtRightDestinationTest() {
       //Reset the spawn point when the passenger is despawned.
       Mockito.doAnswer(new Answer<Void>() {
        @Override
        public Void answer(InvocationOnMock invocation) throws Throwable {
            passenger.resetSpawnPoint();
            return null;
        }
       }).when(spawner).despawnPassenger(passenger);

       //First if the passenger has a transporter than it is not yet delivered
       passenger.setTransporter(transporter);
       passenger.deliverAtDestination(map, destination);
       verify(spawner).despawnPassenger(passenger);
       //After it is delivered at the right destination it should be delivered
   }

   /**Given the passenger is transported, when an attempt is made to deliver
    * the passenger at the wrong destination, it should not be delivered.
    *
    */
   @Test
   public final void deliverAtWrongDestination() {
       //First if the passenger has a transporter than it is not yet delivered
       passenger.setTransporter(transporter);
       //assertFalse(passenger.isDelivered());
       passenger.deliverAtDestination(map, mock(Destination.class));
       //Delivering the passenger at the wrong destination should not deliver it
       //assertFalse(passenger.isDelivered());
   }
}
