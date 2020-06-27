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

    private final BitmapFont numbersFont;
    private Vector2 numberFontCoords;

    private boolean buttonState;

    public UpgradeButton(Class<? extends Unit> unit, Game game, String up, String down) {
        super(game, up, down);

        this.unit = unit;

        numbersFont = game.getAss().getNumbersFont();

        game.getPlayer().getUpgrades().put(unit, 0);
        game.getEnemy().getUpgrades().put(unit, 0);
    }

    @Override
    protected void setState(boolean state) {
        if (buttonState = state) {
            if (game.getPlayer().upgradeUnit(unit)) {
                logger.info("Buying upgrade: " + this + " Total: " + game.getPlayer().getUpgrades().get(unit));
                game.getAss().playRandomSound(game.getAss().getUpgradeButtonSounds());
                setButtonFrame(getButtonFrame() + 1);
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

        numbersFont.draw(batch, String.valueOf(game.getPlayer().getUpgradePrice(unit)), numberFontCoords.x, numberFontCoords.y - (buttonState ? 1 : 0));
    }

    @Override
    public String toString() {
        return "UpgradeButton{" +
                "unit=" + unit +
                '}';
    }
}
