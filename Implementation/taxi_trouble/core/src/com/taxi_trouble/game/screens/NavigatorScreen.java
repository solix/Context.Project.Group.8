package com.taxi_trouble.game.screens;

import static com.taxi_trouble.game.properties.GameProperties.VIRTUAL_HEIGHT;
import static com.taxi_trouble.game.properties.GameProperties.VIRTUAL_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.taxi_trouble.game.input.MapControls;
import com.taxi_trouble.game.model.GameWorld;
import com.taxi_trouble.game.model.WorldMap;

/**
 * This is the Navigator view class.
 * 
 * @author Aidan
 * 
 */
public class NavigatorScreen extends ViewObserver {

    private World world;
    private SpriteBatch spriteBatch;
    private WorldMap cityMap;
    private Viewport viewport;
    private OrthographicCamera mapCamera;
    private MapControls mapControl;
    private float SCALE = 4;
    private final static float ZERO_TWO_F = 0.2f;   
    
    /**
     * Constructor, creates the game screen.
     * 
     * @param game
     */
    
    public NavigatorScreen(GameWorld game){
    	super(game);
    }

    /**
     * This method is only called once at the beginning to setup the main
     * screen.
     */
    @Override
    public void show() {
        this.world = taxigame.getWorld();
        this.mapCamera = new OrthographicCamera();
        mapCamera.setToOrtho(false, screenWidth, screenHeight);
        this.viewport = new StretchViewport(VIRTUAL_WIDTH * SCALE,
                VIRTUAL_HEIGHT * SCALE, mapCamera);
       
        cityMap = taxigame.getMap();
        mapControl = new MapControls(mapCamera, this);
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

        stayInBounds(cityMap);
        //update the mapCamera's view.
        mapCamera.update();
        
        //tell the camera to update its matrices.
        spriteBatch.setProjectionMatrix(mapCamera.combined);

        // what does this do? should it happen here?
        taxigame.getWorld().step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);
        taxigame.getWorld().clearForces();
        cityMap.render(mapCamera);
        
        super.render(delta);

        // Draw the sprites
     //   drawSprites();

    }

    /**
     * Can this be deleted??
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
    	
    	int mapPixelHeight = cityMap.getHeight();
        int mapPixelWidth = cityMap.getWidth();
        
    	if( mapCamera.position.x < VIRTUAL_WIDTH * sc / 2
    		|| mapCamera.position.x >= mapPixelWidth - VIRTUAL_WIDTH * sc / 2
    		|| mapCamera.position.y < VIRTUAL_HEIGHT * sc / 2
    		|| mapCamera.position.y >= mapPixelHeight - VIRTUAL_HEIGHT * sc / 2) {
    		return;
    	}
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

    public WorldMap getMap(){
    	return this.cityMap;
    }

    @Override
    public SpriteBatch getSpriteBatch() {
        return this.spriteBatch;
    }
    

}
