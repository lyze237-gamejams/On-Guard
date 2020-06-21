package dev.lyze.retro.game.actors.units.behaviours;

import dev.lyze.retro.game.actors.units.Unit;

public abstract class Behaviour {
    protected Unit unit;

    public Behaviour(Unit unit) {
        this.unit = unit;
    }

    /**
     * @return true to not execute all other upcoming behaviours
     */
    public abstract boolean tick(float duration);
}
