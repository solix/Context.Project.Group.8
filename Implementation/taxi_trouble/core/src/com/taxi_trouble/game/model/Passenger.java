package com.taxi_trouble.game.model;

import static com.taxi_trouble.game.properties.GameProperties.PIXELS_PER_METER;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**A passenger.
 *
 * @author Computer Games Project Group 8
 *
 */
public class Passenger {
    public float width, height;
    public Body body;

    /**Initializes a new passenger.
     *
     * @param world : the world in which the passenger is placed
     * @param width : the width of the passenger
     * @param height : the height of the passenger
     * @param position : the position where the passenger should be placed
     */
    public Passenger(World world, float width, float height, Vector2 position) {
        this.width = width;
        this.height = height;
        initializeBody(world, position);
    }

    /**Initialize the body of the solid passenger.
     *
     * @param world : the world in which the solid passenger is placed
     * @param position : the position at which the solid passenger is placed
     */
    private void initializeBody(World world, Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.angle = 0;
        bodyDef.fixedRotation = true;
        this.body = world.createBody(bodyDef);
        InitFixtureDef();
        
    }

    /**Retrieves the fixture for the body of the solid passenger.
     *
     *
     */
    private void InitFixtureDef() {
        FixtureDef fixtureDef = new FixtureDef();

        PolygonShape passengerShape = new PolygonShape();
        passengerShape.setAsBox(this.width / 2, this.height / 2);
        fixtureDef.shape=passengerShape;
        fixtureDef.restitution=0f;
        this.body.createFixture(fixtureDef);
        passengerShape.dispose();
       }

    /**Retrieves the width of the passenger.
     *
     * @return width
     */
    public float getWidth() {
        return this.width;
    }

    /**Retrieves the height of the passenger.
     *
     * @return height
     */
    public float getHeight() {
       return this.height;
    }

    /**Retrieves the body of the passenger.
     *
     * @return body
     */
    public Body getBody() {
        return this.body;
    }
    
    /**Changes the body of the passenger to the specified body.
     *
     * @param body
     */
    public void setBody(Body body) {
        this.body = body;
    }

    /**Retrieves the x-position of the passenger.
     *
     * @return x-position
     */
    public float getXPosition() {
        return this.getBody().getPosition().x;
    }

    /**Retrieves the y-position of the passenger.
     *
     * @return
     */
    public float getYPosition() {
        return this.getBody().getPosition().y;
    }
    
    /**Sets the (initial) sprite of the passenger.
     *
     * @param passSprite
     */
    public void setSprite(Sprite passSprite) {
        passSprite.setSize(this.getWidth(), this.getHeight());
        passSprite.setOrigin(this.getWidth() / 2, this.getHeight() / 2);
        this.getBody().setUserData(passSprite);
    }
    
    /**Renders the sprite(s) of the passenger.
     *
     * @param spriteBatch
     */
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        Sprite sprite = (Sprite) this.body.getUserData();
        sprite.setPosition(this.getXPosition() * PIXELS_PER_METER,
                this.getYPosition() * PIXELS_PER_METER);
        sprite.setRotation(this.getBody().getAngle() * MathUtils.radiansToDegrees);
        sprite.setScale(PIXELS_PER_METER);
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }
}