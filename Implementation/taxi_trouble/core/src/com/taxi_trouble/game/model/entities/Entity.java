package com.taxi_trouble.game.model.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.taxi_trouble.game.properties.GameProperties.PIXELS_PER_METER;

/**An entity that can be placed in the game world.
 * 
 * @author Computer Games Project Group 8
 *
 */
public abstract class Entity {
    private int id;
    private float width;
    private float height;
    private Body body;
    private Sprite sprite;

    /**Constructor for an entity with certain width, height and
     * an unique id identifying the entity.
     * 
     * @param id : the identifier of the entity
     * @param width : the width of the entity
     * @param height : the height of the entity
     */
    public Entity(int id, float width, float height) {
        this.id = id;
        this.width = width;
        this.height = height;
    }

    /**Constructor for an entity with certain width and height.
     * 
     * @param width : the width of the entity
     * @param height : the height of the entity
     */
    public Entity(float width, float height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Initializes the body of this entity.
     * 
     * @param world : the world in which the body should be placed
     * @param position : the position at which the body should be placed
     * @param angle : the angle under which the body should be placed
     * @param bodyType : defines whether the body is dynamic or static
     * @param isSensor : defines whether the body is a sensor
     *                  (does not automatically respond when colliding)
     * @param fixedRotation : defines whether the rotation is fixed
     */
    public void initializeBody(World world, Vector2 position, float angle,
            BodyType bodyType, boolean isSensor, boolean fixedRotation) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = bodyType;
        bodyDef.angle = angle;
        bodyDef.fixedRotation = fixedRotation;
        this.setBody(world.createBody(bodyDef));
        initFixtureDef(isSensor);
    }

    /**
     * Initializes the body of this entity.
     * 
     * @param world : the world in which the body should be placed
     * @param position : the position at which the body should be placed
     * @param angle : the angle under which the body should be placed
     * @param bodyType : defines whether the body is dynamic or static
     * @param isSensor : defines whether the body is a sensor
     *                  (does not automatically respond when colliding)
     */
    public void initializeBody(World world, Vector2 position, float angle,
            BodyType bodyType, boolean isSensor) {
        initializeBody(world, position, angle, bodyType, isSensor, false);
    }

    /**Initializes the body of this entity, placed under an angle of 0.
     * 
     * @param world : the world in which the body should be placed
     * @param position : the position at which the body should be placed
     * @param bodyType : defines whether the body is dynamic or static
     * @param isSensor : defines whether the body is a sensor
     *                  (does not automatically respond when colliding)
     */
    public void initializeBody(World world, Vector2 position, 
            BodyType bodyType, boolean isSensor) {
        initializeBody(world, position, 0, bodyType, isSensor);
    }

    /**
     * Initializes the fixture of the body.
     * 
     */
    private void initFixtureDef(boolean isSensor) {
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape entityShape = new PolygonShape();
        entityShape.setAsBox(this.width / 2, this.height / 2);
        fixtureDef.shape = entityShape;
        fixtureDef.isSensor = isSensor;
        fixtureDef.restitution = 0f;
        this.body.createFixture(fixtureDef);
        entityShape.dispose();
    }

    /**
     * Retrieves the width of the entity.
     * 
     * @return width
     */
    public float getWidth() {
        return this.width;
    }

    /**
     * Retrieves the height of the entity.
     * 
     * @return height
     */
    public float getHeight() {
        return this.height;
    }

    /**
     * Retrieves the id of the entity.
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the position of this entity.
     * 
     * @return position
     */
    public Vector2 getPosition() {
        assert (this.getBody() != null);
        return this.getBody().getPosition();
    }

    /**
     * Retrieves the x-position.
     * 
     * @return x-position
     */
    public float getXPosition() {
        assert (this.getBody() != null);
        return this.getBody().getPosition().x;
    }

    /**
     * Retrieves the y-position.
     * 
     * @return y-position
     */
    public float getYPosition() {
        assert (this.getBody() != null);
        return this.getBody().getPosition().y;
    }

    /**
     * Retrieves the current angle in radians under which the entity is
     * placed in the world.
     * 
     * @return angle
     */
    public float getAngle() {
        assert (this.getBody() != null);
        return this.getBody().getAngle();
    }

    /**
     * Sets the angle in radians of the entity.
     * 
     * @param angle
     *            : the new angle
     */
    public void setAngle(float angle) {
        assert (this.getBody() != null);
        this.getBody().setTransform(getPosition(), angle);
    }

    /**
     * Sets the position of a passenger.
     * 
     * @param position
     *            : the position of the passenger
     */
    public void setPosition(Vector2 position) {
        assert (this.getBody() != null);
        this.getBody().setTransform(position, this.body.getAngle());
    }

    /**
     * Retrieves the body of the entity.
     * 
     * @return body
     */
    public Body getBody() {
        return this.body;
    }

    /**
     * Sets the body of the entity.
     * 
     * @param body
     *            : the body to be set
     */
    public void setBody(Body body) {
        this.body = body;
        this.body.setUserData(this);
    }

    /**Sets the sprite of this entity.
     * 
     * @param sprite : the new sprite of the entity
     */
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.sprite.setSize(width, height);
        this.sprite.setOrigin(this.getWidth() / 2,
                this.getHeight() / 2);
        this.sprite.setScale(PIXELS_PER_METER);
    }

    /**Retrieves the sprite of the entity.
     *
     * @return sprite of the entity
     */
    public Sprite getSprite() {
        return this.sprite;
    }

    /**Removes the body of the entity from the specified world.
     *
     * @param world : the world from which
     */
    public void removeBodyFromWorld(World world) {
        assert (this.getBody() != null);
        if(!world.isLocked()) {
            world.destroyBody(getBody());
        }
    }

    public void addBodyToWorld(World world) {
    }
}
