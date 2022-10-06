package me.rochblondiaux.ancientvalley.ui.components;

import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.gui.GuiComponent;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.util.Imaging;
import lombok.Getter;
import me.rochblondiaux.ancientvalley.utils.AssetUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;

/**
 * AncientValley
 *
 * @author Roch Blondiaux
 * @date 06/10/2022
 */
public class GUIButton extends GuiComponent {


    private final BufferedImage img;
    @Getter
    private final int type;

    public GUIButton(int type, double x, double y, double scale) {
        super(x, y);
        this.type = type;

        img = Imaging.scale(AssetUtils.getSprite("ui_buttons", type), scale);
        this.getComponents().add(new ImageComponent(getX(), getY(), img));
        setWidth(img.getWidth());
        setHeight(img.getHeight());
        setX(x - getWidth() / 2);
        setY(y - getHeight() / 2);
    }

    private BufferedImage getGrayScaleImage() {
        ImageFilter filter = new GrayFilter(true, 10);
        ImageProducer producer = new FilteredImageSource(img.getSource(), filter);
        return Imaging.toBufferedImage(Toolkit.getDefaultToolkit().createImage(producer));
    }

    @Override
    public void render(Graphics2D g) {
        if (!isEnabled()) {
            String key = "btn_%d_%d_%d".formatted(type, img.getWidth(), img.getHeight());
            ImageRenderer.render(g, AssetUtils.getOrCache(key, getGrayScaleImage()), getX(), getY());
            return;
        }
        if (isHovered()) {
            ImageRenderer.renderRotated(g, img, getX(), getY(), type % 2 == 0 ? 1.3 : -1.3);
            return;
        }
        ImageRenderer.render(g, img, getX(), getY());
    }
}
