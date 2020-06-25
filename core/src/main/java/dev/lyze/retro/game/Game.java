package dev.lyze.retro.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.czyzby.kiwi.log.Logger;
import com.github.czyzby.kiwi.log.LoggerService;
import dev.lyze.retro.game.actors.Map;
import dev.lyze.retro.game.actors.units.Unit;
import lombok.Getter;

import java.util.ListIterator;
import java.util.Random;

public class Game extends Stage {
    private static final Logger logger = LoggerService.forClass(Game.class);

    @Getter
    private Map map;

    @Getter
    private final Assets ass = new Assets();

    private float timeSinceLastTick;
    private final float roundTickTime = 0.3f;
    private final float unitTickTime = 0.1f;

    private final Random random = new Random(1);

    @Getter
    private int roundCounter;

    @Getter
    private final Player player, enemy;

    public Game() {
        super(new FitViewport(160, 144));

        addActor(map = new Map(this));

        player = new Player(this, true);
        enemy = new Player(this, false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        player.act(delta);
        enemy.act(delta);

        if (player.getRoundUnitsToSpawn().isEmpty() && enemy.getRoundUnitsToSpawn().isEmpty()
                && player.getRoundUnits().isEmpty() && enemy.getRoundUnits().isEmpty()) { // field is completely empty
            startRound();
        }

        var localRoundTickTime = roundTickTime;
        var localUnitTickTime = unitTickTime;
        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            localRoundTickTime /= 4f;
            localUnitTickTime /= 4f;
        }

        if (player.hasRoundUnits() ^ enemy.hasRoundUnits()) { // only one of the sides has units => xor
            localRoundTickTime = 0.05f;
            localUnitTickTime = 0;
        }

        if ((timeSinceLastTick += delta) > localRoundTickTime) {
            player.spawnRoundUnit();
            enemy.spawnRoundUnit();

            timeSinceLastTick = 0;

            var playerUnitsIterator = player.getRoundUnits().listIterator();
            var enemyUnitsIterator = enemy.getRoundUnits().listIterator();

            while (playerUnitsIterator.hasNext() || enemyUnitsIterator.hasNext()) {
                ListIterator<Unit> pickedIterator;
                if (playerUnitsIterator.hasNext() && enemyUnitsIterator.hasNext()) {
                    var playerSide = random.nextBoolean();
                    if (roundCounter >= 2) // first round don't shuffle so player wins
                        playerSide = true;
                    pickedIterator = playerSide ? playerUnitsIterator : enemyUnitsIterator;
                }
                else if (playerUnitsIterator.hasNext()) {
                    pickedIterator = playerUnitsIterator;
                }
                else {
                    pickedIterator = enemyUnitsIterator;
                }

                var unit = pickedIterator.next();
                if (unit.isDead()) {
                    logger.info("Unit " + unit + " died");
                    pickedIterator.remove();
                    getActors().removeValue(unit, true);
                    getOtherPlayer(unit.getPlayer()).addCoins(1);
                    continue;
                }
                if (unit.getPlayer().isHuman() && unit.getPathPoints().get(unit.getCurrentPoint()).equals(map.getStartPoint())) {
                    logger.info("Player unit " + unit + " reached enemy base");
                    pickedIterator.remove();
                    getActors().removeValue(unit, true);
                    player.addCoins(1);
                    continue;
                } else if (!unit.getPlayer().isHuman() && unit.getPathPoints().get(unit.getCurrentPoint()).equals(map.getFinishPoint())) {
                    logger.info("Enemy unit " + unit + " reached player base");
                    pickedIterator.remove();
                    getActors().removeValue(unit, true);
                    player.addHealth(- 1);
                    enemy.addCoins(1);
                    continue;
                }

                unit.tick(localUnitTickTime);
            }
        }
    }

    private void startRound() {
        if (player.getBoughtUnits().isEmpty())
            return;

        logger.info("Starting round");

        player.startRound();
        enemy.startRound();

        enemy.addCoins(10);
        player.addCoins(10);

        roundCounter++;
    }

    public void spawnParticle(TextureAtlas.AtlasRegion texture, float x, float y, float duration) {
        var particle = new Image(texture);
        particle.setPosition(x, y);
        addActor(particle);

        var action = new AlphaAction();
        action.setDuration(duration);
        action.setAlpha(0);
        particle.addAction(action);
    }

    public Player getOtherPlayer(Player player) {
        if (this.player == player)
            return enemy;
        return this.player;
    }
}
