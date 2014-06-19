package com.tudelftcontext.taxitrouble;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import com.taxi_trouble.game.model.GameWorld;
import com.taxi_trouble.game.multiplayer.SetupInterface;

public class AndroidLauncher extends AndroidApplication implements
		GameHelperListener, SetupInterface, RoomUpdateListener,
		RoomStatusUpdateListener {

	private GameHelper aHelper;
	private boolean iAmHost = false;
	private String myId;
	private GameWorld gameWorld;
	private String roomId;
	private AndroidMultiplayerAdapter multiplayerInterface;
	private MessageAdapter messageAdapter;
	private boolean doSetup;
	// arbitrary request code for the waiting room UI.
	// This can be any integer that's unique in your Activity.
	private final static int RC_WAITING_ROOM = 10002;
	private final static int RC_SELECT_PLAYERS = 10000;
	private final static int RC_LEADERBOARD = 10003;
	private final static String LEADERBOARD_ID = "CgkI1YWnl7kREAIQBg";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		aHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		doSetup = true;
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		for(int i = 0; i < 1000; i++) {
		    System.out.println("WE AR LOGGING IN BLABLABLABLABLABLA TESTTEST");
		}
		login();
		gameWorld = new GameWorld(this, multiplayerInterface);
		messageAdapter = new MessageAdapter(gameWorld);
		initialize(gameWorld, cfg);
	}

	@Override
	public void onStart() {
		super.onStart();
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
		if (request == RC_WAITING_ROOM) {
			if (response == RESULT_OK) {
				Log.d("MULTI", "game started trough activityResult");
				gameWorld.startGame();
				System.out.println("teamID = "
						+ gameWorld.getTeam().getTeamId());
			}
		}

		if (request == RC_SELECT_PLAYERS) {
			if (response != RESULT_OK) {
				// user canceled
				return;
			}

			// get the invitee list
			Bundle extras = data.getExtras();
			final ArrayList<String> invitees = data
					.getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);

			// get auto-match criteria
			Bundle autoMatchCriteria = null;
			int minAutoMatchPlayers = data.getIntExtra(
					Multiplayer.EXTRA_MIN_AUTOMATCH_PLAYERS, 0);
			int maxAutoMatchPlayers = data.getIntExtra(
					Multiplayer.EXTRA_MAX_AUTOMATCH_PLAYERS, 0);

			if (minAutoMatchPlayers > 0) {
				autoMatchCriteria = RoomConfig.createAutoMatchCriteria(
						minAutoMatchPlayers, maxAutoMatchPlayers, 0);
			} else {
				autoMatchCriteria = null;
			}

			// create the room and specify a variant if appropriate
			RoomConfig.Builder roomConfigBuilder = makeBasicRoomConfigBuilder();
			roomConfigBuilder.addPlayersToInvite(invitees);
			if (autoMatchCriteria != null) {
				roomConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);
			}
			RoomConfig roomConfig = roomConfigBuilder.build();
			Games.RealTimeMultiplayer
					.create(aHelper.getApiClient(), roomConfig);

			// prevent screen from sleeping during handshake
			getWindow()
					.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		}
	}

	public void onSignInFailed() {
		System.out.println("sign in failed");
	}

	public void onSignInSucceeded() {
		System.out.println("sign in succeeded");

		if (aHelper.getInvitationId() != null) {
			RoomConfig.Builder roomConfigBuilder = makeBasicRoomConfigBuilder();
			roomConfigBuilder
					.setInvitationIdToAccept(aHelper.getInvitationId());
			Games.RealTimeMultiplayer.join(aHelper.getApiClient(),
					roomConfigBuilder.build());

			// prevent screen from sleeping during handshake
			getWindow()
					.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			return;
		}
	}

	public void login() {
		if (doSetup) {
			aHelper.setup(this);
			System.out.println("started AL login");
			doSetup = false;
		}
		aHelper.onStart(this);
		multiplayerInterface = new AndroidMultiplayerAdapter(
				aHelper.getApiClient());
		aHelper.beginUserInitiatedSignIn();
	}

	@Override
	public void logout() {
		try {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					aHelper.signOut();
				}
			});
		} catch (final Exception ex) {
		    showToast("Unable to logout.");
		}
	}

	public boolean isSignedIn() {
		return aHelper.isSignedIn();
	}

	@Override
	public void startGame() {
		if (isSignedIn()) {
			Intent intent = Games.RealTimeMultiplayer.getSelectOpponentsIntent(
					aHelper.getApiClient(), 1, 7);
			startActivityForResult(intent, RC_SELECT_PLAYERS);
		} else {
		    showToast("Please wait to be signed in.");
		    login();
		}
	}

	/**Displays a toast on screen with a specified message.
	 * 
	 * @param message : the message to displayed on the toast
	 */
	public void showToast(final String message) {
	    runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),
                        message, Toast.LENGTH_SHORT).show();
            }
        });
	}

	private RoomConfig.Builder makeBasicRoomConfigBuilder() {
		RoomConfig.Builder builder = RoomConfig.builder(this);
		builder.setMessageReceivedListener(messageAdapter);
		builder.setRoomStatusUpdateListener(this);

		return builder;
	}

	@Override
	public void onConnectedToRoom(Room room) {
		myId = room.getParticipantId(Games.Players.getCurrentPlayerId(aHelper
				.getApiClient()));
		roomId = room.getRoomId();

		multiplayerInterface.setMyId(myId);
		multiplayerInterface.setRoomId(roomId);
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

	@Override
	public void onJoinedRoom(int statusCode, Room room) {
		if (statusCode != GamesStatusCodes.STATUS_OK) {
			// display error
			return;
		}

		Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(
				aHelper.getApiClient(), room, 2);
		startActivityForResult(i, RC_WAITING_ROOM);
	}

	@Override
	public void onLeftRoom(int arg0, String arg1) {
		Log.d("ROOM", "left");
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
				aHelper.getApiClient(), room, Integer.MAX_VALUE);
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

		setHost(room);
		if (iAmHost) {
			messageAdapter.onRealTimeMessageReceived(multiplayerInterface
					.setTeams(room));
			System.out.println("done setting teams");
		}
	}

	private void setHost(Room room) {
		List<String> ids = room.getParticipantIds();
		Collections.sort(ids);
		if (ids.get(0).equals(myId)) {
			iAmHost = true;
		} else {
			iAmHost = false;
		}
		gameWorld.setHost(iAmHost);
		multiplayerInterface.setIds(room.getParticipantIds());

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

	@Override
	public void showLeaderBoard() {
	    if (isSignedIn()) {
	        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
				aHelper.getApiClient(), LEADERBOARD_ID), RC_LEADERBOARD);
	    }
	    else {
	        showToast("Please wait to be signed in.");
            login();
	    }
	}
}