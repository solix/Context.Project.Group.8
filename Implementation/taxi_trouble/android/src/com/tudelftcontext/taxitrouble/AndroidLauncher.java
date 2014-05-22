package com.tudelftcontext.taxitrouble;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import com.taxi_trouble.game.gdxGooglePlay.GooglePlayInterface;
import com.taxi_trouble.game.model.GameWorld;

public class AndroidLauncher extends AndroidApplication implements
		GameHelperListener, GooglePlayInterface, RoomUpdateListener,
		RoomStatusUpdateListener, RealTimeMessageReceivedListener {

	private GameHelper aHelper;
	private boolean iAmHost = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		aHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		// cfg.useGL20 = false;
		aHelper.setup(this);
		initialize(new GameWorld(this), cfg);
	}

	@Override
	public void onStart() {
		super.onStart();
		aHelper.onStart(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		aHelper.onStop();
	}

	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		aHelper.onActivityResult(request, response, data);
	}

	public void onSignInFailed() {
		System.out.println("sign in failed");
	}

	public void onSignInSucceeded() {
		System.out.println("sign in succeeded");
		startQuickGame();
	}

	public void Login() {
		System.out.println("started AL login");
		aHelper.beginUserInitiatedSignIn();
	}

	public void LogOut() {
		try {
			runOnUiThread(new Runnable() {

				// @Override
				public void run() {
					aHelper.signOut();
				}
			});
		} catch (final Exception ex) {

		}

	}

	public boolean getSignedIn() {
		return aHelper.isSignedIn();
	}

	private void startQuickGame() {
		// auto-match criteria to invite one random automatch opponent.
		// You can also specify more opponents (up to 3).
		Bundle am = RoomConfig.createAutoMatchCriteria(1, 1, 0);

		// build the room config:
		RoomConfig.Builder roomConfigBuilder = makeBasicRoomConfigBuilder();
		roomConfigBuilder.setAutoMatchCriteria(am);
		RoomConfig roomConfig = roomConfigBuilder.build();

		// create room:
		Games.RealTimeMultiplayer.create(aHelper.getApiClient(), roomConfig);

		// prevent screen from sleeping during handshake
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		// go to game screen
	}

	private RoomConfig.Builder makeBasicRoomConfigBuilder() {
		RoomConfig.Builder builder = RoomConfig.builder(this);
		builder.setMessageReceivedListener(this);
		builder.setRoomStatusUpdateListener(this);

		// ...add other listeners as needed...

		return builder;
	}

	@Override
	public void submitScore(int score) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getScores() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getScoresData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRealTimeMessageReceived(RealTimeMessage rtm) {
		String message = new String(rtm.getMessageData());
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onConnectedToRoom(Room arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDisconnectedFromRoom(Room arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onP2PConnected(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onP2PDisconnected(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPeerInvitedToRoom(Room arg0, List<String> arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPeerJoined(Room arg0, List<String> arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRoomAutoMatching(Room arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRoomConnecting(Room arg0) {
		// TODO Auto-generated method stub

	}

	// arbitrary request code for the waiting room UI.
	// This can be any integer that's unique in your Activity.
	final static int RC_WAITING_ROOM = 10002;

	@Override
	public void onJoinedRoom(int statusCode, Room room) {
		if (statusCode != GamesStatusCodes.STATUS_OK) {
			// display error
			return;
		}

		// get waiting room intent
		Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(
				aHelper.getApiClient(), room, 2);
		startActivityForResult(i, RC_WAITING_ROOM);
	}

	@Override
	public void onLeftRoom(int arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRoomCreated(int statusCode, Room room) {
		System.out.println("room created!!!!");
		if (statusCode != GamesStatusCodes.STATUS_OK) {
			// display error
			return;
		}

		// get waiting room intent
		Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(
				aHelper.getApiClient(), room, 2);
		startActivityForResult(i, RC_WAITING_ROOM);
	}

	@Override
	public void onRoomConnected(int statusCode, Room room) {
		System.out.println("room connected!!");
		if (statusCode != GamesStatusCodes.STATUS_OK) {
			// let screen go to sleep
			getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

			// show error message, return to main screen.
		}
	}

	// are we already playing?
	boolean mPlaying = false;

	// at least 2 players required for our game
	final static int MIN_PLAYERS = 2;

	// returns whether there are enough players to start the game
	boolean shouldStartGame(Room room) {
		int connectedPlayers = 0;
		for (Participant p : room.getParticipants()) {
			if (p.isConnectedToRoom())
				++connectedPlayers;
		}
		return connectedPlayers >= MIN_PLAYERS;
	}

	// Returns whether the room is in a state where the game should be canceled.
	boolean shouldCancelGame(Room room) {
		// TODO: Your game-specific cancellation logic here. For example, you
		// might decide to
		// cancel the game if enough people have declined the invitation or left
		// the room.
		// You can check a participant's status with Participant.getStatus().
		// (Also, your UI should have a Cancel button that cancels the game too)
		return false;
	}

	@Override
	public void onPeersConnected(Room room, List<String> peers) {
		if (mPlaying) {
			// add new player to an ongoing game
		} else if (shouldStartGame(room)) {
			// start game!
		}

		if (room.getParticipants().size() == 1) {
			iAmHost = true;
		} else if (room.getParticipants().size() == 2) {
			Games.RealTimeMultiplayer.sendReliableMessage(
					aHelper.getApiClient(), null, "test".getBytes(),
					room.getRoomId(), room.getParticipants().get(1)
							.getParticipantId());
		}
	}

	@Override
	public void onPeersDisconnected(Room room, List<String> peers) {
		if (mPlaying) {
			// do game-specific handling of this -- remove player's avatar
			// from the screen, etc. If not enough players are left for
			// the game to go on, end the game and leave the room.
		} else if (shouldCancelGame(room)) {
			// cancel the game
			Games.RealTimeMultiplayer.leave(aHelper.getApiClient(), null,
					room.getRoomId());
			getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
	}

	@Override
	public void onPeerLeft(Room room, List<String> peers) {
		// peer left -- see if game should be canceled
		if (!mPlaying && shouldCancelGame(room)) {
			Games.RealTimeMultiplayer.leave(aHelper.getApiClient(), null,
					room.getRoomId());
			getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
	}

	@Override
	public void onPeerDeclined(Room room, List<String> peers) {
		// peer declined invitation -- see if game should be canceled
		if (!mPlaying && shouldCancelGame(room)) {
			Games.RealTimeMultiplayer.leave(aHelper.getApiClient(), null,
					room.getRoomId());
			getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
	}
}