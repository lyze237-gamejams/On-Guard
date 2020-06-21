package dev.lyze.retro.game.actors.enums;

import lombok.Getter;

public enum Type {
    START("start"),
    FINISH("finish"),
    DIRECTION("direction");

    @Getter
    private final String value;

    Type(String value) {
        this.value = value;
    }
}
