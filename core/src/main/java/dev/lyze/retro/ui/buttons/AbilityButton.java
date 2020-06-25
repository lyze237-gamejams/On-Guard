package dev.lyze.retro.ui.buttons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import dev.lyze.retro.game.Game;

public class AbilityButton extends Button {
    private int price;

    public AbilityButton(int price, Game game, String up, String down) {
        super(game, up, down);

        this.price = price;
    }

    @Override
    protected void setState(boolean state) {
        if (!state)
            return;

        if (game.getCoins() < price)
            return;

        game.setCoins(game.getCoins() - price);

        game.getRoundUnits().stream().filter(u -> !u.isPlayerUnit()).forEach(u -> u.damage(1));
    }
}
