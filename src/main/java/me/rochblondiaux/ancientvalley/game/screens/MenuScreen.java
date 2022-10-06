package me.rochblondiaux.ancientvalley.game.screens;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.Imaging;
import me.rochblondiaux.ancientvalley.ui.components.AnimatedBackground;
import me.rochblondiaux.ancientvalley.ui.components.GUIButton;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * AncientValley
 *
 * @author Roch Blondiaux
 * @date 05/10/2022
 */
public class MenuScreen extends UpdatableScreen {

    private BufferedImage logo;
    private double logoScale;
    private AnimatedBackground background;

    public MenuScreen() {
        super(GameScreens.MENU);
    }

    @Override
    public void prepare() {
        super.prepare();

        Game.window().getRenderComponent().setBackground(Color.BLACK);
        Game.graphics().setBaseRenderScale(6f * Game.window().getResolutionScale());
    }

    @Override
    protected void initializeComponents() {
        super.initializeComponents();

        // Logo
        this.logo = Resources.images().get("logo-minimalist");

        // Background
        this.background = AnimatedBackground.getBackground(Game.random().nextInt(1, 4));

        // Play Button
        GUIButton playBtn = new GUIButton(1, 0, 0, 3);
       // playBtn.onClicked(componentMouseEvent -> GameManager.setState(GameState.LOADING));
        this.getComponents().add(playBtn);

        // Settings Button
        GUIButton settingsBtn = new GUIButton(4, 0, 0, 3);
       // settingsBtn.onClicked(componentMouseEvent -> GameManager.setState(GameState.SETTINGS));
        this.getComponents().add(settingsBtn);

        // Exit Button
        GUIButton exitBtn = new GUIButton(1, 0, 0, 3);
        exitBtn.onClicked(componentMouseEvent -> System.exit(0));
        this.getComponents().add(exitBtn);
    }

    @Override
    public void update() {
        // Logo
        this.logoScale = (1.4 + 0.15 * Math.sin(Game.time().sinceEnvironmentLoad() / 400.0)) / 1.5;

        // Background
        this.background.update();
    }

    @Override
    public void render(Graphics2D g) {
        // Background
        this.background.render(g);

        // Logo
        ImageRenderer.render(g, Imaging.scale(logo, logoScale), Game.window().getCenter().getX() - (logoScale * logo.getWidth()) / 2,
                Game.window().getHeight() * 1.5 / 8 - (logoScale * logo.getHeight()) / 2);

        super.render(g);
    }
}
