package me.rochblondiaux.ancientvalley.game.screens;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.gui.screens.Screen;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * AncientValley
 *
 * @author Roch Blondiaux
 * @date 05/10/2022
 */
public abstract class UpdatableScreen extends Screen implements IUpdateable {

    protected static final Logger log = Game.log();

    public UpdatableScreen(String screenName) {
        super(screenName);
    }

    @Override
    public void prepare() {
        super.prepare();

        log.log(Level.INFO, "Attaching screen %s to main loop.".formatted(this.getName()));
        Game.loop().attach(this);
    }

    @Override
    public void suspend() {
        super.suspend();

        log.log(Level.INFO, "Detaching screen %s to main loop.".formatted(this.getName()));
        Game.loop().detach(this);
    }
}