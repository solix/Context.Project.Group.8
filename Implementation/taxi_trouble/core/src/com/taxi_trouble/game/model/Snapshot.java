package com.taxi_trouble.game.model;

import java.util.Calendar;

public class Snapshot {
	private int time;
	private float x;
	private float y;
	private float angle;
	private float speed;
	
	public Snapshot(float x, float y, float angle, float speed){
		Calendar calendar = Calendar.getInstance();
		this.time = calendar.get(Calendar.SECOND)*1000 + calendar.get(Calendar.MILLISECOND);
		//System.out.println("New record added! TIME = " + this. time);
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.speed = speed;
	}
	
	public int getTime(){
		return this.time;
	}
	
	public float getX(){
		return this.x;
	}
	
	public float getY(){
		return this.y;
	}
	
	public float getAngle(){
		return this.angle;
	}
	
	public float getSpeed(){
		return this.speed;
	}
	
	public String toString(){
		return "Snapshot[time = " + this.time + "]";
	}

}
