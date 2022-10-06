package me.rochblondiaux.ancientvalley;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.GameWindow;
import de.gurkenlabs.litiengine.gui.GuiProperties;
import de.gurkenlabs.litiengine.gui.screens.Resolution;
import de.gurkenlabs.litiengine.gui.screens.ScreenManager;
import de.gurkenlabs.litiengine.resources.Resources;
import lombok.extern.log4j.Log4j2;
import me.rochblondiaux.ancientvalley.assets.AssetManager;
import me.rochblondiaux.ancientvalley.game.GameState;
import me.rochblondiaux.ancientvalley.game.screens.MenuScreen;

import javax.swing.*;
import java.awt.*;

/**
 * AncientValley
 *
 * @author Roch Blondiaux
 * @date 04/10/2022
 */
@Log4j2(topic = "AncientValley")
public class AncientValley {

    private static AncientValley instance;

    public AncientValley(String[] args) {
        instance = this;

        log.info("Initializing AncientValley...");
        initialize(args);
        log.info("AncientValley initialized.");

        log.info("Starting AncientValley...");
        Game.start();
        log.info("AncientValley started.");
    }

    private void initialize(String[] args) {
        initializeAssets();
        registerGameInformation();

        Game.init(args);

        initializeWindow();
        registerScreens();
    }

    private void initializeAssets() {
        // Menu
        AssetManager.add(GameState.MENU, "assets/menu.resources");
        AssetManager.loadAssets(GameState.MENU)
                .thenAccept(unused -> GuiProperties.setDefaultFont(Resources.fonts().get("fonts.default")))
                .join();
    }

    private void registerGameInformation() {
        log.info("Registering game information...");
        Game.info().setName("AncientValley");
        Game.info().setSubTitle("Made by Roch Blondiaux!");
        Game.info().setVersion("v1.0.0");
        Game.info().setWebsite("https://www.roch-blondiaux.com");
        log.info("Game information registered.");
    }

    private void registerScreens() {
        log.info("Registering game screens...");
        ScreenManager manager = Game.screens();
        manager.add(new MenuScreen());
        log.info("Game screens registered.");
    }

    private void initializeWindow() {
        log.info("Initializing window...");
        GameWindow window = Game.window();
        window.setTitle("Ancient Valley");
        window.setResolution(Resolution.Ratio16x9.RES_1920x1080);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Container container = window.getHostControl();
        int x = (int) ((dimension.getWidth() - container.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - container.getHeight()) / 2);
        container.setLocation(x, y);

        window.setIcon(Resources.images().get("logo"));
        log.info("Window initialized!");
    }

    public static AncientValley get() {
        return instance;
    }
}
