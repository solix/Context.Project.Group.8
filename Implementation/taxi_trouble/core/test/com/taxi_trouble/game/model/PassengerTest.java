package com.taxi_trouble.game.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.taxi_trouble.game.model.Passenger;
import com.taxi_trouble.game.Character;

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


/**This class tests the functionality of the Passenger class.
 *
 * @author Computer Games Project Group 8
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PassengerTest {

    private Passenger passenger;

    @Mock
    private Character character;

    @Mock
    private Sprite sprite;

    /**Initialize the passenger that should be tested.
    *
    */
   @Before
   public void initPassenger() {
       when(character.getStanding()).thenReturn(sprite);
       passenger = new Passenger(2, 2, character);
   }

   @Test
   public final void templateTest() {
       assertEquals(0, 0);
   }
}
