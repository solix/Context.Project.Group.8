package com.taxi_trouble.game.input;

import static com.taxi_trouble.game.properties.GameProperties.UI_HEIGHT;
import static com.taxi_trouble.game.properties.GameProperties.UI_WIDTH;
import static com.taxi_trouble.game.properties.GameProperties.translateScreenX;
import static com.taxi_trouble.game.properties.GameProperties.translateScreenY;

import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.InputAdapter;
import com.taxi_trouble.game.multiplayer.SetupInterface;
import com.taxi_trouble.game.ui.UIButton;
import com.taxi_trouble.game.ui.UIElement;

/**
 * The InputProcessor for the MenuScreen.
 */
public class MenuControl extends InputAdapter {
	private Map<String, UIElement> elements;
	private SetupInterface setupInterface;

	public MenuControl(Map<String, UIElement> elements,
			SetupInterface setupInterface) {
		this.elements = elements;
		this.setupInterface = setupInterface;
	}

	/**
	 * Handles actions when a UIElement is touched.
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int b) {
		screenX = translateScreenX(screenX, UI_WIDTH);
		screenY = translateScreenY(screenY, UI_HEIGHT);

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

	/**
	 * Resets the UIElement states ones they are not touched anymore.
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		screenX = translateScreenX(screenX, UI_WIDTH);
		screenY = translateScreenY(screenY, UI_HEIGHT);

		Entry<String, UIElement> entry = getTouchedElement(screenX, screenY);
		if (entry == null) {
			return false;
		} else if (entry.getKey().equals("play")) {
			((UIButton) entry.getValue()).setActive(false);
		} else if (entry.getKey().equals("board")) {
			System.out.println("board");
			((UIButton) entry.getValue()).setActive(false);
		}

		return true;
	}

	/**
	 * @return the UIElement that is currently being touched.
	 */
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
}
