package dev.lyze.retro.game.actors.units;

import dev.lyze.retro.game.Game;

public class SkullUnit extends Unit {
    public SkullUnit(Game game, boolean playerUnit) {
        super(game, game.getAss().getSkullUnit(), playerUnit, 3);
    }
}
