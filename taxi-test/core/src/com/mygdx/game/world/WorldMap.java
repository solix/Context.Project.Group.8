package com.mygdx.game.world;

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
import com.mygdx.game.BoxProp;

import static com.mygdx.game.properties.GameProperties.*;

public class WorldMap {
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private World world;
	
	public WorldMap(String mapFile, World world) {
		this.world = world;
		this.map = new TmxMapLoader().load("maps/test_map2.tmx");
		this.renderer = new OrthogonalTiledMapRenderer(map);
		
		this.loadMapObjects();
	}

	private void loadMapObjects() {
		// load the map objects
		MapLayer layer = map.getLayers().get("box2D");
		MapObjects objects = layer.getObjects();

		Iterator<MapObject> obj_iterator = objects.iterator();
		while (obj_iterator.hasNext()) {
			MapObject obj = obj_iterator.next();
			if (obj instanceof RectangleMapObject) {
				Rectangle r = ((RectangleMapObject) obj).getRectangle();
				new BoxProp(world, (r.getWidth())
						/ PIXELS_PER_METER, (r.getHeight()) / PIXELS_PER_METER,
						new Vector2(r.getX() / PIXELS_PER_METER + r.getWidth()
								/ (2 * PIXELS_PER_METER), r.getY()
								/ PIXELS_PER_METER + r.getHeight()
								/ (2 * PIXELS_PER_METER)));
			}
		}
	}
	
	public void render(OrthographicCamera camera) {
		renderer.setView(camera);
		renderer.render();
	}
	
	public int getWidth() {
		return map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class);
	}
	
	public int getHeight() {
		return map.getProperties().get("height",
				Integer.class)
				* map.getProperties().get("tileheight", Integer.class);
	}
}
