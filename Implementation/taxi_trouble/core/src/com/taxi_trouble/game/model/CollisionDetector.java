package com.taxi_trouble.game.model;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Detects collisions on a map and performs the right actions accordingly.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class CollisionDetector implements ContactListener {

    private WorldMap map;

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
        collide(collider, collidee);
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
    public void collide(Object collider, Object collidee) {
        if (collider instanceof Taxi) {
            if (collidee instanceof Destination) {
                taxiAtDestination((Taxi) collider, (Destination) collidee);
            }
            if (collidee instanceof Taxi) {
                taxiAtTaxi((Taxi) collider, (Taxi) collidee);
            }
            if (collidee instanceof Passenger) {
                taxiAtPassenger((Taxi) collider, (Passenger) collidee);
            }
            if (collidee instanceof PowerUp) {
                taxiAtPowerUp((Taxi) collider, (PowerUp) collidee);
            }
        }
    }

    /**
     * Defines the behaviour of a taxi colliding with a powerUp
     * 
     * @param collider
     * @param collidee
     */
    private void taxiAtPowerUp(Taxi taxi, PowerUp powerup) {
        taxi.handlePowerUp(powerup, map);
    }

    /**
     * Defines the behaviour of a taxi colliding with a destination.
     * 
     * @param taxi
     * @param destination
     */
    private void taxiAtDestination(Taxi taxi, Destination destination) {
        taxi.dropOffPassenger(destination, map);
    }

    /**
     * Defines the behaviour of a taxi colliding with a passenger.
     * 
     * @param taxi
     * @param passenger
     */
    private void taxiAtPassenger(Taxi taxi, Passenger passenger) {
        taxi.pickUpPassenger(passenger);
    }

    /**
     * Defines the behaviour of a taxi colliding with another taxi.
     * 
     * @param taxi1
     * @param taxi2
     */
    private void taxiAtTaxi(Taxi taxi1, Taxi taxi2) {
        taxi1.stealPassenger(taxi2);
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
