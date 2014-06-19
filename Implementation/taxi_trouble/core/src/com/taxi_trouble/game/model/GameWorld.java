package com.taxi_trouble.game.model;

import static com.taxi_trouble.game.properties.ResourceManager.getTiledMap;
import static com.taxi_trouble.game.properties.ResourceManager.loadResources;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.taxi_trouble.game.model.entities.Passenger;
import com.taxi_trouble.game.model.entities.Taxi;
import com.taxi_trouble.game.model.entities.powerups.PowerUp;
import com.taxi_trouble.game.model.team.Team;
import com.taxi_trouble.game.multiplayer.AndroidMultiplayerInterface;
import com.taxi_trouble.game.multiplayer.SetupInterface;
import com.taxi_trouble.game.screens.DriverScreen;
import com.taxi_trouble.game.screens.MenuScreen;
import com.taxi_trouble.game.screens.NavigatorScreen;

/**
 * Provides the main model for all the elements of a game that is played.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class GameWorld extends Game {
	private WorldMap map;
	private Team ownTeam;
	private CountDownTimer timer;
	private AndroidMultiplayerInterface multiplayerInterface;
	private DriverScreen driverScreen;
	private NavigatorScreen navigatorScreen;
	private MenuScreen menuScreen;
	private SetupInterface setupInterface;
	private boolean driver; // TODO: rework these kind of attributes!
	private Map<Integer, Team> teams;
	private boolean host = false;

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
		System.out.println("gameworld created!!!");
		collisionDetector.setMultiPlayerInterface(multiplayerInterface);
		map.setMultiPlayerInterface(multiplayerInterface);

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

	@Override
	public final void render() {
		super.render();
		// Spawn a new passenger/power-up if there are less than #taxis-1.
		if (host) {
			Collection<Passenger> passengers = getPassengers();
			if (passengers.size() < teams.size() || passengers.size() == 0) {
				map.getSpawner().spawnPassenger(getWorld());
			}

			Collection<PowerUp> powerups = getPowerUps();
			if (powerups.size() < teams.size() || powerups.size() == 0) {
				map.getSpawner().spawnPowerUp(getWorld());
			}
		}
	}

	public final void startGame() {
		timer.startTimer();
		setScreen();
	}

	public void setDriver(boolean driver) {
		System.out.println("setDriver called, driver = " + driver);
		this.driver = driver;
	}

	/**
	 * Sets the assigned view. The assigned view of a player will be either the
	 * DriverScreen or NavigatorScreen.
	 */
	public void setScreen() {
		System.out.println("setscreen called, driver = " + driver);
		if (driver) {
			setScreen(driverScreen);
		} else {
			setScreen(navigatorScreen);
		}
	}

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
		return this.map.getWorld();
	}

	/**
	 * Retrieves the passengers that are currently in the game.
	 * 
	 * @return passengers
	 */
	public final Collection<Passenger> getPassengers() {
		return this.map.getSpawner().getActivePassengers().values();
	}

	public void setTeams(int teamId, int totalTeams) {
		for (int i = 0; i < totalTeams; i++) {
			Team team = new Team(i, map.getSpawner().spawnTaxi(getWorld(), i));

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

	public PowerUp getPowerUpById(int id) {
		PowerUp result = map.getSpawner().getActivePowerUps().get(id);
		if (result == null) {
			System.out
					.println("DESYNC DETECTED: POWERUP #" + id + " NOT FOUND");
		}
		return result;
	}

	public final Collection<PowerUp> getPowerUps() {
		return this.map.getSpawner().getActivePowerUps().values();
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
}
