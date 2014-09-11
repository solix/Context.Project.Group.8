package com.taxi_trouble.game.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**The most basic building block of the menu UI.
 * 
 * @author Computer Games Project Group 8
 *
 */
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

	public void setSprite(Sprite sprite) {
	    this.sprite = sprite;
	}

	@Override
	public String toString() {
		return body.x + ", " + body.y + ";" + body.width + "," + body.height
				+ " :: " + sprite.getHeight();
	}
}
