package com.mygdx.game.input;

import static com.mygdx.game.properties.GameProperties.VIRTUAL_HEIGHT;
import static com.mygdx.game.properties.GameProperties.VIRTUAL_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.screens.MapScreen;
import com.mygdx.game.world.WorldMap;

public class MapControlsUI implements InputProcessor {

	//private int tapSquareSize = 14, touchDownX = -1, touchDownY = -1;
	Vector3 last_touch_down = new Vector3();
	private OrthographicCamera mapCamera;
	int old_x;
	int old_y;
	float last_dist;
	Viewport viewport;
	MapScreen mapscreen;
	float old_factor = 1;
	float ZOOM = 1;
	//WorldMap map;

	public MapControlsUI(OrthographicCamera cam, Viewport port, MapScreen mapscreen){
		this.mapCamera = cam;
		this.viewport = port;
		this.mapscreen = mapscreen;
		//	this.map = map;
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
		if(pointer == 0){
			old_x = screenX;
			old_y = screenY;
		}
		if(Gdx.input.isTouched(1) && Gdx.input.isTouched(0)){
			Vector2 pointA = new Vector2(Gdx.input.getX(0), Gdx.input.getY(0));
			Vector2 pointB = new Vector2(Gdx.input.getX(1), Gdx.input.getY(1));
			last_dist = pyth(pointA, pointB);

		}
		if(Gdx.input.isTouched(1) && !Gdx.input.isTouched(0)){
			mapCamera.position.set(0,0,0);
		}
		//System.out.println("posX = " + Gdx.input.getX(0) + " posY = " + Gdx.input.getY(0) );
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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
		//		lowest = lowestTouch();
		if(Gdx.input.isTouched(0) && pointer == 0){
			float delta_x =  (old_x - x) * -1 * ZOOM;
			float delta_y = (old_y - y) * ZOOM;
			System.out.println("ZOOM = " + ZOOM);
			Vector3 newCamPos = mapCamera.position.sub(delta_x, delta_y, 0);
			mapCamera.position.set(newCamPos);
			old_x = x;
			old_y = y;
			return true;
		}
		else if(Gdx.input.isTouched(1) && Gdx.input.isTouched(0)){
			Vector2 pointA = new Vector2(Gdx.input.getX(0), Gdx.input.getY(0));
			Vector2 pointB = new Vector2(Gdx.input.getX(1), Gdx.input.getY(1));
			float dist = pyth(pointA, pointB);
			//float factor = last_dist / dist;
			//System.out.println("last_dist = " + last_dist + " dist = " + dist);
			float factor = last_dist / dist;
			last_dist = dist;
			//old_factor = factor;
			System.out.println("factor = "  + factor);
			zoom(mapCamera, factor);
			old_factor = factor;
			return true;
		}
		/*else if(Gdx.input.isTouched(1) && Gdx.input.isTouched(0)){
			Vector2 pointA = new Vector2(Gdx.input.getX(0), Gdx.input.getY(0));
			Vector2 pointB = new Vector2(Gdx.input.getX(0), Gdx.input.getY(0));
			float dist_AB = pointA.dst(pointB);
			float factor = last_dist / dist_AB;
			this.viewport.setWorldSize(viewport.getWorldHeight() * factor, viewport.getWorldWidth() * factor);
			last_dist = dist_AB;
			return true;
		}
		 */
		else{
			return false;
		}

	}

	public int lowestTouch(){
		int i = 0;
		return i;
	}

	public float pyth(Vector2 v1, Vector2 v2){
		float x = Math.abs(v1.x - v2.x);
		float y = Math.abs(v1.y - v2.y);
		x = x * x;
		y = y * y;
		System.out.println("x + y = " + x + " " + y);
		System.out.println((float) Math.sqrt(x + y));
		return (float) Math.sqrt(x + y);
	}
	
	public void zoom(OrthographicCamera cam, float factor){
		float factor_thresh = Math.abs(factor -1);
		float factor_old_tresh = Math.abs(old_factor - 1);
		float dif = Math.abs(factor_old_tresh - factor_thresh);
		
		
		if(dif == 0){
			return;
		}
		if(factor > 1){
			factor = (float) (1 + factor_thresh * 0.8);
		}
		else if (factor < 1) {
			factor = (float) (1 - factor_thresh * 0.8);
		}
		if(cam.zoom * factor > 1.5 || cam.zoom * factor < 0.5){
			return;
		}
		cam.zoom = cam.zoom * factor;
		ZOOM = cam.zoom;
		mapscreen.setScale(factor * mapscreen.SCALE);
		System.out.println("zoom = " + cam.zoom);
		
	}
}
