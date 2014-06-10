package com.taxi_trouble.game.input;

import com.badlogic.gdx.InputProcessor;
import com.taxi_trouble.game.Acceleration;
import com.taxi_trouble.game.SteerDirection;
import com.taxi_trouble.game.model.Taxi;
import com.taxi_trouble.game.properties.GameProperties;

/**
 * InputProcessor for controlling the taxi using the virtual buttons.
 */
public class DriverControl implements InputProcessor {
	private ControlsUI controlsUI;
	private Taxi taxi;

	/**
	 * New DriverControl.
	 * 
	 * @param taxi
	 *            the taxi to be controlled
	 * @param controlsUI
	 *            the virtual buttons that will catch the input
	 */
	public DriverControl(Taxi taxi, ControlsUI controlsUI) {
		this.controlsUI = controlsUI;
		this.taxi = taxi;
	}

	/**
	 * Keys are not used on mobile...
	 */
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	/**
	 * Keys are not used on mobile...
	 */
	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Keys are not used on mobile...
	 */
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


    /**
     * Handles the input for the buttons.
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer,
            int mouseButton) {

		screenX = (int) (screenX * ((float) GameProperties.CONTROLS_WIDTH / GameProperties.screenWidth));
		screenY = (int) (screenY * ((float) GameProperties.CONTROLS_HEIGHT / GameProperties.screenHeight));
		screenY = (int) (GameProperties.CONTROLS_HEIGHT - screenY);

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
		controlsUI.setInActive(pointer);
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

	/**
	 * No mouse on mobile...
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * No mouse on mobile...
	 */
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
