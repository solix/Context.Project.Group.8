package com.taxi_trouble.game.screens;

import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taxi_trouble.game.model.GameWorld;
import com.taxi_trouble.game.model.Passenger;
import com.taxi_trouble.game.model.Taxi;
import com.taxi_trouble.game.model.Team;
import com.taxi_trouble.game.model.WorldMap;
import com.taxi_trouble.game.properties.GameProperties;
import com.taxi_trouble.game.sound.TaxiJukebox;

/**
 * Basic class for extending independent screen of the game.
 * 
 * @author Computer Games Project Group 8
 */
public abstract class ViewObserver implements Screen {
	protected final GameWorld taxigame;
	protected int screenWidth = GameProperties.screenWidth;
	protected int screenHeight = GameProperties.screenHeight;
	protected static int PIXELS_PER_METER = GameProperties.PIXELS_PER_METER;
	protected WorldMap cityMap;
	private OrthographicCamera scoreCam;
    private final static int THREE = 3;
	protected Taxi ownTaxi;

	/**
	 * Constructor for creating game Screen.
	 * 
	 * @param taxigame
	 */
	public ViewObserver(GameWorld taxigame) {
		this.taxigame = taxigame;
	}

	/**
	 * Called when the screen is set as current screen.
	 * 
	 */
	@Override
	public void show() {
		this.ownTaxi = taxigame.getTeam().getTaxi();
		this.cityMap = taxigame.getMap();
		this.scoreCam = new OrthographicCamera();
		scoreCam.setToOrtho(false, screenWidth, screenHeight);

        //TaxiJukebox.loopMusic("BobMarley", true);
        //TaxiJukebox.playMusic("BobMarley");
        //TaxiJukebox.loopMusic("street", true);
        //TaxiJukebox.playMusic("street");
        //TaxiJukebox.setMusicVolume("BobMarley", 0.8f);
        //TaxiJukebox.setMusicVolume("street", 0.4f);

		// TODO: Also retrieve and render the other taxis in the game.
	}

	/**
	 * Update the world and draw the sprites of the world.
	 * 
	 * @param delta
	 *            delta to be rendered.
	 */
	@Override
	public void render(float delta) {
		// Update the taxi movement
		for (Entry<Integer, Team> e : taxigame.getTeams().entrySet()) {
			e.getValue().getTaxi().update(Gdx.app.getGraphics().getDeltaTime());
		}

		// Progress the physics of the game
		taxigame.getWorld().step(Gdx.app.getGraphics().getDeltaTime(), THREE,
				THREE);
		
		// Render the taxi sprites using the spriteBatch
		for (Entry<Integer, Team> e : taxigame.getTeams().entrySet()) {
			e.getValue().getTaxi().render(getSpriteBatch());
		}

		// Render the passengers into the game
		for (Passenger pass : taxigame.getPassengers().values()) {
			pass.render(getSpriteBatch());
		}
		
		// Show the destination for a taxi picking up the corresponding
		// passenger
		if (ownTaxi.pickedUpPassenger()) {
			ownTaxi.getPassenger().getDestination().render(getSpriteBatch());
		}

		getSpriteBatch().setProjectionMatrix(scoreCam.combined);
		taxigame.getTeam().getScoreBoard().render(getSpriteBatch());
	}

	/**
	 * Retrieve the spriteBatch that should be used.
	 * 
	 * @return spriteBatch
	 */
	public abstract SpriteBatch getSpriteBatch();

}
