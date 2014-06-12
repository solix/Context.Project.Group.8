package com.taxi_trouble.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer.Task;
import com.taxi_trouble.game.model.CountDownTimer;
import com.taxi_trouble.game.model.GameWorld;
import com.taxi_trouble.game.model.Passenger;
import com.taxi_trouble.game.model.Taxi;
import com.taxi_trouble.game.model.WorldMap;
import com.taxi_trouble.game.model.powerups.PowerUp;
import com.taxi_trouble.game.model.team.Team;
import com.taxi_trouble.game.properties.ResourceManager;
import com.taxi_trouble.game.screens.hud.EndGameHUD;
import com.taxi_trouble.game.screens.hud.HUDComponent;
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
    protected TimerHUD dropOffTimerHUD;

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
        taxigame.getTimer().setEndCountDownEvent(new Task() {
            @Override
            public void run() {
                showEndResultsBoard();
            }
        });
    }

    /**Displays the winner of the game at the end.
     * 
     */
    private void showEndResultsBoard() {
        this.hud.removeAll();
        Team winner = taxigame.getTeam();
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
            showDropOffTimer(taxi.getPassenger().getDropOffTimer());
        }
        else {
            hideDropOffTimer();
        }

        for (PowerUp pow : cityMap.getSpawner().getActivePowerUps()) {
            pow.render(getSpriteBatch());
        }

        getSpriteBatch().setProjectionMatrix(hudCamera.combined);
        this.hud.render(getSpriteBatch());
    }

    private void showDropOffTimer(CountDownTimer dropOffTimer) {
        if (!this.hud.contains(dropOffTimerHUD)) {
            dropOffTimerHUD = new TimerHUD("Drop-off time-limit:", 300, 100, 
                dropOffTimer);
            this.hud.add(dropOffTimerHUD);
        }
    }

    private void hideDropOffTimer() {
        this.hud.remove(this.dropOffTimerHUD);
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
