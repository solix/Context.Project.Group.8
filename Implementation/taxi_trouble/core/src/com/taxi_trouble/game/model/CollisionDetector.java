package com.taxi_trouble.game.model;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.taxi_trouble.game.model.entities.Destination;
import com.taxi_trouble.game.model.entities.Passenger;
import com.taxi_trouble.game.model.entities.Taxi;
import com.taxi_trouble.game.model.entities.powerups.PowerUp;
import com.taxi_trouble.game.multiplayer.AndroidMultiplayerInterface;
import com.taxi_trouble.game.sound.TaxiJukebox;

/**
 * Detects collisions on a map and performs the right actions accordingly.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class CollisionDetector implements ContactListener {

	private WorldMap map;
	private AndroidMultiplayerInterface networkInterface;

	/**
	 * Initializes a new CollisionDetector defining the behaviour for collisions
	 * in a game.
	 * 
	 * @param map
	 *            : the map to which the collision detector applies
	 */
	public CollisionDetector(WorldMap map) {
		super();
		this.map = map;

	}

	/**
	 * Defines the actions that are taken when contact between two instances
	 * begins.
	 * 
	 */
	@Override
	public final void beginContact(final Contact contact) {
		Object collider = contact.getFixtureA().getBody().getUserData();
		Object collidee = contact.getFixtureB().getBody().getUserData();
		
		if (collider instanceof Taxi) {
			taxiCollide(collider, collidee);
		} else if (collidee instanceof Taxi) {
			taxiCollide(collidee, collider);
		}
	}

	/**
	 * Defines the behaviour of two instances that move into each other as
	 * collider and collidee.
	 * 
	 * @param collider
	 *            : the collider which moved into the other instance
	 * @param collidee
	 *            : the collidee into which another instance has moved
	 */
	public void taxiCollide(Object collider, Object collidee) {
		System.out.println("collision:  " + collider.getClass().getName()
				+ "collided with" + collidee.getClass().getName());

		if (networkInterface.isHost())
			System.out.println("I am host!");
		else
			System.out.println("I am not host");
		if (collidee instanceof Destination) {
			taxiAtDestination((Taxi) collider, (Destination) collidee);
		}
		if (collidee instanceof Taxi) {
			taxiAtTaxi((Taxi) collider, (Taxi) collidee);
			TaxiJukebox.playSound("carcrashfx");
		}
		if (collidee instanceof Passenger) {
			taxiAtPassenger((Taxi) collider, (Passenger) collidee);
		}
		if (collidee instanceof PowerUp) {
			taxiAtPowerUp((Taxi) collider, (PowerUp) collidee);
			TaxiJukebox.playSound("powerup");
		}
	}

	/**
	 * Defines the behaviour of a taxi colliding with a powerUp
	 * 
	 * @param taxi
	 * @param powerUp
	 */
	private void taxiAtPowerUp(Taxi taxi, PowerUp powerUp) {
		if (networkInterface.isHost()) {
			if (map.getSpawner().powerUpIsAvailable(powerUp)) {
				taxi.pickUpPowerUp(powerUp, map);
				networkInterface.powerUpMessage(taxi, powerUp);
			}
		}
	}

	/**
	 * Defines the behaviour of a taxi colliding with a destination.
	 * 
	 * @param taxi
	 * @param destination
	 */
	private void taxiAtDestination(Taxi taxi, Destination destination) {
		if (networkInterface.isHost()) {
			if (taxi.dropOffDetected(destination, map)) {
				String message = "DROP " + taxi.getTeam().getTeamId() + " "
						+ taxi.getPassenger().getId();
				networkInterface.reliableBroadcast(message);
				taxi.dropOffPassenger(destination, map);
			}

		}
	}

	/**
	 * Defines the behaviour of a taxi colliding with a passenger.
	 * 
	 * @param taxi
	 * @param passenger
	 */
	private void taxiAtPassenger(Taxi taxi, Passenger passenger) {
		if (networkInterface.isHost()) {
			if (taxi.pickUpPassenger(passenger)) {
				networkInterface.passengerMessage(taxi, passenger);
			}

		}
	}

	/**
	 * Defines the behaviour of a taxi colliding with another taxi.
	 * 
	 * @param taxi1
	 * @param taxi2
	 */
	private void taxiAtTaxi(Taxi taxi1, Taxi taxi2) {
		if (networkInterface.isHost()) {
			if (taxi1.stealPassenger(taxi2)) {
				networkInterface.passengerMessage(taxi1, taxi1.getPassenger());
			} else if (taxi2.stealPassenger(taxi1)) {
				networkInterface.passengerMessage(taxi2, taxi2.getPassenger());
			}

		}
	}

	public void setMultiPlayerInterface(AndroidMultiplayerInterface i) {
		this.networkInterface = i;
	}

	@Override
	public void endContact(final Contact contact) {
	}

	@Override
	public void preSolve(final Contact contact, final Manifold oldManifold) {
	}

	@Override
	public void postSolve(final Contact contact, final ContactImpulse impulse) {
	}

}
