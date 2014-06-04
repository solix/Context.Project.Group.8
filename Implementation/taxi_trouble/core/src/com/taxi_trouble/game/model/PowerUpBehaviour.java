package com.taxi_trouble.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public interface PowerUpBehaviour {

    public void triggerEvent(Taxi taxi);

    public void render(SpriteBatch spriteBatch, Vector2 location);

    public PowerUpAnimation getPowerAnimation();

}
