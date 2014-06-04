package com.taxi_trouble.game.multiplayer;

import com.taxi_trouble.game.model.Passenger;
import com.taxi_trouble.game.model.Taxi;

public interface AndroidMultiplayerInterface {

	public void capturedPassenger(int passengerId, int teamId);

	public void createPassenger(float x, float y, int passengerId);

	public void sendScore(int score);

	public void sendCarLocation(String message);

	public void reliableBroadcast(String message);

	public void setHost(boolean host);

	void passengerMessage(Taxi taxi, Passenger passenger);

	public boolean isHost();
}
