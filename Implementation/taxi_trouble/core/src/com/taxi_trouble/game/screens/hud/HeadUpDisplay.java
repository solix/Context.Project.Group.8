package com.taxi_trouble.game.screens.hud;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.taxi_trouble.game.model.team.Team;

/**A Head-Up Display (HUD) which is displayed on top of each screen to indicate
 * things as in which team one is playing and the current score of the team.
 * 
 * @author Computer Games Project Group 8
 *
 */
public class HeadUpDisplay implements HUDComponent {
    private ArrayList<HUDComponent> hudComponents;
    private BitmapFont hudFont;
    private Team team;

    /**Initialize a new HUD for a specified team and using
     * a given font for drawing text.
     * 
     * @param hudFont : the font to be used
     * @param team : the team to which the hud applies
     */
    public HeadUpDisplay(BitmapFont hudFont, Team team) {
        this.team = team;
        this.hudFont = hudFont;
        this.hudComponents = new ArrayList<HUDComponent>();
        this.setColorTheme();
    }

    /**Adds a new component to the hud.
     * 
     * @param hudComponent
     */
    public void add(HUDComponent hudComponent) {
        this.hudComponents.add(hudComponent);
    }

    /**Removes a component from the hud.
     * 
     * @param hudComponent
     */
    public void remove(HUDComponent hudComponent) {
        this.hudComponents.remove(hudComponent);
    }

    /**Sets the color of the hudFont to the team theme color.
     * 
     */
    private void setColorTheme() {
        this.hudFont.setColor(this.team.getTeamTheme().getTeamColor());
    }

    /**Renders this hud with its components on screen.
     * 
     * @param spriteBatch
     */
    public void render(SpriteBatch spriteBatch) {
        render(spriteBatch, this.hudFont, this.team);
    }

    /**Renders the hud components on screen.
     * 
     * @param spriteBatch : the batch to be used for drawing the components
     * @param hudFont : the font to be used
     * @param team : the team to which the components should apply
     * 
     */
    @Override
    public void render(SpriteBatch spriteBatch, BitmapFont hudFont, Team team) {
        Iterator<HUDComponent> hudComponentIterator = hudComponents.iterator();
        while (hudComponentIterator.hasNext()) {
            hudComponentIterator.next().render(spriteBatch, hudFont, team);
        }
    }
}
