package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.properties.GameProperties;

public class VirtualButton {
	protected Rectangle body;
	protected Sprite sprite;
	protected ControlsUI.Action ACTION;
	public boolean ACTIVE;
	protected float scale = GameProperties.scale;

	public VirtualButton(Rectangle body, Sprite sprite, ControlsUI.Action action) {
		this.body = body;
		this.sprite = sprite;
		sprite.setPosition(body.x, body.y);
		sprite.setSize(body.width * scale, body.height * scale);
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
		// System.out.println(inBound(screenX, screenY));
		return inBound(screenX, screenY);
	}
}
