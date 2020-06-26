package dev.lyze.retro.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Assets {
    private final AssetManager assMan = new AssetManager();

    private static final String ATLAS_PATH = "atlas/atlas.atlas";
    private static final String GAMEBOY_FONT_PATH = "fonts/EarlyGameBoy.fnt";
    private static final String NUMBERS_FONT_PATH = "fonts/Numbers.fnt";

    public static final String MAP_PATH = "maps/Test.tmx";

    public static final String[] MELEE_SOUND_PATHS = new String[] { "sounds/Sword 1.ogg", "sounds/Sword 2.ogg" };

    public static final String[] RANGED_SOUND_PATHS = new String[] { "sounds/Action Misc 8.ogg" };

    public static final String[] COIN_SOUND_PATHS = new String[] { "sounds/Coin 1.ogg", "sounds/Coin 2.ogg" };

    public static final String[] WIN_SOUND_PATHS = new String[] { "sounds/Coin Total Win.ogg", "sounds/Coin Total Win 2.ogg" };

    public static final String[] BUY_SOUND_PATHS = new String[] { "sounds/Extra Life.ogg" };

    public static final String[] UPGRADE_SOUND_PATHS = new String[] { "sounds/Power Up 1.ogg", "sounds/Power Up 2.ogg", "sounds/Power Up 3.ogg", "sounds/Power Up 4.ogg", "sounds/Power Up 5.ogg" };

    public static final String[] POTION_SOUND_PATH = new String[] { "sounds/Magic Dark.ogg" };

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

    @Getter
    private List<Sound> melees, rangedes, coins, wins, buyButtons, upgradeButtons, potionButtons;

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

        Arrays.stream(MELEE_SOUND_PATHS).forEach(sound -> assMan.load(sound, Sound.class));
        Arrays.stream(RANGED_SOUND_PATHS).forEach(sound -> assMan.load(sound, Sound.class));
        Arrays.stream(COIN_SOUND_PATHS).forEach(sound -> assMan.load(sound, Sound.class));
        Arrays.stream(WIN_SOUND_PATHS).forEach(sound -> assMan.load(sound, Sound.class));
        Arrays.stream(BUY_SOUND_PATHS).forEach(sound -> assMan.load(sound, Sound.class));
        Arrays.stream(UPGRADE_SOUND_PATHS).forEach(sound -> assMan.load(sound, Sound.class));
        Arrays.stream(POTION_SOUND_PATH).forEach(sound -> assMan.load(sound, Sound.class));

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

        melees = Arrays.stream(MELEE_SOUND_PATHS).map(sound -> assMan.get(sound, Sound.class)).collect(Collectors.toList());
        rangedes = Arrays.stream(RANGED_SOUND_PATHS).map(sound -> assMan.get(sound, Sound.class)).collect(Collectors.toList());
        coins = Arrays.stream(COIN_SOUND_PATHS).map(sound -> assMan.get(sound, Sound.class)).collect(Collectors.toList());
        wins = Arrays.stream(WIN_SOUND_PATHS).map(sound -> assMan.get(sound, Sound.class)).collect(Collectors.toList());
        buyButtons = Arrays.stream(BUY_SOUND_PATHS).map(sound -> assMan.get(sound, Sound.class)).collect(Collectors.toList());
        upgradeButtons = Arrays.stream(UPGRADE_SOUND_PATHS).map(sound -> assMan.get(sound, Sound.class)).collect(Collectors.toList());
        potionButtons = Arrays.stream(POTION_SOUND_PATH).map(sound -> assMan.get(sound, Sound.class)).collect(Collectors.toList());
    }

    public <T> T get (String fileName) {
        return assMan.get(fileName);
    }

    public <T> T get(String fileName, Class<T> type) {
        return assMan.get(fileName, type);
    }

    public TextureAtlas.AtlasRegion getRegion(String path) {
        var region = atlas.findRegion(path);
        if (region == null)
            throw new IllegalArgumentException("Unknown path " + path);
        return region;
    }

    public Array<TextureAtlas.AtlasRegion> getRegions(String path) {
        var regions = atlas.findRegions(path);
        if (regions == null)
            throw new IllegalArgumentException("Unknown path " + path);
        return regions;
    }

    private Random random = new Random();
    public void playRandomSound(List<Sound> sounds) {
        sounds.get(random.nextInt(sounds.size())).play(0.5f);
    }
}
