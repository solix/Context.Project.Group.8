package com.taxi_trouble.game.screens;

import static com.taxi_trouble.game.properties.GameProperties.UI_HEIGHT;
import static com.taxi_trouble.game.properties.GameProperties.UI_WIDTH;
import static com.taxi_trouble.game.properties.ResourceManager.menuBgSprite;
import static com.taxi_trouble.game.properties.ResourceManager.menuBoardActiveSprite;
import static com.taxi_trouble.game.properties.ResourceManager.menuBoardSprite;
import static com.taxi_trouble.game.properties.ResourceManager.menuPlayActiveSprite;
import static com.taxi_trouble.game.properties.ResourceManager.menuPlaySprite;
import static com.taxi_trouble.game.properties.ResourceManager.menuTitleSprite;

import java.util.LinkedHashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.taxi_trouble.game.input.MenuControl;
import com.taxi_trouble.game.multiplayer.SetupInterface;
import com.taxi_trouble.game.ui.UIButton;
import com.taxi_trouble.game.ui.UIElement;

public class MenuScreen implements Screen {
	private Map<String, UIElement> elements;
	private SpriteBatch spriteBatch;
	private OrthographicCamera uiCamera;
	private MenuControl menuControl;

	public MenuScreen(SetupInterface setupInterface) {
		elements = new LinkedHashMap<String, UIElement>();
		elements.put("background", new UIElement(new Rectangle(0, 0, UI_WIDTH,
				UI_HEIGHT), menuBgSprite));
		elements.put("play", new UIButton(new Rectangle(UI_WIDTH / 2 - 220,
				320, 441, 91), menuPlaySprite, menuPlayActiveSprite));
		elements.put("title", new UIElement(new Rectangle(UI_WIDTH / 2 - 334,
				UI_HEIGHT - 220, 668, 168), menuTitleSprite));
		elements.put("board", new UIButton(new Rectangle(UI_WIDTH / 2 - 220,
				200, 441, 91), menuBoardSprite, menuBoardActiveSprite));

		menuControl = new MenuControl(elements, setupInterface);
	}

	@Override
	public void show() {
		spriteBatch = new SpriteBatch();
		this.uiCamera = new OrthographicCamera();
		this.uiCamera.setToOrtho(false, UI_WIDTH, UI_HEIGHT);
		uiCamera.update();
		Gdx.input.setInputProcessor(menuControl);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isKeyPressed(Keys.BACK)) {
			System.out.println("Back key pressed");
		}

		uiCamera.update();

		spriteBatch.setProjectionMatrix(uiCamera.combined);

		for (UIElement element : elements.values()) {
			element.render(spriteBatch);
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		spriteBatch = new SpriteBatch();
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
	}
}
