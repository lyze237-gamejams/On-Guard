package dev.lyze.retro.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.lyze.retro.game.Game;
import dev.lyze.retro.game.actors.units.GuardUnit;
import dev.lyze.retro.game.actors.units.MageUnit;
import dev.lyze.retro.game.actors.units.SamuraiUnit;
import dev.lyze.retro.game.actors.units.SnakeUnit;
import dev.lyze.retro.ui.buttons.*;

import java.util.ArrayList;

public class GameUi extends Stage {
    private final Game game;

    private final ArrayList<Button> buttons = new ArrayList<>();

    public GameUi(Game game) {
        super(new FitViewport(160, 144));

        Gdx.input.setInputProcessor(this);

        this.game = game;

        var root = new Table();
        root.setFillParent(true);

        var table = new Table();

        table.add(new CoinButton(game, "Buttons/Stats/Stat_Coins", "Buttons/Stats/Stat_Coins")).padTop(1);
        table.add(new HealthButton(game, "Buttons/Stats/Stat_Health", "Buttons/Stats/Stat_Health")).padTop(1).row();
        table.add(new UnitButton(SnakeUnit.class, game, "Buttons/Snake/Snake_Button_Up", "Buttons/Snake/Snake_Button_Down")).padTop(1);
        table.add(new UpgradeButton(SnakeUnit.class, game, "Buttons/Upgrade/Snake_Upgrade_Up", "Buttons/Upgrade/Snake_Upgrade_Down")).padTop(1).row();
        table.add(new UnitButton(GuardUnit.class, game, "Buttons/Guard/Guard_Button_Up", "Buttons/Guard/Guard_Button_Down")).padTop(1);
        table.add(new UpgradeButton(GuardUnit.class, game, "Buttons/Upgrade/Snake_Upgrade_Up", "Buttons/Upgrade/Snake_Upgrade_Down")).padTop(1).row();
        table.add(new UnitButton(MageUnit.class, game, "Buttons/Mage/Mage_Button_Up", "Buttons/Mage/Mage_Button_Down")).padTop(1);
        table.add(new UpgradeButton(MageUnit.class, game, "Buttons/Upgrade/Snake_Upgrade_Up", "Buttons/Upgrade/Snake_Upgrade_Down")).padTop(1).row();
        table.add(new UnitButton(SamuraiUnit.class, game, "Buttons/Samurai/Samurai_Button_Up", "Buttons/Samurai/Samurai_Button_Down")).padTop(1);
        table.add(new UpgradeButton(SamuraiUnit.class, game, "Buttons/Upgrade/Snake_Upgrade_Up", "Buttons/Upgrade/Snake_Upgrade_Down")).padTop(1).row();
        table.add(new AbilityButton(game, "Buttons/Ability/Ability_Button_Up", "Buttons/Ability/Ability_Button_Down")).padTop(1);
        table.add(new SoundButton(game, "Buttons/Sound/Sound_Button_On", "Buttons/Sound/Sound_Button_Off")).padTop(1).row();

        root.add(table).expand().right().top().padRight(2);
        addActor(root);
    }
}
