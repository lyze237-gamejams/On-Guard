package dev.lyze.retro.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.lyze.retro.game.actors.FontTest;
import dev.lyze.retro.game.actors.Pixel;

public class GameUi extends Stage {
    public GameUi() {
        super(new FitViewport(160, 144));

        addActor(new Pixel());
        addActor(new FontTest());
    }
}
