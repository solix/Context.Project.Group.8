package com.mygdx.game.screens;

import static com.mygdx.game.properties.GameProperties.VIRTUAL_HEIGHT;
import static com.mygdx.game.properties.GameProperties.VIRTUAL_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.input.MapControlsUI;
import com.mygdx.game.properties.ResourceManager;
import com.mygdx.game.world.WorldMap;

/**
 * This is the Navigator view class.
 * 
 * @author Aidan
 * 
 */
public class MapScreen extends BasicScreen {

    private World world;
    private SpriteBatch spriteBatch;
    private WorldMap map;
    private Viewport viewport;
    private OrthographicCamera mapCamera;
    private MapControlsUI mapControl;
    private float SCALE = 4;
    private final static float ZERO_TWO_F = 0.2f;
    private final static int THREE = 3;

    /**
     * This method is only called once at the beginning to setup the main
     * screen.
     */
    @Override
    public void show() {

        this.mapCamera = new OrthographicCamera();
        mapCamera.setToOrtho(false, screenWidth, screenHeight);
        this.viewport = new StretchViewport(VIRTUAL_WIDTH * SCALE,
                VIRTUAL_HEIGHT * SCALE, mapCamera);
        world = new World(new Vector2(0.0f, 0.0f), true);
        map = new WorldMap(ResourceManager.mapFile, world);
        mapControl = new MapControlsUI(mapCamera, this);
        Gdx.input.setInputProcessor(mapControl);
        spriteBatch = new SpriteBatch();
    }

    /**
     * This method is called every cycle to render the objects.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, ZERO_TWO_F, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        stayInBounds(map);
        mapCamera.update();
        spriteBatch.setProjectionMatrix(mapCamera.combined);

        world.step(Gdx.app.getGraphics().getDeltaTime(), THREE, THREE);

        world.clearForces();
        map.render(mapCamera);

        // Draw the sprites
        drawSprites();

    }

    /**
     * This method applies the provided sprites to their objects.
     */
    public void drawSprites() {
        spriteBatch.begin();
        Array<Body> tmpBodies = new Array<Body>();
        world.getBodies(tmpBodies);
        for (Body body : tmpBodies) {
            if (body.getUserData() != null
                    && body.getUserData() instanceof Sprite) {
                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition(body.getPosition().x * PIXELS_PER_METER,
                        body.getPosition().y * PIXELS_PER_METER);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.setScale(PIXELS_PER_METER);
                sprite.draw(spriteBatch);
            }
        }
        spriteBatch.end();
    }

    /**
     * This method is called to resize/update the viewport accordingly.
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();

    }

    /**
     * This method makes it that the camera doesn't go out of bounds from the
     * map.
     * 
     * @param map
     *            Worldmap map is needed so that we can stay in bounds.
     */
    public void stayInBounds(WorldMap map) {
        int mapPixelHeight = map.getHeight();
        int mapPixelWidth = map.getWidth();

        // Check if the camera is near the left border of the map
        if (mapCamera.position.x < VIRTUAL_WIDTH * SCALE / 2) {
            mapCamera.position.x = VIRTUAL_WIDTH * SCALE / 2;
        }
        // Check if the camera is near the right border of the map
        if (mapCamera.position.x >= mapPixelWidth - VIRTUAL_WIDTH * SCALE / 2) {
            mapCamera.position.x = mapPixelWidth - VIRTUAL_WIDTH * SCALE / 2;
        }
        // Check if the camera is near the bottom border of the map
        if (mapCamera.position.y < VIRTUAL_HEIGHT * SCALE / 2) {
            mapCamera.position.y = VIRTUAL_HEIGHT * SCALE / 2;
        }
        // Check if the camera is near the top border of the map
        if (mapCamera.position.y >= mapPixelHeight - VIRTUAL_HEIGHT * SCALE / 2) {
            mapCamera.position.y = mapPixelHeight - VIRTUAL_HEIGHT * SCALE / 2;
        }
    }

    /**
     * This method changes the SCALE value.
     * 
     * @param sc
     *            sc is the new SCALE to be set.
     */
    public void setScale(float sc) {
        SCALE = sc;
    }

    /**
     * This method returns the SCALE value.
     * 
     * @return
     */
    public float getScale() {
        return SCALE;
    }

}
