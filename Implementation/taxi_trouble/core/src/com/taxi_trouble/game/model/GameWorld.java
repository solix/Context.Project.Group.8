package com.taxi_trouble.game.model;

import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.taxi_trouble.game.gdxGooglePlay.GooglePlayInterface;
import com.taxi_trouble.game.properties.ResourceManager;
import com.taxi_trouble.game.screens.DriverScreen;
import com.taxi_trouble.game.screens.NavigatorScreen;
import com.taxi_trouble.game.sound.TaxiJukebox;

/**
 * Provides the main model for all the elements of a game that is played.
 * (Please note: implementation will change when implementing multiplayer)
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class GameWorld extends Game {
    private World world;
    private WorldMap map;
    // private List<Team> teams;
    // Temporary: single team (may change when implementing multiplayer)
    private Team team;
    private GooglePlayInterface platformInterface;
    private DriverScreen driverScreen;
    private NavigatorScreen navigatorScreen;
    final static int THREE = 3;
    

    // private List<Passenger> passengers;
    // private ScoreBoard score;
    
    
    
   public GameWorld(GooglePlayInterface aInterface) {
		platformInterface = aInterface;
		platformInterface.Login();
	}

    /**
     * Called when the game world is first created.
     * 
     */
    @Override
    public final void create() {
        loadResources();
        world = new World(new Vector2(0.0f, 0.0f), true);
        map = new WorldMap(ResourceManager.mapFile, world);
        team = new Team(map.getSpawner().spawnTaxi(world));
        world.setContactListener(new CollisionDetector(map));
        System.out.println("gameworld created!!!");
        driverScreen = new DriverScreen(this);
        navigatorScreen = new NavigatorScreen(this);
        setScreen(new DriverScreen(this)); // this fixes invisible car and invisible controls. I do not know why?
        
    }
    
    @Override
    public void resume() {
    	loadResources();
    }

    /**
     * Loads the game resources.
     * 
     */
    public void loadResources() {
        TaxiJukebox.createMusicInGame("sound/bobmar.mp3", "BobMarley");
        TaxiJukebox.createMusicInGame("sound/street.mp3", "street");
        ResourceManager.loadMap();
        ResourceManager.loadSprites();
        ResourceManager.loadFonts();
    }

    @Override
    public final void render() {
        super.render();
        if(getScreen() instanceof DriverScreen){
        sendLocation(getTeam().getTaxi().getXPosition(), getTeam().getTaxi().getYPosition(), getTeam().getTaxi().getBody().getAngle());
        }
        // Spawn a new passenger if there are less than #taxis-1.
        // TODO: Instead of '3' adapt to #taxis-1 in the game.
        List<Passenger> passengers = map.getSpawner().getActivePassengers();
        if (passengers.size() < THREE) {
            map.getSpawner().spawnPassenger(world);
        }
    }
    
	public void setScreen(boolean driver) {
		if (driver) {
			setScreen(driverScreen);
		} else {
			setScreen(navigatorScreen);
		}
	}

    /**
     * Retrieves the game world map.
     * 
     * @return map
     */
    public final WorldMap getMap() {
        return this.map;
    }

    /**
     * Retrieves the single team.
     * 
     * @return team
     */
    public final Team getTeam() {
        return this.team;
    }

    /**
     * Retrieves the world in which the game is played.
     * 
     * @return world
     */
    public final World getWorld() {
        return this.world;
    }

    /**
     * Retrieves the passengers that are currently in the game.
     * 
     * @return passengers
     */
    public final List<Passenger> getPassengers() {
        return this.map.getSpawner().getActivePassengers();
    }
    
    public void setTaxiLocation(float id, float x, float y, float a) {
    	//System.out.println("received taxi location!!");
		getTeam().getTaxi().getBody().setTransform(x, y, a);
	}

	public void sendLocation(float f, float g, float a) {
		platformInterface.sendLocation(f, g, a);
	}
}
