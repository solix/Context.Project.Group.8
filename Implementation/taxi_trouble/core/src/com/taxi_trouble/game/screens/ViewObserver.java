package com.taxi_trouble.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taxi_trouble.game.model.GameWorld;
import com.taxi_trouble.game.model.Passenger;
import com.taxi_trouble.game.model.Taxi;
import com.taxi_trouble.game.model.WorldMap;
import com.taxi_trouble.game.properties.GameProperties;
import com.taxi_trouble.game.properties.ScoreBoard;
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
    protected Taxi taxi;
    protected WorldMap cityMap;

    /**
     * Constructor for creating game Screen.
     *
     * @param game
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
        this.taxi = taxigame.getTeam().getTaxi();
        this.cityMap = taxigame.getMap();

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
     */
    @Override
    public void render(float delta) {
        // Update the taxi movement
        taxi.update(Gdx.app.getGraphics().getDeltaTime());

        // Progress the physics of the game
        taxigame.getWorld().step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);

        // Render the taxi sprites using the spriteBatch
        taxi.render(getSpriteBatch());

        // Render the passengers into the game
        for (Passenger pass : taxigame.getPassengers()) {
            pass.render(getSpriteBatch());
        }

        //Show the destination for a taxi picking up the corresponding passenger
        if (taxi.pickedUpPassenger()) {
            taxi.getPassenger().getDestination().render(getSpriteBatch());
        }
    }

    /**
     * Retrieve the spriteBatch that should be used.
     *
     * @return spriteBatch
     */
    public abstract SpriteBatch getSpriteBatch();

}
