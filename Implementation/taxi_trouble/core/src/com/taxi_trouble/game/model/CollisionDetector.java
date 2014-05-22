package com.taxi_trouble.game.model;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**Detects collisions on a map and performs the right actions accordingly.
 *
 * @author Computer Games Project Group 8
 *
 */
public class CollisionDetector implements ContactListener {

    private WorldMap map;

    public CollisionDetector(WorldMap map) {
        super();
        this.map = map;
    }

    @Override
    public final void beginContact(final Contact contact) {
        Object collider = contact.getFixtureA().getBody().getUserData();
        Object collidee = contact.getFixtureB().getBody().getUserData();
        if (collider instanceof Taxi) {
            if (collidee instanceof Passenger) {
                ((Taxi) collider).pickUpPassenger((Passenger) collidee);
            }
        }
        if (collider instanceof Taxi) {
            if (collidee instanceof Destination) {
                ((Taxi) collider).dropOffPassenger((Destination) collidee, map);
            }
        }
    }

    @Override
    public void endContact(final Contact contact) {
        // TODO Auto-generated method stub
    }

    @Override
    public void preSolve(final Contact contact, final Manifold oldManifold) {
        // TODO Auto-generated method stub
    }

    @Override
    public void postSolve(final Contact contact, final ContactImpulse impulse) {
        // TODO Auto-generated method stub
    }

}
