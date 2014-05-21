package com.taxi_trouble.game.model;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.reflect.Constructor;
/**
 * Animation implementation of passenger moving towards taxi
 * @author 5oheil
 *
 */
public class PassangerAnimation{
	/**

	 * This array will hold each frame (sprite) of the animation. */
	
	Animation walkAnimation;
	TextureRegion[] walkframes;
    Texture  walkSheet;      
    TextureRegion   currentFrame;       // #7

	SpriteBatch spriteBatch;
	float stateTime;
	Passenger passenger; 
	final int FRAME_COL=32;
	final int FRAME_ROW=32;
	
	
	public void PassangerAnimation(){
		walkSheet = new Texture(Gdx.files.internal("SP-sht.png")); 
	    walkframes = new TextureRegion[FRAME_COL * FRAME_ROW];
	}
	/**
 * 
 * Creates a texture from animation_sheet.png which is placed in the assets directory of the project
 	Using the split convenience method on the texture, we obtain a
  	two dimensional array of the frames from the texture.
  	Then with the help of a temporary variable, the walkFrames array is populated. This is necessary, 
  	as the Animation works with one dimensional arrays only.
 */
	
public  void create(){
	
	TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COL, walkSheet.getHeight()/FRAME_ROW);              // #10
    int index = 0;
    for (int i = 0; i < FRAME_ROW; i++) {
        for (int j = 0; j < FRAME_COL; j++) {
            walkframes[index++] = tmp[i][j];
        }
    }
    walkAnimation = new Animation(0.025f, walkframes);      
    spriteBatch = new SpriteBatch();               
    stateTime = 0f;                        
}

/**
 * 
 */


public void render() {
    stateTime += Gdx.graphics.getDeltaTime();           // #15
    currentFrame = walkAnimation.getKeyFrame(stateTime, true);  // #16
    spriteBatch.begin();
    for(int i =50; i<60;i++){
    spriteBatch.draw(currentFrame, i, 50);             // #17
    	
    }
    spriteBatch.end();
}

public void resize(int width, int height) {
	// TODO Auto-generated method stub
	
}


public void pause() {
	// TODO Auto-generated method stub
	
}


public void resume() {
	// TODO Auto-generated method stub
	
}

public void dispose() {
	// TODO Auto-generated method stub
	
}


}
