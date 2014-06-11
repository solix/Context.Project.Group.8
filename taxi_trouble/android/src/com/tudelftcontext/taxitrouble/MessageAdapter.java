package com.tudelftcontext.taxitrouble;

import java.util.Scanner;

import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.taxi_trouble.game.model.GameWorld;
import com.taxi_trouble.game.model.Passenger;
import com.taxi_trouble.game.model.Taxi;
import com.taxi_trouble.game.model.powerups.PowerUp;
import com.taxi_trouble.game.model.team.Team;

public class MessageAdapter implements RealTimeMessageReceivedListener {
	private GameWorld gameWorld;
	private int count = 0;
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

		//System.out.println(message);

		if (flag.equals("TAXI")) {
			resolveTaxiMessage(sc);
		} else if (flag.equals("PASSENGER")){
			System.out.println(message);
			resolvePassengerMessage(sc);
		} else if (flag.equals("POWERUP")){
			System.out.println(message);
			resolvePowerUpMessage(sc);
		} else if (flag.equals("ACTIVATE")){
			System.out.println(message);
			resolveActivateMessage(sc);
		} else if (flag.equals("DROP")) {
			System.out.println(message);
			resolvePassengerDrop(sc);
		} else if (flag.equals("NEWPASSENGER")){
			System.out.println(message);
			gameWorld.getMap().getSpawner().spawnPassenger(gameWorld.getWorld(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
		} else if (flag.equals("NEWPOWERUP")){
			System.out.println(message);
			gameWorld.getMap().getSpawner().spawnPowerUp(sc.nextInt(), sc.nextInt(), sc.nextInt(), gameWorld.getWorld());
		} else if (flag.equals("SETUP")) {
			boolean driver = sc.nextBoolean();
			gameWorld.setDriver(driver);
			int teamId = sc.nextInt();
			int totalTeams = sc.nextInt();
			gameWorld.setTeams(teamId, totalTeams);
		} else if (flag.equals("NAVIGATOR")) {
			gameWorld.setDriver(false);
		}

		//this is purely for debugging, should be deleted for official releases
		if (count > 10) {
			//System.out.println(message);
			count = 0;
		}

		sc.close();
		}
	
	private void resolveActivateMessage(Scanner sc) {
		int teamId = sc.nextInt();
		int behaviourId = sc.nextInt();
		Team team = gameWorld.getTeams().get(teamId);
		if (!team.hasPowerUp() ||team.getPowerUp().getBehaviour().getId() != behaviourId){
			System.out.println("UNOBTAINED POWERUP ACTIVATED!! --> RESYNCING AND ACTIVATING!!");
			PowerUp powerUp = gameWorld.getMap().getSpawner().spawnPowerUp(behaviourId, gameWorld.getWorld());
			team.setPowerUp(powerUp);
		}
		team.forcePowerUpUse();
		
	}

	private void resolvePowerUpMessage(Scanner sc) {
		int taxiId = sc.nextInt();
		int powerUpId = sc.nextInt();
		Taxi taxi = gameWorld.getTaxiById(taxiId);
		PowerUp powerUp = gameWorld.getPowerUpById(powerUpId);
		taxi.pickUpPowerUp(powerUp, gameWorld.getMap());
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