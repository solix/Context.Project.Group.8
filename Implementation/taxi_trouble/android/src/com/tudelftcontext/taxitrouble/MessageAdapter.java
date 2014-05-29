package com.tudelftcontext.taxitrouble;

import java.util.Scanner;

import android.util.Log;

import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.taxi_trouble.game.Acceleration;
import com.taxi_trouble.game.SteerDirection;
import com.taxi_trouble.game.model.GameWorld;

public class MessageAdapter implements RealTimeMessageReceivedListener {
	private GameWorld gameWorld;
	private int count = 0;
	private boolean iAmHost;

	public MessageAdapter(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}
	@Override
	public void onRealTimeMessageReceived(RealTimeMessage rtm) {
		onRealTimeMessageReceived(new String(rtm.getMessageData()));
	}
	
	public void onRealTimeMessageReceived(String rtm) {
		count++;
		String message = rtm;

		Scanner sc = new Scanner(message);
		String flag = sc.next();

		if (!flag.equals("TAXI"))
		System.out.println(message);

		if (flag.equals("changes")) {
			assert(iAmHost == true);
			int id = sc.nextInt();
			flag = sc.next();
			if (flag.equals("accel")){
				gameWorld.getTeams().get(id).getTaxi().setAccelerate(Acceleration.values[sc.nextInt()]);
				flag = sc.next();
			}
			if (flag.equals("steer")){
				gameWorld.getTeams().get(id).getTaxi().setSteer(SteerDirection.values[sc.nextInt()]);
			}
			
		//	gameWorld.setTaxiLocation(id, x, y, a);
		} else if (flag.equals("SETUP")) {
			boolean driver = sc.nextBoolean();
			gameWorld.setDriver(driver);
			int teamId = sc.nextInt();
			gameWorld.getTeam().setTeamId(teamId);
			GameWorld.multiplayerInterface.setTeamId(teamId);
			int totalTeams = sc.nextInt();
			gameWorld.setTeams(totalTeams);
		} else if (flag.equals("NAVIGATOR")) {
			gameWorld.setDriver(false);
		}
		
		if (count> 10){
			System.out.println(message);
			count = 0;
		}

		sc.close();
	}
	
	
	public void setIAmHost(boolean host) {
		this.iAmHost = host;
	}
}
