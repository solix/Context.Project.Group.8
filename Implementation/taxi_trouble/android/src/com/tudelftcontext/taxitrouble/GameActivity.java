package com.tudelftcontext.taxitrouble;

import java.util.List;

import android.os.Bundle;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.example.games.basegameutils.BaseGameActivity;
import com.google.example.games.basegameutils.GameHelper;
import com.taxi_trouble.game.gdxGooglePlay.GooglePlayInterface;
import com.taxi_trouble.game.model.GameWorld;

public class GameActivity extends BaseGameActivity implements
		GooglePlayInterface, RoomUpdateListener, RoomStatusUpdateListener, RealTimeMessageReceivedListener {
	GameHelper gh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gh = new GameHelper(this, GameHelper.CLIENT_GAMES);
		gh.setup(listener);;
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
	    Games.RealTimeMultiplayer.create(gh.getApiClient(), roomConfig);

	    // prevent screen from sleeping during handshake
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new GameWorld(this), config);// go to game screen
	}


	@Override
	public void Login() {
		// TODO Auto-generated method stub

	}

	@Override
	public void LogOut() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getSignedIn() {
		// TODO Auto-generated method stub
		return false;
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
	
	 private RoomConfig.Builder makeBasicRoomConfigBuilder() {
	        RoomConfig.Builder builder = RoomConfig.builder(this);
	        builder.setMessageReceivedListener(this);
	        builder.setRoomStatusUpdateListener(this);
	        

	        // ...add other listeners as needed...

	        return builder;
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

	@Override
	public void onJoinedRoom(int arg0, Room arg1) {
		// TODO Auto-generated method stub
		
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
	public void onRoomCreated(int arg0, Room arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}

}
