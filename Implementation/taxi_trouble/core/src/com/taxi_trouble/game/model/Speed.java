package com.taxi_trouble.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Speed implements PowerUpBehaviour {

    private PowerUpAnimation powerAnim;

    public Speed(PowerUpAnimation anim) {
        powerAnim = anim;
    }

    @Override
    public void triggerEvent(Taxi taxi) {
        taxi.triggerSpeed();

    }

    @Override
    public void render(SpriteBatch spriteBatch, Vector2 location) {
        powerAnim.render(spriteBatch, location);

    }

    @Override
    public PowerUpAnimation getPowerAnimation() {
        return powerAnim;
    }
}
