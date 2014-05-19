package com.taxi_trouble.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taxi_trouble.game.model.GameWorld;
import com.taxi_trouble.game.model.Passenger;
import com.taxi_trouble.game.properties.GameProperties;

/**
 * 
 * @author 5oheil Basic class for extending independent screen of the game
 */
public abstract class ViewObserver implements Screen {
	final GameWorld taxigame;
	protected int screenWidth = GameProperties.screenWidth;
	protected int screenHeight = GameProperties.screenHeight;
	protected static int PIXELS_PER_METER = GameProperties.PIXELS_PER_METER;

	/**
	 * Constructor for creating game Screen
	 * 
	 * @param game
	 */
	public ViewObserver(GameWorld taxigame) {
		this.taxigame = taxigame;
	}
	
	@Override
	public void render(float delta) {
        for (Passenger passtest : taxigame.getPassengers()) {
            passtest.render(getSpriteBatch());
        }
	}
	
	public abstract SpriteBatch getSpriteBatch();

}
