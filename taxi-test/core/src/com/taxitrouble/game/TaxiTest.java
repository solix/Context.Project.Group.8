package com.taxitrouble.game;

import com.badlogic.gdx.Game;
import com.taxitrouble.game.screens.GameScreen;
import com.taxitrouble.game.screens.MapScreen;

public class TaxiTest extends Game {
    private GameScreen gameScreen;
	private MapScreen mapScreen;

	@Override
	public void create() {
		gameScreen = new GameScreen();
		mapScreen = new MapScreen();
		setScreen(gameScreen);
	}

	@Override
	public void dispose() {

	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

}
