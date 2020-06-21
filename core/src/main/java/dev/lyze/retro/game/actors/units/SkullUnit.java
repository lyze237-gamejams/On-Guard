package dev.lyze.retro.game.actors.units;

import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import dev.lyze.retro.game.Game;

public class SkullUnit extends Unit {
    public static final String RESOURCE_PATH = "enemies/skull.png";

    private final MoveToAction moveAction = new MoveToAction();

    public SkullUnit(Game game, boolean playerUnit) {
        super(game, game.getAssMan().get(RESOURCE_PATH), playerUnit, 3);
    }
}
