package dev.lyze.retro.game.actors.units;

import dev.lyze.retro.game.Game;
import dev.lyze.retro.game.actors.units.behaviours.AttackBehaviour;
import dev.lyze.retro.game.actors.units.behaviours.MovementBehaviour;

public class HumanUnit extends Unit {
    public HumanUnit(Game game, boolean playerUnit) {
        super(game, game.getAss().getHumanUnit(), playerUnit, 1);

        addBehaviour(new AttackBehaviour(this, 1));
        addBehaviour(new MovementBehaviour(this, 1));
    }
}
