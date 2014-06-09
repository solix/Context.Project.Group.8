package com.taxi_trouble.game.input;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.taxi_trouble.game.properties.GameProperties;

import static com.taxi_trouble.game.properties.ResourceManager.throttleSprite;
import static com.taxi_trouble.game.properties.ResourceManager.brakeSprite;
import static com.taxi_trouble.game.properties.ResourceManager.dPadSprite;

/**The graphical interface of the controls for a driver.
 *
 * @author Computer Games Project Group 8
 *
 */
public class DriverControlsUI {
    private Map<String, VirtualButton> buttons;
    private Map<Integer, Action> active;

    /**Initializes the graphical interface for the driver controls.
     *
     */
    public DriverControlsUI() {
        this.buttons = new HashMap<String, VirtualButton>();
        this.active = new HashMap<Integer, Action>();

        buttons.put("throttle", new VirtualButton(new Rectangle(
                GameProperties.BUTTON_CAM_WIDTH - 110, 10, 100, 120),
                throttleSprite,
                Action.ACCELERATE));

        buttons.put("brake", new VirtualButton(new Rectangle(
                GameProperties.BUTTON_CAM_WIDTH - 210, 10, 100, 120),
                brakeSprite,
                Action.BRAKE));

        buttons.put("dpad", new VirtualDPad(new Rectangle(10, 10, 184, 80),
                dPadSprite,
                Action.DPAD_DEFAULT));
    }

    /**Retrieves whether the driver is currently steering or not.
     *
     * @return boolean indicating steering
     */
    public boolean steering() {
        for (Entry<Integer, Action> e : active.entrySet()) {
            if (e.getValue() != null) {
                switch (e.getValue()) {
                case LEFT:
                case RIGHT:
                    return true;
                default:
                    break;
                }
            }
        }
        return false;
    }

    /**Retrieves whether the driver is currently driving/accelerating or not.
    *
    * @return boolean indicating accelerating
    */
    public boolean driving() {
        for (Entry<Integer, Action> e : active.entrySet()) {
            if (e.getValue() != null) {
                switch (e.getValue()) {
                case BRAKE:
                case ACCELERATE:
                    return true;
                default:
                    break;
                }
            }
        }
        return false;
    }

    /**Renders the buttons of the graphical control interface.
     *
     * @param spriteBatch
     */
    public void render(SpriteBatch spriteBatch) {
        for (VirtualButton button : buttons.values()) {
            button.render(spriteBatch);
        }
    }

    /**Retrieves the buttons of the interface.
     *
     * @return map of buttons
     */
    public Map<String, VirtualButton> getButtons() {
        return this.buttons;
    }

    /**Sets the current active action.
     *
     * @param pointer - key
     * @param button - the corresponding button
     */
    public void setActive(int pointer, VirtualButton button) {
        active.put(pointer, button.ACTION);
    }

    /**Sets the action of key pointer to inactive.
     *
     * @param pointer - key
     */
    public void setInActive(int pointer) {
        active.put(pointer, Action.NOT_ACTIVE);
    }
}
