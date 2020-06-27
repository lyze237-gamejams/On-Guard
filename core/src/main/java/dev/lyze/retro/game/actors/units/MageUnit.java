package dev.lyze.retro.game.actors.units;

import dev.lyze.retro.Stats;
import dev.lyze.retro.game.Game;
import dev.lyze.retro.game.Player;
import dev.lyze.retro.game.actors.units.behaviours.BlockingMovementBehaviour;
import dev.lyze.retro.game.actors.units.behaviours.RangedAttackBehaviour;

public class MageUnit extends Unit {
    public MageUnit(Game game, Player player) {
        super(game, game.getAss().getMageUnit(), player, Stats.MAGE.getHealth());

        addBehaviour(new RangedAttackBehaviour(this, Stats.MAGE.getAttack(), Stats.MAGE.getRange()));
        addBehaviour(new BlockingMovementBehaviour(this, 1));
    }
}
