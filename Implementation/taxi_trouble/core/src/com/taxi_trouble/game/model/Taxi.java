package com.taxi_trouble.game.model;

import static com.taxi_trouble.game.properties.GameProperties.PIXELS_PER_METER;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.taxi_trouble.game.Acceleration;
import com.taxi_trouble.game.SteerDirection;

/**
 * A controllable taxi which can be steered and for which certain properties
 * hold. A Taxi has a width, length, maximum steering angle, maximum speed,
 * power, box2d body, sprite and a set of wheels.
 *
 * @author Computer Games Project Group 8
 *
 */
public class Taxi {
    private float width;
    private float length;
    private float maxSteerAngle;
    private float maxSpeed;
    private float power;
    private float wheelAngle;
    private Body taxiBody;
    private Sprite taxiSprite;
    private List<Wheel> wheels;
    private SteerDirection steer;
    private Acceleration acceleration;
    private Passenger passenger;
    private Team team;
    private boolean invincibility;

    /**
     * Initializes a new Taxi which can be controlled by a player.
     *
     * @param width
     *            : the width of the taxi
     * @param length
     *            : the length of the taxi
     * @param maxSteerAngle
     *            : the maximum angle under which the taxi can be steered
     * @param maxSpeed
     *            : the maximum speed of the taxi
     * @param power
     *            : the power of the taxi's engine
     */
    public Taxi(float width, float length, float maxSteerAngle, float maxSpeed,
            float power) {
        this.width = width;
        this.length = length;
        this.maxSteerAngle = maxSteerAngle;
        this.maxSpeed = maxSpeed;
        this.power = power;
        this.wheels = new ArrayList<Wheel>();
        this.wheelAngle = 0;
        this.steer = SteerDirection.STEER_NONE;
        this.acceleration = Acceleration.ACC_NONE;
        this.invincibility = false;

    }

    /**
     * Creates a body for this taxi in a world on a given position and placed
     * under a given angle(radian).
     *
     * @param world
     *            : the world used to create the Body
     * @param position
     *            : the position where the taxi should be placed
     * @param angle
     *            : the angle under which the taxi is placed
     */
    public void initializeBody(World world, Vector2 position, float angle) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);
        bodyDef.angle = angle * MathUtils.degreesToRadians;
        this.setBody(world.createBody(bodyDef));
        this.createFixture();
        this.initializeWheels(world);
    }

    /**
     * Creates a fixture for the body of this taxi.
     * 
     */
    private void createFixture() {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.6f;
        fixtureDef.restitution = 0.4f;
        PolygonShape carShape = new PolygonShape();
        carShape.setAsBox(this.getWidth() / 2, this.getLength() / 2);
        fixtureDef.shape = carShape;
        this.getBody().createFixture(fixtureDef);
    }

    /**
     * Retrieves the body of the taxi.
     * 
     * @return taxiBody : body of the taxi
     */
    public Body getBody() {
        return this.taxiBody;
    }

    /**
     * Changes the body of the taxi to a given body.
     * 
     * @param body
     *            : the new body of the taxi
     */
    public void setBody(Body body) {
        this.taxiBody = body;
        taxiBody.setUserData(this);
    }

    /**
     * Initializes the wheels of the taxi in a given world.
     * 
     * @param world
     *            : world used to create the bodies of the taxi wheels
     */
    private void initializeWheels(World world) {
        this.wheels.add(new Wheel(world, this, -1f, -1.4f, 0.4f, 0.6f, true,
                true)); // top left
        this.wheels.add(new Wheel(world, this, 1f, -1.4f, 0.4f, 0.6f, true,
                true)); // top right
        this.wheels.add(new Wheel(world, this, -1f, 1.2f, 0.4f, 0.6f, false,
                false)); // back left
        this.wheels.add(new Wheel(world, this, 1f, 1.2f, 0.4f, 0.6f, false,
                false)); // back right
    }

    /**
     * Retrieves the width of the taxi.
     * 
     * @return width : the width of the taxi
     */
    public float getWidth() {
        return this.width;
    }

    /**
     * Retrieves the length of the taxi.
     * 
     * @return length : the length of the taxi
     */
    public float getLength() {
        return this.length;
    }

    /**
     * Retrieves the maximum angle under which the taxi can be steered.
     * 
     * @return maxSteerAngle : the maximum angle under which the taxi can be
     *         steered
     */
    public float getMaxSteerAngle() {
        return this.maxSteerAngle;
    }

    /**
     * Changes the maximum angle under which the taxi can be steered.
     * 
     * @param maxSteerAngle
     *            : the new maximum steering angle
     */
    public void setMaxSteerAngle(float maxSteerAngle) {
        this.maxSteerAngle = maxSteerAngle;
    }

    /**
     * Retrieves the maximum speed with which the taxi can drive.
     * 
     * @return maxSpeed : maximum speed of the taxi
     */
    public float getMaxSpeed() {
        return this.maxSpeed;
    }

    /**
     * Changes the maximum speed with which the taxi can drive.
     * 
     * @param maxSpeed
     *            : new maximum speed of the taxi
     */
    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    /**
     * Retrieves the power of the taxi's engine.
     * 
     * @return power : power of the taxi
     */
    public float getPower() {
        return this.power;
    }

    /**
     * Changes the power of the taxi's engine.
     * 
     * @param power
     *            : new power of the taxi
     */
    public void setPower(float power) {
        this.power = power;
    }

    /**
     * Retrieves the speed of the taxi in kilometers per hour.
     * 
     * @return
     */
    public float getSpeedKMH() {
        assert (this.getBody() != null);
        Vector2 velocity = this.getBody().getLinearVelocity();
        float len = velocity.len();
        return (len / 1000) * 3600;
    }

    /**
     * Changes the speed to a given speed in kilometers per hour.
     * 
     * @param speed
     *            : new speed of the taxi
     */
    public void setSpeedKMH(float speed) {
        assert (this.getBody() != null);
        Vector2 velocity = this.getBody().getLinearVelocity();
        velocity = velocity.nor();
        velocity = new Vector2(velocity.x * ((speed * 1000.0f) / 3600.0f),
                velocity.y * ((speed * 1000.0f) / 3600.0f));
        this.getBody().setLinearVelocity(velocity);
    }

    /**
     * Retrieves the x-position of the taxi.
     * 
     * @return x-position of the taxi
     */
    public float getXPosition() {
        assert (this.getBody() != null);
        return this.getBody().getPosition().x;
    }

    /**
     * Retrieves the y-position of the taxi.
     * 
     * @return y-position of the taxi
     */
    public float getYPosition() {
        assert (this.getBody() != null);
        return this.getBody().getPosition().y;
    }

    /**
     * Retrieves the position of the taxi.
     * 
     * @return position of the taxi
     */
    public Vector2 getPosition() {
        assert (this.getBody() != null);
        return this.getBody().getPosition();
    }

    /**
     * Retrieves the velocity vector relative to the taxi.
     * 
     * @return
     */
    public Vector2 getLocalVelocity() {
        assert (this.getBody() != null);
        return this.getBody().getLocalVector(
                this.getBody().getLinearVelocityFromLocalPoint(
                        new Vector2(0, 0)));
    }

    /**
     * Changes the sprite of the taxi and the wheels for the given sprites.
     * 
     * @param taxisprite
     *            : the sprite to be set as the taxi's sprite
     * @param wheelsprite
     *            : the sprite to be set as wheel's sprite
     */
    public void setSprite(Sprite taxisprite, Sprite wheelsprite) {
        taxisprite.setSize(this.getWidth(), this.getLength());
        taxisprite.setOrigin(this.getWidth() / 2, this.getLength() / 2);
        this.taxiSprite = taxisprite;
        setWheelSprite(wheelsprite);
    }

    /**
     * Changes the sprite of the taxi's wheels to the given sprite.
     * 
     * @param sprite
     *            : the sprite to be set as the wheels' sprite
     */
    private void setWheelSprite(Sprite sprite) {
        for (Wheel wheel : this.getWheels()) {
            wheel.setSprite(sprite);
        }
    }

    /**
     * Retrieves the wheels of the taxi.
     * 
     * @return wheels of the taxi
     */
    public List<Wheel> getWheels() {
        return this.wheels;
    }

    /**
     * Retrieves the revolving wheels of the taxi.
     * 
     * @return revolving wheels
     */
    public List<Wheel> getRevolvingWheels() {
        List<Wheel> revolvingWheels = new ArrayList<Wheel>();
        for (Wheel wheel : this.getWheels()) {
            if (wheel.getRevolving()) {
                revolvingWheels.add(wheel);
            }
        }
        return revolvingWheels;
    }

    /**
     * Retrieves the powered wheels of the taxi.
     * 
     * @return powered wheels
     */
    public List<Wheel> getPoweredWheels() {
        List<Wheel> poweredWheels = new ArrayList<Wheel>();
        for (Wheel wheel : this.getWheels()) {
            if (wheel.getPowered()) {
                poweredWheels.add(wheel);
            }
        }
        return poweredWheels;
    }

    /**
     * Retrieves the current angle under which the taxi stands on the map.
     * 
     * @return angle
     */
    public float getAngle() {
        assert (this.getBody() != null);
        return this.taxiBody.getAngle();
    }

    /**
     * Retrieves the direction in which the taxi is steered.
     * 
     * @return steer direction of the taxi
     */
    public SteerDirection getSteer() {
        return this.steer;
    }

    /**
     * Sets the direction in which to steer.
     * 
     * @param direction
     *            : direction to steer
     */
    public void setSteer(SteerDirection direction) {
        this.steer = direction;
    }

    /**
     * Retrieves the way in which the taxi is accelerating.
     * 
     * @return acceleration of the taxi
     */
    public Acceleration getAccelerate() {
        return this.acceleration;
    }

    /**
     * Sets the acceleration of the taxi.
     * 
     * @param acceleration
     *            : the way in which the taxi accerelates
     */
    public void setAccelerate(Acceleration acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * Retrieves the team to which this taxi belongs.
     * 
     * @return team
     */
    public Team getTeam() {
        return this.team;
    }

    /**
     * Set the team of the taxi.
     * 
     * @param team
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * Retrieve whether a passenger has been picked up by the taxi.
     * 
     * @return boolean indicating whether the taxi has a passenger
     */
    public boolean pickedUpPassenger() {
        return this.passenger != null;
    }

    /**
     * Retrieve the passenger of the taxi.
     * 
     * @return passenger
     */
    public Passenger getPassenger() {
        return this.passenger;
    }

    /**
     * Picks up a passenger and places it into this taxi.
     *
     * @param passenger
     *            : the passenger to pickup
     */
    public void pickUpPassenger(Passenger passenger) {
        assert (passenger != null);
        // Check if there is no passenger already picked up
        if (!this.pickedUpPassenger() && !passenger.isTransported()) {
            this.passenger = passenger;
            this.passenger.setTransporter(this);
            this.triggerInvincibility();
        }
    }

    /**Triggers invincibility for this taxi for a short period of five seconds.
     *
     */
    private void triggerInvincibility() {
        this.invincibility = true;
        final Taxi taxi = this;
        Timer.schedule(new Task() {
            @Override
            public void run() {
                taxi.turnOffInvincibility();
            }
        }, 5);
    }

    /**Disable invincibility of this taxi, i.e. another
     * taxi can steal a passenger from this taxi.
     *
     */
    private void turnOffInvincibility() {
        this.invincibility = false;
    }

    /**
     * Drop off the passenger, i.e. get it out of the taxi.
     *
     * @param destination
     * @param map
     *
     */
    public void dropOffPassenger(Destination destination, WorldMap map) {
        if (pickedUpPassenger()
                && this.passenger.getDestination().equals(destination)) {
            passenger.deliverAtDestination(map, destination);
            this.losePassenger();
            this.team.incScore();
        }
    }

    /**
     * Updates the taxi's steer angle and acceleration.
     *
     * @param deltaTime
     */
    public void update(float deltaTime) {
        updateSteerAngle(deltaTime);
        updateAcceleration(deltaTime);
    }

    /**
     * Updates the direction in which the taxi's wheels should be pointed.
     *
     * @param deltaTime
     *            : difference in time in which the wheel angle updates
     */
    private void updateSteerAngle(float deltaTime) {
        for (Wheel wheel : wheels) {
            wheel.killSidewaysVelocity();
        }

        float incr = (this.getMaxSteerAngle()) * deltaTime * 5;
        switch (this.getSteer()) {
        case STEER_LEFT:
            this.wheelAngle = Math.min(Math.max(this.wheelAngle, 0) + incr,
                    this.maxSteerAngle);
            break;
        case STEER_RIGHT:
            this.wheelAngle = Math.max(Math.min(this.wheelAngle, 0) - incr,
                    -this.maxSteerAngle);
            break;
        default:
            this.wheelAngle = 0;
            break;
        }
        this.updateRevolvingWheelsAngle();
    }

    /**
     * Updates the angle of the wheels.
     *
     */
    private void updateRevolvingWheelsAngle() {
        for (Wheel wheel : this.getRevolvingWheels()) {
            wheel.setAngle(wheelAngle);
        }
    }

    /**
     * Updates the acceleration of the taxi.
     *
     * @param deltaTime
     */
    private void updateAcceleration(float deltaTime) {
        // Vector pointing in the direction the force will be applied to the
        // wheels
        Vector2 baseVector = new Vector2(0, 0);
        switch (this.getAccelerate()) {
        case ACC_ACCELERATE:
            if (this.getLocalVelocity().y > 0)
                baseVector = new Vector2(0f, -1.3f);
            if (this.getSpeedKMH() < this.getMaxSpeed())
                baseVector = new Vector2(0, -1.5f);
            break;
        case ACC_BRAKE:
            if (this.getLocalVelocity().y < 0)
                baseVector = new Vector2(0f, 1.3f);
            else if (this.getSpeedKMH() < this.getMaxSpeed())
                baseVector = new Vector2(0f, 0.5f);
            break;
        default:
            if (this.getSpeedKMH() < 7) {
                this.setSpeedKMH(0);
            } else if (this.getLocalVelocity().y < 0) {
                baseVector = new Vector2(0, 1.1f);
            } else if (this.getLocalVelocity().y > 0) {
                baseVector = new Vector2(0, -1.1f);
            }
            break;
        }

        Vector2 forceVector = new Vector2(this.power * baseVector.x, this.power
                * baseVector.y);
        updatePoweredWheelsForce(forceVector);
    }

    /**
     * Applies the force specified by a vector to the wheels of the taxi.
     *
     * @param forceVector
     */
    private void updatePoweredWheelsForce(Vector2 forceVector) {
        for (Wheel wheel : this.getPoweredWheels()) {
            Vector2 position = wheel.getBody().getWorldCenter();
            wheel.getBody().applyForce(
                    wheel.getBody().getWorldVector(
                            new Vector2(forceVector.x, forceVector.y)),
                    position, true);
        }
    }

    /**
     * Render the sprites of the taxi using a given SpriteBatch.
     *
     * @param spriteBatch
     */
    public void render(SpriteBatch spriteBatch) {
        for (Wheel wheel : getWheels()) {
            wheel.render(spriteBatch);
        }
        spriteBatch.begin();
        taxiSprite.setPosition(taxiBody.getPosition().x * PIXELS_PER_METER,
                taxiBody.getPosition().y * PIXELS_PER_METER);
        taxiSprite
                .setRotation(taxiBody.getAngle() * MathUtils.radiansToDegrees);
        taxiSprite.setScale(PIXELS_PER_METER);
        taxiSprite.draw(spriteBatch);
        spriteBatch.end();
    }

    /**Let this taxi steal the passenger (if any) from the other specified taxi.
     *
     * @param taxi : the taxi from which the passenger is stolen
     */
    public void stealPassenger(Taxi taxi) {
        if (taxi.pickedUpPassenger() && !this.pickedUpPassenger()
                && !taxi.isInvincible()) {
            Passenger pas = taxi.getPassenger();
            taxi.losePassenger();
            this.pickUpPassenger(pas);
        }

    }

    /**Retrieve whether the taxi is invincible, i.e. whether a passenger can be
     * stolen from this taxi or not.
     *
     * @return invincibility
     */
    private boolean isInvincible() {
        return this.invincibility;
    }

    /**Make the taxi lose its passenger.
     *
     */
    private void losePassenger() {
        this.getPassenger().cancelTransport();
        this.passenger = null;
    }
}
