package me.rochblondiaux.ancientvalley.game.screens;

import de.gurkenlabs.litiengine.Align;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.graphics.TextRenderer;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.Imaging;
import me.rochblondiaux.ancientvalley.constants.GameColors;
import me.rochblondiaux.ancientvalley.ui.components.AnimatedBackground;
import me.rochblondiaux.ancientvalley.ui.components.GUIButton;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

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
        GUIButton playBtn = new GUIButton(0, 0, 0, 3);
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

        // Buttons
        final Dimension dimension = Game.window().getResolution();
        final double x = Game.window().getCenter().getX();
        final double y = Game.window().getCenter().getY() + (dimension.getHeight() / 2.5);
        final double offset = (dimension.getHeight() / 20);

        List<GUIButton> btns = getComponents()
                .stream()
                .filter(guiComponent -> guiComponent instanceof GUIButton)
                .map(guiComponent -> (GUIButton) guiComponent)
                .toList();
        for (int i = 0; i < btns.size(); i++) {
            GUIButton button = btns.get(btns.size() - 1 - i);
            button.setX(x - button.getWidth() / 2);
            button.setY(y - ((i + 1) * (offset + button.getHeight())) - button.getHeight() / 2);
        }

    }

    @Override
    public void render(Graphics2D g) {
        // Background
        this.background.render(g);

        // Logo
        ImageRenderer.render(g, Imaging.scale(logo, logoScale), Game.window().getCenter().getX() - (logoScale * logo.getWidth()) / 2,
                Game.window().getHeight() * 1.5 / 8 - (logoScale * logo.getHeight()) / 2);

        // Credits
        RenderingHints originalHints = g.getRenderingHints();
        TextRenderer.enableTextAntiAliasing(g);
        g.setColor(GameColors.TEXT_COLOR);
        TextRenderer.render(g, "Copyright © Roch Blondiaux", Align.CENTER, Valign.MIDDLE_DOWN, 0, 150);
        g.setRenderingHints(originalHints);

        super.render(g);
    }
}
