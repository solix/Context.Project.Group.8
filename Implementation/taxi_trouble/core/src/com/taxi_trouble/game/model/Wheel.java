package com.taxi_trouble.game.model;

import static com.taxi_trouble.game.properties.GameProperties.PIXELS_PER_METER;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

/**
 * A wheel as part of a Taxi which is controlled by a player.
 *
 * @author Computer Games Project Group 8
 *
 */
public class Wheel {
	private Taxi taxi;
	private float width;
	private float length;
	private boolean revolving;
	private boolean powered;
	private Body wheelBody;

	public Wheel(World world, Taxi taxi, float posX, float posY, float width,
			float length, boolean revolving, boolean powered) {
		this.taxi = taxi;
		this.width = width;
		this.length = length;
		this.revolving = revolving;
		this.powered = powered;
		this.createBody(world, new Vector2(posX, posY));
	}

	/**
	 * Creates the body of this wheel.
	 *
	 * @param world
	 *            : the world for which the body should be created
	 * @param position
	 *            : the (relative) position to the taxi
	 */
	public void createBody(World world, Vector2 position) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(taxi.getBody().getWorldPoint(position));
		bodyDef.angle = taxi.getBody().getAngle();
		this.wheelBody = world.createBody(bodyDef);
		this.createFixture();
		this.createJoint(world);
	}

	/**
	 * Creates the fixture of this wheel.
	 *
	 */
	private void createFixture() {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1.0f;
		// Indicate that the wheel does not participate in collision
		// calculations
		fixtureDef.isSensor = true;
		PolygonShape wheelShape = new PolygonShape();
		wheelShape.setAsBox(this.width / 2, this.length / 2);
		fixtureDef.shape = wheelShape;
		this.getBody().createFixture(fixtureDef);
		wheelShape.dispose();
	}

	/**
	 * Creates joint to connect the wheel to the taxi body.
	 * 
	 * @param world : the world into which the joint should
	 *                be initialized
	 *
	 */
	private void createJoint(World world) {
	    JointDef jointdef = null;
		if (this.getRevolving()) {
			jointdef = createRevolvingWheelJoint();
		} else {
		    jointdef = createNonRevolvingWheelJoint();
		}
		world.createJoint(jointdef);
	}

	/**Creates joint for a non-revolving wheel.
	 * 
	 * @return jointdef for a non-revolving wheel
	 */
	private JointDef createNonRevolvingWheelJoint() {
	    PrismaticJointDef jointdef = new PrismaticJointDef();         
        jointdef.initialize(this.taxi.getBody(), this.getBody(),
                this.getBody().getWorldCenter(), new Vector2(1, 0));
        jointdef.enableLimit = true;
        jointdef.lowerTranslation = jointdef.upperTranslation = 0;
        return jointdef;
    }

	/**Creates a joint for a revolving wheel.
	 * 
	 * @return jointdef for a revolving wheel
	 */
    private JointDef createRevolvingWheelJoint() {
	    RevoluteJointDef jointdef = new RevoluteJointDef();
        jointdef.initialize(this.taxi.getBody(), this.getBody(), 
                this.getBody().getWorldCenter());
        jointdef.enableMotor = false;
        return jointdef;
	}

	/**
	 * Retrieve whether the wheel revolves when steering.
	 *
	 * @return revolving
	 */
	public boolean getRevolving() {
		return this.revolving;
	}

	/**
	 * Retrieve whether the wheel is powered.
	 *
	 * @return powered
	 */
	public boolean getPowered() {
		return this.powered;
	}

	/**
	 * Sets the sprite of the wheel to a given sprite.
	 *
	 * @param sprite
	 *            : sprite of the wheel
	 */
	public void setSprite(Sprite sprite) {
		sprite.setSize(this.width, this.length);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		this.getBody().setUserData(sprite);
	}

	/**
	 * Retrieves the box2d body of this wheel.
	 *
	 * @return
	 */
	public Body getBody() {
		return this.wheelBody;
	}

	/**
	 * Changes the body of the wheel to a given body.
	 *
	 * @param body
	 *            : the new body of the wheel
	 */
	public void setBody(Body body) {
		this.wheelBody = body;
	}

	/**
	 * Changes the angle (degrees) relative to the taxi the wheel belongs to.
	 *
	 * @param angle
	 *            : angle of the wheel relative to the taxi
	 */
	public void setAngle(float angle) {
		this.getBody().setTransform(getBody().getPosition(),
				this.taxi.getBody().getAngle() + (float) Math.toRadians(angle));
	};

	/**
	 * Retrieves the velocity vector relative to the taxi it belongs to
	 *
	 * @return
	 */
	public Vector2 getLocalVelocity() {
		return taxi.getBody().getLocalVector(
				taxi.getBody().getLinearVelocityFromLocalPoint(
						getBody().getPosition()));
	};

	/**
	 * Retrieves a world unit vector which is pointing in the direction the
	 * wheel is moving.
	 *
	 * @return direction vector
	 */
	public Vector2 getDirectionVector() {
		Vector2 directionVector;
		if (this.getLocalVelocity().y > 0)
			directionVector = new Vector2(0, 1);
		else
			directionVector = new Vector2(0, -1);
		return directionVector.rotate((float) Math.toDegrees(getBody()
				.getAngle()));
	};

	/**
	 * Subtracts sideways velocity from the wheel's velocity vector.
	 *
	 * @return remaining front-facing velocity vector
	 */
	private Vector2 getKillVelocityVector() {
		Vector2 velocity = this.getBody().getLinearVelocity();
		Vector2 sidewaysAxis = this.getDirectionVector();
		float dotprod = velocity.dot(sidewaysAxis);
		return new Vector2(sidewaysAxis.x * dotprod, sidewaysAxis.y * dotprod);
	};

	/**
	 * Removes all sideways velocity from the wheels velocity.
	 *
	 */
	public void killSidewaysVelocity() {
		this.getBody().setLinearVelocity(this.getKillVelocityVector());
	}

	public void render(SpriteBatch spriteBatch) {
		spriteBatch.begin();
		Sprite sprite = (Sprite) wheelBody.getUserData();
		sprite.setPosition(wheelBody.getPosition().x * PIXELS_PER_METER,
				wheelBody.getPosition().y * PIXELS_PER_METER);
		sprite.setRotation(wheelBody.getAngle() * MathUtils.radiansToDegrees);
		sprite.setScale(PIXELS_PER_METER);
		sprite.draw(spriteBatch);
		spriteBatch.end();
	};
}
