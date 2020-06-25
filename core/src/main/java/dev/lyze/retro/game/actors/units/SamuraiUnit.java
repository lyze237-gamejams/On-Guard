package dev.lyze.retro.game.actors.units;

import dev.lyze.retro.game.Game;
import dev.lyze.retro.game.Player;
import dev.lyze.retro.game.actors.units.behaviours.AttackBehaviour;
import dev.lyze.retro.game.actors.units.behaviours.MovementBehaviour;

public class SamuraiUnit extends Unit {
    public SamuraiUnit(Game game, Player player) {
        super(game, game.getAss().getSamuraiUnit(), player, 1);

        addBehaviour(new AttackBehaviour(this, 4));
        addBehaviour(new MovementBehaviour(this, 0));
    }
}
