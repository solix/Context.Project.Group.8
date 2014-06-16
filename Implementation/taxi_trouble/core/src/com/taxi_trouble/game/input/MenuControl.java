package com.taxi_trouble.game.input;

import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.InputProcessor;
import com.taxi_trouble.game.multiplayer.SetupInterface;
import com.taxi_trouble.game.properties.GameProperties;
import com.taxi_trouble.game.ui.UIButton;
import com.taxi_trouble.game.ui.UIElement;

import static com.taxi_trouble.game.properties.GameProperties.UI_HEIGHT;
import static com.taxi_trouble.game.properties.GameProperties.UI_WIDTH;
import static com.taxi_trouble.game.properties.GameProperties.getScreenWidth;
import static com.taxi_trouble.game.properties.GameProperties.getScreenHeight;

public class MenuControl implements InputProcessor {
	private Map<String, UIElement> elements;
	private SetupInterface setupInterface;

	public MenuControl(Map<String, UIElement> elements,
			SetupInterface setupInterface) {
		this.elements = elements;
		this.setupInterface = setupInterface;
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
	public boolean touchDown(int screenX, int screenY, int pointer, int b) {
		screenX = (int) (screenX * ((float) UI_WIDTH / getScreenWidth()));
		screenY = (int) (screenY * ((float) UI_HEIGHT / getScreenHeight()));
		screenY = (int) (UI_HEIGHT - screenY);

		Entry<String, UIElement> entry = getTouchedElement(screenX, screenY);
		if (entry == null) {
			return false;
		} else if (entry.getKey().equals("play")) {
			setupInterface.login();
			((UIButton) entry.getValue()).setActive(true);
		} else if (entry.getKey().equals("board")) {
			System.out.println("board");
			((UIButton) entry.getValue()).setActive(true);
		}

		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		screenX = (int) (screenX * ((float) UI_WIDTH / getScreenWidth()));
		screenY = (int) (screenY * ((float) UI_HEIGHT / getScreenHeight()));
		screenY = (int) (UI_HEIGHT - screenY);

		Entry<String, UIElement> entry = getTouchedElement(screenX, screenY);
		if (entry == null) {
			return false;
		} else if (entry.getKey().equals("play")) {
			//setupInterface.login();
			((UIButton) entry.getValue()).setActive(false);
		} else if (entry.getKey().equals("board")) {
			System.out.println("board");
			((UIButton) entry.getValue()).setActive(false);
		}
		return true;
	}

	public Entry<String, UIElement> getTouchedElement(int screenX, int screenY) {
		for (Entry<String, UIElement> entry : elements.entrySet()) {
			UIElement element = entry.getValue();
			if (element instanceof UIButton) {
				UIButton button = (UIButton) element;
				if (button.inBound(screenX, screenY)) {
					return entry;
				}
			}
		}

		return null;
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
