import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.taxitrouble.game.Car;

public class CarTest extends Game {
	Car car;
	World world;
	Vector2 vector;

	@Before
	public void setUp() throws Exception {
		vector = new Vector2(0.0f, 0.0f);
		world = new World(new Vector2(0.0f, 0.0f), true);
		try {

			// Initialize the taxi
			this.car = new Car(world, 2, 4, new Vector2(10, 10),
					(float) Math.PI, 60, 20, 60);
		} catch (Exception e) {
			e.getMessage();
		}

	}

	@Test
	public void testCar() {

	}

	@Test
	public void testGetPoweredWheels() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLocalVelocity() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRevolvingWheels() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSpeedKMH() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetSpeed() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub

	}

}
