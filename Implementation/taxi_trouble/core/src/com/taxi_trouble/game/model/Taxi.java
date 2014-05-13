package com.taxi_trouble.game.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.taxi_trouble.game.Acceleration;
import com.taxi_trouble.game.Car;
import com.taxi_trouble.game.SteerDirection;

/**A controllable taxi which can be steered and for which certain properties hold.
 * A Taxi has a width, length, maximum steering angle, maximum speed,
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
    private List<Wheel> wheels;
    private SteerDirection steer;
    private Acceleration acceleration;

    /**Initializes a new Taxi which can be controlled by a player.
     * 
     * @param width : the width of the taxi
     * @param length : the length of the taxi
     * @param maxSteerAngle : the maximum angle under which the taxi can be steered
     * @param maxSpeed : the maximum speed of the taxi
     * @param power : the power of the taxi's engine
     */
    public Taxi(float width, float length, float maxSteerAngle, float maxSpeed, float power) {
        this.width = width;
        this.length = length;
        this.maxSteerAngle = maxSteerAngle;
        this.maxSpeed = maxSpeed;
        this.power = power;
        this.wheels = new ArrayList<Wheel>();
        this.wheelAngle = 0;
        this.steer = SteerDirection.STEER_NONE;
        this.acceleration = Acceleration.ACC_NONE;
    }

    /**Creates a body for this taxi in a world on a given position
     * and placed under a given angle(radian).
     * 
     * @param world : the world used to create the Body
     * @param position : the position where the taxi should be placed
     * @param angle : the angle under which the taxi is placed
     * @return the created body
     */
    public void createBody(World world, Vector2 position, float angle) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);
        bodyDef.angle = angle;
        this.taxiBody = world.createBody(bodyDef);
        this.createFixture(taxiBody);
        this.initializeWheels(world);
    }

    /**Creates a fixture for the body of this taxi.
     * 
     * @param body : the body for which the fixture should be created
     */
    private void createFixture(Body body) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.6f;
        fixtureDef.restitution = 0.4f;
        PolygonShape carShape = new PolygonShape();
        carShape.setAsBox(this.getWidth()/2, this.getLength()/2);
        fixtureDef.shape = carShape;
        body.createFixture(fixtureDef);
    }

    /**Retrieves the body of the taxi.
     * 
     * @return taxiBody : body of the taxi
     */
    public Body getBody() {
        return this.taxiBody;
    }

    /**Changes the body of the taxi to a given body.
     * 
     * @param body : the new body of the taxi
     */
    public void setBody(Body body) {
        this.taxiBody = body;
    }

    /**Initializes the wheels of the taxi in a given world.
     * 
     * @param world : world used to create the bodies of the taxi wheels
     */
    private void initializeWheels(World world) {
        this.wheels.add(new Wheel(world, this, -0.875f, -1.45f, 0.2f, 0.6f, true, true)); // top right
        this.wheels.add(new Wheel(world, this, 0.9f, -1.4f, 0.2f, 0.6f, true, true)); // top left
        this.wheels.add(new Wheel(world, this, -0.875f, 1.1f, 0.2f, 0.6f, false, false)); // back right
        this.wheels.add(new Wheel(world, this, 0.9f, 1.2f, 0.2f, 0.6f, false, false)); // back left
    }

    /**Retrieves the width of the taxi.
     * 
     * @return width : the width of the taxi
     */
    public float getWidth() {
        return this.width;
    }

    /**Retrieves the length of the taxi.
     * 
     * @return length : the length of the taxi
     */
    public float getLength() {
        return this.length;
    }

    /**Retrieves the maximum angle under which the taxi can be steered.
     * 
     * @return maxSteerAngle : the maximum angle under which the taxi can be steered
     */
    public float getMaxSteerAngle() {
        return this.maxSteerAngle;
    }
    
    /**Changes the maximum angle under which the taxi can be steered.
     * 
     * @param maxSteerAngle : the new maximum steering angle
     */
    public void setMaxSteerAngle(float maxSteerAngle) {
        this.maxSteerAngle = maxSteerAngle;
    }

    /**Retrieves the maximum speed with which the taxi can drive.
     * 
     * @return maxSpeed : maximum speed of the taxi
     */
    public float getMaxSpeed() {
        return this.maxSpeed;
    }
    
    /**Changes the maximum speed with which the taxi can drive.
     * 
     * @param maxSpeed : new maximum speed of the taxi
     */
    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    /**Retrieves the power of the taxi's engine.
     * 
     * @return power : power of the taxi
     */
    public float getPower() {
        return this.power;
    }
    
    /**Changes the power of the taxi's engine.
     * 
     * @param power : new power of the taxi
     */
    public void setPower(float power) {
        this.power = power;
    }

    /**Retrieves the speed of the taxi in kilometers per hour.
     * 
     * @return
     */
    public float getSpeedKMH() {
        Vector2 velocity = this.getBody().getLinearVelocity();
        float len = velocity.len();
        return (len / 1000) * 3600;
    }
    
    /**Changes the speed to a given speed in kilometers per hour.
     * 
     * @param speed : new speed of the taxi
     */
    public void setSpeedKMH(float speed) {
        Vector2 velocity = this.getBody().getLinearVelocity();
        velocity = velocity.nor();
        velocity = new Vector2(velocity.x * ((speed * 1000.0f) / 3600.0f),
                velocity.y * ((speed * 1000.0f) / 3600.0f));
        this.getBody().setLinearVelocity(velocity);
    }
    
    /**Retrieves the x-position of the taxi.
     * 
     * @return x-position of the taxi
     */
    public float getXPosition() {
        return this.getBody().getPosition().x;
    }

    /**Retrieves the y-position of the taxi.
     * 
     * @return y-position of the taxi
     */
    public float getYPosition() {
        return this.getBody().getPosition().y;
    }

    /**Retrieves the velocity vector relative to the taxi
     * 
     * @return
     */
    public Vector2 getLocalVelocity() {
        return this.getBody().getLocalVector(this.getBody().getLinearVelocityFromLocalPoint(new Vector2(0, 0)));
    }

    /**Changes the sprite of the taxi to the given sprite.
     * 
     * @param sprite : the sprite to be set as the taxi's sprite
     */
    public void setSprite(Sprite sprite) {
        sprite.setSize(this.getWidth(), this.getLength());
        sprite.setOrigin(this.getWidth()/2, this.getLength()/2);
        this.getBody().setUserData(sprite);
    }
    
    /**Retrieves the wheels of the taxi.
     * 
     * @return wheels of the taxi
     */
    public List<Wheel> getWheels() {
        return this.wheels;
    }

    /**Retrieves the revolving wheels of the taxi.
     * 
     * @return revolving wheels
     */
    public List<Wheel> getRevolvingWheels() {
        List<Wheel> revolvingWheels = new ArrayList<Wheel>();
        for (Wheel wheel : this.wheels) {
            if (wheel.revolving)
                revolvingWheels.add(wheel);
        }
        return revolvingWheels;
    }
    
    /** Retrieves the powered wheels of the taxi
     * 
     * @return powered wheels
     */
    public List<Wheel> getPoweredWheels() {
        List<Wheel> poweredWheels = new ArrayList<Wheel>();
        for (Wheel wheel : this.wheels) {
            if (wheel.powered)
                poweredWheels.add(wheel);
        }
        return poweredWheels;
    }

    /**Retrieves the direction in which the taxi is steered.
     * 
     * @return steer direction of the taxi
     */
    public SteerDirection getSteer() {
        return this.steer;
    }

    /**Sets the direction in which to steer.
     * 
     * @param direction : direction to steer
     */
    public void setSteer(SteerDirection direction) {
        this.steer = direction;
    }

    /**Retrieves the way in which the taxi is accelerating.
     * 
     * @return acceleration of the taxi
     */
    public Acceleration getAccelerate() {
        return this.acceleration;
    }

    /**Sets the acceleration of the taxi.
     * 
     * @param acceleration : the way in which the taxi accerelates
     */
    public void setAccelerate(Acceleration acceleration) {
        this.acceleration = acceleration;
    }

    /**Updates the taxi's steer angle and acceleration.
     * 
     * @param deltaTime
     */
    public void update(float deltaTime) {
        updateSteerAngle(deltaTime);
        updateAcceleration(deltaTime);
    }

    /**Updates the direction in which the taxi's wheels should be pointed.
     * 
     * @param deltaTime : difference in time in which the wheel angle updates
     */
    private void updateSteerAngle(float deltaTime) {
        for (Wheel wheel : wheels) {
            wheel.killSidewaysVelocity();
        }
        
        float incr = (this.getMaxSteerAngle()) * deltaTime * 5;
        switch(this.getSteer()) {
           case STEER_LEFT:
               this.wheelAngle = Math.min(Math.max(this.wheelAngle, 0) + incr, this.maxSteerAngle);
               break;
           case STEER_RIGHT:
               this.wheelAngle = Math.max(Math.min(this.wheelAngle, 0) - incr, -this.maxSteerAngle);
               break;
           default:
               this.wheelAngle = 0;
               break;
        }
        this.updateRevolvingWheelsAngle();
    }

    /**Updates the angle of the wheels.
     * 
     */
    private void updateRevolvingWheelsAngle() {
        for(Wheel wheel : this.getRevolvingWheels()) {
            wheel.setAngle(wheelAngle);
        }
    }
    
    /**Updates the acceleration of the taxi.
     * 
     * @param deltaTime
     */
    private void updateAcceleration(float deltaTime) {
        //Vector pointing in the direction the force will be applied to the wheels
        Vector2 baseVector = new Vector2(0, 0);
        switch(this.getAccelerate()) {
        case ACC_ACCELERATE:
            if(this.getSpeedKMH() < this.getMaxSpeed())
                baseVector = new Vector2(0, -1);
            break;
        case ACC_BRAKE:
            if (this.getLocalVelocity().y < 0)
                baseVector = new Vector2(0f, 1.3f);
            else
                baseVector = new Vector2(0f, 0.7f);
            break;
        default:
            if (this.getSpeedKMH() < 7) {
                this.setSpeedKMH(0);
            }
            else if (this.getLocalVelocity().y < 0) {
                baseVector = new Vector2(0, 0.7f);
            }
            else if (this.getLocalVelocity().y > 0) {
                baseVector = new Vector2(0, -0.7f);
            }
            break;
        }
        
        Vector2 forceVector = new Vector2(this.power * baseVector.x, this.power * baseVector.y);
        updatePoweredWheelsForce(forceVector);
    }
    
    /**Applies the force specified by a vector to the wheels of the taxi.
     * 
     * @param forceVector
     */
    private void updatePoweredWheelsForce(Vector2 forceVector) {
        for (Wheel wheel : this.getPoweredWheels()) {
            Vector2 position = wheel.body.getWorldCenter();
            wheel.body.applyForce(wheel.body.getWorldVector(new Vector2(
                    forceVector.x, forceVector.y)), position, true);
        }
    }
}
