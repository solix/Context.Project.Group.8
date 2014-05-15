package com.taxi_trouble.game.model;

import java.util.Iterator;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import static com.taxi_trouble.game.properties.GameProperties.*;

/**The map for the world in which a game can take place and its objects.
 *
 * @author Computer Games Project Group 8
 *
 */
public class WorldMap {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;

    /**Initializes a new WorldMap for a specified world using the specified
     * directory of the map file.
     *
     * @param mapFile : location/directory of the map (tmx-file)
     * @param world : the world for which the map is created
     */
    public WorldMap(String mapFile, World world) {
        this.world = world;
        this.map = new TmxMapLoader().load(mapFile);
        this.renderer = new OrthogonalTiledMapRenderer(map);

        this.loadMapObjects();
    }

    /**Loads the objects of this world map in the world.
     *
     */
    private void loadMapObjects() {
        loadMapObjectsOfType("box2D");
	}
    
    /**Loads the objects of the world map of a specified type.
     *
     * @param type
     */
    private void loadMapObjectsOfType(String type) {
        MapLayer layer = map.getLayers().get(type);
        MapObjects objects = layer.getObjects();

        Iterator<MapObject> obj_iterator = objects.iterator();
        while (obj_iterator.hasNext()) {
            MapObject obj = obj_iterator.next();
            if (obj instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) obj).getRectangle();
                createSolidBox(rect);
            }
        }
    }
    
    /**Creates a solid box in the map's world given a Rectangle
     * specifying the 
     *
     * @param r : rectangle specifying width, height and position
     */
    private void createSolidBox(Rectangle r) {
        new SolidBox(
                world,
                (r.getWidth())/ PIXELS_PER_METER,
                (r.getHeight()) / PIXELS_PER_METER,
                new Vector2(r.getX() / PIXELS_PER_METER + r.getWidth()
                        / (2 * PIXELS_PER_METER), 
                        r.getY() / PIXELS_PER_METER + r.getHeight()
                        / (2 * PIXELS_PER_METER)));
    }

    /**Renders the world map.
     *
     * @param camera
     */
    public void render(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render();
    }

	/**Retrieves the width of the world map in the world.
	 *
	 * @return
	 */
	public int getWidth() {
	    //As TiledMap width is the number of tiles in width, multiply by tilewidth.
		return map.getProperties().get("width", Integer.class) * getTileWidth();
	}
	
	/**Retrieves the height of the world map in the world.
	 *
	 * @return height
	 */
	public int getHeight() {
	  //As TiledMap height is the number of tiles in height, multiply by tilewidth.
		return map.getProperties().get("height", Integer.class) * getTileWidth();
	}

	/**Retrieves the tilewidth of the tiles of the map.
	 *
	 * @return tilewidth
	 */
    private int getTileWidth() {
        return map.getProperties().get("tilewidth", Integer.class);
    }
}
