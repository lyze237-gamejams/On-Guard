package dev.lyze.retro.game.actors.units;

import dev.lyze.retro.game.Game;
import dev.lyze.retro.game.Player;
import dev.lyze.retro.game.actors.units.behaviours.BlockingMovementBehaviour;
import dev.lyze.retro.game.actors.units.behaviours.RangedAttackBehaviour;

public class MageUnit extends Unit {
    public MageUnit(Game game, Player player) {
        super(game, game.getAss().getMageUnit(), player, 1);

        addBehaviour(new RangedAttackBehaviour(this, 1, 3));
        addBehaviour(new BlockingMovementBehaviour(this, 1));
    }
}
