package dev.lyze.retro.game.bot;

import lombok.Getter;

public enum BotTarget {
    BUY(60), UPGRADE(35), ABILITY(5);

    @Getter
    private final int probability;

    BotTarget(int probability) {
        this.probability = probability;
    }
}

