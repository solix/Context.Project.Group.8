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
import com.taxi_trouble.game.screens.TutorialScreen;

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
    private TutorialScreen tutorialScreen;
	private CountDownTimer timer;
	private CountDownTimer nextGameTimer;

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
		
		
		
		driverScreen = new DriverScreen(this);
		navigatorScreen = new NavigatorScreen(this);
		menuScreen = new MenuScreen(setupInterface);
		setScreen(menuScreen);
        tutorialScreen = new TutorialScreen(setupInterface);
	}

	@Override
	public void resume() {
		loadResources();
	}

	@Override
	public final void render() {
		super.render();
		if (isHost() &&  stillTime()) {
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
		this.resetTimer(300);
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
     * Brings the player to the tutorial-screen.
     */
    public void showTutorial() {
        setScreen(tutorialScreen);
    }

    public void getNextTutorial() {
        tutorialScreen.getNext();
    }
 
    public void getPreviousTutorial() {
        tutorialScreen.getBack();
    }


	/**
	 * Restarts the game by despawning all entities in the gameWorld.
	 * This method is only executed by the host, all despawning will
	 * propegate through the network.
	 */
	public void restartPhase1() {
		this.map.getSpawner().despawnAll();

		this.multiplayerInterface.sendRestartMessage();

		restartPhase2();

	}
	
	/**
	 * Restarts the game further. Resets the teams, screens and timers.
	 * This function will be executed by both host and client, after performing
	 * restartPhase1.
	 */
	public void restartPhase2() {

		for (Team team : this.teams.values()) {
			this.map.getSpawner().despawnTaxi(team.getTaxi());
		}

		this.setTeams(this.ownTeam.getTeamId(), this.teams.size());

		this.resetTimer(300);

		driverScreen = new DriverScreen(this);
		this.setScreen();
		this.timer.startTimer();

	}

	/**
	 * Resets the timers. 
	 * 
	 * @param time: the amount of time (in seconds) that the round should take.
	 */
	public void resetTimer(int time) {
		
		//reset the timer that runs in between two games.
		this.nextGameTimer = new CountDownTimer(20);
		//add event for the host, so that the host will initiate the restart mechanic.
		if (this.isHost()) {
			this.nextGameTimer.setEndCountDownEvent(new Task() {
				@Override
				public void run() {
					restartPhase1();
				}
			});
		}
		
		//reset the actual game timer.
		this.timer = new CountDownTimer(time);
		this.timer.setEndCountDownEvent(new Task() {
			@Override
			public void run() {
				getMultiplayerInterface().submitScore(ownTeam.getScore());
				if (getMultiplayerInterface().isHost()) {
					Team winner = getWinner();
					getMultiplayerInterface().sendEndMessage(winner);
					getActiveScreen().showEndResultsBoard(winner);
					getNextGameTimer().startTimer(); 
				}
			}
		});
	}
	
	/**
	 * Returns the Screen currently in use. Does not support MenuScreen.
	 * @return
	 */
	public ViewObserver getActiveScreen() {
		if (this.isDriver()) {
			return this.driverScreen;
		} else {
			return this.navigatorScreen;
		}
	}

	public CountDownTimer getNextGameTimer() {
		return this.nextGameTimer;
	}

	/**
	 * Returns true if the gametime is > 0.
	 * @return
	 */
	public boolean stillTime() {
		return this.timer.getTimeRemaining()>0;
	}
}