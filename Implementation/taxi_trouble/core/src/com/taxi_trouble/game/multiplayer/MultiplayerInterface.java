package com.taxi_trouble.game.multiplayer;

public interface MultiplayerInterface {

	public void login();

	public void logout();

	public void sendCarLocation(int teamId, float f, float g, float a);

	public void capturedPassenger(int passengerId, int teamId);

	public void createPassenger(float x, float y, int passengerId);

	public void submitScore(int score);
}
