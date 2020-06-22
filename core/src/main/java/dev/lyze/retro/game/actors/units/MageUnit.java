package dev.lyze.retro.game.actors.units;

import dev.lyze.retro.game.Game;

public class MageUnit extends Unit {
    public MageUnit(Game game, boolean playerUnit) {
        super(game, game.getAss().getMageUnit(), playerUnit, 5);
    }
}
