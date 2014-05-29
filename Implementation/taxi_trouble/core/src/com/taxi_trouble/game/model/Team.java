package com.taxi_trouble.game.model;

import com.taxi_trouble.game.properties.ScoreBoard;
import static com.taxi_trouble.game.properties.ResourceManager.scoreFont;

public class Team {
    private Taxi taxi;
    private ScoreBoard scoreBoard;

    public Team(Taxi taxi) {
        this.taxi = taxi;
        this.taxi.setTeam(this);
        this.scoreBoard = new ScoreBoard(scoreFont);
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

    /**Retrieve the team scoreboard.
     *
     * @return scoreboard
     */
    public ScoreBoard getScoreBoard() {
        return this.scoreBoard;
    }
}
