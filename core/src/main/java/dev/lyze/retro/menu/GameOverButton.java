package dev.lyze.retro.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import dev.lyze.retro.game.Assets;

public class GameOverButton extends Image {
    private final BitmapFont numbersFont;
    private final TextureAtlas.AtlasRegion lyze;
    private Vector2 coinsFontCoords, roundsFontCoords, lyzeCoords;

    private Assets ass;
    private int rounds, coins;

    public GameOverButton(Assets ass, int rounds, int coins) {
        super(ass.getGameOverButton());

        this.rounds = rounds;
        this.coins = coins;

        this.lyze = ass.getLyze().get(rounds >= 20 ? 1 : 0);

        numbersFont = ass.getNumbersFont();

        setPosition(33, 20);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (roundsFontCoords == null || roundsFontCoords .x < 100) {
            roundsFontCoords = localToStageCoordinates(new Vector2(63, numbersFont.getLineHeight() + 30));
        }

        if (coinsFontCoords == null || coinsFontCoords.x < 100) {
            coinsFontCoords = localToStageCoordinates(new Vector2(63, numbersFont.getLineHeight() + 22));
        }

        if (lyzeCoords == null || lyzeCoords.x < 20) {
            lyzeCoords = localToStageCoordinates(new Vector2(8, getHeight() - 40));
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        numbersFont.draw(batch, String.valueOf(rounds), roundsFontCoords.x, roundsFontCoords.y);
        numbersFont.draw(batch, String.valueOf(coins), coinsFontCoords.x, coinsFontCoords.y);

        batch.draw(lyze, lyzeCoords.x, lyzeCoords.y);

    }
}
