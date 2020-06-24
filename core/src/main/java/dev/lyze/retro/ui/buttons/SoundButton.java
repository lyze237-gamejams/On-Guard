package dev.lyze.retro.ui.buttons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import dev.lyze.retro.game.Game;

public class SoundButton extends Button {
    public SoundButton(Game game, String up, String down) {
        super(game, up, down, true);
    }

    @Override
    protected void setState(boolean state) {

    }
}
