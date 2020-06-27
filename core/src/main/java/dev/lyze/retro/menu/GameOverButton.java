package dev.lyze.retro.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import dev.lyze.retro.game.Assets;

public class GameOverButton extends Image {
    private final BitmapFont numbersFont;
    private Vector2 coinsFontCoords, roundsFontCoords;

    private Assets ass;
    private int rounds, coins;

    public GameOverButton(Assets ass, int rounds, int coins) {
        super(ass.getGameOverButton());

        this.rounds = rounds;
        this.coins = coins;

        numbersFont = ass.getNumbersFont();

        setPosition(33, 20);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (roundsFontCoords == null || roundsFontCoords .x < 100) {
            roundsFontCoords = localToStageCoordinates(new Vector2(56, numbersFont.getLineHeight() + 30));
        }

        if (coinsFontCoords == null || coinsFontCoords.x < 100) {
            coinsFontCoords = localToStageCoordinates(new Vector2(56, numbersFont.getLineHeight() + 22));
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        numbersFont.draw(batch, String.valueOf(rounds), roundsFontCoords.x, roundsFontCoords.y);
        numbersFont.draw(batch, String.valueOf(coins), coinsFontCoords.x, coinsFontCoords.y);
    }
}
