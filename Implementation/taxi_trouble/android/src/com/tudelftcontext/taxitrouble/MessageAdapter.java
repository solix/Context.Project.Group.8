package com.tudelftcontext.taxitrouble;

import java.util.Scanner;

import android.util.Log;

import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.taxi_trouble.game.Acceleration;
import com.taxi_trouble.game.SteerDirection;
import com.taxi_trouble.game.model.GameWorld;
import com.taxi_trouble.game.model.Snapshot;

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

		if (flag.equals("CHANGES")) {
			assert(iAmHost == true);
			System.out.println("checking changes");
			int id = sc.nextInt();
			if(sc.hasNext()) 
				flag = sc.next();
			if (flag.equals("accel")){
				gameWorld.getTeams().get(id).getTaxi().setAccelerate(Acceleration.values[sc.nextInt()]);
				System.out.println("accel altered!");
				if(sc.hasNext()) 
					flag = sc.next();
			}
			if (flag.equals("steer")){
				gameWorld.getTeams().get(id).getTaxi().setSteer(SteerDirection.values[sc.nextInt()]);
				if(sc.hasNext()) 
					flag = sc.next();
			}
		} else if(flag.equals("SERVER")){
			verifyPrediction(sc);
			
			
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
	
	public void verifyPrediction(Scanner sc){
		int time = sc.nextInt();
		Snapshot snapshot = GameWorld.multiplayerInterface.getRecord(time);
		if (snapshot == null)
			System.out.println("NO RECORDS - SERVER CLIENT DESYCHRONIZED");
		String flag;
		while(sc.hasNext()){
			flag = sc.next();
			if (flag.equals("TAXI")){
				verifyTaxi(snapshot, sc);
			}
		}
		sc.close();
		
		
	}


	private void verifyTaxi(Snapshot snapshot, Scanner sc) {
		int teamId = sc.nextInt();
		if(teamId != gameWorld.getTeam().getTeamId()){
			return;
		}
		float serverX = Float.parseFloat(sc.next());
		float deltaX = Math.abs(serverX - snapshot.getX());
		float serverY = Float.parseFloat(sc.next());
		float deltaY = Math.abs(serverY - snapshot.getY());
		float serverAngle = Float.parseFloat(sc.next());
		float deltaAngle = Math.abs(serverAngle - snapshot.getAngle());
		float serverSpeed = Float.parseFloat(sc.next());
		float deltaSpeed = Math.abs(serverSpeed - snapshot.getSpeed());
		
		if (deltaX < 1){
			
		}
		else if (deltaX < 2.5 ){
			//System.out.println("Smooth Correction needed!");
			
		}
		else{
			//System.out.println("correction needed!");
			//gameWorld.getTeams().get(teamId).getTaxi().correct();
		}
		
		
		
		
	}
	
	
	public void setIAmHost(boolean host) {
		this.iAmHost = host;
	}
}
