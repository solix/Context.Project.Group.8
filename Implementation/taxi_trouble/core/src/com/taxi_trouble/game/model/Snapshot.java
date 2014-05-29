package com.taxi_trouble.game.model;

public class Snapshot {
	private float time;
	private float x;
	private float y;
	private float angle;
	private float speed;
	
	public Snapshot(float x, float y, float angle, float speed){
		this.time = (float)(System.currentTimeMillis()/1000.0);
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.speed = speed;
	}
	
	

}
