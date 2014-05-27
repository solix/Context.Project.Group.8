package com.tudelftcontext.taxitrouble;

import java.util.Scanner;

import android.util.Log;

import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.taxi_trouble.game.model.GameWorld;

public class MessageAdapter implements RealTimeMessageReceivedListener {
	private GameWorld gameWorld;

	public MessageAdapter(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}

	@Override
	public void onRealTimeMessageReceived(RealTimeMessage rtm) {
		String message = new String(rtm.getMessageData());

		Scanner sc = new Scanner(message);
		String flag = sc.next();

		// if (!flag.equals("TAXI"))
		Log.d("MULTI", message);

		if (flag.equals("TAXI")) {
			int id = sc.nextInt();
			float x = Float.parseFloat(sc.next());
			float y = Float.parseFloat(sc.next());
			float a = Float.parseFloat(sc.next());
			gameWorld.setTaxiLocation(id, x, y, a);
		} else if (flag.equals("TEAM")) {
			int teamId = sc.nextInt();
			gameWorld.getTeam().setTeamId(teamId);
		} else if (flag.equals("DRIVER")) {
			gameWorld.setDriver(true);
		} else if (flag.equals("NAVIGATOR")) {
			gameWorld.setDriver(false);
		} else if (flag.equals("TOTALTEAMS")) {
			int totalTeams = sc.nextInt();
			gameWorld.setTeams(totalTeams);
		}

		sc.close();
	}
}
