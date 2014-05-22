package com.taxi_trouble.game.properties;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ScoreBoard{

	public static int score;
	private static BitmapFont scoreFont;
	private static String scoreName;
	
	public void createScoreBoard(){
		score=0;
		scoreName="Score  "+ getScore();
		scoreFont=new BitmapFont();
		
	}
	
	public void render(){
		SpriteBatch scoreBatch=new SpriteBatch();
		scoreBatch.begin();
		scoreFont.setColor(0.0f, 0.0f, 1.0f, 1.0f);
		scoreFont.draw(scoreBatch, scoreName, 10, GameProperties.screenHeight -10);
		scoreBatch.end();  
		
	}
	
	public int getScore(){
		return score;
	}

}
