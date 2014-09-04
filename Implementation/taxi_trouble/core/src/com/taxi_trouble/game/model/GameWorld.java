package com.taxi_trouble.game.model;

import static com.taxi_trouble.game.properties.ResourceManager.getTiledMap;
import static com.taxi_trouble.game.properties.ResourceManager.loadResources;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer.Task;
import com.taxi_trouble.game.model.entities.Passenger;
import com.taxi_trouble.game.model.entities.Taxi;
import com.taxi_trouble.game.model.entities.powerups.PowerUp;
import com.taxi_trouble.game.model.team.Team;
import com.taxi_trouble.game.multiplayer.AndroidMultiplayerInterface;
import com.taxi_trouble.game.multiplayer.SetupInterface;
import com.taxi_trouble.game.screens.DriverScreen;
import com.taxi_trouble.game.screens.MenuScreen;
import com.taxi_trouble.game.screens.NavigatorScreen;
import com.taxi_trouble.game.screens.ViewObserver;

/**
 * Provides the main model for all the elements of a game that is played.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class GameWorld extends Game {
    private WorldMap map;
    private Team ownTeam;
    private Map<Integer, Team> teams;
    private AndroidMultiplayerInterface multiplayerInterface;
    private SetupInterface setupInterface;
    private DriverScreen driverScreen;
    private NavigatorScreen navigatorScreen;
    private MenuScreen menuScreen;
    private CountDownTimer timer;

    /**
     * Initializes a new game world in which a game can be played.
     * 
     * @param setupInterface
     * @param multiplayerInterface
     */
    public GameWorld(SetupInterface setupInterface,
            AndroidMultiplayerInterface multiplayerInterface) {
        this.setupInterface = setupInterface;
        this.multiplayerInterface = multiplayerInterface;
        this.teams = new HashMap<Integer, Team>();
    }

    /**
     * Called when the game world is first created.
     * 
     */
    @Override
    public final void create() {
        loadResources();
        World world = new World(new Vector2(0.0f, 0.0f), true);
        map = new WorldMap(getTiledMap(), world);
        CollisionDetector collisionDetector = new CollisionDetector(map);
        world.setContactListener(collisionDetector);
        collisionDetector.setMultiPlayerInterface(multiplayerInterface);
        map.setMultiPlayerInterface(multiplayerInterface);
        this.resetTimer(10); //for testing purposes, should be reverted back to 300.

        driverScreen = new DriverScreen(this);
        navigatorScreen = new NavigatorScreen(this);
        menuScreen = new MenuScreen(setupInterface);
        setScreen(menuScreen);
    }

    @Override
    public void resume() {
        loadResources();
    }

    @Override
    public final void render() {
        super.render();
        if (isHost()) {
            // Spawn a new passenger/power-up if there are less than #taxis-1.
            Collection<Passenger> passengers = getPassengers();
            if (passengers.size() < teams.size() || passengers.size() == 0) {
                getSpawner().spawnPassenger(getWorld());
            }

            Collection<PowerUp> powerups = getPowerUps();
            if (powerups.size() < 3) {
                getSpawner().spawnPowerUp(getWorld());
            }
        }
    }

    /**
     * Starts the game by starting the timer and switching to the right screen.
     */
    public final void startGame() {
        timer.startTimer();
        setScreen();
    }

    /**
     * Sets whether the player is a driver or not.
     * 
     * @param driver
     *            : boolean indicating whether the player is a driver
     */
    public void setDriver(boolean driver) {
        multiplayerInterface.setDriver(driver);
    }

    /**
     * Retrieves whether the player is a driver.
     * 
     * @return true if the player is a driver, false otherwise (a navigator)
     */
    public boolean isDriver() {
        return multiplayerInterface.isDriver();
    }

    /**
     * Sets the assigned view. The assigned view of a player will be either the
     * DriverScreen or NavigatorScreen.
     */
    public void setScreen() {
        if (isDriver()) {
            setScreen(driverScreen);
        } else {
            setScreen(navigatorScreen);
        }
    }

    /**
     * Brings the player back to the menu screen.
     */
    public void returnToMenu() {
        setScreen(menuScreen);
    }

    /**
     * Retrieves the game world map.
     * 
     * @return map
     */
    public WorldMap getMap() {
        return this.map;
    }

    /**
     * Retrieves the spawner that is used to spawn entities.
     * 
     * @return spawner
     */
    public final Spawner getSpawner() {
        return this.map.getSpawner();
    }

    /**
     * Retrieves the single team.
     * 
     * @return team
     */
    public final Team getTeam() {
        return this.ownTeam;
    }

    /**
     * Retrieves the world in which the game is played.
     * 
     * @return world
     */
    public final World getWorld() {
        return map.getWorld();
    }

    /**
     * Retrieves the passengers that are currently in the game.
     * 
     * @return passengers
     */
    public final Collection<Passenger> getPassengers() {
        return getSpawner().getActivePassengers().values();
    }

    /**
     * Sets up the teams that join the game.
     * 
     * @param teamId
     *            : the own team identifier
     * @param totalTeams
     *            : the total number of teams - 1
     */
    public void setTeams(int teamId, int totalTeams) {
        for (int i = 0; i < totalTeams; i++) {
            Team team = new Team(i, getSpawner().spawnTaxi(getWorld(), i));
            if (i == teamId) {
                this.ownTeam = team;
            }
            teams.put(i, team);
        }
    }

    /**
     * Retrieves all teams that have joined the team.
     * 
     * @return teams
     */
    public Map<Integer, Team> getTeams() {
        return teams;
    }

    public AndroidMultiplayerInterface getMultiplayerInterface() {
        return multiplayerInterface;
    }

    public void setHost(boolean host) {
        this.multiplayerInterface.setHost(host);
    }

    public boolean isHost() {
        return this.multiplayerInterface.isHost();
    }

    public Passenger getPassengerById(int id) {
        return getSpawner().getActivePassengers().get(id);
    }

    public Taxi getTaxiById(int id) {
        return getTeamById(id).getTaxi();
    }

    public Team getTeamById(int id) {
        return getTeams().get(id);
    }

    public PowerUp getPowerUpById(int id) {
        return getSpawner().getActivePowerUps().get(id);
    }

    /**
     * Retrieves all currently available power-ups.
     * 
     * @return power-ups
     */
    public final Collection<PowerUp> getPowerUps() {
        return getSpawner().getActivePowerUps().values();
    }

    /**
     * Retrieves the game countdown-timer.
     * 
     * @return timer
     */
    public final CountDownTimer getTimer() {
        return this.timer;
    }

    /**
     * Retrieves the winning team of a game.
     * 
     * @return winner
     */
    public Team getWinner() {
        Team team = null;
        int highscore = -1;
        for (Team t : teams.values()) {
            if (t.getScore() > highscore) {
                team = t;
                highscore = t.getScore();
            }
        }
        return team;
    }
    
    /**
     * Restarts the game by despawning all entities in the gameWorld.
     */
	public void restart() {
		System.out.println("restarting!");
		this.map.getSpawner().despawnAll();
		System.out.println("despawned all npcs and powerups!");
		this.setTeams(this.ownTeam.getTeamId(), this.teams.size());
		System.out.println("teams have been reset!!");
		System.out.println("driverScreen reset!");
		this.resetTimer(15);
		System.out.println("time has been reset!");
		driverScreen = new DriverScreen(this);
		this.setScreen();
		this.timer.startTimer();
		
	}
	
	public void resetTimer(int time){
		this.timer= new CountDownTimer(time);
		this.timer.setEndCountDownEvent(new Task() {
            @Override
            public void run() {
                getMultiplayerInterface().submitScore(ownTeam.getScore());
                if (getMultiplayerInterface().isHost()) {
                   /* Team winner = getWinner();
                    getMultiplayerInterface().sendEndMessage(winner);
                   getActiveScreen().showEndResultsBoard(winner);*/
                	//@TODO: 1. Send restart message. 2. Restart local game
                	restart();
                }
            }
        });
	}
	
	public ViewObserver getActiveScreen(){
		if (this.isDriver()){
			return this.driverScreen;
		}
		else {
			return this.navigatorScreen;
		}
	}
}