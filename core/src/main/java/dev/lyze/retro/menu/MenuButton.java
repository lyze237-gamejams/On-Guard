package dev.lyze.retro.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import dev.lyze.retro.GameScreen;
import dev.lyze.retro.RetroTowerdefence;

public class MenuButton extends Actor {
    public MenuButton(RetroTowerdefence towerdefence, int map, int x, int y, int width, int height) {
        setBounds(x, y, width, height);

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                towerdefence.setScreen(new GameScreen(towerdefence.getAss(), map));
                return false;
            }
        });
    }
}
