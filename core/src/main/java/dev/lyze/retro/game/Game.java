package dev.lyze.retro.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.lyze.retro.game.actors.FontTest;
import dev.lyze.retro.game.actors.Map;
import dev.lyze.retro.game.actors.Pixel;

public class Game extends Stage {
    public Game() {
        super(new FitViewport(160, 144));

        addActor(new Map());
        addActor(new Pixel());
        addActor(new FontTest());
    }
}
