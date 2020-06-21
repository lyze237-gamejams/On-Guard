package dev.lyze.retro.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Pixel extends Actor {
    private Texture texture;

    public Pixel() {
        texture = new Texture(Gdx.files.internal("Pixel.png"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, 0, 0);
    }
}
