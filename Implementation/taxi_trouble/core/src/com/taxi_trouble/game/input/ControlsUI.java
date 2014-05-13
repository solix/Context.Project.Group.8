package com.taxi_trouble.game.input;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.taxi_trouble.game.model.Taxi;
import com.taxi_trouble.game.properties.GameProperties;
import com.taxi_trouble.game.properties.SpriteProperties;
import com.taxi_trouble.game.Acceleration;
import com.taxi_trouble.game.SteerDirection;

public class ControlsUI extends InputAdapter {
	private Map<String, VirtualButton> buttons;
	private Map<Integer, Action> active;
	private Taxi taxi;
	private float scale = GameProperties.scale;

	public enum Action {
		ACCELERATE, BRAKE, LEFT, RIGHT, DPAD_DEFAULT, NOT_ACTIVE
	}

	public ControlsUI(Taxi taxi) {
		this.buttons = new HashMap<String, VirtualButton>();
		this.active = new HashMap<Integer, Action>();
		this.taxi = taxi;

		buttons.put("throttle", new VirtualButton(new Rectangle(
				GameProperties.screenWidth - 110 * scale, 10, 100 * scale,
				120 * scale), new Sprite(new Texture(
				SpriteProperties.throttleSprite)), Action.ACCELERATE));
		buttons.put("brake", new VirtualButton(new Rectangle(
				GameProperties.screenWidth - 210 * scale, 10, 100 * scale,
				120 * scale), new Sprite(new Texture(
				SpriteProperties.breakSprite)), Action.BRAKE));

		buttons.put("dpad", new VirtualDPad(new Rectangle(10, 50, 184 * scale,
				80 * scale), new Sprite(
				new Texture(SpriteProperties.dPadSprite)), Action.DPAD_DEFAULT));
	}

	private boolean steering() {
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

	private boolean driving() {
		for (Entry<Integer, Action> e : active.entrySet()) {
			System.out.println("DEBUG3: " + e.getKey() + ", " + e.getValue());
			if (e.getValue() != null) {
				switch (e.getValue()) {
				case BRAKE:
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

	private void showDrivingDebug() {
		System.out.println("D4: S " + steering());
		System.out.println("D4: D" + driving());
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer,
			int mouseButton) {
		screenY = GameProperties.screenHeight - screenY;

		showDrivingDebug();
		for (VirtualButton button : buttons.values()) {
			if (button.touchDown(screenX, screenY, mouseButton)) {
				active.put(pointer, button.ACTION);
				switch (button.ACTION) {
				case ACCELERATE:
					taxi.setAccelerate(Acceleration.ACC_ACCELERATE);
					break;
				case BRAKE:
					taxi.setAccelerate(Acceleration.ACC_BRAKE);
					break;
				default:
				    taxi.setAccelerate(Acceleration.ACC_NONE);
					break;
				}

				switch (button.ACTION) {
				case LEFT:
					System.out.println("Left");
					taxi.setSteer(SteerDirection.STEER_LEFT);
					break;
				case RIGHT:
					System.out.println("Right");
					taxi.setSteer(SteerDirection.STEER_RIGHT);
					break;
				default:
				    taxi.setSteer(SteerDirection.STEER_NONE);
					break;
				}
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		active.put(pointer, Action.NOT_ACTIVE);
		if (steering() && !driving()) {
		    taxi.setAccelerate(Acceleration.ACC_NONE);
			return true;
		}
		if (!steering() && driving()) {
		    taxi.setSteer(SteerDirection.STEER_NONE);
			return true;
		}
		if (!steering() && !driving()) {
		    taxi.setAccelerate(Acceleration.ACC_NONE);
		    taxi.setSteer(SteerDirection.STEER_NONE);
			return true;
		}
		return false;
	}
}
