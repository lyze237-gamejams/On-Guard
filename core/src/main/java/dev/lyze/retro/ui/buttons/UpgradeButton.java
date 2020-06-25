package dev.lyze.retro.ui.buttons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.github.czyzby.kiwi.log.Logger;
import com.github.czyzby.kiwi.log.LoggerService;
import dev.lyze.retro.Stats;
import dev.lyze.retro.game.Game;
import dev.lyze.retro.game.actors.units.Unit;

public class UpgradeButton extends Button {
    private static final Logger logger = LoggerService.forClass(UpgradeButton.class);

    private final Class<? extends Unit> unit;
    private int price;

    private final BitmapFont numbersFont;
    private Vector2 numberFontCoords;

    private boolean buttonState;

    public UpgradeButton(Class<? extends Unit> unit, Game game, String up, String down) {
        super(game, up, down);

        this.unit = unit;
        this.price = Stats.UPGRADE_PRICE;

        numbersFont = game.getAss().getNumbersFont();

        game.getPlayer().getUpgrades().put(unit, 0);
        game.getEnemy().getUpgrades().put(unit, 0);
    }

    @Override
    protected void setState(boolean state) {
        if (buttonState = state) {
            if (getButtonFrame() + 1 >= getButtonFramesCount())
                return;

            if (!game.getPlayer().getBoughtUnits().contains(unit))
                return; // didn't buy yet so don't allow user to upgrade unit

            if (game.getPlayer().subtractCoins(price))
            {
                game.getPlayer().getUpgrades().replace(unit, game.getPlayer().getUpgrades().get(unit) + 1);
                setButtonFrame(getButtonFrame() + 1);

                logger.info("Buying upgrade: " + this + " Total: " + game.getPlayer().getUpgrades().get(unit));

                if ((price *= 2) > 9)
                    price = 9;
            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (numberFontCoords == null || numberFontCoords.x < 100) {
            numberFontCoords = localToStageCoordinates(new Vector2(22, numbersFont.getLineHeight() + 4));
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        numbersFont.draw(batch, String.valueOf(price), numberFontCoords.x, numberFontCoords.y - (buttonState ? 1 : 0));
    }

    @Override
    public String toString() {
        return "UpgradeButton{" +
                "unit=" + unit +
                ", price=" + price +
                '}';
    }
}
