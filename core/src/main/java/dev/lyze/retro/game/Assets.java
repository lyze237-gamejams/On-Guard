package dev.lyze.retro.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Assets {
    private final AssetManager assMan = new AssetManager();

    private static final String ATLAS_PATH = "atlas/atlas.atlas";
    private static final String GAMEBOY_FONT_PATH = "fonts/EarlyGameBoy.fnt";
    private static final String NUMBERS_FONT_PATH = "fonts/Numbers.fnt";

    public static final String[] MAP_PATHS = new String[] { "maps/Map1.tmx", "maps/Map2.tmx", "maps/Map3.tmx", "maps/Map4.tmx" };

    public static final String[] MELEE_SOUND_PATHS = new String[] { "sounds/Sword 1.ogg", "sounds/Sword 2.ogg" };

    public static final String[] RANGED_SOUND_PATHS = new String[] { "sounds/Action Misc 8.ogg" };

    public static final String[] COIN_SOUND_PATHS = new String[] { "sounds/Coin 1.ogg", "sounds/Coin 2.ogg" };

    public static final String[] WIN_SOUND_PATHS = new String[] { "sounds/Coin Total Win.ogg", "sounds/Coin Total Win 2.ogg" };

    public static final String[] BUY_SOUND_PATHS = new String[] { "sounds/Extra Life.ogg" };

    public static final String[] UPGRADE_SOUND_PATHS = new String[] { "sounds/Power Up 1.ogg", "sounds/Power Up 2.ogg", "sounds/Power Up 3.ogg", "sounds/Power Up 4.ogg", "sounds/Power Up 5.ogg" };

    public static final String[] POTION_SOUND_PATH = new String[] { "sounds/Magic Dark.ogg" };

    public static final String MAIN_MENU_MUSIC_PATH = "music/BoosterTitle.ogg";
    public static final String GAME_MUSIC_PATH = "music/BoosterLevel_1.ogg";

    @Getter
    private Array<TextureAtlas.AtlasRegion> snakeUnit;
    @Getter
    private Array<TextureAtlas.AtlasRegion> samuraiUnit;
    @Getter
    private Array<TextureAtlas.AtlasRegion> guardUnit;
    @Getter
    private Array<TextureAtlas.AtlasRegion> mageUnit;

    @Getter
    private BitmapFont gameboyFont, numbersFont;

    @Getter
    private TextureAtlas.AtlasRegion hitParticle, rangedAttackParticle;
    private TextureAtlas atlas;

    @Getter
    private List<TiledMap> maps;

    @Getter
    private List<Sound> melees, rangedes, coins, wins, buyButtons, upgradeButtons, potionButtons;

    @Getter @Setter
    private boolean soundMuted;

    @Getter
    private TextureAtlas.AtlasRegion mainMenu, gameOverButton;

    @Getter
    private Music mainMenuMusic, gameMenuMusic;

    public Assets() {
        setupAssetManager();
        extractTextureRegions();
        setupAssets();
    }

    private void setupAssets() {
        maps = Arrays.stream(MAP_PATHS).map(map -> assMan.get(map, TiledMap.class)).collect(Collectors.toList());

        gameboyFont = assMan.get(GAMEBOY_FONT_PATH);
        numbersFont = assMan.get(NUMBERS_FONT_PATH);

        mainMenuMusic = assMan.get(MAIN_MENU_MUSIC_PATH);
        mainMenuMusic.setLooping(true);
        mainMenuMusic.setVolume(0.5f);
        gameMenuMusic = assMan.get(GAME_MUSIC_PATH);
        gameMenuMusic.setLooping(true);
        gameMenuMusic.setVolume(0.5f);
    }

    private void setupAssetManager() {
        assMan.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        Arrays.stream(MAP_PATHS).forEach(map -> assMan.load(map, TiledMap.class));

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

        assMan.load(MAIN_MENU_MUSIC_PATH, Music.class);
        assMan.load(GAME_MUSIC_PATH, Music.class);

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

        mainMenu = atlas.findRegion("MainMenu/MainMenu");
        gameOverButton = atlas.findRegion("MainMenu/GameOver");

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

    private final Random random = new Random();
    public void playRandomSound(List<Sound> sounds) {
        if (!soundMuted)
            sounds.get(random.nextInt(sounds.size())).play(0.35f);
    }

    public void playMusic(Music music) {
        mainMenuMusic.stop();
        gameMenuMusic.stop();

        if (!soundMuted)
            music.play();
    }
}
