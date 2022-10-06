package me.rochblondiaux.ancientvalley.assets;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;
import me.rochblondiaux.ancientvalley.game.GameState;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * AncientValley
 *
 * @author Roch Blondiaux
 * @date 06/10/2022
 */
public class AssetManager {

    private static final Logger log = Game.log();
    private static final Map<GameState, List<Asset>> assets = new HashMap<>();

    public static CompletableFuture<Void> loadAssets(GameState state) {
        log.info("Loading assets for state %s".formatted(state));
        if (!assets.containsKey(state))
            log.info("No assets found for state %s".formatted(state));

        final long start = System.currentTimeMillis();
        return CompletableFuture.allOf(get(state)
                        .stream()
                        .map(asset -> asset.type().getInjector().load(asset, asset.name(), asset.path().toString()))
                        .filter(voidCompletableFuture -> !voidCompletableFuture.isCompletedExceptionally())
                        .toArray(CompletableFuture[]::new))
                .thenApply(unused -> {
                    log.info("Loaded assets for state %s in %d ms".formatted(state, System.currentTimeMillis() - start));
                    return null;
                });

    }

    public static void add(GameState state, String url) {
        AssetFileLoader loader;
        try {
            loader = new AssetFileLoader(new File(Resources.getLocation(url).toURI()).toPath());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        try {
            List<Asset> loadedAssets = loader.load();
            assets.computeIfAbsent(state, k -> new ArrayList<>())
                    .addAll(loadedAssets);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clear(GameState state) {
        if (!assets.containsKey(state))
            log.info("No assets found for state %s".formatted(state));
        assets.get(state).clear();
    }

    public static void clear() {
        assets.clear();
    }

    public static List<Asset> get(GameState state) {
        return assets.computeIfAbsent(state, state1 -> new ArrayList<>());
    }

}