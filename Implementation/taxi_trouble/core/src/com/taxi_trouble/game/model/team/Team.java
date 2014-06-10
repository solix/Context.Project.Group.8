package com.taxi_trouble.game.model.team;

import static com.taxi_trouble.game.properties.ResourceManager.noPowerUpButtonSprite;
import static com.taxi_trouble.game.properties.ResourceManager.wheelSprite;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.taxi_trouble.game.model.Taxi;
import com.taxi_trouble.game.model.powerups.PowerUp;

/**The team class defines a team to which a driver and navigator can belong.
 * Each team has a team id with theme, score, taxi and a power-up.
 * 
 * @author Computer Games Project Group 8
 *
 */
public class Team {
    private int teamId;
    private TeamTheme teamTheme;
    private Taxi taxi;
    private int score;
    private PowerUp powerUp;

    /**Initializes a new Team with given id and corresponding
     * to the specified taxi.
     * 
     * @param teamId
     * @param taxi
     */
    public Team(int teamId, Taxi taxi) {
        this.taxi = taxi;
        this.taxi.setTeam(this);
        this.teamId = teamId;
        this.powerUp = null;
        this.score = 0;
        setTeamTheme();
    }

    /**Sets the theme of the team based on the id.
     * 
     */
    private void setTeamTheme() {
        switch (teamId) {
            case 0: this.teamTheme = new YellowTeamTheme(); break;
            case 1: this.teamTheme = new BlueTeamTheme(); break;
            case 2: this.teamTheme = new GreenTeamTheme(); break;
            default: this.teamTheme = new RedTeamTheme(); break;
        }
        taxi.setSprite(teamTheme.getTaxiSprite(), wheelSprite);
    }

    /**Retrieve the theme of this team.
     * 
     * @return theme
     */
    public TeamTheme getTeamTheme() {
        return this.teamTheme;
    }

    /**Retrieves the team id.
     * 
     * @return team id
     */
    public int getTeamId() {
        return this.teamId;
    }

    /**
     * Retrieves the taxi of the team.
     *
     * @return taxi
     */
    public Taxi getTaxi() {
        return taxi;
    }

    /**
     * Increment the team score.
     *
     */
    public void incScore() {
        this.score++;
    }

    /**
     * Adds a specified amount to the score.
     */
    public void addScore(int add) {
        this.score += add;
    }

    /**
     * Retrieve the score of the team.
     *
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Set the score of the team to the given score.
     *
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }
    
    /**Sets the powerUp of the team (when the taxi picks one up).
     * 
     * @param powerUp : the new PowerUp
     */
    public void setPowerUp(PowerUp powerUp) {
        this.powerUp = powerUp;
    }

    /**Retrieves whether the team picked up a powerUp.
     *
     */
    public boolean hasPowerUp() {
        return this.powerUp != null;
    }

    /**Activates the powerUp of the team, if the team has a powerUp.
     * 
     */
    public void usePowerUp() {
        if(hasPowerUp()) {
            powerUp.activatePowerUp(taxi);
            this.powerUp = null;
        }
    }
    
    /**Retrieves the sprite of the button for the teams current powerup
     * 
     */
    public Sprite getPowerUpButtonSprite() {
        if(hasPowerUp()) {
            return this.powerUp.getBehaviour().getActivationButtonSprite();
        }
        return noPowerUpButtonSprite;
    }
}