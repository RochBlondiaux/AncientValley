package me.rochblondiaux.ancientvalley;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.Resolution;
import de.gurkenlabs.litiengine.gui.screens.ScreenManager;
import de.gurkenlabs.litiengine.resources.Fonts;
import de.gurkenlabs.litiengine.resources.Images;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.resources.Spritesheets;
import lombok.extern.log4j.Log4j2;
import me.rochblondiaux.ancientvalley.game.screens.MenuScreen;

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
        Images images = Resources.images();

        // Logo
        images.add("logo", images.get("images/normal-logo.png"));
        images.add("logo-minimalist", images.get("images/minimalist-logo.png"));

        // Backgrounds
        images.add("backgrounds.1.sky", images.get("images/backgrounds/1/sky.png"));
        images.add("backgrounds.1.rocks", images.get("images/backgrounds/1/rocks_2.png"));
        images.add("backgrounds.1.clouds1", images.get("images/backgrounds/1/clouds_1.png"));
        images.add("backgrounds.1.clouds2", images.get("images/backgrounds/1/clouds_2.png"));
        images.add("backgrounds.1.clouds3", images.get("images/backgrounds/1/clouds_3.png"));
        images.add("backgrounds.1.clouds4", images.get("images/backgrounds/1/clouds_4.png"));

        images.add("backgrounds.2.sky", images.get("images/backgrounds/2/sky.png"));
        images.add("backgrounds.2.birds", images.get("images/backgrounds/2/birds.png"));
        images.add("backgrounds.2.rocks", images.get("images/backgrounds/2/rocks_1.png"));
        images.add("backgrounds.2.clouds1", images.get("images/backgrounds/2/clouds_1.png"));
        images.add("backgrounds.2.clouds2", images.get("images/backgrounds/2/clouds_2.png"));
        images.add("backgrounds.2.clouds3", images.get("images/backgrounds/2/clouds_3.png"));

        images.add("backgrounds.3.sky", images.get("images/backgrounds/3/sky.png"));
        images.add("backgrounds.3.plant", images.get("images/backgrounds/3/plant.png"));
        images.add("backgrounds.3.rocks", images.get("images/backgrounds/3/rocks.png"));
        images.add("backgrounds.3.ground1", images.get("images/backgrounds/3/ground_1.png"));
        images.add("backgrounds.3.ground2", images.get("images/backgrounds/3/ground_2.png"));
        images.add("backgrounds.3.ground3", images.get("images/backgrounds/3/ground_3.png"));
        images.add("backgrounds.3.clouds1", images.get("images/backgrounds/3/clouds_1.png"));
        images.add("backgrounds.3.clouds2", images.get("images/backgrounds/3/clouds_2.png"));

        images.add("backgrounds.4.sky", images.get("images/backgrounds/4/sky.png"));
        images.add("backgrounds.4.rocks", images.get("images/backgrounds/4/rocks.png"));
        images.add("backgrounds.4.ground", images.get("images/backgrounds/4/ground.png"));
        images.add("backgrounds.4.clouds1", images.get("images/backgrounds/4/clouds_1.png"));
        images.add("backgrounds.4.clouds2", images.get("images/backgrounds/4/clouds_2.png"));

        // Fonts
        Fonts fonts = Resources.fonts();
        fonts.add("fonts.default", fonts.get("fonts/MinimalPixel.ttf"));

        // Spritesheets
        Spritesheets spritesheets = Resources.spritesheets();
        spritesheets.add("ui.buttons", spritesheets.load("images/ui/buttons.png", 53, 40));

        // Sounds
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
        Game.window().setTitle("Ancient Valley");
        Game.window().setResolution(Resolution.Ratio16x9.RES_1920x1080);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Container container = Game.window().getHostControl();
        int x = (int) ((dimension.getWidth() - container.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - container.getHeight()) / 2);
        container.setLocation(x, y);

        Game.window().setIcon(Resources.images().get("logo"));
        log.info("Window initialized!");
    }

    public static AncientValley get() {
        return instance;
    }
}
