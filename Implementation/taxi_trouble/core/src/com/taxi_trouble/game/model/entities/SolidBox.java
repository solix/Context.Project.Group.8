package com.taxi_trouble.game.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

/**A solid box that can be placed in a world through which taxis cannot drive.
 *
 * @author Computer Games Project Group 8
 *
 */
public class SolidBox extends Entity {

    /**Initializes a new SolidBox in a world with specified width,
     * height and position.
     *
     * @param world : the world in which the box is placed
     * @param width : the width of the box
     * @param height : the height of the box
     * @param position : the position where the box should be placed
     */
    public SolidBox(World world, float width, float height, Vector2 position) {
        super(width, height);
        initializeBody(world, position);
    }

    /**Initialize the body of the solid box.
     *
     * @param world : the world in which the solid box is placed
     * @param position : the position at which the solid box is placed
     */
    private void initializeBody(World world, Vector2 position) {
        initializeBody(world, position, 0, BodyType.StaticBody, false, true);
    }
}