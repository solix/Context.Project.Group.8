package com.taxi_trouble.game.model;

import com.badlogic.gdx.math.Vector2;

/**A spawnpoint is a point where either a passenger or taxi is spawned or the
 * place for a destination.
 *
 * @author Computer Games Project Group 8
 *
 */
public class SpawnPoint {
    private Vector2 position;
    private float spawnAngle;
    private float width;
    private float height;
    private boolean active;

    /**Initializes a new SpawnPosition with location (x,y) and
     * with spawning angle as specified.
     *
     * @param x : the x-position of the SpawnPoint
     * @param y : the y-position of the SpawnPoint
     * @param spawnAngle : the spawning angle in degrees
     */
    public SpawnPoint(float x, float y, float spawnAngle) {
        this.position = new Vector2(x, y);
        this.width = 0;
        this.height = 0;
        this.spawnAngle = spawnAngle;
        this.active = false;
    }

    /**Retrieves the spawn position.
     *
     * @return position
     */
    public Vector2 getPosition() {
        return this.position;
    }

    /**Retrieves the x-position of the spawn point.
     *
     * @return x-position
     */
    public float getXPosition() {
        return this.position.x;
    }

    /**Retrieves the y-position of the spawn point.
     *
     * @return y-position
     */
    public float getYPosition() {
        return this.position.y;
    }

    /**Retrieves the spawning angle of the spawn point.
     *
     * @return spawnAngle
     */
    public float getAngle() {
        return this.spawnAngle;
    }

    /**Retrieves the width of the spawn point.
     *
     * @return width
     */
    public float getWidth() {
        return this.width;
    }

    /**Set the width of the spawn point.
     *
     * @param width
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**Set the height of the spawn point.
     *
     * @param height
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**Retrieves the height of the spawn point.
     *
     * @return height
     */
    public float getHeight() {
        return this.height;
    }

    /**Retrieves whether the spawning point has been used.
    *
    * @return active
    */
    public boolean isActive() {
        return active;
    }

    /**Sets the current state of the spawning point.
     *
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
