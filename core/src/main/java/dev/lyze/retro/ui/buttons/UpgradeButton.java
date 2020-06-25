package dev.lyze.retro.ui.buttons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import dev.lyze.retro.game.Game;
import dev.lyze.retro.game.actors.units.Unit;

public class UpgradeButton extends Button {
    private final Class<? extends Unit> unit;
    private final int price;

    private final BitmapFont numbersFont;
    private Vector2 numberFontCoords;

    private boolean buttonState;

    public UpgradeButton(Class<? extends Unit> unit, int price, Game game, String up, String down) {
        super(game, up, down);

        this.unit = unit;
        this.price = price;

        numbersFont = game.getAss().getNumbersFont();
    }

    @Override
    protected void setState(boolean state) {
        buttonState = state;
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
}
