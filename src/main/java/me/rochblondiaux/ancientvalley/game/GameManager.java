package me.rochblondiaux.ancientvalley.game;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.ScreenManager;
import me.rochblondiaux.ancientvalley.game.screens.GameScreens;

/**
 * AncientValley
 *
 * @author Roch Blondiaux
 * @date 06/10/2022
 */
public class GameManager {

    private static GameState state = GameState.MENU;

    public static boolean is(GameState s) {
        return state == s;
    }

    public static void setState(GameState s) {
        if (state == s)
            return;
        final ScreenManager screens = Game.screens();
        switch (s) {
            case SETTINGS -> screens.display(GameScreens.SETTINGS);
            case MENU -> screens.display(GameScreens.MENU);
            case PLAYING -> {
                int delay = state == GameState.PAUSED ? 500 : 1500;
                transition(GameScreens.GAME, delay);
                Game.loop().perform(delay, () -> state = s);
                return;
            }
            case TUTORIAL -> transition(GameScreens.GAME, 1500);
            case PAUSED -> transition(GameScreens.PAUSE, 250);
        }
        state = s;
    }

    private static void transition(String screenName, int duration) {
        Game.window().getRenderComponent().fadeOut(duration);
        Game.loop().perform(duration, () -> {
            Game.window().getRenderComponent().fadeIn(duration / 3);
            Game.screens().display(screenName);
        });
    }
}
