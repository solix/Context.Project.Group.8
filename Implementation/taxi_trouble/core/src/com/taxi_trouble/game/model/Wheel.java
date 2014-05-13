package com.taxi_trouble.game.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.taxi_trouble.game.model.Taxi;

public class Wheel {	

	public static final float PIXELS_PER_METER = 60.0f;
	
	private Taxi taxi;
	private float width; // width in meters
	private float length; // length in meters
	private boolean revolving;
	private boolean powered;
	private Body wheelBody;

	public Wheel(World world, Taxi taxi, float posX, float posY, float width, float length,
			boolean revolving, boolean powered) {
		this.taxi = taxi;
		this.width = width;
		this.length = length;
		this.revolving = revolving;
		this.powered = powered;
		this.createBody(world, new Vector2(posX, posY));
	}
	
	public void createBody(World world, Vector2 position) { 
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(taxi.getBody().getWorldPoint(position));
		bodyDef.angle = taxi.getBody().getAngle();
		this.wheelBody = world.createBody(bodyDef);
		this.createFixture();
		this.createJoint(world);
	}
	
	private void createFixture() {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1.0f;
		fixtureDef.isSensor=true; //wheel does not participate in collision calculations: resulting complications are unnecessary
		PolygonShape wheelShape = new PolygonShape();
		wheelShape.setAsBox(this.width/2, this.length/2);
		fixtureDef.shape = wheelShape;
		this.getBody().createFixture(fixtureDef);
		wheelShape.dispose();
	}
	
	/**Creates joint to connect the wheel to the body.
	 * 
	 */
	private void createJoint(World world) {
		
	    //create joint to connect wheel to body
	    if (this.revolving){
	    	RevoluteJointDef jointdef=new RevoluteJointDef();
	        jointdef.initialize(this.taxi.getBody(), this.getBody(), this.getBody().getWorldCenter());
	        jointdef.enableMotor=false; //we'll be controlling the wheel's angle manually
		    world.createJoint(jointdef);
	    } else{
	    	PrismaticJointDef jointdef=new PrismaticJointDef();
	        jointdef.initialize(this.taxi.getBody(), this.getBody(), this.getBody().getWorldCenter(), new Vector2(1, 0));
	        jointdef.enableLimit=true;
	        jointdef.lowerTranslation=jointdef.upperTranslation=0;
		    world.createJoint(jointdef);
	    }
	}
	
	/**Retrieve whether the wheel revolves when steering.
	 * 
	 * @return revolving
	 */
	public boolean getRevolving() {
	    return this.revolving;
	}
	
	/**Retrieve whether the wheel is powered.
	 * 
	 * @return powered
	 */
	public boolean getPowered() {
	    return this.powered;
	}
	
	/**Sets the sprite of the wheel to a given sprite.
	 * 
	 * @param sprite : sprite of the wheel
	 */
	public void setSprite(Sprite sprite) {
        sprite.setSize(this.width, this.length);
        sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
        this.getBody().setUserData(sprite);
	}
	
	/**Retrieves the box2d body of this wheel.
	 * 
	 * @return
	 */
	public Body getBody() {
	    return this.wheelBody;
	}
	
    /**Changes the body of the wheel to a given body.
     * 
     * @param body : the new body of the wheel
     */
    public void setBody(Body body) {
        this.wheelBody = body;
    }
	
	/**Changes the angle (degrees) relative to the taxi the wheel belongs to.
	 * 
	 * @param angle : angle of the wheel relative to the taxi
	 */
	public void setAngle(float angle) {
		this.getBody().setTransform(getBody().getPosition(), this.taxi.getBody().getAngle() + (float) Math.toRadians(angle));
	};

	/**Retrieves the velocity vector relative to the taxi it belongs to
	 * 
	 * @return
	 */
	public Vector2 getLocalVelocity() {
	    return taxi.getBody().getLocalVector(taxi.getBody().getLinearVelocityFromLocalPoint(getBody().getPosition()));
	};

    /**Retrieves a world unit vector which is pointing in the direction the
     * wheel is moving.
     * 
     * @return direction vector
     */
    public Vector2 getDirectionVector() {
    	Vector2 directionVector;
    	if (this.getLocalVelocity().y > 0)
    		directionVector = new Vector2(0,1);
    	else
			directionVector = new Vector2(0,-1);
			
		return directionVector.rotate((float) Math.toDegrees(getBody().getAngle()));	    
	};

    /**Subtracts sideways velocity from the wheel's velocity vector
     * 
     * @return remaining front-facing velocity vector
     */
	public Vector2 getKillVelocityVector() {
	    Vector2 velocity = this.getBody().getLinearVelocity();
	    Vector2 sidewaysAxis=this.getDirectionVector();
	    float dotprod = velocity.dot(sidewaysAxis);
	    return new Vector2(sidewaysAxis.x*dotprod, sidewaysAxis.y*dotprod);
	};

	/**Removes all sideways velocity from the wheels velocity.
	 * 
	 */
	public void killSidewaysVelocity() {
	    this.getBody().setLinearVelocity(this.getKillVelocityVector());
	};
}
