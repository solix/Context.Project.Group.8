package com.taxi_trouble.game.input;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class VirtualDPad extends VirtualButton {
	private List<VirtualButton> buttons;

	public VirtualDPad(Rectangle body, Sprite sprite, ControlsUI.Action action) {
		super(body, sprite, action);
		this.buttons = new ArrayList<VirtualButton>();
		System.out.println(sprite.getWidth());
		System.out.println(sprite.getHeight());
		TextureRegion[][] splits = sprite.split(
				(int) (sprite.getWidth() / (2 * scale)),
				(int) (sprite.getHeight() / scale));

		buttons.add(new VirtualButton(new Rectangle(this.body.x, this.body.y,
				this.body.width / 2, this.body.height),
				new Sprite(splits[0][0]), ControlsUI.Action.LEFT));

		buttons.add(new VirtualButton(new Rectangle(this.body.x
				+ this.body.width / 2, this.body.y, this.body.width / 2,
				this.body.height), new Sprite(splits[0][1]),
				ControlsUI.Action.RIGHT));
	}

	@Override
	public boolean inBound(int screenX, int screenY) {
		System.out.println("inbound");
		for (VirtualButton button : buttons) {
			if (button.inBound(screenX, screenY)) {
				System.out.println(button.ACTION);
				this.ACTION = button.ACTION;
				return true;
			}
		}
		return false;
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		// System.out.println("render");
		for (VirtualButton button : buttons) {
			button.render(spriteBatch);
		}
	}
}
