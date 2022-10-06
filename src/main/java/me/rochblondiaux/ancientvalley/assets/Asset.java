package me.rochblondiaux.ancientvalley.assets;

import de.gurkenlabs.litiengine.util.io.FileUtilities;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * AncientValley
 *
 * @author Roch Blondiaux
 * @date 06/10/2022
 */
public record Asset(AssetType type, String name, Path path, int[] size) {

    public boolean exists() {
        return Files.exists(path) && Files.isRegularFile(path);
    }

    public boolean hasRightExtension() {
        return Optional.of(FileUtilities.getExtension(path.toString()))
                .map(s -> type.getExtensions().contains(s))
                .orElse(false);
    }

}