package com.taxi_trouble.game.model;

import com.badlogic.gdx.math.Vector2;

public class SpawnPoint {
    private Vector2 position;
    private float spawnAngle;

    /**Initializes a new SpawnPosition with location (x,y) and 
     * with spawning angle as specified.
     *
     * @param x : the x-position of the SpawnPoint
     * @param y : the y-position of the SpawnPoint
     * @param spawnAngle : the spawning angle in degrees
     */
    public SpawnPoint(float x, float y, float spawnAngle) {
        this.position = new Vector2(x, y);
        this.spawnAngle = spawnAngle;
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
}
