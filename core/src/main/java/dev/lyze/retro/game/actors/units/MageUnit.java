package dev.lyze.retro.game.actors.units;

import dev.lyze.retro.game.Game;
import dev.lyze.retro.game.actors.units.behaviours.MovementBehaviour;
import dev.lyze.retro.game.actors.units.behaviours.RangedAttackBehaviour;

public class MageUnit extends Unit {
    public MageUnit(Game game, boolean playerUnit) {
        super(game, game.getAss().getMageUnit(), playerUnit, 1);

        addBehaviour(new RangedAttackBehaviour(this, 2, 5));
        addBehaviour(new MovementBehaviour(this, 1));
    }
}
