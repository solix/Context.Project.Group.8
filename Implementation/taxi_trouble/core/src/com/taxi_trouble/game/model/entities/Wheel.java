package com.taxi_trouble.game.model.entities;

import static com.taxi_trouble.game.properties.GameProperties.PIXELS_PER_METER;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
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
public class Wheel extends Entity {
	private Taxi taxi;
	private boolean revolving;
	private boolean powered;

	/**Initializes a new Wheel for a Taxi in a world at a given position and
	 * with given size. A wheel can be revolving and/or powered.
	 * 
	 * @param world : the world into which this wheel is placed
	 * @param taxi : the taxi to which this wheel belongs
	 * @param position : position of the wheel relative to the taxi
	 * @param width : width of the wheel
	 * @param length : length of the wheel
	 * @param revolving : boolean indicating whether the wheel is revolving
	 * @param powered : boolean indicating whether the wheel is powered
	 */
	public Wheel(World world, Taxi taxi, Vector2 position, float width,
			float length, boolean revolving, boolean powered) {
	    super(width, length);
		this.taxi = taxi;
		this.revolving = revolving;
		this.powered = powered;
		initializeBody(world, position);
	}

    /**
	 * Creates the body of this wheel.
	 *
	 * @param world
	 *            : the world for which the body should be created
	 * @param position
	 *            : the (relative) position to the taxi
	 */
	public void initializeBody(World world, Vector2 position) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(taxi.getBody().getWorldPoint(position));
		bodyDef.angle = taxi.getBody().getAngle();
		setBody(world.createBody(bodyDef));
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
		fixtureDef.isSensor = true;
		PolygonShape wheelShape = new PolygonShape();
		wheelShape.setAsBox(getWidth() / 2, getHeight() / 2);
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
		if (isRevolving()) {
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
	public boolean isRevolving() {
		return this.revolving;
	}

	/**
	 * Retrieve whether the wheel is powered.
	 *
	 * @return powered
	 */
	public boolean isPowered() {
		return this.powered;
	}

	/**
	 * Changes the angle (degrees) relative to the taxi the wheel belongs to.
	 *
	 * @param angle
	 *            : angle of the wheel relative to the taxi
	 */
	@Override
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
		Sprite sprite = getSprite();
		sprite.setPosition(getXPosition() * PIXELS_PER_METER,
				getYPosition() * PIXELS_PER_METER);
		sprite.setRotation(getAngle() * MathUtils.radiansToDegrees);
		sprite.setScale(PIXELS_PER_METER);
		sprite.draw(spriteBatch);
		spriteBatch.end();
	};
}
