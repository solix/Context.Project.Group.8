package com.taxi_trouble.game.screens;

import static com.taxi_trouble.game.properties.GameProperties.UI_HEIGHT;
import static com.taxi_trouble.game.properties.GameProperties.UI_WIDTH;
import static com.taxi_trouble.game.properties.ResourceManager.getSprite;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.taxi_trouble.game.input.MenuControl;
import com.taxi_trouble.game.multiplayer.SetupInterface;
import com.taxi_trouble.game.properties.ResourceManager;
import com.taxi_trouble.game.ui.UIButton;
import com.taxi_trouble.game.ui.UIElement;

/**
 * Screen for the tutorial of the game.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class TutorialScreen implements Screen {
    private Map<Integer, UIElement> sheets;
    private Map<String, UIElement> elements;
    private SpriteBatch spriteBatch;
    private OrthographicCamera uiCamera;
    private MenuControl menuControl;
    private Sprite[] tutorialSheets;
    private int currSheet;

    public TutorialScreen(SetupInterface setupInterface) {
        elements = new LinkedHashMap<String, UIElement>();
        sheets = new LinkedHashMap<Integer, UIElement>();
        tutorialSheets = ResourceManager.getTutorialSheets();
        currSheet = 0;
        for (int i = 0; i < 9; i++) {
            System.out.println(i);
            sheets.put(i, new UIElement(new Rectangle(0, 0, UI_WIDTH, UI_HEIGHT), getSprite("tut"+i)));
        }
        // TODO: Put back, next and menu button on this screen
        elements.put("next", new UIButton(new Rectangle(1116,
                UI_HEIGHT/2 - 100, 118, 138), getSprite("next"), 
                getSprite("next")));
        elements.put("back", new UIButton(new Rectangle(44,
                UI_HEIGHT/2 - 100, 118, 138), getSprite("back"), 
                getSprite("back")));
        elements.put("backToMenu", new UIButton(new Rectangle(UI_WIDTH / 2 - 225,
                -9, 449, 99), getSprite("tutMenuSprite"), 
                getSprite("tutMenuActiveSprite")));

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

        uiCamera.update();

        spriteBatch.setProjectionMatrix(uiCamera.combined);
        
        sheets.get(currSheet).render(spriteBatch);
        for (Entry<String, UIElement> element : elements.entrySet()) {
            if (!((element.getKey().equals("next") && currSheet >= tutorialSheets.length - 1) ||
                    (element.getKey().equals("back")) && currSheet == 0)) {
                element.getValue().render(spriteBatch);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        spriteBatch = new SpriteBatch();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }

    public void getNext() {
        if (currSheet + 1 < tutorialSheets.length) {
            currSheet++;
        }
    }

    public void getBack() {
        if (currSheet - 1 >= 0) {
            currSheet--;
        }
    }
}
