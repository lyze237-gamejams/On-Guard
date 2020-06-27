package dev.lyze.retro;

import dev.lyze.retro.game.actors.units.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

public class Stats {
    public static int START_HEALTH = 100;
    public static int START_COINS = 20;
    public static int COINS_PER_KILL = 1;
    public static int COINS_PER_ROUND = 2;

    public static int UPGRADE_AMOUNT = 5;

    public static UnitStats SNAKE = new UnitStats(1, 1, 2, 1);
    public static UnitStats GUARD = new UnitStats(9, 8, 4, 1);
    public static UnitStats MAGE = new UnitStats(3, 1, 1, 3);
    public static UnitStats SAMURAI = new UnitStats(5, 1, 4, 1);

    public static int ABILITY_PRICE = 30;
    public static int UPGRADE_PRICE = 2;

    public static HashMap<Class<? extends Unit>, UnitStats> ALL_STATS = new HashMap<>();

    static {
        ALL_STATS.put(SnakeUnit.class, SNAKE);
        ALL_STATS.put(GuardUnit.class, GUARD);
        ALL_STATS.put(MageUnit.class, MAGE);
        ALL_STATS.put(SamuraiUnit.class, SAMURAI);
    }

    @Data
    @AllArgsConstructor
    public static class UnitStats {
        private int price, health, attack, range;
    }
}

