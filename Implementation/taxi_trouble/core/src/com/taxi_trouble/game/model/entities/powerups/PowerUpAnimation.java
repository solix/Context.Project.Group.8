package com.taxi_trouble.game.model.entities.powerups;

import static com.taxi_trouble.game.properties.GameProperties.PIXELS_PER_METER;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Animation implementation of powerups.
 * 
 * @author Computer Games Project Group 8
 * 
 */
public class PowerUpAnimation {

    private Animation powerAnimation;
    private float stateTime;

    private final int FRAME_COL = 4;
    private final int FRAME_ROW = 3;

    public PowerUpAnimation(Texture powerUpSpriteSheet) {
        TextureRegion[] powerUpFrames = new TextureRegion[FRAME_COL * FRAME_ROW];
        create(powerUpSpriteSheet, powerUpFrames);
    }

    /**
     * 
     * Creates the frames of the animation which is placed in the assets
     * directory of the project Using the split convenience method on the
     * texture, we obtain a two dimensional array of the frames from the
     * texture. Then with the help of a temporary variable, the walkFrames array
     * is populated. This is necessary, as the Animation works with one
     * dimensional arrays only.
     */

    public void create(Texture powerUpSpriteSheet, TextureRegion[] powerUpFrames) {

        TextureRegion[][] tmp = TextureRegion.split(powerUpSpriteSheet,
                powerUpSpriteSheet.getWidth() / FRAME_COL, powerUpSpriteSheet.getHeight()
                        / FRAME_ROW);
        int index = 0;
        for (int i = 0; i < FRAME_ROW; i++) {
            for (int j = 0; j < FRAME_COL; j++) {
                powerUpFrames[index++] = tmp[i][j];
            }
        }
        this.powerAnimation = new Animation(0.25f, powerUpFrames);
        stateTime = 0f;
    }

    /**
     * render the animation on the given location.
     * 
     * @param spriteBatch
     * @param location
     */
    public void render(SpriteBatch spriteBatch, Vector2 location) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = powerAnimation.getKeyFrame(stateTime, true);
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, location.x * PIXELS_PER_METER,
                location.y * PIXELS_PER_METER, 32f, 32f);
        spriteBatch.end();
    }
}
