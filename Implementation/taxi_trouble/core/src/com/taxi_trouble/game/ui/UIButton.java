package com.taxi_trouble.game.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**A button in the menu user interface.
 * 
 * @author Computer Games Project Group 8
 *
 */
public class UIButton extends UIElement {
	private Sprite activeSprite;
	private boolean active;

	public UIButton(Rectangle body, Sprite sprite, Sprite activeSprite) {
		super(body, sprite);
		this.activeSprite = activeSprite;
		activeSprite.setX(body.x);
		activeSprite.setY(body.y);
		this.active = false;
	}

	@Override
	public void render(SpriteBatch sb) {
		if (active) {
			sb.begin();
			activeSprite.draw(sb);
			sb.end();
		} else {
			super.render(sb);
		}
	}

	public boolean inBound(int screenX, int screenY) {
		return screenX > body.x && screenX < body.x + body.width
				&& screenY > body.y && screenY < body.y + body.height;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
