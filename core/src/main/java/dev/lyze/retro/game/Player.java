package dev.lyze.retro.game;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.github.czyzby.kiwi.log.Logger;
import com.github.czyzby.kiwi.log.LoggerService;
import dev.lyze.retro.Stats;
import dev.lyze.retro.game.actors.units.SnakeUnit;
import dev.lyze.retro.game.actors.units.Unit;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Player {
    private final Logger logger = LoggerService.forClass(Player.class);

    private final Game game;
    @Getter @Setter
    private int coins = Stats.START_COINS;
    @Getter @Setter
    private int health = Stats.START_HEALTH;

    @Getter
    private final boolean human; // or npc?

    @Getter
    private final ArrayList<Class<? extends Unit>> boughtUnits = new ArrayList<>();
    @Getter
    private final HashMap<Class<? extends Unit>, Integer> upgrades = new HashMap<>();

    @Getter
    private final ArrayList<Unit> roundUnits = new ArrayList<>();

    @Getter
    private ArrayList<Class<? extends Unit>> roundUnitsToSpawn = new ArrayList<>();

    public Player(Game game, boolean human) {
        this.game = game;
        this.human = human;
    }

    public boolean subtractCoins(int amount) {
        if (coins < amount)
            return false;

        coins -= amount;
        return true;
    }

    public void addCoins(int amount) {
        coins += amount;
    }

    public void addHealth(int amount) {
        health += amount;
    }

    public void spawnRoundUnit() {
        if (roundUnitsToSpawn.isEmpty())
            return;

        var unitClazz = roundUnitsToSpawn.remove(0);
        logger.info("Spawning " + unitClazz);

        try {
            Unit unit = (Unit) ClassReflection.getConstructor(unitClazz, Game.class, Player.class).newInstance(game, this);
            roundUnits.add(unit);
            game.addActor(unit);
        } catch (ReflectionException e) {
            e.printStackTrace();
        }
    }

    public void startRound() {
        roundUnitsToSpawn.addAll(boughtUnits);

        Collections.shuffle(roundUnitsToSpawn);
    }

    public void act(float delta) {
        if (isHuman()) // npc act
            return;

        if (game.getRoundCounter() < 2) {
            spawnUnit(SnakeUnit.class);
        }
    }

    public boolean hasRoundUnits() {
        return roundUnits.isEmpty();
    }

    public boolean spawnUnit(Class<? extends Unit> unitClazz) {
        if (subtractCoins(Stats.ALL_STATS.get(unitClazz).getPrice())) {
            boughtUnits.add(unitClazz);
            return true;
        }
        return false;
    }
}
