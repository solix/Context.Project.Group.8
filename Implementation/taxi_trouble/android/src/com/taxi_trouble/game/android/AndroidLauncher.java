package com.taxi_trouble.game.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class AndroidLauncher extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent loginIntent = new Intent(this, LoginActivity.class);
		startActivity(loginIntent);
	}
}
