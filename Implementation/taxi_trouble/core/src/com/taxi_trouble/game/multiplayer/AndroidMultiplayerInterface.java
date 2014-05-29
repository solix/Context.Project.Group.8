package com.taxi_trouble.game.multiplayer;

import com.taxi_trouble.game.Acceleration;

public interface AndroidMultiplayerInterface {
	public void sendCarLocation(int teamId, float f, float g, float a);
	
	public void sendCarAcceleration(int teamId, Acceleration acceleration);

	public void capturedPassenger(int passengerId, int teamId);

	public void createPassenger(float x, float y, int passengerId);

	public void sendScore(int score);

	void sendCar(String networkMessage);
}
