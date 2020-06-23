package dev.lyze.retro.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.czyzby.kiwi.log.Logger;
import com.github.czyzby.kiwi.log.LoggerService;
import com.github.czyzby.kiwi.util.tuple.DoubleTuple;
import com.github.czyzby.kiwi.util.tuple.immutable.Pair;
import dev.lyze.retro.game.actors.Map;
import dev.lyze.retro.game.actors.units.SamuraiUnit;
import dev.lyze.retro.game.actors.units.MageUnit;
import dev.lyze.retro.game.actors.units.SnakeUnit;
import dev.lyze.retro.game.actors.units.Unit;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Game extends Stage {
    private static final Logger logger = LoggerService.forClass(Game.class);

    @Getter
    private Map map;

    @Getter
    private ArrayList<Unit> roundUnits = new ArrayList<>();

    private Queue<Class<? extends Unit>> playerRoundUnitsToSpawn = new LinkedList<>();
    private Queue<Class<? extends Unit>> enemyRoundUnitsToSpawn = new LinkedList<>();

    @Getter
    private ArrayList<Class<? extends Unit>> playerUnits = new ArrayList<>();

    @Getter
    private Assets ass = new Assets();

    private float timeSinceLastTick;
    private float roundTickTime = 1f;
    private float unitTickTime = 0.7f;

    public Game() {
        super(new FitViewport(160, 144));

        addActor(map = new Map(this));
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (roundUnits.isEmpty() && enemyRoundUnitsToSpawn.isEmpty() && playerRoundUnitsToSpawn.isEmpty()) {
            startRound();
        }

        var localRoundTickTime = roundTickTime;
        var localUnitTickTime = unitTickTime;
        if (Gdx.input.isKeyPressed(Input.Keys.F12))  {
            localRoundTickTime /= 4f;
            localUnitTickTime /= 4f;
        }

        if ((timeSinceLastTick += delta) > localRoundTickTime) {
            spawnRoundUnit();

            timeSinceLastTick = 0;

            for (int i = roundUnits.size() - 1; i >= 0; i--) {
                Unit unit = roundUnits.get(i);
                if (unit.isDead()) {
                    logger.info("Unit " + unit + " died");
                    removeUnit(unit);
                    continue;
                }

                if (unit.isPlayerUnit() && unit.getPathPoints().get(unit.getCurrentPoint()).equals(map.getStartPoint())) {
                    logger.info("Player unit " + unit + " reached enemy base");
                    removeUnit(unit);
                    continue;
                }
                else if (!unit.isPlayerUnit() && unit.getPathPoints().get(unit.getCurrentPoint()).equals(map.getFinishPoint())) {
                    logger.info("Enemy unit " + unit + " reached player base");
                    removeUnit(unit);
                    continue;
                }

                unit.tick(localUnitTickTime);
            }
        }
    }

    private void removeUnit(Unit unit) {
        roundUnits.remove(unit);
        getActors().removeValue(unit, true);
    }

    @SneakyThrows
    private void startRound() {
        logger.info("Starting round");

        for (Class<? extends Unit> playerUnit : playerUnits) {
            enemyRoundUnitsToSpawn.add(playerUnit);
            playerRoundUnitsToSpawn.add(playerUnit);
        }
    }

    @SneakyThrows
    private void spawnRoundUnit() {
        if (!enemyRoundUnitsToSpawn.isEmpty())
        {
            var unitClazz = enemyRoundUnitsToSpawn.poll();
            logger.info("Spawning " + unitClazz);
            var unit = unitClazz.getDeclaredConstructor(Game.class, boolean.class).newInstance(this, false);

            roundUnits.add(unit);
            addActor(unit);
        }
        if (!playerRoundUnitsToSpawn.isEmpty())
        {
            var unitClazz = playerRoundUnitsToSpawn.poll();
            logger.info("Spawning " + unitClazz);
            var unit = unitClazz.getDeclaredConstructor(Game.class, boolean.class).newInstance(this, true);

            roundUnits.add(unit);
            addActor(unit);
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

    public void registerPlayerUnit(Class<? extends Unit> unitClazz) {
        playerUnits.add(unitClazz);
    }
}
