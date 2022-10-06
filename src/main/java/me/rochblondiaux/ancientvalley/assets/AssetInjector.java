package me.rochblondiaux.ancientvalley.assets;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.resources.*;
import me.rochblondiaux.ancientvalley.utils.CompletablePromise;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@FunctionalInterface
public interface AssetInjector {

    CompletableFuture<Void> load(Asset asset, String name, String path);

    private static Function<Throwable, ? extends Void> logError(String name) {
        return e -> {
            Game.log().warning("Could not load asset %s: %s".formatted(name, e.getMessage()));
            e.printStackTrace();
            return null;
        };
    }

    AssetInjector IMAGES = (unused, name, path) -> {
        final Images images = Resources.images();
        return new CompletablePromise<>(images.getAsync(path))
                .thenAcceptAsync(bufferedImage -> images.add(name, bufferedImage))
                .exceptionallyAsync(throwable -> logError(name).apply(throwable));
    };

    AssetInjector SOUNDS = (unused, name, path) -> {
        final Sounds sounds = Resources.sounds();
        return new CompletablePromise<>(sounds.getAsync(path))
                .thenAcceptAsync(sound -> sounds.add(name, sound))
                .exceptionallyAsync(throwable -> logError(name).apply(throwable));
    };

    AssetInjector FONTS = (unused, name, path) -> {
        final Fonts fonts = Resources.fonts();
        return new CompletablePromise<>(fonts.getAsync(path))
                .thenAcceptAsync(font -> fonts.add(name, font))
                .exceptionallyAsync(throwable -> logError(name).apply(throwable));
    };

    AssetInjector SPRITE_SHEET = (asset, name, path) -> {
        final Spritesheets spritesheets = Resources.spritesheets();
        return CompletableFuture.supplyAsync(() -> {
            Spritesheet spritesheet = spritesheets.load(path, asset.size()[0], asset.size()[1]);
            spritesheets.add(name, spritesheet);
            return null;
        });
    };

    AssetInjector BLUEPRINTS = (unused, name, path) -> {
        final Blueprints blueprints = Resources.blueprints();
        return new CompletablePromise<>(blueprints.getAsync(path))
                .thenAcceptAsync(blueprint -> blueprints.add(name, blueprint))
                .exceptionallyAsync(throwable -> logError(name).apply(throwable));
    };

    AssetInjector MAPS = (unused, name, path) -> {
        final Maps maps = Resources.maps();
        return new CompletablePromise<>(maps.getAsync(path))
                .thenAcceptAsync(map -> maps.add(name, map))
                .exceptionallyAsync(throwable -> logError(name).apply(throwable));
    };

    AssetInjector TILESETS = (unused, name, path) -> {
        final Tilesets tilesets = Resources.tilesets();
        return new CompletablePromise<>(tilesets.getAsync(path))
                .thenAcceptAsync(tileset -> tilesets.add(name, tileset))
                .exceptionallyAsync(throwable -> logError(name).apply(throwable));
    };
}