package dev.lyze.retro;

import lombok.Getter;

public enum Direction {
    DOWN(0),
    LEFT(1),
    UP(2),
    RIGHT(3);

    @Getter
    private int value;

    Direction(int value) {
        this.value = value;
    }
}
