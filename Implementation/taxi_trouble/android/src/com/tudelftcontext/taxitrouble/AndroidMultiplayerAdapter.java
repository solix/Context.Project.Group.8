package com.tudelftcontext.taxitrouble;

import java.util.Collections;
import java.util.List;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.taxi_trouble.game.multiplayer.AndroidMultiplayerInterface;

public class AndroidMultiplayerAdapter implements AndroidMultiplayerInterface {
	private String roomId;
	private GoogleApiClient apiClient;
	private String myId;
	private int myTeamId;
	private boolean driver;

	public AndroidMultiplayerAdapter(GoogleApiClient apiClient) {
		this.apiClient = apiClient;
	}

	@Override
	public void sendCarLocation(String message) {
		if (roomId != null) {
			Games.RealTimeMultiplayer.sendUnreliableMessageToOthers(apiClient,
					message.getBytes(), roomId);
		}
	}

	@Override
	public void capturedPassenger(int passengerId, int teamId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createPassenger(float x, float y, int passengerId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendScore(int score) {
		// TODO Auto-generated method stub

	}

	public String setTeams(Room room) {
		List<String> ids = room.getParticipantIds();
		Collections.sort(ids);
		boolean role;
		String mySetupMessage = "MessageCreationError";
		int teamId;
		int totalTeams = room.getParticipantIds().size() / 2;
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

	public void reliableBroadcast(String message, Room room) {
		byte[] messageBytes = message.getBytes();
		for (String id : room.getParticipantIds()) {
			Games.RealTimeMultiplayer.sendReliableMessage(apiClient, null,
					messageBytes, room.getRoomId(), id);
		}
	}
}