package com.mygdx.game.input;

import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.Car;
import com.mygdx.game.properties.GameProperties;

/**
 * InputProcessor for controlling the taxi using the virtual buttons.
 */
public class DriverControl implements InputProcessor {
	private ControlsUI controlsUI;
	private Car car;

	/**
	 * New DriverControl.
	 * 
	 * @param car
	 *            the car to be controlled
	 * @param controlsUI
	 *            the virtual buttons that will catch the input
	 */
	public DriverControl(Car car, ControlsUI controlsUI) {
		this.controlsUI = controlsUI;
		this.car = car;
	}

	/**
	 * Keys are not used on mobile...
	 */
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
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
		System.out.println("x: " + screenX);
		screenX = (int) (screenX * ((float) GameProperties.BUTTON_CAM_WIDTH / GameProperties.screenWidth));
		screenY = (int) (screenY * ((float) GameProperties.BUTTON_CAM_HEIGHT / GameProperties.screenHeight));
		screenY = (int) (GameProperties.BUTTON_CAM_HEIGHT - screenY);
		System.out.println("x: " + screenX + ", " + "y: " + screenY);

		for (VirtualButton button : controlsUI.getButtons().values()) {
			if (button.touchDown(screenX, screenY, mouseButton)) {
				controlsUI.setActive(pointer, button);
				switch (button.ACTION) {
				case ACCELERATE:
					car.accelerate = Car.ACC_ACCELERATE;
					break;
				case BREAK:
					car.accelerate = Car.ACC_BRAKE;
					break;
				default:
					break;
				}

				switch (button.ACTION) {
				case LEFT:
					System.out.println("Left");
					car.steer = Car.STEER_LEFT;
					break;
				case RIGHT:
					System.out.println("Right");
					car.steer = Car.STEER_RIGHT;
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
			car.accelerate = Car.ACC_NONE;
			return true;
		}
		if (!controlsUI.steering() && controlsUI.driving()) {
			car.steer = Car.STEER_NONE;
			return true;
		}
		if (!controlsUI.steering() && !controlsUI.driving()) {
			car.accelerate = Car.ACC_NONE;
			car.steer = Car.STEER_NONE;
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
