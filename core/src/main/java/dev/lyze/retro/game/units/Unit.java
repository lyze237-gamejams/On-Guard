package dev.lyze.retro.game.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import dev.lyze.retro.game.Game;
import dev.lyze.retro.utils.IntVector2;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Unit extends Actor {
    private Texture texture;
    protected Game game;

    protected ArrayList<IntVector2> pathPoints;
    protected int currentPoint = 0;

    @Getter
    private boolean playerUnit;

    public Unit(Game game, Texture texture, boolean playerUnit) {
        this.game = game;
        this.texture = texture;
        this.playerUnit = playerUnit;

        pathPoints = game.getMap().getPathPoints();
        if (playerUnit)
            Collections.reverse(pathPoints = new ArrayList<>(pathPoints));

        setBounds(pathPoints.get(0).getX() * game.getMap().getTileWidth(), pathPoints.get(0).getY() * game.getMap().getTileHeight(), texture.getWidth(), texture.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public abstract void tick(float duration);
}
