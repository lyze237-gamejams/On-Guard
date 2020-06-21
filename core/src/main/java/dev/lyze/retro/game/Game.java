package dev.lyze.retro.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.czyzby.kiwi.log.Logger;
import com.github.czyzby.kiwi.log.LoggerService;
import dev.lyze.retro.game.actors.Map;
import dev.lyze.retro.game.actors.units.*;
import lombok.Getter;

import java.util.ArrayList;

public class Game extends Stage {
    private static final Logger logger = LoggerService.forClass(Game.class);
    @Getter
    private final AssetManager assMan = new AssetManager();

    @Getter
    private Map map;

    @Getter
    private ArrayList<Unit> units = new ArrayList<>();

    private float timeSinceLastTick;
    private float roundTickTime = 1f;
    private float unitTickTime = 0.7f;

    public Game() {
        super(new FitViewport(160, 144));

        loadAssets();

        addActor(map = new Map(this));

        addUnit(new SnakeUnit(this, false));
        addUnit(new SnakeUnit(this, true));

        addUnit(new HumanUnit(this, false));
        addUnit(new HumanUnit(this, true));
    }

    private void addUnit(Unit unit) {
        units.add(unit);
        addActor(unit);
    }

    private void loadAssets() {
        assMan.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        assMan.load(Map.RESOURCE_PATH, TiledMap.class);

        assMan.load(SkullUnit.RESOURCE_PATH, Texture.class);
        assMan.load(HumanUnit.RESOURCE_PATH, Texture.class);
        assMan.load(MageUnit.RESOURCE_PATH, Texture.class);
        assMan.load(SnakeUnit.RESOURCE_PATH, Texture.class);

        assMan.finishLoading();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        var localRoundTickTime = roundTickTime;
        var localUnitTickTime = unitTickTime;
        if (Gdx.input.isKeyPressed(Input.Keys.F12))  {
            localRoundTickTime /= 4f;
            localUnitTickTime /= 4f;
        }

        if ((timeSinceLastTick += delta) > localRoundTickTime) {
            timeSinceLastTick = 0;

            for (int i = units.size() - 1; i >= 0; i--) {
                Unit unit = units.get(i);
                if (unit.isDead()) {
                    units.remove(i);
                    getActors().removeValue(unit, true);
                    continue;
                }

                unit.tick(localUnitTickTime);
            }
        }
    }
}
