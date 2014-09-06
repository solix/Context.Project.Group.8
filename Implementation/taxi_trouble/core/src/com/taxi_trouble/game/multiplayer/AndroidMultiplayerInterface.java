package com.taxi_trouble.game.multiplayer;

import com.taxi_trouble.game.model.entities.Passenger;
import com.taxi_trouble.game.model.entities.Taxi;
import com.taxi_trouble.game.model.entities.powerups.PowerUp;
import com.taxi_trouble.game.model.team.Team;

public interface AndroidMultiplayerInterface {

    /**
     * Sends data of the car to all other clients in the room.
     * 
     * @param message
     *            : the message containing the car data.
     */
    public void sendCarLocation(String message);

    /**
     * Sends a reliableMessage to all other clients in the room.
     * 
     * @param message
     *            : the message to send.
     */
    public void reliableBroadcast(String message);

    /**
     * Sets whether the client is the host of the game.
     * 
     * @param host
     *            : boolean indicating whether client is host
     */
    public void setHost(boolean host);

    /**
     * Creates and sends a message with the state of a passenger.
     * 
     * @param taxi
     *            : The taxi that currently has the passenger.
     * @param passenger
     *            : the passenger of which the state is being send.
     */
    public void passengerMessage(Taxi taxi, Passenger passenger);

    /**
     * Retrieves whether this client is the host.
     * 
     * @return true if this client is the host, false otherwise.
     */
    public boolean isHost();

    /**
     * Sets whether the client is a driver. If the client is not a driver it
     * automatically is assigned the role of a navigator.
     * 
     * @param driver
     *            : boolean indicating whether client is a driver
     */
    public void setDriver(boolean driver);

    /**
     * Retrieves whether the client has a driver role.
     *
     * @return true if the client is a driver, false otherwise
     */
    public boolean isDriver();

    /**
     * Generates and broadcasts a message containing data of a newly created
     * powerup.
     * 
     * @param spawnId
     *            : the spawnId of the powerUp.
     * @param behaviourId
     *            : the behaviourId of the powerUp.
     * @param powerUpId
     *            : the ID of the newly created powerUp.
     */
    public void newPowerUpMessage(int spawnId, int behaviourId, int powerUpId);

    /**
     * Creates and sends a message with information about a picked up powerUp.
     * 
     * @param taxi
     *            : The taxi that currently has the powerUp.
     * @param powerUp
     *            : the powerUp of which the information is being send.
     */
    public void powerUpMessage(Taxi taxi, PowerUp powerUp);

    /**
     * Creates and sends a message with information about a activated powerUp.
     * 
     * @param team
     *            : the team that activated the powerUp.
     * @param powerUp
     *            : the powerUp that has been activated.
     */
    void activateMessage(Team team, PowerUp powerUp);

    /**
     * Creates and sends a message that informs other clients that the game has
     * ended and who has won the game.
     * 
     * @param team
     *            : the team that won the game.
     */
    public void sendEndMessage(Team team);

    /**
     * Submits the specified score of the client (to a leaderboard).
     * 
     * @param score
     *            : the score to submit
     */
    public void submitScore(int score);

	public void sendRestartMessage();
}
