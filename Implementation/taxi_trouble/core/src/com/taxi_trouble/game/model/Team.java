package com.taxi_trouble.game.model;

import static com.taxi_trouble.game.properties.ResourceManager.scoreFont;

public class Team {
	private Taxi taxi;
	private ScoreBoard scoreBoard;
	private int teamId;

    public Team(Taxi taxi) {
        this.taxi = taxi;
        this.taxi.setTeam(this);
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
