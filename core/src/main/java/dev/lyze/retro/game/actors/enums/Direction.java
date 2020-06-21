package dev.lyze.retro.game.actors.enums;

import lombok.Getter;

public enum Direction {
    DOWN(0, 0, -1),
    RIGHT(1, 1, 0),
    LEFT(3, -1, 0),
    UP(2, 0, 1);

    @Getter
    private final int value;
    @Getter
    private final int addToX, addToY;

    Direction(int value, int addToX, int addToY) {
        this.value = value;
        this.addToX = addToX;
        this.addToY = addToY;
    }
}
