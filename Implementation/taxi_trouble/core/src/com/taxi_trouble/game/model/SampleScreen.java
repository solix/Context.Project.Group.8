package com.taxi_trouble.game.model;

import static com.taxi_trouble.game.properties.GameProperties.PIXELS_PER_METER;

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
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		spriteBatch=new SpriteBatch();
		
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
		// TODO Auto-generated method stub
		
	}

}
