package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ui.ControlsUI;

public class VirtualButtonScreen extends BasicScreen {
	private SpriteBatch sb;
	private ControlsUI controlsUI;

	public void show() {
		this.sb = new SpriteBatch();
		// // this.controlsUI = new ControlsUI();
		// Gdx.input.setInputProcessor(controlsUI);
	}

	public void render(float delta) {
		// controlsUI.render(sb);
	}
}
