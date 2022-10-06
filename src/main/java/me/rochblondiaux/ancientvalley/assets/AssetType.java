package me.rochblondiaux.ancientvalley.assets;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum AssetType {
    IMAGE(AssetInjector.IMAGES, "png", "jpg", "jpeg", "gif", "bmp"),
    SPRITESHEET(AssetInjector.SPRITE_SHEET, "png", "jpg", "jpeg", "gif", "bmp"),
    SOUND(AssetInjector.SOUNDS, "wav", "mp3", "ogg"),
    FONT(AssetInjector.FONTS, "ttf", "otf"),
    BLUEPRINT(AssetInjector.BLUEPRINTS),
    MAP(AssetInjector.MAPS, "tmx"),
    STRING(null, "txt"),
    TILESET(AssetInjector.TILESETS, "tsx");

    private final List<String> extensions;
    private final AssetInjector injector;

    AssetType(AssetInjector injector, String... extensions) {
        this.injector = injector;
        this.extensions = Arrays.asList(extensions);
    }


}