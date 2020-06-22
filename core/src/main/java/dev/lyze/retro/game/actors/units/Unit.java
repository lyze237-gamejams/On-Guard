package dev.lyze.retro.game.actors.units;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import dev.lyze.retro.game.Game;
import dev.lyze.retro.game.actors.units.behaviours.Behaviour;
import dev.lyze.retro.utils.IntVector2;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Unit extends Image {
    @Getter
    protected Game game;

    private final ArrayList<Behaviour> behaviours = new ArrayList<>();

    @Getter
    protected ArrayList<IntVector2> pathPoints;
    @Getter @Setter
    protected int currentPoint = 0;

    @Getter
    private boolean playerUnit;

    @Getter
    private int health;

    public Unit(Game game, TextureAtlas.AtlasRegion texture, boolean playerUnit, int health) {
        super(texture);
        this.game = game;
        this.playerUnit = playerUnit;
        this.health = health;

        pathPoints = game.getMap().getPathPoints();
        if (playerUnit) {
            Collections.reverse(pathPoints = new ArrayList<>(pathPoints));
            //sprite.flip(false, true);
        }

        setPosition(pathPoints.get(0).getX() * game.getMap().getTileWidth(), pathPoints.get(0).getY() * game.getMap().getTileHeight());
    }

    public void addBehaviour(Behaviour behaviour) {
        behaviours.add(behaviour);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        setPosition(getX(), getY());
    }

    public void tick(float duration) {
        for (Behaviour behaviour : behaviours) {
            if (behaviour.tick(duration))
                break;
        }
    }

    public void damage(int amount) {
        health -= amount;

        game.spawnParticle(game.getAss().getHitParticle(), getX(), getY(), 0.2f);
    }

    public boolean isDead() {
        return health <= 0;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "playerUnit=" + playerUnit +
                ", health=" + health +
                '}';
    }
}
