package com.taxi_trouble.game.screens;

import static com.taxi_trouble.game.properties.GameProperties.BUTTON_CAM_HEIGHT;
import static com.taxi_trouble.game.properties.GameProperties.BUTTON_CAM_WIDTH;
import static com.taxi_trouble.game.properties.ResourceManager.getHudFont;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer.Task;
import com.taxi_trouble.game.model.CountDownTimer;
import com.taxi_trouble.game.model.GameWorld;
import com.taxi_trouble.game.model.WorldMap;
import com.taxi_trouble.game.model.entities.Passenger;
import com.taxi_trouble.game.model.entities.Taxi;
import com.taxi_trouble.game.model.entities.powerups.PowerUp;
import com.taxi_trouble.game.model.team.Team;
import com.taxi_trouble.game.screens.hud.EndGameHUD;
import com.taxi_trouble.game.screens.hud.HUDComponent;
import com.taxi_trouble.game.screens.hud.HeadUpDisplay;
import com.taxi_trouble.game.screens.hud.ScoreHUD;
import com.taxi_trouble.game.screens.hud.TeamHUD;
import com.taxi_trouble.game.screens.hud.TimerHUD;

/**
 * Basic class for extending independent screen of the game.
 * 
 * @author Computer Games Project Group 8
 */
public abstract class ViewObserver implements Screen {
	protected final GameWorld taxigame;
	protected Team team;
	protected Taxi ownTaxi;
	protected WorldMap cityMap;
	protected OrthographicCamera hudCamera;
	protected HeadUpDisplay hud;
	protected TimerHUD dropOffTimerHUD;

	/**
	 * Constructor for creating game Screen.
	 * 
	 * @param taxigame
	 */
	public ViewObserver(GameWorld taxiGame) {
		this.taxigame = taxiGame;
	}

	/**
	 * Called when the screen is set as current screen.
	 * 
	 */
	@Override
	public void show() {
		this.team = taxigame.getTeam();
		this.ownTaxi = taxigame.getTeam().getTaxi();
		this.cityMap = taxigame.getMap();
		this.hudCamera = new OrthographicCamera();
		this.hudCamera.setToOrtho(false, BUTTON_CAM_WIDTH, BUTTON_CAM_HEIGHT);
		this.initializeHUD();
		taxigame.getTimer().setEndCountDownEvent(new Task() {
			@Override
			public void run() {
				taxigame.getMultiplayerInterface().submitScore(team.getScore());
				if (taxigame.getMultiplayerInterface().isHost()) {
					Team winner = taxigame.getWinner();
					taxigame.getMultiplayerInterface().sendEndMessage(winner);
					showEndResultsBoard(winner);
					taxigame.setActive(false);
				}
			}
		});
	}

	/**
	 * Displays the winner of the game at the end.
	 * 
	 */
	public void showEndResultsBoard(Team winner) {
		this.hud.removeAll();
		HUDComponent endGameHud = new EndGameHUD(winner, 340, 300);
		this.hud.add(endGameHud);
	}

	/**
	 * Update the world and draw the sprites of the world.
	 * 
	 * @param delta
	 *            delta to be rendered.
	 */
	@Override
	public void render(float delta) {
		if (!taxigame.isActive()){
			taxigame.reset();
			return;
		}
		// Initializes new and removes old entity bodies into/from the world
		taxigame.getSpawner().updateEntityBodies(taxigame.getWorld());

		// Progress the physics of the game
		taxigame.getWorld().step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);

		// Update the taxi movement for all taxis and render their sprites
		for (Team team : taxigame.getTeams().values()) {
			Taxi taxi = team.getTaxi();
			taxi.update(Gdx.app.getGraphics().getDeltaTime());
			taxi.render(getSpriteBatch());
		}

		// Render the passengers into the game
		for (Passenger pass : taxigame.getPassengers()) {
			pass.render(getSpriteBatch());
		}

		// Show the destination for a taxi picking up the corresponding
		// passenger
		if (ownTaxi.pickedUpPassenger()) {
			ownTaxi.getPassenger().getDestination().render(getSpriteBatch());
			showDropOffTimer(ownTaxi.getPassenger().getDropOffTimer());
		} else {
			hideDropOffTimer();
		}

		// Render the power-ups into the game
		for (PowerUp pow : taxigame.getPowerUps()) {
			if (!pow.isTaken()) {
				pow.render(getSpriteBatch());
			}
		}
		getSpriteBatch().setProjectionMatrix(hudCamera.combined);
		this.hud.render(getSpriteBatch());
	}

	private void showDropOffTimer(CountDownTimer dropOffTimer) {
		if (!this.hud.contains(dropOffTimerHUD)) {
			dropOffTimerHUD = new TimerHUD("Drop-off time-limit:", 250, 100,
					dropOffTimer);
			this.hud.add(dropOffTimerHUD);
		}
	}

	private void hideDropOffTimer() {
		this.hud.remove(this.dropOffTimerHUD);
	}

	/**
	 * Initializes the HUD that should be displayed on screen together with all
	 * components.
	 */
	private void initializeHUD() {
		this.hud = new HeadUpDisplay(getHudFont(), team);
		this.hud.add(new TeamHUD("Team", 10, 470));
		this.hud.add(new ScoreHUD("Score:", 10, 440));
		this.hud.add(new TimerHUD("Time:", 680, 470, taxigame.getTimer()));
	}

	/**
	 * Retrieve the spriteBatch that should be used.
	 * 
	 * @return spriteBatch
	 */
	public abstract SpriteBatch getSpriteBatch();

}
