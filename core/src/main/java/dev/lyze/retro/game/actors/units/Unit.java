package dev.lyze.retro.game.actors.units;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.github.czyzby.kiwi.log.Logger;
import com.github.czyzby.kiwi.log.LoggerService;
import dev.lyze.retro.game.Game;
import dev.lyze.retro.game.Player;
import dev.lyze.retro.game.actors.units.behaviours.Behaviour;
import dev.lyze.retro.utils.IntVector2;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Unit extends Image {
    private static final Logger logger = LoggerService.forClass(Unit.class);

    @Getter
    protected Game game;

    private final ArrayList<Behaviour> behaviours = new ArrayList<>();

    @Getter
    protected ArrayList<IntVector2> pathPoints;
    @Getter @Setter
    protected int currentPoint = 0;

    @Getter
    protected Player player;

    @Getter
    private int health;

    private Array<TextureRegionDrawable> movementTextures;
    private float timeSinceLastTextureSwap;
    private int currentTextureIndex;


    public Unit(Game game, Array<TextureAtlas.AtlasRegion> atlasRegion, Player player, int health) {
        super(atlasRegion.first());
        this.game = game;
        this.player = player;
        this.health = health;
        movementTextures = new Array<>();
        atlasRegion.forEach(t -> movementTextures.add(new TextureRegionDrawable(t)));

        pathPoints = game.getMap().getPathPoints();
        if (player.isHuman()) {
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
        try {
            super.act(delta);
        }
        catch (Exception e) {
            damage(99999);
            logger.error(e, "Movement crashed, lol. Killing myself. Goodbye cruel world.");
        }

        if ((timeSinceLastTextureSwap += delta) > 0.2f) {
            timeSinceLastTextureSwap = 0;
            if (++currentTextureIndex >= movementTextures.size) {
                currentTextureIndex = 0;
            }

            setDrawable(movementTextures.get(currentTextureIndex));
        }

        setPosition(getX(), getY());
    }

    public void tick(float duration) {
        behaviours.forEach(b -> b.tick(duration));
    }

    public void damage(int amount) {
        health -= amount;

        game.spawnParticle(game.getAss().getHitParticle(), getX(), getY(), 0.2f);
    }

    public boolean isDead() {
        return health + player.getUpgrades().get(getClass()) <= 0;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "Player.isHuman()=" + player.isHuman() +
                ", health=" + health +
                '}';
    }
}
