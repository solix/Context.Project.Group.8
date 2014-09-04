package com.tudelftcontext.taxitrouble;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import com.taxi_trouble.game.model.GameWorld;
import com.taxi_trouble.game.multiplayer.SetupInterface;

/**
 * Launches the game for android devices and takes care of room management.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class AndroidLauncher extends AndroidApplication implements
        GameHelperListener, SetupInterface, RoomUpdateListener,
        RoomStatusUpdateListener {

    private GameHelper aHelper;
    private boolean iAmHost = false;
    private String myId;
    private GameWorld gameWorld;
    private AndroidMultiplayerAdapter multiplayerInterface;
    private MessageAdapter messageAdapter;
    private boolean doSetup;
    // Arbitrary request code for the waiting room UI.
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
        login();
        gameWorld = new GameWorld(this, multiplayerInterface);
        messageAdapter = new MessageAdapter(gameWorld);
        initialize(gameWorld, cfg);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!doSetup && !aHelper.getApiClient().isConnected()) {
            aHelper.getApiClient().connect();
        }
    }

    @Override
    public void onActivityResult(int request, int response, Intent data) {
        super.onActivityResult(request, response, data);
        aHelper.onActivityResult(request, response, data);
        if (request == RC_WAITING_ROOM) {
            if (response == RESULT_OK) {
                gameWorld.startGame();
            }
        }

        if (request == RC_SELECT_PLAYERS) {
            if (response != RESULT_OK) {
                // user canceled
                return;
            }

            // get the invitee list
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

    @Override
    public void onSignInFailed() {
        showToast("Signing in failed. Please try again.");
    }

    @Override
    public void onSignInSucceeded() {
        showToast("Signed in.");

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

    /**
     * Logs the player in to the Google Play Services.
     */
    public void login() {
        if (doSetup) {
            aHelper.setup(this);
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

    /**
     * Retrieves whether the player is signed in.
     * 
     * @return true if the player is signed in, false otherwise.
     */
    public boolean isSignedIn() {
        return aHelper.isSignedIn();
    }

    /**
     * Retrieves whether the player is signing in.
     * 
     * @return true if the player is signing in, false otherwise.
     */
    public boolean isSigningIn() {
        return aHelper.isConnecting();
    }

    @Override
    public void startGame() {
        if (isSignedIn()) {
            Intent intent = Games.RealTimeMultiplayer.getSelectOpponentsIntent(
                    aHelper.getApiClient(), 1, 7);
            startActivityForResult(intent, RC_SELECT_PLAYERS);
        } else if (!isSigningIn()) {
            showToast("Please wait to be signed in and try again.");
            login();
        }
    }

    /**
     * Displays a toast on screen with a specified message.
     * 
     * @param message
     *            : the message to displayed on the toast
     */
    public void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message,
                        Toast.LENGTH_SHORT).show();
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
        multiplayerInterface.setMyId(myId);
    }

    @Override
    public void onDisconnectedFromRoom(Room arg0) {
        showToast("You are disconnected from the room.");
        gameWorld.returnToMenu();
    }

    @Override
    public void onP2PConnected(String arg0) {
    }

    @Override
    public void onP2PDisconnected(String arg0) {
    }

    @Override
    public void onPeerInvitedToRoom(Room arg0, List<String> arg1) {
    }

    @Override
    public void onPeerJoined(Room arg0, List<String> arg1) {
    }

    @Override
    public void onRoomAutoMatching(Room arg0) {
    }

    @Override
    public void onRoomConnecting(Room arg0) {
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
        showToast("You left the room.");
        multiplayerInterface.setRoomId(null);
    }

    @Override
    public void onRoomCreated(int statusCode, Room room) {
        multiplayerInterface.setRoomId(room.getRoomId());
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
        multiplayerInterface.setRoomId(room.getRoomId());
        if (statusCode != GamesStatusCodes.STATUS_OK) {
            // let screen go to sleep
            getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            showToast("Could not connect to room.");
        }
        
        //To enable restarting, this should be done somewhere else, or a room has to be created every new game (not preferable)
        setHost(room);
        if (iAmHost) {
            messageAdapter.onRealTimeMessageReceived(multiplayerInterface
                    .setTeams(room)); //specifically this, this got to go
            multiplayerInterface.reliableBroadcast("THIS IS RELIABLE");
        }
    }

    /**
     * Assigns the host for the room.
     * 
     * @param room
     */
    private void setHost(Room room) {
        List<String> ids = room.getParticipantIds();
        Collections.sort(ids);
        multiplayerInterface.setIds(room.getParticipantIds());
        if (ids.get(0).equals(myId)) {
            iAmHost = true;
        } else {
            iAmHost = false;
        }
        gameWorld.setHost(iAmHost);
    }

    @Override
    public void onPeersConnected(Room room, List<String> peers) {
    }

    @Override
    public void onPeersDisconnected(Room room, List<String> peers) {
    }

    @Override
    public void onPeerLeft(Room room, List<String> peers) {
    }

    @Override
    public void onPeerDeclined(Room room, List<String> peers) {
    }

    @Override
    public void showLeaderBoard() {
        if (isSignedIn()) {
            startActivityForResult(
                    Games.Leaderboards.getLeaderboardIntent(
                            aHelper.getApiClient(), LEADERBOARD_ID),
                    RC_LEADERBOARD);
        } else if (!isSigningIn()) {
            showToast("Please wait to be signed in and try again.");
            login();
        }
    }

    @Override
    public void leave() {
        Games.RealTimeMultiplayer.leave(aHelper.getApiClient(), 
                this, multiplayerInterface.getRoomId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        aHelper.onStop();
    }
}