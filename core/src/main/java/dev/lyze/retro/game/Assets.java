package dev.lyze.retro.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import dev.lyze.retro.game.actors.Map;
import lombok.Getter;

public class Assets {
    private final AssetManager assMan = new AssetManager();

    private static final String ATLAS_PATH = "atlas/atlas.atlas";

    @Getter
    private TextureAtlas.AtlasRegion snakeUnit, humanUnit, skullUnit, mageUnit;

    @Getter
    private TextureAtlas.AtlasRegion hitParticle, rangedAttackParticle;
    private TextureAtlas atlas;

    public Assets() {
        setupAssetManager();
        extractTextureRegions();
    }

    private void setupAssetManager() {
        assMan.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        assMan.load(Map.RESOURCE_PATH, TiledMap.class);

        assMan.load(ATLAS_PATH, TextureAtlas.class);

        assMan.finishLoading();
    }

    private void extractTextureRegions() {
        atlas = assMan.get(ATLAS_PATH, TextureAtlas.class);
        snakeUnit = atlas.findRegion("Units/Snake");
        skullUnit = atlas.findRegion("Units/Skull");
        humanUnit = atlas.findRegion("Units/Human");
        mageUnit = atlas.findRegion("Units/Mage");

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
