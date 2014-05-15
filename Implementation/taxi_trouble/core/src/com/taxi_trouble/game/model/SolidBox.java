package com.taxi_trouble.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**A solid box that can be placed in a world through which taxis cannot drive.
 * 
 * @author Computer Games Project Group 8
 *
 */
public class SolidBox {
    public float width, height;
    public Body body;

    /**Initializes a new SolidBox in a world with specified width, height and position.
     * 
     * @param world : the world in which the box is placed
     * @param width : the width of the box
     * @param height : the height of the box
     * @param position : the position where the box should be placed
     */
    public SolidBox(World world, float width, float height, Vector2 position) {
        this.width = width;
        this.height = height;
        initializeBody(world, position);
    }

    /**Initialize the body of the solid box.
     * 
     * @param world : the world in which the solid box is placed
     * @param position : the position at which the solid box is placed
     */
    private void initializeBody(World world, Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.angle = 0;
        bodyDef.fixedRotation = true;
        this.body = world.createBody(bodyDef);
        this.body.createFixture(getFixtureDef());
    }

    /**Retrieves the fixture for the body of the solid box.
     * 
     * @return fixtureDef
     */
    private FixtureDef getFixtureDef() {
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(this.width / 2, this.height / 2);
        fixtureDef.shape=boxShape;
        fixtureDef.restitution=0f;
        boxShape.dispose();
        return fixtureDef;
    }

    /**Retrieves the width of the solid box.
     * 
     * @return width
     */
    public float getWidth() {
        return this.width;
    }

    /**Retrieves the height of the solid box.
     * 
     * @return height
     */
    public float getHeight() {
       return this.height;
	}

    /**Retrieves the body of the solid box.
     * 
     * @return body
     */
    public Body getBody() {
        return this.body;
    }
    
    /**Changes the body of the solid box to the specified body.
     * 
     * @param body
     */
    public void setBody(Body body) {
        this.body = body;
    }

    /**Retrieves the x-position of the solid box.
     * 
     * @return x-position
     */
    public float getXPosition() {
        return this.getBody().getPosition().x;
    }

    /**Retrieves the y-position of the solid box.
     * 
     * @return
     */
    public float getYPosition() {
        return this.getBody().getPosition().y;
    }
}