package com.taxi_trouble.game.model;

import static com.taxi_trouble.game.properties.GameProperties.PIXELS_PER_METER;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * After a passenger is picked by a taxi, the team corresponding to the taxi
 * should see the destination to bring the passenger to.
 *
 * @author Computer Games Project Group 8
 *
 */
public class Destination {
    private Body body;
    private float width;
    private float height;
    private Sprite destinationSprite;

    /**Initializes a new destination where a passenger can be dropped off.
     *
     * @param width
     * @param height
     */
    public Destination(float width, float height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Initializes the body of this destination.
     *
     * @param world
     * @param vector2
     */
    public void initializeBody(World world, Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        this.setBody(world.createBody(bodyDef));
        initFixtureDef();
    }

    /**
     * Initializes the fixture of the body.
     *
     */
    private void initFixtureDef() {
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape destShape = new PolygonShape();
        destShape.setAsBox(this.width / 2, this.height / 2);
        fixtureDef.shape = destShape;
        fixtureDef.isSensor = true;
        fixtureDef.restitution = 0f;
        this.body.createFixture(fixtureDef);
        destShape.dispose();
    }

    /**
     * Retrieves the position of this destination.
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
     * Retrieves the body of the destination.
     *
     * @return body
     */
    public Body getBody() {
        return this.body;
    }

    /**
     * Sets the body of the destination.
     *
     * @param body
     *            : the body to be set
     */
    public void setBody(Body body) {
        this.body = body;
        this.body.setUserData(this);
    }

    /**
     * Retrieves the width of this destination.
     *
     * @return width
     */
    public float getWidth() {
        return width;
    }

    /**
     * Retrieves the height of this destination.
     *
     * @return height
     */
    public float getHeight() {
        return height;
    }

    /**
     * Sets the sprite of the destination point.
     *
     * @param destSprite
     */
    public void setSprite(Sprite destSprite) {
        destSprite.setSize(this.getWidth(), this.getHeight());
        destSprite.setOrigin(this.getWidth() / 2, this.getHeight() / 2);
        this.destinationSprite = destSprite;
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        destinationSprite.setPosition(this.getXPosition() * PIXELS_PER_METER,
                this.getYPosition() * PIXELS_PER_METER);
        destinationSprite.setScale(PIXELS_PER_METER);
        destinationSprite.draw(spriteBatch);
        spriteBatch.end();
    }
}
