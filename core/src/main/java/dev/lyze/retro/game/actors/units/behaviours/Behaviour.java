package dev.lyze.retro.game.actors.units.behaviours;

import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import dev.lyze.retro.game.actors.units.Unit;

public abstract class Behaviour {
    protected Unit unit;

    public Behaviour(Unit unit) {
        this.unit = unit;
    }

    public abstract void tick(float duration);

    protected void bobUnit(float duration) {
        var sequence = new SequenceAction();
        var up = new ScaleByAction();
        var down = new ScaleByAction();

        up.setAmount(0.1f);
        up.setDuration(duration * 0.1f);

        down.setAmount(-0.1f);
        down.setDuration(duration * 0.1f);

        sequence.addAction(up);
        sequence.addAction(down);

        unit.addAction(sequence);
    }
}
