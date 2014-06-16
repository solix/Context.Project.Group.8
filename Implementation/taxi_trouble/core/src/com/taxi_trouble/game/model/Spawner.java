package com.taxi_trouble.game.model;

import static com.taxi_trouble.game.properties.GameProperties.getPowerUpBehaviours;
import static com.taxi_trouble.game.properties.ResourceManager.destinationSprite;
import static com.taxi_trouble.game.properties.ResourceManager.getRandomCharacter;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.taxi_trouble.game.Character;
import com.taxi_trouble.game.model.powerups.PowerUp;
import com.taxi_trouble.game.model.powerups.PowerUpBehaviour;
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
    private List<Passenger> passengers;
    private List<PowerUp> powerups;
    private List<PowerUpBehaviour> behaviours;

    /**
     * Initializes a new Spawner which can store spawn points and spawn taxis,
     * passengers and create destination points.
     * 
     */
    public Spawner() {
        passengerspawnpoints = new ArrayList<SpawnPoint>();
        taxispawnpoints = new ArrayList<SpawnPoint>();
        destinationpoints = new ArrayList<SpawnPoint>();
        passengers = new ArrayList<Passenger>();
        powerups = new ArrayList<PowerUp>();
        poweruppoints = new ArrayList<SpawnPoint>();
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

    public void addPowerup(SpawnPoint spawnPoint) {
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
        int random = random(passengerspawnpoints.size());
        while (passengerspawnpoints.get(random).isActive()) {
            random = random(passengerspawnpoints.size());
        }
        SpawnPoint spawnPoint = passengerspawnpoints.get(random);
        spawnPoint.setActive(true);
        // Assign a random character to the passenger
        Character character = getRandomCharacter();
        Passenger pass = new Passenger(2, 2, character);
        pass.initializeBody(world, spawnPoint);
        Destination dest = randomDestination(world);
        // If the distance is to close, choose another destination.
        while (pass.getPosition().dst(dest.getPosition()) < 50) {
            dest = randomDestination(world);
        }
        pass.setDestination(dest);
        // Add the new passenger to the list of active passengers
        passengers.add(pass);
        return pass;
    }

    /**
     * Despawn the specified passenger from the map.
     * 
     * @param passenger
     */
    public void despawnPassenger(Passenger passenger) {
        passenger.resetSpawnPoint();
        passengers.remove(passenger);
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
        int random = random(destinationpoints.size());
        SpawnPoint spawnPoint = destinationpoints.get(random);
        Destination dest = new Destination(spawnPoint.getWidth(),
                spawnPoint.getHeight());
        dest.initializeBody(world, new Vector2(spawnPoint.getXPosition(),
                spawnPoint.getYPosition()));
        dest.setSprite(destinationSprite);
        return dest;
    }

    /**
     * Spawn a new taxi into a specified world at a randomly chosen spawn point.
     * 
     * @param world
     *            : the world into which the passenger should be spawned
     * @return
     */
    public Taxi spawnTaxi(World world) {
        // Pick a random taxi spawn point as location to spawn a taxi.
        int random = random(taxispawnpoints.size());
        while (taxispawnpoints.get(random).isActive()) {
            random = random(taxispawnpoints.size());
        }
        SpawnPoint spawnPoint = taxispawnpoints.get(random);
        spawnPoint.setActive(true);
        // Initialize the new Taxi spawned at the randomly chosen location
        Taxi taxi = new Taxi(2, 4, 20, 60, 60);
        taxi.initializeBody(world, spawnPoint.getPosition(),
                spawnPoint.getAngle());
        taxi.setSprite(ResourceManager.yellowTaxiSprite,
                ResourceManager.wheelSprite);
        return taxi;
    }

    /**
     * Spawns a PowerUp at a random location on the map.
     * 
     * @param world
     * @return
     */
    public PowerUp spawnPowerUp(World world) {
        int random = random(poweruppoints.size());

        while (poweruppoints.get(random).isActive()) {
            System.out.println("Im already in use");
            random = random(poweruppoints.size());
        }
        SpawnPoint spawnPoint = poweruppoints.get(random);
        spawnPoint.setActive(true);

        // Get a random powerup
        PowerUp power = getRandomPowerUp(spawnPoint, world);
        powerups.add(power);
        System.out.println("Position of powerup: "
                + power.getPosition().toString());
        System.out.println("Type: " + power.getBehaviour().toString());
        return power;
    }

    /**
     * Generates a random powerup.
     * 
     * @param point
     * @param world
     * @return
     */
    public PowerUp getRandomPowerUp(SpawnPoint spawnPoint, World world) {
        int random = random(behaviours.size());
        PowerUpBehaviour behaviour = behaviours.get(random);
        PowerUp res = new PowerUp(spawnPoint);
        res.setBehaviour(behaviour);
        res.initializeBody(world);
        return res;
    }

    /**
     * Despawns the powerup from the world.
     * 
     * @param powerup
     * @param world
     */
    public void despawnPowerup(PowerUp powerup) {
        powerup.resetSpawnpoint();
        powerups.remove(powerup);
    }

    /**
     * Retrieves the active (spawned) passengers of the game.
     * 
     * @return
     */
    public List<Passenger> getActivePassengers() {
        return this.passengers;
    }

    /**
     * Retrieves the active (spawned) powerups of the game.
     * 
     * @return
     */
    public List<PowerUp> getActivePowerUps() {
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
}
