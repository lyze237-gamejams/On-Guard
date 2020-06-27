package dev.lyze.retro.game;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.github.czyzby.kiwi.log.Logger;
import com.github.czyzby.kiwi.log.LoggerService;
import dev.lyze.retro.Stats;
import dev.lyze.retro.game.actors.units.SnakeUnit;
import dev.lyze.retro.game.actors.units.Unit;
import dev.lyze.retro.game.bot.BotTarget;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class Player {
    private final Logger logger = LoggerService.forClass(Player.class);

    private final Game game;
    @Getter
    @Setter
    private int coins = Stats.START_COINS;
    @Getter
    @Setter
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

    private ArrayList<Class<? extends Unit>> unitClasses;

    public Player(Game game, boolean human) {
        this.game = game;
        this.human = human;

        unitClasses = new ArrayList<>(Stats.ALL_STATS.keySet());
    }

    public boolean subtractCoins(int amount) {
        if (coins < amount)
            return false;

        coins -= amount;
        return true;
    }

    public void addCoins(int amount) {
        if ((coins += amount) > 999)
            coins = 999;
    }

    public void addHealth(int amount) {
        if ((health += amount) > 999)
            health = 999;
    }

    public void spawnRoundUnit() {
        if (roundUnitsToSpawn.isEmpty())
            return;

        var unitClazz = roundUnitsToSpawn.remove(0);

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

    private BotTarget masterTarget = BotTarget.BUY;
    private Class<? extends Unit> subTarget = SnakeUnit.class;
    private Random random = new Random(1);

    public void act(float delta) {
        if (isHuman()) // npc act
            return;

        if (game.getRoundCounter() < 2) {
            if (boughtUnits.size() < 1) {
                buyUnit(SnakeUnit.class);
            }
        } else {
            switch (masterTarget) {
                case BUY:
                    if (buyUnit(subTarget))
                        generateNewTarget();
                    break;
                case UPGRADE:
                    if (upgradeUnit(subTarget))
                        generateNewTarget();
                    break;
                case ABILITY:
                    if (useAbility())
                        generateNewTarget();
                    break;
            }
        }
    }

    private void generateNewTarget() {
        BotTarget[] targets = BotTarget.values();

        var totalSum = 0;
        int sum = 0;
        int i = 0;
        for (BotTarget target : targets) {
            totalSum += target.getProbability();
        }

        int index = random.nextInt(totalSum);
        while (sum < index) {
            sum = sum + targets[i++].getProbability();
        }
        masterTarget = targets[Math.max(0, i - 1)];

        if (masterTarget == BotTarget.ABILITY && coins < 25)
            generateNewTarget();

        subTarget = unitClasses.get(random.nextInt(unitClasses.size()));

        logger.info("SETTING BOT TARGET TO: " + masterTarget + " " + subTarget.getSimpleName());
    }

    public boolean hasRoundUnits() {
        return roundUnits.isEmpty();
    }

    public boolean useAbility() {
        if (subtractCoins(Stats.ABILITY_PRICE) && !roundUnits.isEmpty() && !game.getPlayer().roundUnits.isEmpty()) {
            game.getAss().playRandomSound(game.getAss().getAbilityButtonSounds());
            game.getPlayer().getRoundUnits().forEach(u -> u.damage(1));

            return true;
        }

        return false;
    }

    public boolean buyUnit(Class<? extends Unit> unitClazz) {
        if (subtractCoins(Stats.ALL_STATS.get(unitClazz).getPrice())) {
            boughtUnits.add(unitClazz);
            return true;
        }
        return false;
    }

    public boolean upgradeUnit(Class<? extends Unit> unitClazz) {
        var price = Stats.UPGRADE_PRICE + upgrades.get(unitClazz) * 2;
        if (price > 9)
            price = 9;

        if (upgrades.get(unitClazz) >= Stats.UPGRADE_AMOUNT) {
            return true;
        }

        upgrades.replace(unitClazz, upgrades.get(unitClazz) + 1);

        if (subtractCoins(price)) {
            boughtUnits.add(unitClazz);
            return true;
        }
        return false;
    }
}
