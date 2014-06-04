package com.tudelftcontext.taxitrouble;

import java.util.Scanner;

import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.taxi_trouble.game.model.GameWorld;
import com.taxi_trouble.game.model.Passenger;
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

		//System.out.println(message);

		if (flag.equals("TAXI")) {
			resolveTaxiMessage(sc);
		} else if (flag.equals("PASSENGER")){
			System.out.println(message);
			resolvePassengerMessage(sc);
		} else if (flag.equals("DROP")) {
			System.out.println(message);
			resolvePassengerDrop(sc);
		} else if (flag.equals("NEWPASSENGER")){
			System.out.println(message);
			gameWorld.getMap().getSpawner().spawnPassenger(gameWorld.getWorld(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
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

		//this is purely for debugging, should be deleted for official releases
		if (count > 10) {
			//System.out.println(message);
			count = 0;
		}

		sc.close();
		unhandledMessages--;
	}
	
	private void resolvePassengerDrop(Scanner sc) {
		Taxi taxi = gameWorld.getTaxiById(sc.nextInt());
		Passenger passenger = gameWorld.getPassengerById(sc.nextInt());
		if(taxi.getPassenger() == passenger && passenger.getTransporter() == taxi){
			//if the taxi-pair is real on this client, do the drop off.
			taxi.dropOffPassenger(passenger.getDestination(), gameWorld.getMap());
		}
		else {
			//if somehow this taxi-pair is not real on this client, sync it, then do the drop off.
			taxi.syncPassenger(passenger);
			taxi.dropOffPassenger(passenger.getDestination(), gameWorld.getMap());
		}
		
		
		
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
	
	private void resolvePassengerMessage(Scanner sc){
		Taxi taxi = gameWorld.getTaxiById(sc.nextInt());
		Passenger passenger = gameWorld.getPassengerById(sc.nextInt());
		taxi.syncPassenger(passenger);
	}
}