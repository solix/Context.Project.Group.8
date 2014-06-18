package com.tudelftcontext.taxitrouble;

import java.util.Collections;
import java.util.List;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.taxi_trouble.game.model.entities.Passenger;
import com.taxi_trouble.game.model.entities.Taxi;
import com.taxi_trouble.game.model.entities.powerups.PowerUp;
import com.taxi_trouble.game.model.team.Team;
import com.taxi_trouble.game.multiplayer.AndroidMultiplayerInterface;

public class AndroidMultiplayerAdapter implements AndroidMultiplayerInterface {
	private String roomId;
	private GoogleApiClient apiClient;
	private String myId;
	private List<String> ids;
	private boolean host = false;
	private String hostId;

	public AndroidMultiplayerAdapter(GoogleApiClient apiClient) {
		this.apiClient = apiClient;
	}

	/**
	 * Sends data of the car to all other clients in the room.
	 * 
	 * @param message
	 *            : the message containing the car data.
	 */
	@Override
	public void sendCarLocation(String message) {
		if (roomId != null) {
			Games.RealTimeMultiplayer.sendUnreliableMessageToOthers(apiClient,
					message.getBytes(), roomId);
		}
	}

	@Override
	public void sendScore(int score) {
		// TODO Auto-generated method stub

	}

	/**
	 * Assigns teams and roles and notifies other players of their assigned role
	 * and team.
	 * 
	 * @param room
	 *            : the room in which the game takes place
	 * @return
	 */
	public String setTeams(Room room) {
		ids = room.getParticipantIds();
		Collections.sort(ids);
		boolean role;
		String mySetupMessage = "MessageCreationError";
		int teamId;
		int totalTeams = (int) Math.ceil(room.getParticipantIds().size() / 2);
		for (int i = 0; i < ids.size(); i++) {
			teamId = (int) Math.floor(i / 2.0);
			if (i % 2 == 0) {
				// role = "DRIVER";
				role = true;
			} else {
				// role = "NAVIGATOR";
				role = false;
			}

			String message = "SETUP " + role + " " + teamId + " " + totalTeams;

			if (!ids.get(i).equals(myId)) {
				Games.RealTimeMultiplayer.sendReliableMessage(apiClient, null,
						message.getBytes(), room.getRoomId(), ids.get(i));

			} else {
				mySetupMessage = message;
			}
		}

		// reliableBroadcast("TOTALTEAMS " + totalTeams, room);

		return mySetupMessage;
	}

	public void setMyId(String myId) {
		this.myId = myId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	/**
	 * Sends a reliableMessage to all other clients in the room.
	 * 
	 * @param message
	 *            : the message to send.
	 */
	@Override
	public void reliableBroadcast(String message) {
		log(message);
		byte[] messageBytes = message.getBytes();
		for (String id : ids) {
			if (!id.equals(myId))
				Games.RealTimeMultiplayer.sendReliableMessage(apiClient, null,
						messageBytes, roomId, id);
		}
	}

	/**
	 * creates and sends a message with the state of a passenger.
	 * 
	 * @param taxi
	 *            : The taxi that currently has the passenger.
	 * @param passenger
	 *            : the passenger of which the state is being send.
	 */
	@Override
	public void passengerMessage(Taxi taxi, Passenger passenger) {
		String message = "PASSENGER ";
		message += taxi.getTeam().getTeamId() + " " + passenger.getId();
		reliableBroadcast(message);
	}

	/**
	 * Sends a reliable message to the host of the game.
	 * 
	 * @param message
	 *            : the message of to be send.
	 */
	public void sendToHost(String message) {
		byte[] messageBytes = message.getBytes();
		if (!host)
			Games.RealTimeMultiplayer.sendReliableMessage(apiClient, null,
					messageBytes, roomId, hostId);
	}

	@Override
	public void setHost(boolean host) {
		this.host = host;
	}

	/**
	 * checks if this client is the host.
	 * 
	 * @return true is this client is the host, false otherwise.
	 */
	@Override
	public boolean isHost() {
		return host;
	}

	/**
	 * Sets the ids of the clients connected to the room. These will be used to
	 * send messages.
	 * 
	 * @param ids
	 *            : the IDs of the clients in the room.
	 */
	public void setIds(List<String> ids) {
		this.hostId = ids.get(0);
		this.ids = ids;
	}

	public String getHostId() {
		return this.hostId;
	}

	/**
	 * generates and broadcasts a message containing data of a newly created
	 * powerup.
	 * 
	 * @param spawnId
	 *            : the spawnId of the powerUp.
	 * @param behaviourId
	 *            : the behaviourId of the powerUp.
	 * @param powerUpId
	 *            : the ID of the newly created powerUp.
	 */
	@Override
	public void newPowerUpMessage(int spawnId, int behaviourId, int powerUpId) {
		String message = "NEWPOWERUP " + spawnId + " " + behaviourId + " "
				+ powerUpId;
		reliableBroadcast(message);

	}

	/**
	 * creates and sends a message with information about a picked up powerUp.
	 * 
	 * @param taxi
	 *            : The taxi that currently has the powerUp.
	 * @param powerUp
	 *            : the powerUp of which the information is being send.
	 */
	@Override
	public void powerUpMessage(Taxi taxi, PowerUp powerUp) {
		String message = "POWERUP " + taxi.getTeam().getTeamId() + " "
				+ powerUp.getId();
		reliableBroadcast(message);

	}

	/**
	 * creates and sends a message with information about a activated powerUp.
	 * 
	 * @param team
	 *            : the team that activated the powerUp.
	 * @param powerUp
	 *            : the powerUp that has been activated.
	 */
	@Override
	public void activateMessage(Team team, PowerUp powerUp) {
		System.out.println("activateMessage called!");

		String message = "ACTIVATE " + team.getTeamId() + " "
				+ powerUp.getBehaviour().getId();
		reliableBroadcast(message);
	}

	/**
	 * creates and sends a message that informs other clients that the game has
	 * ended and who has won the game.
	 * 
	 * @param team
	 *            : the team that won the game.
	 */
	@Override
	public void sendEndMessage(Team team) {
		String message = "END " + team.getTeamId();
		reliableBroadcast(message);

	}

	/**
	 * An internal function used to log broadcasted messages.
	 * 
	 * @param message
	 *            : the message that should be logged.
	 */
	public void log(String message) {
		System.out.println("BROADCASTED: " + message);
	}

}
