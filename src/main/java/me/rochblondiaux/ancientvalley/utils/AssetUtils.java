package me.rochblondiaux.ancientvalley.utils;

import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.resources.Images;
import de.gurkenlabs.litiengine.resources.Resources;
import lombok.experimental.UtilityClass;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * AncientValley
 *
 * @author Roch Blondiaux
 * @date 05/10/2022
 */
@UtilityClass
public class AssetUtils {

    public static BufferedImage getSprite(String name, int index) {
        Spritesheet spritesheet = Resources.spritesheets().get(name);
        if (spritesheet == null)
            return null;
        return spritesheet.getSprite(index);
    }

    public static List<BufferedImage> getImages(List<String> names) {
        final Images images = Resources.images();
        return names.stream()
                .map(images::get)
                .toList();
    }

    public static BufferedImage getOrCache(String key, BufferedImage defaultImg) {
        final Images images = Resources.images();
        if (images.contains(key))
            return images.get(key);
        images.add(key, defaultImg);
        return defaultImg;
    }
}
