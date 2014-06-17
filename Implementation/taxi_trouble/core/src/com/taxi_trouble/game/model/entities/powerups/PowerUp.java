package com.taxi_trouble.game.model.entities.powerups;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.taxi_trouble.game.model.SpawnPoint;
import com.taxi_trouble.game.model.entities.Entity;
import com.taxi_trouble.game.model.entities.Taxi;
import com.taxi_trouble.game.multiplayer.AndroidMultiplayerInterface;

/**
 * A power-up rewards a team with a special power when picked
 * up by the team's taxi. The effect of the power-up when
 * activated is defined by its power-up behaviour.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class PowerUp extends Entity {
    private SpawnPoint spawnPoint;
    private PowerUpBehaviour behaviour;
    private AndroidMultiplayerInterface networkInterface;
    private boolean taken;

    /**
     * Creates a new PowerUp at the location of a spawnPoint.
     * 
     * @param point
     */
    public PowerUp(SpawnPoint point, int id, AndroidMultiplayerInterface networkInterface) {
        super(id, point.getWidth(), point.getHeight());
        this.spawnPoint = point;
        this.networkInterface = networkInterface;
        this.taken = false;
    }

    public PowerUp(AndroidMultiplayerInterface networkinterface){
        super(0, 0);
        this.taken = true;
    	this.networkInterface = networkinterface;
    }

    /**
     * Initializes the body of the powerup.
     * 
     * @param world
     */
    public void initializeBody(World world) {
        initializeBody(world, spawnPoint.getPosition(), BodyType.DynamicBody, true);
    }

    /**
     * Sets the spawnpoint active to false, meaning it's free again.
     */
    public void resetSpawnpoint() {
        assert (this.spawnPoint != null);
        this.spawnPoint.setActive(false);

    }

    /**
     * Sets the behaviour of the powerup to the given argument.
     * 
     * @param behaviour
     */
    public void setBehaviour(PowerUpBehaviour behaviour) {
        this.behaviour = behaviour;
    }

    /**
     * Returns the behaviour of the powerup.
     * 
     * @return
     */
    public PowerUpBehaviour getBehaviour() {
        return this.behaviour;
    }

    /**Activate the powerup for the passed taxi.
     * 
     * @param taxi
     */
    public void activatePowerUp(Taxi taxi) {
    	networkInterface.activateMessage(taxi.getTeam(), this);
    	forceActivatePowerUp(taxi);
    }
    
    /**
     * Render method for the power-up which calls the render method of the
     * behaviour for drawing the animation of the powerup.
     * 
     * @param spriteBatch
     */
    public void render(SpriteBatch spriteBatch) {
        this.behaviour.render(spriteBatch, getPosition());
    }

    /**Sets whether the power-up is taken.
     * 
     * @param isTaken
     */
	public void setTaken(boolean isTaken) {
		this.taken = isTaken;
		
	}

	/**Retrieves whether the power-up is taken.
	 * 
	 * @return
	 */
	public boolean isTaken(){
		return this.taken;
	}

	/**Forces the activation of the power-up applied to
	 * the specified taxi.
	 *
	 * @param taxi
	 */
	public void forceActivatePowerUp(Taxi taxi) {
		this.behaviour.triggerEvent(taxi);
	}
}
