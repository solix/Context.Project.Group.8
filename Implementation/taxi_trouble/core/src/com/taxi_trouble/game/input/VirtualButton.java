package com.taxi_trouble.game.input;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Virtual, on-screen button.
 */
public class VirtualButton {
	protected Rectangle body;
	protected Sprite sprite;
	protected Action ACTION;
	protected boolean ACTIVE;

	/**
	 * Initializes a new VirtualButton.
	 * 
	 * @param body
	 *            : rectangle describing the position and size
	 * @param sprite
	 *            : the sprite to be used
	 * @param action
	 *            : corresponding action
	 */
	public VirtualButton(Rectangle body, Sprite sprite, Action action) {
		this.body = body;
		this.ACTION = action;
		setSprite(sprite);
	}

	/**
	 * Draw the button on screen.
	 * 
	 * @param spriteBatch
	 */
	public void render(SpriteBatch spriteBatch) {
		spriteBatch.begin();
		sprite.draw(spriteBatch);
		spriteBatch.end();
	}

	/**
	 * @param x
	 *            pointer x-coord
	 * @param y
	 *            pointer y-coord
	 * @return true iff the pointer is within the bounds of this button
	 */
	protected boolean inBound(int x, int y) {
		return x > body.x && x < body.x + body.width && y > body.y
				&& y < body.y + body.height;
	}

	public boolean touchDown(int screenX, int screenY, int mouseButton) {
		return inBound(screenX, screenY);
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
		sprite.setPosition(body.x, body.y);
		sprite.setSize(body.width, body.height);
	}
}
