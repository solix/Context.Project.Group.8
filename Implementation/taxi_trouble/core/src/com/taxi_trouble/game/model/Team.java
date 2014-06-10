package com.taxi_trouble.game.model;

import static com.taxi_trouble.game.properties.ResourceManager.scoreFont;
import static com.taxi_trouble.game.properties.ResourceManager.noPowerUpButtonSprite;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Team {
    private Taxi taxi;
    private PowerUp powerUp;
    private ScoreBoard scoreBoard;
	private int teamId;

    public Team(Taxi taxi) {
        this.taxi = taxi;
        this.taxi.setTeam(this);
        this.powerUp = null;
        setScoreBoard(new ScoreBoard(scoreFont));
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
        scoreBoard.incrScore();
    }

    /**
     * Retrieve the score of the team.
     *
     */
    public int getScore() {
        return scoreBoard.getScore();
    }

    /**
     * Set the score of the team to the given score.
     *
     * @param score
     */
    public void setScore(int score) {
        scoreBoard.setScore(score);
    }

    /**Retrieve the team scoreboard.
     *
     * @return scoreboard
     */
    public ScoreBoard getScoreBoard() {
        return this.scoreBoard;
    }

    /**Changes the scoreboard of the team to the given scoreboard.
     *
     * @param scoreBoard
     */
    public void setScoreBoard(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }
    
    /**Sets the powerUp of the team (when the taxi picks one up).
     * 
     * @param powerUp : the new PowerUp
     */
    public void setPowerUp(PowerUp powerUp) {
        this.powerUp = powerUp;
    }
    
    public PowerUp getPowerUp(){
    	return this.powerUp;
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
    
    public void forcePowerUpUse(){
    	powerUp.forceActivatePowerUp(taxi);
        this.powerUp = null;
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
	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public String toString() {
		return "<Team teamId=" + teamId + ">";
	}
}
