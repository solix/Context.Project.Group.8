package com.tudelftcontext.taxitrouble;

import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.taxi_trouble.game.model.GameWorld;
import com.taxi_trouble.game.model.Spawner;
import com.taxi_trouble.game.model.entities.Passenger;
import com.taxi_trouble.game.model.entities.Taxi;
import com.taxi_trouble.game.model.entities.powerups.PowerUp;
import com.taxi_trouble.game.model.team.Team;
import com.taxi_trouble.game.screens.ViewObserver;

/**
 * Receives incoming messages and processes them.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class MessageAdapter implements RealTimeMessageReceivedListener {
    private GameWorld gameWorld;

    public MessageAdapter(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    @Override
    public void onRealTimeMessageReceived(RealTimeMessage rtm) {
        onRealTimeMessageReceived(new String(rtm.getMessageData()));
    }

    public void onRealTimeMessageReceived(String message) {
        Scanner sc = new Scanner(message);
        String flag = sc.next();

        if (flag.equals("TAXI")) {
            resolveTaxiMessage(sc);
        } else if (flag.equals("PASSENGER")) {
            resolvePassengerMessage(sc);
        } else if (flag.equals("POWERUP")) {
            resolvePowerUpMessage(sc);
        } else if (flag.equals("ACTIVATE")) {
            resolveActivateMessage(sc);
        } else if (flag.equals("DROP")) {
            resolvePassengerDrop(sc);
        } else if (flag.equals("NEWPASSENGER")) {
            resolveNewPassengerMessage(sc);
        } else if (flag.equals("NEWPOWERUP")) {
            resolveNewPowerUpMessage(sc);
        } else if (flag.equals("SETUP")) {
            resolveSetupMessage(sc);
        } else if (flag.equals("END")) {
            resolveEndMessage(sc);
        } else if (flag.equals("RESTART")){
        	resolveRestartMessage();
        }
        sc.close();
    }

    private void resolveRestartMessage() {
    	
		Gdx.app.postRunnable(new Runnable() {
			
			@Override
			public void run() {
				getGameWorld().restartPhase2();
				
			}
		});
		
	}

	private void resolveSetupMessage(Scanner sc) {
        boolean driver = sc.nextBoolean();
        int teamId = sc.nextInt();
        int totalTeams = sc.nextInt();
        gameWorld.setDriver(driver);
        gameWorld.setTeams(teamId, totalTeams);
    }

    private void resolveEndMessage(Scanner sc) {
        Team winner = gameWorld.getTeamById(sc.nextInt());
        ((ViewObserver) gameWorld.getScreen()).showEndResultsBoard(winner);
        this.gameWorld.getNextGameTimer().startTimer();
    }

    private void resolveActivateMessage(Scanner sc) {
        final int teamId = sc.nextInt();
        final int behaviourId = sc.nextInt();
        final Team team = gameWorld.getTeams().get(teamId);
        Gdx.app.postRunnable(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (!team.hasPowerUp()
						|| team.getPowerUp().getBehaviour().getId() != behaviourId) {
					System.out
					.println("UNOBTAINED POWERUP ACTIVATED!! --> RESYNCING AND ACTIVATING!!");
					PowerUp powerUp = gameWorld.getMap().getSpawner()
							.spawnPowerUp(behaviourId, gameWorld.getWorld());
					team.setPowerUp(powerUp);
				}
				team.forcePowerUpUse();
				
			}
        	
        });
    }

    private void resolvePowerUpMessage(Scanner sc) {
        int taxiId = sc.nextInt();
        int powerUpId = sc.nextInt();
        final Taxi taxi = gameWorld.getTaxiById(taxiId);
        final PowerUp powerUp = gameWorld.getPowerUpById(powerUpId);
        Gdx.app.postRunnable(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				taxi.pickUpPowerUp(powerUp, gameWorld.getMap());
				
			}
        	
        });
    }

    private void resolvePassengerDrop(Scanner sc) {
        final Taxi taxi = gameWorld.getTaxiById(sc.nextInt());
        final Passenger passenger = gameWorld.getPassengerById(sc.nextInt());
        
        Gdx.app.postRunnable(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (taxi.getPassenger() == passenger
						&& passenger.getTransporter() == taxi) {
					// if the taxi-pair is real on this client, do the drop off.
					taxi.dropOffPassenger(passenger.getDestination(),
							gameWorld.getMap());
				} else {
					// if somehow this taxi-pair is not real on this client, sync it,
					// then do the drop off.
					taxi.syncPassenger(passenger);
					taxi.dropOffPassenger(passenger.getDestination(),
							gameWorld.getMap());
				}
				
			}
        	
        });

    }

    private void resolveTaxiMessage(Scanner sc) {
        int id = sc.nextInt();
        final Taxi taxi = gameWorld.getTeams().get(id).getTaxi();
        float x = Float.parseFloat(sc.next());
        float y = Float.parseFloat(sc.next());
        final float angle = Float.parseFloat(sc.next());
        float xSpeed = Float.parseFloat(sc.next());
        float ySpeed = Float.parseFloat(sc.next());
        final int acceleration = sc.nextInt();
        final int steerDirection = sc.nextInt();
        final Vector2 position = new Vector2(x, y);
        final Vector2 speed = new Vector2(xSpeed, ySpeed);
        Gdx.app.postRunnable(new Runnable() {

			@Override
			public void run() {
				taxi.updatePositionState(position, speed, angle);
				taxi.updateMovementState(acceleration, steerDirection);
				
			}
        	
        });
    }

    private void resolvePassengerMessage(Scanner sc) {
        final Taxi taxi = gameWorld.getTaxiById(sc.nextInt());
        final Passenger passenger = gameWorld.getPassengerById(sc.nextInt());
        
        Gdx.app.postRunnable(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				taxi.syncPassenger(passenger);
			}
        	
        });
    }

    private void resolveNewPassengerMessage(Scanner sc) {
        final Spawner spawner = gameWorld.getMap().getSpawner();
        final int spawnId = sc.nextInt();
        final int destinationId = sc.nextInt();
        final int charId = sc.nextInt();
        final int passengerId = sc.nextInt();
        Gdx.app.postRunnable(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				spawner.spawnPassenger(gameWorld.getWorld(), spawnId, destinationId,
						charId, passengerId);
				
			}
        	
        });
    }

    private void resolveNewPowerUpMessage(Scanner sc) {
        final Spawner spawner = gameWorld.getMap().getSpawner();
        final int spawnId = sc.nextInt();
        final int behaviourId = sc.nextInt();
        final int powerUpId = sc.nextInt();
        
        Gdx.app.postRunnable(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				spawner.spawnPowerUp(spawnId, behaviourId, powerUpId,
						gameWorld.getWorld());
				
			}
        	
        });
    }
    
    public GameWorld getGameWorld(){
    	return this.gameWorld;
    }
}