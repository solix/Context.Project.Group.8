package com.taxi_trouble.game.input;

import static com.taxi_trouble.game.properties.ResourceManager.noPowerUpButtonSprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.taxi_trouble.game.properties.GameProperties;

/**
 * This shows the user interface for the navigator to activate powerUps.
 *
 * @author Computer Games Project Group 8
 *
 */
public class PowerUpControlsUI {

    private VirtualButton activatePowerUpButton;

    /**Initializes a new PowerUpControlsUI.
     * 
     */
    public PowerUpControlsUI() {
        this.activatePowerUpButton = new VirtualButton(new Rectangle(
                GameProperties.BUTTON_CAM_WIDTH - 140, 20, 120, 120),
                noPowerUpButtonSprite,
                Action.ACTIVATE_POWERUP);
    }
    
    /**Renders the buttons of the graphical control interface.
    *
    * @param spriteBatch
    */
   public void render(SpriteBatch spriteBatch) {
       activatePowerUpButton.render(spriteBatch);
   }

   /**Retrieves whether the powerUp button is pressed.
    * 
    * @param screenX
    * @param screenY
    * @param mouseButton
    * @return
    */
   public boolean buttonPressed(int screenX, int screenY, int mouseButton) {
       return this.activatePowerUpButton.touchDown(screenX, screenY, mouseButton);
   }
}
