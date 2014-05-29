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
        if (collider instanceof Taxi) {
            if (collidee instanceof Destination
                    && ((Taxi) collider).pickedUpPassenger()) {
                taxiAtDestination((Taxi) collider, (Destination) collidee);
            } else if (collidee instanceof Taxi) {
                taxiAtTaxi((Taxi) collider, (Taxi) collidee);
                System.out.println("collider has Passenger : "
                        + ((Taxi) collider).pickedUpPassenger() + "no. : "
                        + ((Taxi) collider).getNumber());
                System.out.println("collidee has Passenger : "
                        + ((Taxi) collidee).pickedUpPassenger() + "no. : "
                        + ((Taxi) collidee).getNumber());
            } else if (collidee instanceof Passenger
                    && !((Taxi) collider).pickedUpPassenger()) {
                taxiAtPassenger((Taxi) collider, (Passenger) collidee);
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
        System.out.println("TEST taxiAtPassenger");
    }

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
