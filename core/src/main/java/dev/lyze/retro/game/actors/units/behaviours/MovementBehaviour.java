package dev.lyze.retro.game.actors.units.behaviours;

import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import dev.lyze.retro.game.actors.units.Unit;

public class MovementBehaviour extends Behaviour {
    private final MoveToAction moveAction = new MoveToAction();

    private int currentSleepTicks;
    private int sleepTicks;

    public MovementBehaviour(Unit unit, int sleepTicks) {
        super(unit);

        this.sleepTicks = sleepTicks;
    }

    @Override
    public boolean tick(float duration) {
        if (currentSleepTicks++ < sleepTicks) {
            return false;
        }

        currentSleepTicks = 0;

        moveAction.reset();

        unit.setCurrentPoint(unit.getCurrentPoint() + 1);
        moveAction.setPosition(unit.getPathPoints().get(unit.getCurrentPoint()).getX() * unit.getGame().getMap().getTileWidth(), unit.getPathPoints().get(unit.getCurrentPoint()).getY() * unit.getGame().getMap().getTileHeight());
        moveAction.setDuration(duration);

        unit.addAction(moveAction);

        return false;
    }
}
