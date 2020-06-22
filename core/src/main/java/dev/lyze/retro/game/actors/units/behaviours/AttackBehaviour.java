package dev.lyze.retro.game.actors.units.behaviours;

import com.github.czyzby.kiwi.log.Logger;
import com.github.czyzby.kiwi.log.LoggerService;
import dev.lyze.retro.game.actors.units.Unit;

public class AttackBehaviour extends Behaviour {
    private static final Logger logger = LoggerService.forClass(AttackBehaviour.class);

    private int damage;

    public AttackBehaviour(Unit unit, int damage) {
        super(unit);

        this.damage = damage;
    }

    @Override
    public boolean tick(float duration) {
        var nextPathPoint = unit.getPathPoints().get(unit.getCurrentPoint() + 1);
        for (Unit loopUnit : unit.getGame().getUnits()) {
            if (unit.isPlayerUnit() != loopUnit.isPlayerUnit()) {
                if (unit.getGame().getMap().mapCoordsEqualsPixelCoords(nextPathPoint.getX(), nextPathPoint.getY(), (int) loopUnit.getX(), (int) loopUnit.getY())) {
                    logger.info(unit.toString() + " hit " + loopUnit.toString());
                    loopUnit.damage(damage);
                    return true;
                }
            }
        }

        return false;
    }
}
