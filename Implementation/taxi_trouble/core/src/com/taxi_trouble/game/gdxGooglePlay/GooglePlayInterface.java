package com.taxi_trouble.game.gdxGooglePlay;

public interface GooglePlayInterface {

	public void Login();

	public void LogOut();

	// get if client is signed in to Google+
	public boolean getSignedIn();

	// submit a score to a leaderboard
	public void submitScore(int score);

	// gets the scores and displays them threw googles default widget
	public void getScores();

	// gets the score and gives access to the raw score data
	public void getScoresData();

	public void sendLocation(float f, float g);
}
