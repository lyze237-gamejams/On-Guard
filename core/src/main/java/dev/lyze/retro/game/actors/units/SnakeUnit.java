package dev.lyze.retro.game.actors.units;

import com.github.czyzby.kiwi.log.Logger;
import com.github.czyzby.kiwi.log.LoggerService;
import dev.lyze.retro.game.Game;
import dev.lyze.retro.game.actors.units.behaviours.AttackBehaviour;
import dev.lyze.retro.game.actors.units.behaviours.MovementBehaviour;

public class SnakeUnit extends Unit {
    private static final Logger logger = LoggerService.forClass(SnakeUnit.class);

    public static final String RESOURCE_PATH = "enemies/snake.png";


    public SnakeUnit(Game game, boolean playerUnit) {
        super(game, game.getAssMan().get(RESOURCE_PATH), playerUnit, 7);

        addBehaviour(new AttackBehaviour(this, 1));
        addBehaviour(new MovementBehaviour(this, 0));
    }
}
