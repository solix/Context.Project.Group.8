package com.taxi_trouble.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taxi_trouble.game.model.GameWorld;
import com.taxi_trouble.game.model.Passenger;
import com.taxi_trouble.game.model.Taxi;
import com.taxi_trouble.game.model.WorldMap;
import com.taxi_trouble.game.model.powerups.PowerUp;
import com.taxi_trouble.game.model.team.Team;
import com.taxi_trouble.game.properties.ResourceManager;
import com.taxi_trouble.game.screens.hud.HeadUpDisplay;
import com.taxi_trouble.game.screens.hud.ScoreHUD;
import com.taxi_trouble.game.screens.hud.TeamHUD;
import com.taxi_trouble.game.screens.hud.TimerHUD;

import static com.taxi_trouble.game.properties.ResourceManager.hudFont;
import static com.taxi_trouble.game.properties.GameProperties.BUTTON_CAM_HEIGHT;
import static com.taxi_trouble.game.properties.GameProperties.BUTTON_CAM_WIDTH;

/**
 * Basic class for extending independent screen of the game.
 * 
 * @author Computer Games Project Group 8
 */
public abstract class ViewObserver implements Screen {
    protected final GameWorld taxigame;
    protected Team team;
    protected Taxi taxi;
    protected WorldMap cityMap;
    protected OrthographicCamera hudCamera;
    protected HeadUpDisplay hud;

    /**
     * Constructor for creating game Screen.
     * 
     * @param taxigame
     */
    public ViewObserver(GameWorld taxiGame) {
        this.taxigame = taxiGame;
        this.team = taxiGame.getTeam();
    }

    /**
     * Called when the screen is set as current screen.
     * 
     */
    @Override
    public void show() {
        this.taxi = taxigame.getTeam().getTaxi();
        this.cityMap = taxigame.getMap();
        this.hudCamera = new OrthographicCamera();
        this.hudCamera.setToOrtho(false, BUTTON_CAM_WIDTH,
                BUTTON_CAM_HEIGHT);
        
        this.initializeHUD();

        // TaxiJukebox.loopMusic("BobMarley", true);
        // TaxiJukebox.playMusic("BobMarley");
        // TaxiJukebox.loopMusic("street", true);
        // TaxiJukebox.playMusic("street");
        // TaxiJukebox.setMusicVolume("BobMarley", 0.8f);
        // TaxiJukebox.setMusicVolume("street", 0.4f);

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
        taxi.update(Gdx.app.getGraphics().getDeltaTime());

        // Progress the physics of the game
        taxigame.getWorld().step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);

        // Render the taxi sprites using the spriteBatch
        taxi.render(getSpriteBatch());

        // Render the passengers into the game
        for (Passenger pass : taxigame.getPassengers()) {
            pass.render(getSpriteBatch());
        }

        // Show the destination for a taxi picking up the corresponding
        // passenger
        if (taxi.pickedUpPassenger()) {
            taxi.getPassenger().getDestination().render(getSpriteBatch());
        }

        for (PowerUp pow : cityMap.getSpawner().getActivePowerUps()) {
            pow.render(getSpriteBatch());
        }

        getSpriteBatch().setProjectionMatrix(hudCamera.combined);
        this.hud.render(getSpriteBatch());
    }

    /**
     * Initializes the HUD that should be displayed on screen together
     * with all components.
     */
    private void initializeHUD() {
        this.hud = new HeadUpDisplay(hudFont, team);
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
