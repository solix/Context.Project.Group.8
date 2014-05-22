package com.tudelftcontext.taxitrouble;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
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
		GameHelperListener, GooglePlayInterface, RoomUpdateListener, RoomStatusUpdateListener, RealTimeMessageReceivedListener {

	private GameHelper aHelper;


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
	public void onActivityResult(int request, int response, Intent intent) {
	    if (request == RC_WAITING_ROOM) {
	        if (response == Activity.RESULT_OK) {
	            // (start game)
	        }
	        else if (response == Activity.RESULT_CANCELED) {
	            // Waiting room was dismissed with the back button. The meaning of this
	            // action is up to the game. You may choose to leave the room and cancel the
	            // match, or do something else like minimize the waiting room and
	            // continue to connect in the background.

	            // in this example, we take the simple approach and just leave the room:
	            Games.RealTimeMultiplayer.leave(getApiClient(), null, mRoomId);
	            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	        }
	        else if (response == GamesActivityResultCodes.RESULT_LEFT_ROOM) {
	            // player wants to leave the room.
	            Games.RealTimeMultiplayer.leave(getApiClient(), null, mRoomId);
	            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	        }
	    }
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
	public void onRealTimeMessageReceived(RealTimeMessage arg0) {
		// TODO Auto-generated method stub
		
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
	public void onPeerDeclined(Room arg0, List<String> arg1) {
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
	public void onPeerLeft(Room arg0, List<String> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPeersConnected(Room arg0, List<String> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPeersDisconnected(Room arg0, List<String> arg1) {
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
	    Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(aHelper.getApiClient(), room, 4 );
	    startActivityForResult(i, RC_WAITING_ROOM);
	}

	@Override
	public void onLeftRoom(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRoomConnected(int arg0, Room arg1) {
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
	    Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(aHelper.getApiClient(), room, 4);
	    startActivityForResult(i, RC_WAITING_ROOM);
	}
}