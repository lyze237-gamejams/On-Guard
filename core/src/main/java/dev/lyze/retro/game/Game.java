package dev.lyze.retro.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.czyzby.kiwi.log.Logger;
import com.github.czyzby.kiwi.log.LoggerService;
import dev.lyze.retro.game.actors.Map;
import dev.lyze.retro.game.actors.units.Unit;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game extends Stage {
    private static final Logger logger = LoggerService.forClass(Game.class);

    @Getter
    private Map map;

    @Getter
    private ArrayList<Unit> roundUnits = new ArrayList<>();

    private ArrayList<Class<? extends Unit>> playerRoundUnitsToSpawn = new ArrayList<>();
    private ArrayList<Class<? extends Unit>> enemyRoundUnitsToSpawn = new ArrayList<>();

    @Getter
    private ArrayList<Class<? extends Unit>> playerUnits = new ArrayList<>();

    @Getter
    private Assets ass = new Assets();

    private float timeSinceLastTick;
    private float roundTickTime = 0.3f;
    private float unitTickTime = 0.1f;

    private Random random = new Random(1);

    private int roundCounter;

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
        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            localRoundTickTime /= 4f;
            localUnitTickTime /= 4f;
        }


        if (!roundUnits.isEmpty()) {
            var unit = roundUnits.get(0);
            if (roundUnits.stream().allMatch(u -> u.isPlayerUnit() == unit.isPlayerUnit())) {
                localRoundTickTime = 0.05f;
            }
        }

        if ((timeSinceLastTick += delta) > localRoundTickTime) {
            spawnRoundUnit();

            timeSinceLastTick = 0;

            if (roundCounter >= 2) // first round don't shuffle so player wins
                Collections.shuffle(roundUnits); // shuffle for random who attacks first

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
                } else if (!unit.isPlayerUnit() && unit.getPathPoints().get(unit.getCurrentPoint()).equals(map.getFinishPoint())) {
                    logger.info("Enemy unit " + unit + " reached player base");
                    removeUnit(unit);
                    continue;
                }

                if (roundUnits.stream().allMatch(u -> u.isPlayerUnit() == unit.isPlayerUnit()))
                    localUnitTickTime = 0;
                unit.tick(localUnitTickTime);
            }
        }
    }

    private void removeUnit(Unit unit) {
        roundUnits.remove(unit);
        getActors().removeValue(unit, true);
    }

    private void startRound() {
        if (playerUnits.isEmpty())
            return;

        logger.info("Starting round");

        roundCounter++;

        for (Class<? extends Unit> playerUnit : playerUnits) {
            enemyRoundUnitsToSpawn.add(playerUnit);
            playerRoundUnitsToSpawn.add(playerUnit);
        }

        Collections.shuffle(enemyRoundUnitsToSpawn);
        Collections.shuffle(playerRoundUnitsToSpawn);
    }

    private void spawnRoundUnit() {
        if (!enemyRoundUnitsToSpawn.isEmpty()) {
            var unitClazz = enemyRoundUnitsToSpawn.remove(0);
            logger.info("Spawning " + unitClazz);
            try {
                Unit unit = (Unit) ClassReflection.getConstructor(unitClazz, Game.class, boolean.class).newInstance(this, false);
                roundUnits.add(unit);
                addActor(unit);
            } catch (ReflectionException e) {
                e.printStackTrace();
            }

        }
        if (!playerRoundUnitsToSpawn.isEmpty()) {
            var unitClazz = playerRoundUnitsToSpawn.remove(0);
            logger.info("Spawning " + unitClazz);
            try {
                Unit unit = (Unit) ClassReflection.getConstructor(unitClazz, Game.class, boolean.class).newInstance(this, true);
                roundUnits.add(unit);
                addActor(unit);
            } catch (ReflectionException e) {
                e.printStackTrace();
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

    public void registerPlayerUnit(Class<? extends Unit> unitClazz) {
        playerUnits.add(unitClazz);
    }
}
