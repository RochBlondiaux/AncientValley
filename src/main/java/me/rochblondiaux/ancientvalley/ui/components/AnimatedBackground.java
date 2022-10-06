package me.rochblondiaux.ancientvalley.ui.components;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.resources.Resources;
import me.rochblondiaux.ancientvalley.utils.AssetUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * AncientValley
 *
 * @author Roch Blondiaux
 * @date 05/10/2022
 */
public class AnimatedBackground implements IUpdateable, IRenderable {

    public static final AnimatedBackground ONE = new AnimatedBackground("backgrounds.1.sky", List.of("backgrounds.1.rocks"), List.of("backgrounds.1.clouds1", "backgrounds.1.clouds2", "backgrounds.1.clouds3", "backgrounds.1.clouds4"));
    public static final AnimatedBackground TWO = new AnimatedBackground("backgrounds.2.sky", List.of("backgrounds.2.rocks"), List.of("backgrounds.2.clouds1", "backgrounds.2.clouds2", "backgrounds.2.clouds3", "backgrounds.2.birds"));
    public static final AnimatedBackground THREE = new AnimatedBackground("backgrounds.3.sky", List.of("backgrounds.3.rocks", "backgrounds.3.ground1", "backgrounds.3.ground2", "backgrounds.3.ground3", "backgrounds.3.plant"), List.of("backgrounds.3.clouds1", "backgrounds.3.clouds2"));
    public static final AnimatedBackground FOUR = new AnimatedBackground("backgrounds.4.sky", List.of("backgrounds.4.rocks", "backgrounds.4.ground"), List.of("backgrounds.4.clouds1", "backgrounds.4.clouds2"));

    private final BufferedImage sky;
    private final List<BufferedImage> foreground;
    private final List<BufferedImage> clouds;
    private final double[] cloudX;
    private final double[] cloudY;
    private final double[] cloudSpeed;

    public AnimatedBackground(String sky, List<String> foreground, List<String> clouds) {
        this.sky = Resources.images().get(sky);
        this.foreground = AssetUtils.getImages(foreground);
        this.clouds = AssetUtils.getImages(clouds);
        this.cloudX = new double[clouds.size()];
        this.cloudY = new double[clouds.size()];
        this.cloudSpeed = new double[clouds.size()];

        for (int i = 0; i < clouds.size(); i++) {
            this.cloudX[i] = Game.window().getWidth() - Math.random() * Game.window().getWidth();
            this.cloudY[i] = Math.random() * Game.window().getResolution().getHeight() - this.clouds.get(i).getHeight();
            this.cloudSpeed[i] = Game.random().nextDouble(0.3, 1.5);
        }
    }

    @Override
    public void update() {
        for (int i = 0; i < clouds.size(); i++) {
            cloudX[i] -= cloudSpeed[i];
            if (cloudX[i] < -clouds.get(i).getWidth()) {
                cloudX[i] = Game.window().getWidth();
                cloudY[i] = (int) (Math.random() * (Game.window().getHeight() - clouds.get(i).getHeight()));
                cloudSpeed[i] = Game.random().nextDouble(0.1, 1.5);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        ImageRenderer.render(g, sky, 0, 0);

        for (int i = 0; i < clouds.size(); i++)
            ImageRenderer.render(g, clouds.get(i), cloudX[i], cloudY[i]);

        for (BufferedImage image : foreground)
            ImageRenderer.render(g, image, 0, 0);
    }

    public static AnimatedBackground getBackground(int level) {
        return switch (level) {
            case 2 -> TWO;
            case 3 -> THREE;
            case 4 -> FOUR;
            default -> ONE;
        };
    }
}
