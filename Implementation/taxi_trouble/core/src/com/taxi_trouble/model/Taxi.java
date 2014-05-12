package com.taxi_trouble.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Taxi {
    private float width;
    private float length;
    private float angle;
    private float maxSteerAngle;
    private float maxSpeed;
    private float power;
    private Sprite taxiSprite;
    private Body taxiBody;
    
    /**Initializes a new Taxi which can be controlled by a player.
     * 
     * @param width : the width of the taxi
     * @param length : the length of the taxi
     * @param position : the starting position of the taxi
     * @param angle : the starting angle of the taxi
     * @param maxSteerAngle : the maximum angle under which the taxi can be steered
     * @param maxSpeed : the maximum speed of the taxi
     * @param power : the power of the taxi's engine
     */
    public Taxi(float width, float length, Vector2 pos, float angle, float maxSteerAngle, float maxSpeed, float power) {
        this.width = width;
        this.length = length;
        this.maxSteerAngle = maxSteerAngle;
        this.maxSpeed = maxSpeed;
        this.power = power;
    }
    
    public Body createBody(World world) {
        //TODO: Implement the method
        return null;
    }
     
}
