package com.taxi_trouble.game.model;

import static com.taxi_trouble.game.properties.GameProperties.PIXELS_PER_METER;

import java.util.Iterator;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.taxi_trouble.game.model.entities.SolidBox;
import com.taxi_trouble.game.multiplayer.AndroidMultiplayerInterface;

/**
 * The map for the world in which a game can take place and its objects.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class WorldMap {
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private World world;
	private Spawner spawner;

	/**
	 * Initializes a new WorldMap for a specified world using the specified
	 * directory of the map file.
	 * 
	 * @param map
	 *            : the TiledMap to be used for the map
	 * @param world
	 *            : the world for which the map is created
	 */
	public WorldMap(TiledMap map, World world) {
		this.world = world;
		this.map = map;
		this.renderer = new OrthogonalTiledMapRenderer(map);
		this.spawner = new Spawner();
		this.spawner.setAvailablePowerUpBehaviours();
		this.loadMapObjects();
	}

	/**
	 * Loads the objects of this world map in the world.
	 * 
	 */
	private void loadMapObjects() {
		loadMapObjectsOfType("box2D");
		loadMapObjectsOfType("spawn-passenger");
		loadMapObjectsOfType("spawn-taxi");
		loadMapObjectsOfType("destination-point");
		loadMapObjectsOfType("powerups");
	}

	/**
	 * Loads the objects of the world map of a specified type.
	 * 
	 * @param type
	 */
	private void loadMapObjectsOfType(String type) {
		MapLayer layer = map.getLayers().get(type);
		MapObjects objects = layer.getObjects();

		Iterator<MapObject> objIterator = objects.iterator();
		while (objIterator.hasNext()) {
			MapObject obj = objIterator.next();
			if (obj instanceof RectangleMapObject && type.equals("box2D")) {
				Rectangle rect = ((RectangleMapObject) obj).getRectangle();
				createSolidBox(rect);
			} else if (type.equals("spawn-passenger")) {
				createPassengerSpawnPoint((RectangleMapObject) obj);
			} else if (type.equals("spawn-taxi")) {
				createTaxiSpawnPoint(obj);
			} else if (type.equals("destination-point")) {
				createDestination((RectangleMapObject) obj);
			} else if (type.equals("powerups")) {
				createPowerup((RectangleMapObject) obj);
			}
		}
	}

	/**
	 * Creates a solid box in the map's world given a Rectangle.
	 * 
	 * @param r
	 *            : rectangle specifying width, height and position
	 */
	private void createSolidBox(Rectangle r) {
		new SolidBox(world, (r.getWidth()) / PIXELS_PER_METER, (r.getHeight())
				/ PIXELS_PER_METER, new Vector2(r.getX() / PIXELS_PER_METER
				+ r.getWidth() / (2 * PIXELS_PER_METER), r.getY()
				/ PIXELS_PER_METER + r.getHeight() / (2 * PIXELS_PER_METER)));
	}

	/**
	 * Creates a new passenger spawnpoint where a passenger can spawn into the
	 * world.
	 * 
	 * @param obj
	 *            : the mapobject specifying the properties of the spawn point
	 */
	private void createPassengerSpawnPoint(RectangleMapObject obj) {
		SpawnPoint spawn = new SpawnPoint(getXPosition(obj), getYPosition(obj),
				getAngle(obj));
		spawn.setHeight(getHeight(obj));
		spawn.setWidth(getWidth(obj));
		spawner.addPassengerPoint(spawn);
	}

	/**
	 * Creates a new taxi spawnpoint where a taxi can spawn into the world.
	 * 
	 * @param obj
	 *            : the mapobject specifying the properties of the spawn point
	 */
	private void createTaxiSpawnPoint(MapObject obj) {
		SpawnPoint spawn = new SpawnPoint(getXPosition(obj), getYPosition(obj),
				getAngle(obj));
		spawner.addTaxiPoint(spawn);
	}

	/**
	 * Creates a new destination where a passenger can be dropped of into the
	 * world.
	 * 
	 * @param obj
	 *            : the mapobject specifying the properties of the spawn point
	 */
	private void createDestination(RectangleMapObject obj) {
		SpawnPoint spawn = new SpawnPoint(getXPosition(obj), getYPosition(obj),
				getAngle(obj));
		spawn.setHeight(getHeight(obj));
		spawn.setWidth(getWidth(obj));
		spawner.addDestination(spawn);
	}

	/**
	 * Creates a new poweruppoint where a powerup can be spawned into the world
	 * 
	 * @param obj
	 */
	private void createPowerup(RectangleMapObject obj) {
		SpawnPoint spawn = new SpawnPoint(getXPosition(obj), getYPosition(obj),
				getAngle(obj));
		spawn.setHeight(getHeight(obj));
		spawn.setWidth(getWidth(obj));
		spawner.addPowerupSpawnPoint(spawn);

	}

	/**
	 * Returns the x-position of a given MapObject.
	 * 
	 * @param obj
	 *            : the map object for which the x-position should be retrieved
	 * @return x-position of the mapobject
	 */
	private float getXPosition(MapObject obj) {
		return obj.getProperties().get("x", Float.class) / PIXELS_PER_METER;
	}

	/**
	 * Returns the y-position of a given MapObject.
	 * 
	 * @param obj
	 *            : the map object for which the y-position should be retrieved
	 * @return y-position of the mapobject
	 */
	private float getYPosition(MapObject obj) {
		return obj.getProperties().get("y", Float.class) / PIXELS_PER_METER;
	}

	/**
	 * Returns the width of a given MapObject.
	 * 
	 * @param obj
	 *            : the map object for which the width should be retrieved
	 * @return width of the mapobject
	 */
	private float getWidth(RectangleMapObject obj) {
		return obj.getRectangle().getWidth() / PIXELS_PER_METER;
	}

	/**
	 * Returns the height of a given MapObject.
	 * 
	 * @param obj
	 *            : the map object for which the height should be retrieved
	 * @return height of the mapobject
	 */
	private float getHeight(RectangleMapObject obj) {
		return obj.getRectangle().getHeight() / PIXELS_PER_METER;
	}

	/**
	 * Return the (spawning) angle of a given MapObject (default is zero).
	 * 
	 * @param obj
	 *            : the map object for which the angle should be retrieved
	 * @return
	 */
	private float getAngle(MapObject obj) {
		if (obj.getProperties().get("angle") != null) {
			return Float.parseFloat(obj.getProperties().get("angle",
					String.class));
		}
		// Default angle is zero
		return 0;
	}

	/**
	 * Renders the world map.
	 * 
	 * @param camera
	 */
	public void render(OrthographicCamera camera) {
		renderer.setView(camera);
		renderer.render();
	}

	/**
	 * Retrieves the width of the world map in the world.
	 * 
	 * @return
	 */
	public int getWidth() {
		// As TiledMap width is the number of tiles in width, multiply by
		// tilewidth.
		return map.getProperties().get("width", Integer.class) * getTileWidth();
	}

	/**
	 * Retrieves the height of the world map in the world.
	 * 
	 * @return height
	 */
	public int getHeight() {
		// As TiledMap height is the number of tiles in height, multiply by
		// tilewidth.
		return map.getProperties().get("height", Integer.class)
				* getTileWidth();
	}

	/**
	 * Retrieves the tilewidth of the tiles of the map.
	 * 
	 * @return tilewidth
	 */
	private int getTileWidth() {
		return map.getProperties().get("tilewidth", Integer.class);
	}

	/**
	 * Retrieves the Spawner of the WorldMap. The Spawner can be used to spawn
	 * new objects in the world.
	 * 
	 * @return spawner : the spawner of this map
	 */
	public Spawner getSpawner() {
		return this.spawner;
	}

	/**
	 * Retrieves the World of the WorldMap. This is where all objects are placed
	 * of the game.
	 * 
	 * @return world : the world
	 */
	public World getWorld() {
		return this.world;
	}

    public void setMultiPlayerInterface(AndroidMultiplayerInterface i) {
        spawner.setMultiplayerInterface(i);
    }
}
