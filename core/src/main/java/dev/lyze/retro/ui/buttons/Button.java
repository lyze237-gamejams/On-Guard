package dev.lyze.retro.ui.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.czyzby.kiwi.log.Logger;
import com.github.czyzby.kiwi.log.LoggerService;
import dev.lyze.retro.game.Game;

public abstract class Button extends Actor {
    private static final Logger logger = LoggerService.forClass(Button.class);

    private final TextureAtlas.AtlasRegion up, down;
    private final boolean toggle;
    protected final Game game;

    private boolean isTouched;

    public Button(Game game, String up, String down, boolean toggle) {
        this.game = game;
        this.up = game.getAss().getRegion(up);
        this.down = game.getAss().getRegion(down);
        this.toggle = toggle;

        setBounds(0, 0, this.up.getRegionWidth(), this.up.getRegionHeight());

        addListeners();
    }

    public Button(Game game, String up, String down) {
        this(game, up, down, false);
    }

    private void addListeners() {
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (toggle) {
                    isTouched = !isTouched;
                } else {
                    isTouched = true;
                }
                setState(isTouched);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (!toggle)
                    isTouched = false;

                setState(isTouched);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(isTouched ? down : up, getX(), getY(), getWidth(), getHeight());
    }

    protected abstract void setState(boolean state);
}
