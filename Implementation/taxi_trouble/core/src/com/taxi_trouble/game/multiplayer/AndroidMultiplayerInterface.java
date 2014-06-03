package com.taxi_trouble.game.multiplayer;

public interface AndroidMultiplayerInterface {

	public void capturedPassenger(int passengerId, int teamId);

	public void createPassenger(float x, float y, int passengerId);

	public void sendScore(int score);

	public void sendCarLocation(String message);

	public void reliableBroadcast(String message);
}
