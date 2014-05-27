package com.taxi_trouble.game.multiplayer;

public interface AndroidMultiplayerInterface {
	public void sendCarLocation(int teamId, float f, float g, float a);

	public void capturedPassenger(int passengerId, int teamId);

	public void createPassenger(float x, float y, int passengerId);

	public void sendScore(int score);
}
