package dev.lyze.retro.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.lyze.retro.game.actors.Map;
import dev.lyze.retro.game.units.*;
import lombok.Getter;

import java.util.ArrayList;

public class Game extends Stage {
    @Getter
    private final AssetManager assMan = new AssetManager();

    @Getter
    private Map map = new Map();

    private ArrayList<Unit> units = new ArrayList<>();

    private float timeSinceLastTick;
    private float roundTickTime = 1f;
    private float unitTickTime = 0.7f;

    public Game() {
        super(new FitViewport(160, 144));

        loadAssets();

        addActor(map);

        SnakeUnit snake = new SnakeUnit(this, false);
        units.add(snake);
        addActor(snake);

        SnakeUnit snake2 = new SnakeUnit(this, true);
        units.add(snake2);
        addActor(snake2);

        snake.setPosition(map.getStartPoint().getX() * map.getTileWidth(), map.getStartPoint().getY() * map.getTileHeight());
    }

    private void loadAssets() {
        assMan.load(SkullUnit.RESOURCE_PATH, Texture.class);
        assMan.load(HumanUnit.RESOURCE_PATH, Texture.class);
        assMan.load(MageUnit.RESOURCE_PATH, Texture.class);
        assMan.load(SnakeUnit.RESOURCE_PATH, Texture.class);

        assMan.finishLoading();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if ((timeSinceLastTick += delta) > roundTickTime) {
            timeSinceLastTick = 0;

            units.forEach(unit -> unit.tick(unitTickTime));
        }
    }
}
