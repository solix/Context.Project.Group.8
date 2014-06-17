package com.taxi_trouble.game.model;

import static com.taxi_trouble.game.properties.GameProperties.getPowerUpBehaviours;
import static com.taxi_trouble.game.properties.ResourceManager.destinationSprite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.taxi_trouble.game.model.entities.Destination;
import com.taxi_trouble.game.model.entities.Entity;
import com.taxi_trouble.game.model.entities.Passenger;
import com.taxi_trouble.game.model.entities.Taxi;
import com.taxi_trouble.game.model.entities.powerups.PowerUp;
import com.taxi_trouble.game.model.entities.powerups.PowerUpBehaviour;
import com.taxi_trouble.game.multiplayer.AndroidMultiplayerInterface;
import com.taxi_trouble.game.properties.ResourceManager;

/**
 * A spawner which can be called for spawning new passengers, taxis and setting
 * destination/deliver points for a taxi.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class Spawner {

	private List<SpawnPoint> passengerspawnpoints;
	private List<SpawnPoint> taxispawnpoints;
	private List<SpawnPoint> destinationpoints;
	private List<SpawnPoint> poweruppoints;
	private ConcurrentHashMap<Integer, Passenger> passengers;
	private ConcurrentHashMap<Integer, PowerUp> powerups;
	private List<PowerUpBehaviour> behaviours;
	private AndroidMultiplayerInterface multiplayerInterface;
	private ConcurrentLinkedQueue<Entity> insertionQueue;
	private ConcurrentLinkedQueue<Entity> deletionQueue;

	/**
	 * Initializes a new Spawner which can store spawn points and spawn taxis,
	 * passengers and create destination points.
	 * 
	 */
	public Spawner() {
		passengerspawnpoints = new ArrayList<SpawnPoint>();
		taxispawnpoints = new ArrayList<SpawnPoint>();
		destinationpoints = new ArrayList<SpawnPoint>();
		passengers = new ConcurrentHashMap<Integer, Passenger>();
		powerups = new ConcurrentHashMap<Integer, PowerUp>();
		poweruppoints = new ArrayList<SpawnPoint>();
		insertionQueue = new ConcurrentLinkedQueue<Entity>();
		deletionQueue = new ConcurrentLinkedQueue<Entity>();
	}

	/**
	 * Defines the available powerup behaviours.
	 * 
	 * @param behaviours
	 */
	public void setAvailablePowerUpBehaviours() {
		this.behaviours = getPowerUpBehaviours();
	}

	/**
	 * Add a new Passenger spawn point.
	 * 
	 * @param spawnPoint
	 *            : position of the spawn point
	 */
	public void addPassengerPoint(SpawnPoint spawnPoint) {
		spawnPoint.getPosition().add((float) (spawnPoint.getWidth() / 2),
				(float) (spawnPoint.getHeight() / 2));
		passengerspawnpoints.add(spawnPoint);
	}

	/**
	 * Add a new Taxi spawn point.
	 * 
	 * @param spawnPoint
	 *            : position of the spawn point
	 */
	public void addTaxiPoint(SpawnPoint spawnPoint) {
		taxispawnpoints.add(spawnPoint);
	}

	/**
	 * Add a new destination point.
	 * 
	 * @param spawnPoint
	 *            : the position of the destination point
	 */
	public void addDestination(SpawnPoint spawnPoint) {
		destinationpoints.add(spawnPoint);
	}

	/**
	 * Add a new power-up spawn point.
	 * 
	 * @param spawnPoint
	 */
	public void addPowerupSpawnPoint(SpawnPoint spawnPoint) {
		poweruppoints.add(spawnPoint);

	}

	/**
	 * Spawn a new passenger into a specified world at a randomly chosen spawn
	 * point.
	 * 
	 * @param world
	 *            : the world into which the passenger should be spawned
	 * @return the spawned passenger
	 */
	public Passenger spawnPassenger(World world) {
		// Pick a random passenger spawn point as location to spawn a passenger
		int random = (int) (Math.abs(Math.random()
				* passengerspawnpoints.size() - 1));
		while (passengerspawnpoints.get(random).isActive()) {
			random = (int) (Math.abs(Math.random()
					* passengerspawnpoints.size() - 1));
		}
		int destinationId = (int) (Math.abs(Math.random()
				* destinationpoints.size() - 1));
		int charId = ResourceManager.getRandomCharacterId();

		int passengerId = getNextId(passengers);

		multiplayerInterface.reliableBroadcast("NEWPASSENGER  " + random + " "
				+ destinationId + " " + charId + " " + passengerId);
		return spawnPassenger(world, random, destinationId, charId, passengerId);
	}

	/**
	 * Spawn a new passenger into a specified world at a chosen spawn point.
	 * 
	 * @param world
	 *            : the world into which the passenger should be spawned
	 * @param spawnId
	 *            : the id of the spawnpoint
	 * @param passengerId
	 *            : the id of the new passenger
	 * @param destinationId
	 *            : the id of the destination of the passenger
	 * @return the spawned passenger
	 */
	public Passenger spawnPassenger(World world, int spawnId,
			int destinationId, int charId, int passengerId) {
		assert (!passengers.containsKey(passengerId));
		SpawnPoint spawnPoint = passengerspawnpoints.get(spawnId);
		Passenger pass = new Passenger(spawnPoint, passengerId);
		insertionQueue.offer(pass);
	    pass.setSprite(ResourceManager.getCharacter(charId));
		pass.setDestination(destination(world, destinationId));
		// Add the new passenger to the list of active passengers
		passengers.put(passengerId, pass);
		return pass;
	}

	/**
	 * Despawn the specified passenger from the map.
	 * 
	 * @param passenger
	 */
	public void despawnPassenger(Passenger passenger) {
		passenger.resetSpawnPoint();
		passengers.remove(passenger.getId());
	}

	/**
	 * Retrieves a random destination from the world.
	 * 
	 * @param world
	 * @return random destination
	 */
	public Destination randomDestination(World world) {
		// Pick a random destination spawn point as location to spawn a
		// passenger
		int random = (int) (Math.abs(Math.random() * destinationpoints.size()
				- 1));
		return destination(world, random);
	}

	/**
	 * Retrieves a specified destination from the world.
	 * 
	 * @param world
	 * @return random destination
	 */
	public Destination destination(World world, int spawnId) {
		// Pick a destination spawn point as location to spawn a
		// passenger
		SpawnPoint spawnPoint = destinationpoints.get(spawnId);
		Destination dest = new Destination(spawnPoint.getWidth(),
				spawnPoint.getHeight());
		dest.initializeBody(world, new Vector2(spawnPoint.getXPosition(),
				spawnPoint.getYPosition()));
		dest.setSprite(destinationSprite);
		return dest;
	}

	/**
	 * Spawn a new taxi into a specified world at a chosen spawn point.
	 * 
	 * @param world
	 *            : the world into which the passenger should be spawned id :
	 *            the place of the spawnpoint in the list of spawnpoints
	 * @return
	 */
	public Taxi spawnTaxi(World world, int pointId) {
		// Pick a taxi spawn point as location to spawn a taxi.
		SpawnPoint spawnPoint = taxispawnpoints.get(pointId);
		spawnPoint.setActive(true);
		// Initialize the new Taxi spawned at the randomly chosen location
		Taxi taxi = new Taxi(2, 4, 20, 60, 60);
		taxi.initializeBody(world, spawnPoint.getPosition(),
				spawnPoint.getAngle());
		return taxi;
	}

	/**
	 * Spawns a PowerUp at a random location on the map.
	 * 
	 * @param world
	 * @return
	 */
	public PowerUp spawnPowerUp(World world) {
		int spawnId = random(poweruppoints.size());

		while (poweruppoints.get(spawnId).isActive()) {
			spawnId = random(poweruppoints.size());
		}

		int behaviourId = random(behaviours.size());
		int powerUpId = getNextId(powerups);
		multiplayerInterface.newPowerUpMessage(spawnId, behaviourId, powerUpId);
		return spawnPowerUp(spawnId, behaviourId, powerUpId, world);
	}

	public PowerUp spawnPowerUp(int spawnId, int behaviourId, int powerUpId,
			World world) {
		SpawnPoint spawnPoint = poweruppoints.get(spawnId);
		spawnPoint.setActive(true);
		PowerUpBehaviour behaviour = behaviours.get(behaviourId);
		PowerUp power = new PowerUp(spawnPoint, powerUpId, multiplayerInterface);
		power.setBehaviour(behaviour);
		insertionQueue.offer(power);
		powerups.put(powerUpId, power);
		return power;
	}

	public PowerUp spawnPowerUp(int behaviourId, World world) {
		PowerUpBehaviour behaviour = behaviours.get(behaviourId);
		PowerUp power = new PowerUp(multiplayerInterface);
		power.setBehaviour(behaviour);
		return power;
	}

	/**
	 * Despawns the powerup from the world.
	 * 
	 * @param powerup
	 * @param world
	 */
	public void despawnPowerup(PowerUp powerup) {
		powerup.resetSpawnpoint();
		scheduleBodyRemoval(powerup);
		powerups.remove(powerup.getId());
	}

	/**Schedules the body of the passed entity for removal.
	 * 
	 * @param entity
	 */
	private void scheduleBodyRemoval(Entity entity) {
	    deletionQueue.offer(entity);
	}

	/**
	 * Retrieves the active (spawned) passengers of the game.
	 * 
	 * @return
	 */
	public ConcurrentHashMap<Integer, Passenger> getActivePassengers() {
		return this.passengers;
	}

	/**
	 * Retrieves the active (spawned) powerups of the game.
	 * 
	 * @return
	 */
	public ConcurrentHashMap<Integer, PowerUp> getActivePowerUps() {
		return this.powerups;
	}

	/**
	 * Retrieves whether the powerUp is available.
	 * 
	 * @param powerUp
	 *            : the respective powerUp
	 * @return boolean indicating whether the powerUp is available
	 */
	public boolean powerUpIsAvailable(PowerUp powerUp) {
		return this.getActivePowerUps().contains(powerUp);
	}

	/**
	 * Retrieves the available passenger spawnpoints.
	 * 
	 * @return
	 */
	public List<SpawnPoint> getPassengerspawnpoints() {
		return passengerspawnpoints;
	}

	/**
	 * Retrieves the available taxi spawnpoints.
	 * 
	 * @return
	 */
	public List<SpawnPoint> getTaxispawnpoints() {
		return taxispawnpoints;
	}

	/**
	 * Retrieves the spawnpoints for destinations.
	 * 
	 * @return
	 */
	public List<SpawnPoint> getDestinationpoints() {
		return destinationpoints;
	}

	/**Finds the next available id for a new passenger to spawn in a hashmap.
	 * 
	 * @param map
	 * @return
	 */
	private <T> int getNextId(ConcurrentHashMap<Integer, T> map) {
		int res = 0;
		while (map.containsKey(res)) {
			res++;
		}
		return res;
	}

	/**
	 * Returns a random number smaller than the size.
	 * 
	 * @param size
	 * @return
	 */
	public int random(int size) {
		int res = (int) Math.abs(Math.random() * size);
		return res;
	}

	public void setMultiplayerInterface(AndroidMultiplayerInterface i) {
		multiplayerInterface = i;
	}

	public ConcurrentLinkedQueue<Entity> getDeletionQueue() {
		return this.deletionQueue;
		
	}

	public ConcurrentLinkedQueue<Entity> getInsertionQueue() {
		return this.insertionQueue;
	}

	/**Removes the scheduled bodies to be removed from the specified world.
	 * 
	 * @param world
	 */
	public void removeDespawnedEntityBodies(World world) {
	    while(!deletionQueue.isEmpty()) {
	        deletionQueue.poll().removeBodyFromWorld(world);
	    }
	}

	public void addEntityBodies(World world){
	    while(!insertionQueue.isEmpty()){
	        insertionQueue.poll().addBodyToWorld(world);
	    }
	}
}
