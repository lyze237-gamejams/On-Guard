package dev.lyze.retro.game.actors.units;

import dev.lyze.retro.game.Game;
import dev.lyze.retro.game.Player;
import dev.lyze.retro.game.actors.units.behaviours.AttackBehaviour;
import dev.lyze.retro.game.actors.units.behaviours.MovementBehaviour;

public class SnakeUnit extends Unit {
    public SnakeUnit(Game game, Player player) {
        super(game, game.getAss().getSnakeUnit(), player, 2);

        addBehaviour(new AttackBehaviour(this, 1));
        addBehaviour(new MovementBehaviour(this, 0));
    }
}
