package com.taxi_trouble.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Animation implementation of passenger moving towards taxi
 * 
 * @author Context group 8
 * 
 */
public class PowerUpAnimation {
    /**
     * 
     * This array will hold each frame (sprite) of the animation.
     */

    Animation powerAnimation;
    TextureRegion[] powerframes;
    Texture powerSheet;
    TextureRegion currentFrame; // #7

    float stateTime;

    PowerUp powerup;

    final int FRAME_COL = 4;
    final int FRAME_ROW = 3;

    final int WIDTH = 64 * 4;
    final int HEIGHT = 64 * 3;

    public PowerUpAnimation() {
        powerSheet = new Texture(
                Gdx.files
                        .internal("sprites/powerups/invincible-spritesheet.png"));
        powerframes = new TextureRegion[FRAME_COL * FRAME_ROW];
    }

    /**
     * 
     * Creates a texture from animation_sheet.png which is placed in the assets
     * directory of the project Using the split convenience method on the
     * texture, we obtain a two dimensional array of the frames from the
     * texture. Then with the help of a temporary variable, the walkFrames array
     * is populated. This is necessary, as the Animation works with one
     * dimensional arrays only.
     */

    public void create() {

        TextureRegion[][] tmp = TextureRegion.split(powerSheet,
                powerSheet.getWidth() / FRAME_COL, powerSheet.getHeight()
                        / FRAME_ROW); // #10
        int index = 0;
        for (int i = 0; i < FRAME_ROW; i++) {
            for (int j = 0; j < FRAME_COL; j++) {
                powerframes[index++] = tmp[i][j];
            }
        }
        powerAnimation = new Animation(0.5f, powerframes);
        stateTime = 0f;
    }

    /**
 * 
 */

    public void render(SpriteBatch spriteBatch, Vector2 location) {
        stateTime += Gdx.graphics.getDeltaTime(); // #15
        currentFrame = powerAnimation.getKeyFrame(stateTime, true); // #16
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, location.x, location.y,
                ((powerSheet.getWidth() / FRAME_COL) / 4),
                ((powerSheet.getHeight() / FRAME_ROW) / 4)); // #17
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
