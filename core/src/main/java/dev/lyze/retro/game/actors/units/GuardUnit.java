package dev.lyze.retro.game.actors.units;

import dev.lyze.retro.game.Game;
import dev.lyze.retro.game.actors.units.behaviours.AttackBehaviour;
import dev.lyze.retro.game.actors.units.behaviours.MovementBehaviour;

public class GuardUnit extends Unit {
    private MovementBehaviour movementBehaviour;

    public GuardUnit(Game game, boolean playerUnit) {
        super(game, game.getAss().getGuardUnit(), playerUnit, 8);

        addBehaviour(new AttackBehaviour(this, 4));
        addBehaviour(movementBehaviour = new MovementBehaviour(this, 2));
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (game.getUnitUpgrades().get(getClass()) == 4)
            movementBehaviour.setSleepTicks(1);
    }
}
