package com.tudelftcontext.taxitrouble;

import java.util.Scanner;

import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.taxi_trouble.game.model.GameWorld;
import com.taxi_trouble.game.model.Taxi;

public class MessageAdapter implements RealTimeMessageReceivedListener {
	private GameWorld gameWorld;
	private int count = 0;
	private int unhandledMessages = 0;

	public MessageAdapter(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}

	@Override
	public void onRealTimeMessageReceived(RealTimeMessage rtm) {
		onRealTimeMessageReceived(new String(rtm.getMessageData()));
	}

	public void onRealTimeMessageReceived(String rtm) {
		unhandledMessages++;
		//System.out.println("OPEN MESSAGES: " + unhandledMessages);
		count++;
		String message = rtm;

		Scanner sc = new Scanner(message);
		String flag = sc.next();

		System.out.println(message);

		if (flag.equals("TAXI")) {
			resolveTaxiMessage(sc);
		} else if (flag.equals("SETUP")) {
			boolean driver = sc.nextBoolean();
			gameWorld.setDriver(driver);
			int teamId = sc.nextInt();
			gameWorld.getTeam().setTeamId(teamId);
			int totalTeams = sc.nextInt();
			gameWorld.setTeams(totalTeams);
		} else if (flag.equals("NAVIGATOR")) {
			gameWorld.setDriver(false);
		}

		if (count > 10) {
			System.out.println(message);
			count = 0;
		}

		sc.close();
		unhandledMessages--;
	}
	
	private void resolveTaxiMessage(Scanner sc) {
		//System.out.println("resolver called!");
		int id = sc.nextInt();
		Taxi taxi = gameWorld.getTeams().get(id).getTaxi();
		float x = Float.parseFloat(sc.next());
		
		float y = Float.parseFloat(sc.next());
		float angle = Float.parseFloat(sc.next());
		float xSpeed = Float.parseFloat(sc.next());
		float ySpeed = Float.parseFloat(sc.next());
		/*int acceleration = sc.nextInt();
		int steerDirection = sc.nextInt();*/
		taxi.setInfo(x,y,angle, xSpeed, ySpeed); //,xSpeed, ySpeed, acceleration, steerDirection);
		//System.out.println("resolving done!");
		
	}
}