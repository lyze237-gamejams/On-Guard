package dev.lyze.retro.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class FontTest extends Actor {
    private BitmapFont font;

    public FontTest() {
        font = new BitmapFont(Gdx.files.internal("fonts/EarlyGameBoy.fnt"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch, "Hello", 10, 10);
    }
}
