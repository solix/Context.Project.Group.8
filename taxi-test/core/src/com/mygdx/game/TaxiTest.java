package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.StageTestScreen;

public class TaxiTest extends Game {
	private GameScreen gameScreen;
	private StageTestScreen stScreen;

	@Override
	public void create() {
		gameScreen = new GameScreen();
		stScreen = new StageTestScreen();
		setScreen(stScreen);
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
