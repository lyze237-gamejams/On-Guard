package dev.lyze.retro.game.actors.units.behaviours;

import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import dev.lyze.retro.game.actors.units.Unit;
import lombok.Setter;

public class BlockingMovementBehaviour extends Behaviour {
    private final MoveToAction moveAction = new MoveToAction();

    private int currentSleepTicks;
    @Setter
    private int sleepTicks;

    public BlockingMovementBehaviour(Unit unit, int sleepTicks) {
        super(unit);

        this.sleepTicks = sleepTicks;
    }

    @Override
    public void tick(float duration) {
        if (currentSleepTicks++ < sleepTicks)
            return;

        if (unit.getCurrentPoint() + 1 >= unit.getPathPoints().size())
            return;

        currentSleepTicks = 0;

        moveAction.reset();

        for (Unit roundUnit : unit.getGame().getOtherPlayer(unit.getPlayer()).getRoundUnits()) {
            if (roundUnit.getPathPoints().get(roundUnit.getCurrentPoint()).equals(unit.getPathPoints().get(unit.getCurrentPoint() + 1)))
                return;
        }

        for (Unit roundUnit : unit.getPlayer().getRoundUnits()) {
            if (roundUnit.getPathPoints().get(roundUnit.getCurrentPoint()).equals(unit.getPathPoints().get(unit.getCurrentPoint() + 1)))
                return;
        }

        unit.setCurrentPoint(unit.getCurrentPoint() + 1);
        moveAction.setPosition(unit.getPathPoints().get(unit.getCurrentPoint()).getX() * unit.getGame().getMap().getTileWidth(), unit.getPathPoints().get(unit.getCurrentPoint()).getY() * unit.getGame().getMap().getTileHeight());
        moveAction.setDuration(duration);

        unit.addAction(moveAction);
    }
}
