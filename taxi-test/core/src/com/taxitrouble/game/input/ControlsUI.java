package com.taxitrouble.game.input;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
<<<<<<< HEAD:taxi-test/core/src/com/mygdx/game/input/ControlsUI.java
import com.mygdx.game.properties.GameProperties;
import com.mygdx.game.properties.SpriteProperties;
=======
import com.taxitrouble.game.Car;
import com.taxitrouble.game.properties.GameProperties;
import com.taxitrouble.game.properties.SpriteProperties;
>>>>>>> sprint2_refactor:taxi-test/core/src/com/taxitrouble/game/input/ControlsUI.java

public class ControlsUI {
	private Map<String, VirtualButton> buttons;
	private Map<Integer, Action> active;

	public enum Action {
		ACCELERATE, BREAK, LEFT, RIGHT, DPAD_DEFAULT, NOT_ACTIVE
	}

	public ControlsUI() {
		this.buttons = new HashMap<String, VirtualButton>();
		this.active = new HashMap<Integer, Action>();

		buttons.put("throttle", new VirtualButton(new Rectangle(
				GameProperties.BUTTON_CAM_WIDTH - 110, 10, 100, 120),
				new Sprite(new Texture(SpriteProperties.throttleSprite)),
				Action.ACCELERATE));

		buttons.put("brake", new VirtualButton(new Rectangle(
				GameProperties.BUTTON_CAM_WIDTH - 210, 10, 100, 120),
				new Sprite(new Texture(SpriteProperties.breakSprite)),
				Action.BREAK));

		buttons.put("dpad", new VirtualDPad(new Rectangle(10, 10, 184, 80),
				new Sprite(new Texture(SpriteProperties.dPadSprite)),
				Action.DPAD_DEFAULT));
	}

	public boolean steering() {
		for (Entry<Integer, Action> e : active.entrySet()) {
			System.out.println("DEBUG3: " + e.getKey() + ", " + e.getValue());
			if (e.getValue() != null) {
				switch (e.getValue()) {
				case LEFT:
				case RIGHT:
					return true;
				default:
					break;
				}
			}
		}
		return false;
	}

	public boolean driving() {
		for (Entry<Integer, Action> e : active.entrySet()) {
			System.out.println("DEBUG3: " + e.getKey() + ", " + e.getValue());
			if (e.getValue() != null) {
				switch (e.getValue()) {
				case BREAK:
				case ACCELERATE:
					return true;
				default:
					break;
				}
			}
		}
		return false;
	}

	public void render(SpriteBatch spriteBatch) {
		for (VirtualButton button : buttons.values()) {
			button.render(spriteBatch);
		}
	}

	public Map<String, VirtualButton> getButtons() {
		return this.buttons;
	}

	public void setActive(int pointer, VirtualButton button) {
		active.put(pointer, button.ACTION);
	}

	public void setInActive(int pointer) {
		active.put(pointer, Action.NOT_ACTIVE);
	}
}
