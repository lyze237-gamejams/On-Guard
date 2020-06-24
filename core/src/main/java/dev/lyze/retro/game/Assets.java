package dev.lyze.retro.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;

public class Assets {
    private final AssetManager assMan = new AssetManager();

    private static final String ATLAS_PATH = "atlas/atlas.atlas";
    private static final String GAMEBOY_FONT_PATH = "fonts/EarlyGameBoy.fnt";
    private static final String NUMBERS_FONT_PATH = "fonts/Numbers.fnt";

    public static final String MAP_PATH = "maps/Test.tmx";

    @Getter
    private Array<TextureAtlas.AtlasRegion> snakeUnit;
    @Getter
    private Array<TextureAtlas.AtlasRegion> samuraiUnit;
    @Getter
    private Array<TextureAtlas.AtlasRegion> guardUnit;
    @Getter
    private Array<TextureAtlas.AtlasRegion> mageUnit;

    @Getter private BitmapFont gameboyFont, numbersFont;

    @Getter
    private TextureAtlas.AtlasRegion hitParticle, rangedAttackParticle;
    private TextureAtlas atlas;

    @Getter
    private TiledMap map;

    public Assets() {
        setupAssetManager();
        extractTextureRegions();
        setupAssets();
    }

    private void setupAssets() {
        map = assMan.get(MAP_PATH);

        gameboyFont = assMan.get(GAMEBOY_FONT_PATH);
        numbersFont = assMan.get(NUMBERS_FONT_PATH);
    }

    private void setupAssetManager() {
        assMan.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        assMan.load(MAP_PATH, TiledMap.class);

        assMan.load(ATLAS_PATH, TextureAtlas.class);

        assMan.load(GAMEBOY_FONT_PATH, BitmapFont.class);
        assMan.load(NUMBERS_FONT_PATH, BitmapFont.class);

        assMan.finishLoading();
    }

    private void extractTextureRegions() {
        atlas = assMan.get(ATLAS_PATH, TextureAtlas.class);
        snakeUnit = atlas.findRegions("Units/Snake/Snake_move");
        guardUnit = atlas.findRegions("Units/Guard/Guard_move");
        samuraiUnit = atlas.findRegions("Units/Samurai/Samurai_move");
        mageUnit = atlas.findRegions("Units/Mage/Mage_move");

        hitParticle = atlas.findRegion("Particles/Hit");
        rangedAttackParticle = atlas.findRegion("Particles/Ranged_Attack");
    }

    public <T> T get (String fileName) {
        return assMan.get(fileName);
    }

    public <T> T get(String fileName, Class<T> type) {
        return assMan.get(fileName, type);
    }

    public TextureAtlas.AtlasRegion getRegion(String path) {
        TextureAtlas.AtlasRegion region = atlas.findRegion(path);
        if (region == null)
            throw new IllegalArgumentException("Unknown path " + path);
        return region;
    }
}
