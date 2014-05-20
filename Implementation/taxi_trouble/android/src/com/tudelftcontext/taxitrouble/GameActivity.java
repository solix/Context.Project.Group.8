package com.tudelftcontext.taxitrouble;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.taxi_trouble.game.gdxGooglePlay.GooglePlayInterface;
import com.taxi_trouble.game.model.GameWorld;

public class GameActivity extends AndroidApplication implements
		GooglePlayInterface {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new GameWorld(this), config);
	}

	@Override
	public void Login() {
		// TODO Auto-generated method stub

	}

	@Override
	public void LogOut() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getSignedIn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void submitScore(int score) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getScores() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getScoresData() {
		// TODO Auto-generated method stub

	}

}
