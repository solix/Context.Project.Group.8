package com.taxi_trouble.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taxi_trouble.game.model.GameWorld;
import com.taxi_trouble.game.model.Passenger;
import com.taxi_trouble.game.model.Taxi;
import com.taxi_trouble.game.model.WorldMap;
import com.taxi_trouble.game.properties.GameProperties;
import com.taxi_trouble.game.properties.ResourceManager;
import com.taxi_trouble.game.properties.ScoreBoard;

/**Basic class for extending independent screen of the game
 * 
 * @author Computer Games Project Group 8
 */
public abstract class ViewObserver implements Screen {
	protected final GameWorld taxigame;
	protected int screenWidth = GameProperties.screenWidth;
	protected int screenHeight = GameProperties.screenHeight;
	protected static int PIXELS_PER_METER = GameProperties.PIXELS_PER_METER;
	protected Taxi taxi;
	protected WorldMap cityMap;
	
	//animation
	float statetime;
	SpriteBatch batch;

	
	//scoreboard
	ScoreBoard score=new ScoreBoard();

	/**
	 * Constructor for creating game Screen
	 * 
	 * @param game
	 */
	public ViewObserver(GameWorld taxigame) {
		this.taxigame = taxigame;
		statetime=0F;
		batch=new SpriteBatch();
	}
	
    /**
     * Called when the screen is set as current screen.
     *
     */
	@Override
	public void show() {
	    this.taxi = taxigame.getTaxi();
	    this.cityMap = taxigame.getMap();
	    //TODO: Also retrieve and render the other taxis in the game.
	    
	}
	
	/**
	 * Update the world and draw the sprites of the world.
	 * 
	 */
	@Override
	public void render(float delta) {    
        //Render the passengers into the game
        for (Passenger passtest : taxigame.getPassengers()) {
            passtest.render(getSpriteBatch());
        }
        //Update the taxi movement
        taxi.update(Gdx.app.getGraphics().getDeltaTime());

        //Progress the physics of the game
        taxigame.getWorld().step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);
        //taxigame.getWorld().clearForces(); //this is the default setting
        
        //Render the taxi sprites using the spriteBatch
        taxi.render(getSpriteBatch());

        score.render();
        
        
        

        
        //animates passenger
        //statetime+=Gdx.graphics.getDeltaTime();
        //ResourceManager.current_passenger_frame=ResourceManager.loading_passenger_animation.getKeyFrame(statetime, true);
        
        //for drawing animation not a right place to draw need refactoring later on size etc 
       // batch.begin();
        //batch.draw(ResourceManager.current_passenger_frame,256,256);
        //batch.end();
        
        
	}
	
	/**Retrieve the spriteBatch that should be used.
	 * 
	 * @return spriteBatch
	 */
	public abstract SpriteBatch getSpriteBatch();

}
