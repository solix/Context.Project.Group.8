package com.taxi_trouble.game.properties;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ScoreBoard{

	private static int score;
	private static BitmapFont scoreFont;
	private static String scoreName;
	
	public void createScoreBoard(){
		score=0;
		scoreName="score : 0";
		scoreFont=new BitmapFont();
		
	}
	
	public void render(){
		SpriteBatch scoreBatch=new SpriteBatch();
		scoreBatch.begin();
		scoreFont.setColor(1.0f,1.0f,1.0f,1.0f);
		scoreFont.draw(scoreBatch, scoreName, 0, 0);
		scoreBatch.end();  
		
	}
	

}
