package com.taxi_trouble.game.input;

import static com.taxi_trouble.game.properties.GameProperties.BUTTON_CAM_HEIGHT;
import static com.taxi_trouble.game.properties.GameProperties.BUTTON_CAM_WIDTH;
import static com.taxi_trouble.game.properties.GameProperties.translateScreenX;
import static com.taxi_trouble.game.properties.GameProperties.translateScreenY;

import com.badlogic.gdx.InputAdapter;
import com.taxi_trouble.game.Acceleration;
import com.taxi_trouble.game.SteerDirection;
import com.taxi_trouble.game.model.entities.Taxi;
import com.taxi_trouble.game.properties.ResourceManager;

/**
 * InputProcessor for controlling the taxi using the virtual buttons.
 */
public class DriverControls extends InputAdapter {
	private DriverControlsUI controlsUI;
	private Taxi taxi;

	/**
	 * New DriverControl.
	 * 
	 * @param taxi
	 *            the taxi to be controlled
	 * @param controlsUI
	 *            the virtual buttons that will catch the input
	 */
	public DriverControls(Taxi taxi, DriverControlsUI controlsUI) {
		this.controlsUI = controlsUI;
		this.taxi = taxi;
		ResourceManager.loadFx();
	}

	/**
	 * Handles the input for the buttons.
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer,
			int mouseButton) {
		screenX = translateScreenX(screenX, BUTTON_CAM_WIDTH);
		screenY = translateScreenY(screenY, BUTTON_CAM_HEIGHT);

		for (VirtualButton button : controlsUI.getButtons().values()) {
			if (button.touchDown(screenX, screenY, mouseButton)) {
				controlsUI.setActive(pointer, button);
				switch (button.ACTION) {
				case ACCELERATE:
					taxi.setAccelerate(Acceleration.ACC_ACCELERATE);
					break;
				case BRAKE:
					taxi.setAccelerate(Acceleration.ACC_BRAKE);
					break;
				default:
					break;
				}

				switch (button.ACTION) {
				case LEFT:
					taxi.setSteer(SteerDirection.STEER_LEFT);
					break;
				case RIGHT:
					taxi.setSteer(SteerDirection.STEER_RIGHT);
					break;
				default:
					break;
				}
			}
		}
		return false;
	}

	/**
	 * Allows for sliding over the dpad.
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return touchDown(screenX, screenY, pointer, 1);
	}

	/**
	 * When player releases a button, take the appropriate action.
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		controlsUI.setInactive(pointer);
		if (controlsUI.steering() && !controlsUI.driving()) {
			taxi.setAccelerate(Acceleration.ACC_NONE);
			return true;
		}
		if (!controlsUI.steering() && controlsUI.driving()) {
			taxi.setSteer(SteerDirection.STEER_NONE);
			return true;
		}
		if (!controlsUI.steering() && !controlsUI.driving()) {
			taxi.setAccelerate(Acceleration.ACC_NONE);
			taxi.setSteer(SteerDirection.STEER_NONE);
			return true;
		}
		return false;
	}
}
