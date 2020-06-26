package dev.lyze.retro.ui.buttons;

import dev.lyze.retro.Stats;
import dev.lyze.retro.game.Game;
import dev.lyze.retro.game.actors.units.Unit;

public class UnitButton extends Button {
    private final Class<? extends Unit> unitClazz;
    private final int price;

    public UnitButton(Class<? extends Unit> unitClazz, Game game, String up, String down) {
        super(game, up, down);

        this.price = Stats.ALL_STATS.get(unitClazz).getPrice();
        this.unitClazz = unitClazz;
    }

    @Override
    protected void setState(boolean state) {
        if (state) {
            if (game.getPlayer().subtractCoins(price)) {
                game.getAss().playRandomSound(game.getAss().getBuyButtons());
                game.getPlayer().getBoughtUnits().add(unitClazz);
            }
        }
    }
}
