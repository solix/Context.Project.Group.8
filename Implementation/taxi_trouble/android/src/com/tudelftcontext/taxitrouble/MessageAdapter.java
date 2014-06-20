package com.tudelftcontext.taxitrouble;

import java.util.Scanner;

import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.taxi_trouble.game.model.GameWorld;
import com.taxi_trouble.game.model.Spawner;
import com.taxi_trouble.game.model.entities.Passenger;
import com.taxi_trouble.game.model.entities.Taxi;
import com.taxi_trouble.game.model.entities.powerups.PowerUp;
import com.taxi_trouble.game.model.team.Team;
import com.taxi_trouble.game.screens.ViewObserver;

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

        if (!gameWorld.isActive() && !(flag.equals("SETUP"))) {
            // System.out.println("REJECTING " + message);
            sc.close();
            return;
        } else
        // System.out.println("ACCEPTED " + flag);

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
            System.out.println(message);
            resolveEndMessage(sc);
        }
        sc.close();
    }

    private void resolveSetupMessage(Scanner sc) {
        gameWorld.setActive(true);
        boolean driver = sc.nextBoolean();
        int teamId = sc.nextInt();
        int totalTeams = sc.nextInt();
        gameWorld.setDriver(driver);
        gameWorld.setTeams(teamId, totalTeams);
    }

    private void resolveEndMessage(Scanner sc) {
        Team winner = gameWorld.getTeamById(sc.nextInt());
        ((ViewObserver) gameWorld.getScreen()).showEndResultsBoard(winner);
        gameWorld.setActive(false);
        gameWorld.scheduleReset();

    }

    private void resolveActivateMessage(Scanner sc) {
        int teamId = sc.nextInt();
        int behaviourId = sc.nextInt();
        Team team = gameWorld.getTeams().get(teamId);
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

    private void resolveTaxiMessage(Scanner sc) {
        int id = sc.nextInt();
        Taxi taxi = gameWorld.getTeams().get(id).getTaxi();
        float x = Float.parseFloat(sc.next());
        float y = Float.parseFloat(sc.next());
        float angle = Float.parseFloat(sc.next());
        float xSpeed = Float.parseFloat(sc.next());
        float ySpeed = Float.parseFloat(sc.next());
        int acceleration = sc.nextInt();
        int steerDirection = sc.nextInt();
        taxi.updateState(x, y, angle, xSpeed, ySpeed, acceleration,
                steerDirection);
    }

    private void resolvePassengerMessage(Scanner sc) {
        Taxi taxi = gameWorld.getTaxiById(sc.nextInt());
        Passenger passenger = gameWorld.getPassengerById(sc.nextInt());
        taxi.syncPassenger(passenger);
    }

    private void resolveNewPassengerMessage(Scanner sc) {
        Spawner spawner = gameWorld.getMap().getSpawner();
        int spawnId = sc.nextInt();
        int destinationId = sc.nextInt();
        int charId = sc.nextInt();
        int passengerId = sc.nextInt();
        spawner.spawnPassenger(gameWorld.getWorld(), spawnId, destinationId,
                charId, passengerId);
    }

    private void resolveNewPowerUpMessage(Scanner sc) {
        Spawner spawner = gameWorld.getMap().getSpawner();
        int spawnId = sc.nextInt();
        int behaviourId = sc.nextInt();
        int powerUpId = sc.nextInt();
        spawner.spawnPowerUp(spawnId, behaviourId, powerUpId,
                gameWorld.getWorld());
    }
}