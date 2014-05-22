package com.taxi_trouble.game.model;

import static com.taxi_trouble.game.properties.GameProperties.PIXELS_PER_METER;

<<<<<<< HEAD
import java.time.format.ResolverStyle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.taxi_trouble.game.properties.ResourceManager;

public class SampleScreen implements Screen {
	private float camWidth = 0.0f;		//the width of the cam
	private float camHeight = 0.0f;		//the height of the cam
	private float aspectRatio = 0.0f;	//the aspect ratio
	private int screenWidth = 0;		//screen width in pixel size
	private int screenHeight = 0;		//screen height in pixel size
	private OrthographicCamera cam;		//our camera
	
	float statetime = 0f;
	SpriteBatch spriteBatch;
	private Vector2 velocity;
	private Vector2 position;
	private float angle;
	private float rotationSpeed;
	
	public SampleScreen(){ 
	velocity = new Vector2(0.5f, 0.5f);
	 position = new Vector2(0.3f, 0.5f);
	 angle = 0.0f;
	 rotationSpeed = 40.0f; //40 degrees per second
	}
	@Override
	public void render(float delta) {
		statetime += Gdx.graphics.getDeltaTime();
		if (statetime > 0.8)
			statetime = 0f;
		/**position.x +=velocity.x * statetime;
		position.y +=velocity.y * statetime;
		angle += rotationSpeed * statetime;
		
		while(angle > 360.0f){
			angle -= 360.0f;
		}
		if(position.x > camWidth){velocity.x = -.5f;}
		if(position.y > camHeight){velocity.y = -.5f;}
		if(position.x < 0.0f){velocity.x = .5f;}
		if(position.y < 0.0f){velocity.y = .5f;}*/
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
		
		//PlayMode loop=Animation.PlayMode.LOOP;
		ResourceManager.loadPassengerAnimation(Gdx.files
				.internal("sprites/spriteSheet/aroos.png"));
		ResourceManager.current_passenger_frame = ResourceManager.loading_passenger_animation
				.getKeyFrame(statetime, true);
		spriteBatch.begin();
		spriteBatch.draw(ResourceManager.current_passenger_frame, 255, 250);
		System.out.println(statetime);
		//ResourceManager.loading_passenger_animation.setPlayMode(loop);
		spriteBatch.end();
=======
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taxi_trouble.game.properties.ResourceManager;

public class SampleScreen implements Screen {

		
	float statetime=0f;
	SpriteBatch spriteBatch;
	@Override
	public void render(float delta) {
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);                        // #14
		statetime+=Gdx.graphics.getDeltaTime(); 
		if(statetime > 0.4)
			statetime = 0f;
        ResourceManager.loadPassengerAnimation(Gdx.files.internal("sprites/spriteSheet/character1_spritesheet.png"));
        ResourceManager.current_passenger_frame=ResourceManager.loading_passenger_animation.getKeyFrame(statetime, true);
    	spriteBatch.begin();
        spriteBatch.draw(ResourceManager.current_passenger_frame,0,0);
        System.out.println(statetime);
        spriteBatch.end();
>>>>>>> 25e3747efb26aefb3e0f2947bae5250dff8efe08
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
<<<<<<< HEAD

=======
		
>>>>>>> 25e3747efb26aefb3e0f2947bae5250dff8efe08
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
<<<<<<< HEAD
		spriteBatch = new SpriteBatch();

=======
		spriteBatch=new SpriteBatch();
		
>>>>>>> 25e3747efb26aefb3e0f2947bae5250dff8efe08
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
<<<<<<< HEAD

=======
		
>>>>>>> 25e3747efb26aefb3e0f2947bae5250dff8efe08
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
<<<<<<< HEAD

=======
		
>>>>>>> 25e3747efb26aefb3e0f2947bae5250dff8efe08
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
<<<<<<< HEAD

=======
		
>>>>>>> 25e3747efb26aefb3e0f2947bae5250dff8efe08
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
<<<<<<< HEAD

=======
		
>>>>>>> 25e3747efb26aefb3e0f2947bae5250dff8efe08
	}

}
