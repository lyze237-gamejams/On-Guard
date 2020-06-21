package dev.lyze.retro.game.actors.units;

import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import dev.lyze.retro.game.Game;
import dev.lyze.retro.game.actors.units.behaviours.AttackBehaviour;
import dev.lyze.retro.game.actors.units.behaviours.MovementBehaviour;

public class HumanUnit extends Unit {
    public static final String RESOURCE_PATH = "enemies/human.png";

    public HumanUnit(Game game, boolean playerUnit) {
        super(game, game.getAssMan().get(RESOURCE_PATH), playerUnit, 1);

        addBehaviour(new AttackBehaviour(this, 1));
        addBehaviour(new MovementBehaviour(this, 1));
    }
}
