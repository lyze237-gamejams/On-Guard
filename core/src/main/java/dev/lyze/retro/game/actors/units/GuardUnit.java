package dev.lyze.retro.game.actors.units;

import dev.lyze.retro.game.Game;
import dev.lyze.retro.game.Player;
import dev.lyze.retro.game.actors.units.behaviours.AttackBehaviour;
import dev.lyze.retro.game.actors.units.behaviours.BlockingMovementBehaviour;

public class GuardUnit extends Unit {
    private final BlockingMovementBehaviour movementBehaviour;

    public GuardUnit(Game game, Player player) {
        super(game, game.getAss().getGuardUnit(), player, 8);

        addBehaviour(new AttackBehaviour(this, 4));
        addBehaviour(movementBehaviour = new BlockingMovementBehaviour(this, 2));
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (player.getUpgrades().get(getClass()) == 4)
            movementBehaviour.setSleepTicks(1);
    }
}
