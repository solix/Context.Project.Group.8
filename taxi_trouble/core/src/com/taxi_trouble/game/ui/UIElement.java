package com.taxi_trouble.game.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class UIElement {
	protected Rectangle body;
	protected Sprite sprite;

	public UIElement(Rectangle body, Sprite sprite) {
		this.body = body;
		this.sprite = sprite;
		sprite.setPosition(body.x, body.y);
		sprite.setSize(body.width, body.height);
	}

	public void render(SpriteBatch spriteBatch) {
		spriteBatch.begin();
		sprite.draw(spriteBatch);
		spriteBatch.end();
	}

	@Override
	public String toString() {
		return body.x + ", " + body.y + ";" + body.width + "," + body.height
				+ " :: " + sprite.getHeight();
	}
}
