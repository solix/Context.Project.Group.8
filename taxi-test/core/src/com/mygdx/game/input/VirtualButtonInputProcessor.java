package com.mygdx.game.input;

import java.util.List;

import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.properties.GameProperties;
import com.mygdx.game.ui.VirtualButton;

public class VirtualButtonInputProcessor implements InputProcessor {
	private List<VirtualButton> buttons;

	public VirtualButtonInputProcessor(List<VirtualButton> buttons) {
		this.buttons = buttons;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer,
			int mouseButton) {
		screenY = GameProperties.screenHeight - screenY;
		for (VirtualButton button : buttons) {
			return button.touchDown(screenX, screenY, mouseButton);
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
