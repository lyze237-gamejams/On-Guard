package dev.lyze.retro.game.actors.units;

import dev.lyze.retro.Stats;
import dev.lyze.retro.game.Game;
import dev.lyze.retro.game.Player;
import dev.lyze.retro.game.actors.units.behaviours.AttackBehaviour;
import dev.lyze.retro.game.actors.units.behaviours.BlockingMovementBehaviour;

public class SnakeUnit extends Unit {
    public SnakeUnit(Game game, Player player) {
        super(game, game.getAss().getSnakeUnit(), player, Stats.SNAKE.getHealth());

        addBehaviour(new AttackBehaviour(this, Stats.SNAKE.getAttack()));
        addBehaviour(new BlockingMovementBehaviour(this, 0));
    }
}
