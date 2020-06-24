package dev.lyze.retro.ui.buttons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import dev.lyze.retro.game.Game;
import dev.lyze.retro.game.actors.units.Unit;

public class UnitButton extends Button {
    private final Class<? extends Unit> unitClazz;

    public UnitButton(Class<? extends Unit> unitClazz, Game game, String up, String down) {
        super(game, up, down);

        this.unitClazz = unitClazz;
    }

    @Override
    protected void setState(boolean state) {
        if (state) {
            game.registerPlayerUnit(unitClazz);
        }
    }
}
