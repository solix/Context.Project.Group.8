package com.taxi_trouble.game.multiplayer;

/**Interface that is used for frequent multiplayer-communication.
 * 
 * @author Computer Games Project Group 8
 *
 */
public interface MultiplayerInterface {

	public void login();

	public void logout();

	public void sendCarLocation(int teamId, float f, float g, float a);

	public void capturedPassenger(int passengerId, int teamId);

	public void createPassenger(float x, float y, int passengerId);

	public void submitScore(int score);
}
