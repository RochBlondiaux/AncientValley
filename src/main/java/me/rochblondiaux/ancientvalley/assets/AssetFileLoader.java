package me.rochblondiaux.ancientvalley.assets;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.io.FileUtilities;
import me.rochblondiaux.ancientvalley.exceptions.InvalidAssetException;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * AncientValley
 *
 * @author Roch Blondiaux
 * @date 06/10/2022
 */
public record AssetFileLoader(Path path) {

    private static final Logger log = Game.log();

    public List<Asset> load() throws FileNotFoundException, IllegalStateException {
        final ArrayList<Asset> assets = new ArrayList<>();
        final InputStream fileStream = Resources.get(path.toString());
        final Path parent = path.getParent();
        if (fileStream == null)
            throw new FileNotFoundException("Could not find file %s".formatted(path));
        if (!FileUtilities.getExtension(path.toString()).equalsIgnoreCase("resources"))
            throw new InvalidAssetException("File %s has an illegal extension".formatted(path));
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fileStream))) {
            String line;
            AssetType type = null;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty())
                    continue;
                if (line.startsWith("#")) {
                    try {
                        type = AssetType.valueOf(line.substring(1).toUpperCase());
                    } catch (Exception e) {
                        throw new IllegalStateException("Asset type not defined in file %s".formatted(path));
                    }
                    continue;
                }
                if (type == null)
                    throw new IllegalStateException("Asset type not defined in file %s".formatted(path));

                final String[] parts = line.split(";");
                if (parts.length < 2)
                    throw new InvalidAssetException("Asset line %s is invalid in file %s".formatted(line, path));
                final String name = parts[0];
                final String url = "../%s".formatted(parts[1]);
                final int[] size = new int[2];
                if (parts.length == 3)
                    parseSize(size, parts[2]);

                final Path filePath = parent.resolve(url);
                final Asset asset = new Asset(type, name, filePath, size);


                if (!asset.exists())
                    throw new FileNotFoundException("Could not find file %s".formatted(filePath));
                if (!asset.hasRightExtension())
                    throw new InvalidAssetException("File %s has wrong extension".formatted(filePath));
                assets.add(asset);
            }

            log.info("Loaded %d assets from %s".formatted(assets.size(), path));
        } catch (final IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
        return assets;
    }

    private void parseSize(int[] size, String data) {
        String[] parts = data.split(",");
        if (parts.length != 2)
            throw new InvalidAssetException("Size data %s is invalid".formatted(data));
        try {
            size[0] = Integer.parseInt(parts[0]);
            size[1] = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new InvalidAssetException("Size data %s is invalid".formatted(data));
        }
    }
}