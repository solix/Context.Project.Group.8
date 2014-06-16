package com.taxi_trouble.game.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.taxi_trouble.game.model.powerups.PowerUp;
import com.taxi_trouble.game.model.team.Team;
import com.taxi_trouble.game.multiplayer.AndroidMultiplayerInterface;
import com.taxi_trouble.game.multiplayer.SetupInterface;
import com.taxi_trouble.game.properties.ResourceManager;
import com.taxi_trouble.game.screens.DriverScreen;
import com.taxi_trouble.game.screens.MenuScreen;
import com.taxi_trouble.game.screens.NavigatorScreen;

/**
 * Provides the main model for all the elements of a game that is played.
 * (Please note: implementation will change when implementing multiplayer)
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class GameWorld extends Game {
	private World world;
	private WorldMap map;
	// Temporary: single team (may change when implementing multiplayer)
	private Team ownTeam;
	private CountDownTimer timer;
	private AndroidMultiplayerInterface multiplayerInterface;
	private DriverScreen driverScreen;
	private NavigatorScreen navigatorScreen;
	private MenuScreen menuScreen;
	private SetupInterface setupInterface;
	private boolean driver;
	private Map<Integer, Team> teams;
	private boolean host = false;
	private CollisionDetector collisionDetector;
	private boolean multiplayerIntitialized;

	public GameWorld(SetupInterface setupInterface) {
		this.setupInterface = setupInterface;
		// this.setupInterface.login();
		this.teams = new HashMap<Integer, Team>();
		multiplayerIntitialized = false;
	}

	/**
	 * Called when the game world is first created.
	 * 
	 */
	@Override
	public final void create() {
		System.out.println("RUNNING BETA 0.9");
	
		loadResources();
		world = new World(new Vector2(0.0f, 0.0f), true);
		map = new WorldMap(ResourceManager.mapFile, world);
		collisionDetector = new CollisionDetector(map);
		world.setContactListener(collisionDetector);
		System.out.println("gameworld created!!!");

		driverScreen = new DriverScreen(this);
		navigatorScreen = new NavigatorScreen(this);
		timer = new CountDownTimer(300);
		menuScreen = new MenuScreen(setupInterface);
		setScreen(menuScreen);
	}

	@Override
	public void resume() {
		loadResources();
	}
	
	 public final void startGame() {
		 System.out.println("timer started");
        timer.startTimer();
    }

    /**
     * Loads the game resources.
     * 
     */
    public void loadResources() {
        ResourceManager.loadMap();
        ResourceManager.loadSprites();
        ResourceManager.loadFonts();
       // ResourceManager.loadFx();
    }

	@Override
	public final void render() {
		super.render();
		// Spawn a new passenger if there are less than #taxis-1.
		// TODO: Instead of '3' adapt to #taxis-1 in the game.
		if (host && multiplayerIntitialized) {
			ConcurrentHashMap<Integer, Passenger> passengers = map.getSpawner()
					.getActivePassengers();
			if (passengers.size() < 3) {
				map.getSpawner().spawnPassenger(world);
			}

			ConcurrentHashMap<Integer, PowerUp> powerups = map.getSpawner()
					.getActivePowerUps();
			if (powerups.size() < 3) {
				map.getSpawner().spawnPowerUp(world);
			}
		}
	}

	public void setDriver(boolean driver) {
		System.out.println("setDriver called, driver = " + driver);
		this.driver = driver;
	}

	public void setScreen() {
		System.out.println("setscreen called, driver = " + driver);
		if (driver) {
			setScreen(driverScreen);
		} else {
			setScreen(navigatorScreen);
		}
		startGame();
	}

	public void returnToMenu() {
		setScreen(menuScreen);
	}

	/**
	 * Retrieves the game world map.
	 * 
	 * @return map
	 */
	public final WorldMap getMap() {
		return this.map;
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
		return this.world;
	}

	/**
	 * Retrieves the passengers that are currently in the game.
	 * 
	 * @return passengers
	 */
	public final ConcurrentHashMap<Integer, Passenger> getPassengers() {
		return this.map.getSpawner().getActivePassengers();
	}

	public void setTeams(int teamId, int totalTeams) {
		for (int i = 0; i < totalTeams; i++) {
			Team team =  new Team(i, map.getSpawner().spawnTaxi(world, i));

			if (i == teamId) {
				this.ownTeam = team;
				System.out.println("ownteam!");
			}
				
			teams.put(i, team);
			

		}
		System.out.println(ownTeam.toString());
		System.out.println(teams.toString());
	}

	public Map<Integer, Team> getTeams() {
		return teams;
	}

	public AndroidMultiplayerInterface getMultiplayerInterface() {
		return multiplayerInterface;
	}

	public void setMultiplayerInterface(
			AndroidMultiplayerInterface multiplayerInterface) {
		this.multiplayerInterface = multiplayerInterface;
	}

	public void setHost(boolean host) {
		this.host = host;
		this.multiplayerInterface.setHost(host);
	}

	public Passenger getPassengerById(int id) {
		return map.getSpawner().getActivePassengers().get(id);
	}

	public Taxi getTaxiById(int id) {
		return getTeamById(id).getTaxi();
	}

	public Team getTeamById(int id) {
		return getTeams().get(id);
	}

	
	public PowerUp getPowerUpById(int id){
		PowerUp result = map.getSpawner().getActivePowerUps().get(id);
		if (result == null){
			System.out.println("DESYNC DETECTED: POWERUP #" +id + " NOT FOUND");
		}
		return result;
	}
	
	public final ConcurrentHashMap<Integer,PowerUp> getPowerUps() {
        return this.map.getSpawner().getActivePowerUps();
    }

	public void setMultiPlayerInterface(AndroidMultiplayerInterface i) {
		multiplayerInterface = i;
		collisionDetector.setMultiPlayerInterface(i);
		map.setMultiplayerInterface(i);
		multiplayerIntitialized = true;
	}

 /**Retrieves the game countdown-timer.
     * 
     * @return timer
     */
    public final CountDownTimer getTimer() {
        return this.timer;
    }

public Team getWinner() {
	Team team = null;
	int highscore = -1;
	for (Team t: teams.values()){
		if (t.getScore() > highscore){
			team = t;
			highscore = t.getScore();
		}
	}
	return team;
}
}

