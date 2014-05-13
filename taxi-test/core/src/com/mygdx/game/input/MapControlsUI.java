package com.mygdx.game.input;

import static com.mygdx.game.properties.GameProperties.PIXELS_PER_METER;
import static com.mygdx.game.properties.GameProperties.VIRTUAL_HEIGHT;
import static com.mygdx.game.properties.GameProperties.VIRTUAL_WIDTH;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.world.WorldMap;

public class MapControlsUI implements InputProcessor {

	//private int tapSquareSize = 14, touchDownX = -1, touchDownY = -1;
	Vector3 last_touch_down = new Vector3();
	private OrthographicCamera mapCamera;
	private int scale;
	int old_x;
	int old_y;
	final int dragModifier = 10;
	WorldMap map;
	
	public MapControlsUI(OrthographicCamera cam, int scale, WorldMap map){
		this.mapCamera = cam;
		this.scale = scale;
		this.map = map;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
	//	System.out.println("I'm doing something");
		old_x = screenX;
		old_y = screenY;
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
    public boolean touchDragged(int x, int y, int pointer) {
		int delta_x = old_x - x;
		int delta_y = old_y - y;
		//delta_x /= dragModifier;
		//delta_y /= dragModifier;
		//Vector3 newCamPos = mapCamera.position.sub(delta_x, delta_y, 0);
		//mapCamera.position.set(mapCamera.position.sub(delta_x, delta_y, 0));
        //moveCamera( (int)newCamPos.x,(int)newCamPos.y );
        moveCamera(x, y);
        System.out.println(mapCamera.position.toString());
        old_x = x;
        old_y = y;
        return false;
    }

    private void moveCamera( int touch_x, int touch_y ) {
        Vector3 new_position = getNewCameraPosition( touch_x, touch_y );

        if(!cameraOutOfLimit(new_position))
        	mapCamera.translate( new_position.sub( mapCamera.position ) );

        last_touch_down.set( touch_x, touch_y, 0);
    }

    private Vector3 getNewCameraPosition( int x, int y ) {
        Vector3 new_position = last_touch_down;
        new_position.sub(x, y, 0);
        new_position.y = -new_position.y;
        new_position.add( mapCamera.position );

        return new_position;
    }
    
    private boolean cameraOutOfLimit( Vector3 position ) {
    	int mapPixelHeight = map.getHeight();
		int mapPixelWidth = map.getWidth();
		
        int x_left_limit = VIRTUAL_WIDTH / 2;
        int x_right_limit = mapPixelWidth - VIRTUAL_WIDTH / 2;
        int y_bottom_limit = VIRTUAL_HEIGHT / 2;
        int y_top_limit = mapPixelHeight - VIRTUAL_HEIGHT / 2;

        if( position.x < x_left_limit || position.x > x_right_limit )
            return true;
        else if( position.y < y_bottom_limit || position.y > y_top_limit )
            return true;
        else
          return false;
    	}

}
