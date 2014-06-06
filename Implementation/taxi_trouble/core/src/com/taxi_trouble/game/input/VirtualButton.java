package com.taxi_trouble.game.input;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class VirtualButton {
	protected Rectangle body;
	protected Sprite sprite;
	protected ControlsUI.Action ACTION;
	public boolean ACTIVE;

	public VirtualButton(Rectangle body, Sprite sprite, ControlsUI.Action action) {
		this.body = body;
		this.sprite = sprite;
		sprite.setPosition(body.x, body.y);
		sprite.setSize(body.width, body.height);
		this.ACTION = action;
		// sprite.setOrigin(body.width / 2, body.height / 2);
	}

	public void render(SpriteBatch spriteBatch) {
		spriteBatch.begin();
		sprite.draw(spriteBatch);
		spriteBatch.end();
	}

	public boolean inBound(int screenX, int screenY) {
		return screenX > body.x && screenX < body.x + body.width
				&& screenY > body.y && screenY < body.y + body.height;
	}

	public boolean touchDown(int screenX, int screenY, int mouseButton) {
		return inBound(screenX, screenY);
	}
}
