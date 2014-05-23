package com.taxi_trouble.game.model;

import com.taxi_trouble.game.properties.ScoreBoard;

public class Team {
    private Taxi taxi;
    private ScoreBoard scoreBoard;

    public Team(Taxi taxi) {
        this.taxi = taxi;
        this.taxi.setTeam(this);
        this.scoreBoard = new ScoreBoard();
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

    public ScoreBoard getScoreBoard() {
        return this.scoreBoard;
    }
}
