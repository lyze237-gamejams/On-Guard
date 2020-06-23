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

    private static final String UNITS_ATLAS_PATH = "atlas/atlas.atlas";

    @Getter
    private TextureAtlas.AtlasRegion snakeUnit, humanUnit, skullUnit, mageUnit;

    @Getter
    private TextureAtlas.AtlasRegion hitParticle, rangedAttackParticle;

    public Assets() {
        setupAssetManager();
        extractTextureRegions();
    }

    private void setupAssetManager() {
        assMan.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        assMan.load(Map.RESOURCE_PATH, TiledMap.class);

        assMan.load(UNITS_ATLAS_PATH, TextureAtlas.class);

        assMan.finishLoading();
    }

    private void extractTextureRegions() {
        var unitsAtlas = assMan.get(UNITS_ATLAS_PATH, TextureAtlas.class);
        snakeUnit = unitsAtlas.findRegion("Units/Snake");
        skullUnit = unitsAtlas.findRegion("Units/Skull");
        humanUnit = unitsAtlas.findRegion("Units/Human");
        mageUnit = unitsAtlas.findRegion("Units/Mage");

        hitParticle = unitsAtlas.findRegion("Particles/Hit");
        rangedAttackParticle = unitsAtlas.findRegion("Particles/Ranged_Attack");
    }

    public synchronized <T> T get (String fileName) {
        return assMan.get(fileName);
    }

    public synchronized <T> T get(String fileName, Class<T> type) {
        return assMan.get(fileName, type);
    }
}
