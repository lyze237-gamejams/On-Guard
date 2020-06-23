package dev.lyze.retro.game.actors.units;

import dev.lyze.retro.game.Game;
import dev.lyze.retro.game.actors.units.behaviours.AttackBehaviour;
import dev.lyze.retro.game.actors.units.behaviours.MovementBehaviour;

public class GuardUnit extends Unit {
    public GuardUnit(Game game, boolean playerUnit) {
        super(game, game.getAss().getSkullUnit(), playerUnit, 8);

        addBehaviour(new AttackBehaviour(this, 4));
        addBehaviour(new MovementBehaviour(this, 2));
    }
}
