package dev.lyze.retro.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.czyzby.kiwi.log.Logger;
import com.github.czyzby.kiwi.log.LoggerService;
import dev.lyze.retro.game.actors.Map;
import dev.lyze.retro.game.actors.units.HumanUnit;
import dev.lyze.retro.game.actors.units.MageUnit;
import dev.lyze.retro.game.actors.units.SnakeUnit;
import dev.lyze.retro.game.actors.units.Unit;
import lombok.Getter;

import java.util.ArrayList;

public class Game extends Stage {
    private static final Logger logger = LoggerService.forClass(Game.class);

    @Getter
    private Map map;

    @Getter
    private ArrayList<Unit> units = new ArrayList<>();

    @Getter
    private Assets ass = new Assets();

    private float timeSinceLastTick;
    private float roundTickTime = 1f;
    private float unitTickTime = 0.7f;

    public Game() {
        super(new FitViewport(160, 144));

        addActor(map = new Map(this));

        addUnit(new SnakeUnit(this, false));
        addUnit(new HumanUnit(this, false));

        addUnit(new SnakeUnit(this, true));
        addUnit(new MageUnit(this, true));
    }

    private void addUnit(Unit unit) {
        units.add(unit);
        addActor(unit);
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

    public void spawnParticle(TextureAtlas.AtlasRegion texture, float x, float y, float duration) {
        var particle = new Image(texture);
        particle.setPosition(x, y);
        addActor(particle);

        var action = new AlphaAction();
        action.setDuration(duration);
        action.setAlpha(0);
        particle.addAction(action);
    }
}
