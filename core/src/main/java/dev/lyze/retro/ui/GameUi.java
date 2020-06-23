package dev.lyze.retro.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.lyze.retro.game.Game;
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

        table.add(new CoinButton(game.getAss().getRegion("Buttons/Stats/Stat_Coins"), game.getAss().getRegion("Buttons/Stats/Stat_Coins"))).padTop(1);
        table.add(new HealthButton(game.getAss().getRegion("Buttons/Stats/Stat_Health"), game.getAss().getRegion("Buttons/Stats/Stat_Health"))).padTop(1).row();
        table.add(new UnitButton(game.getAss().getRegion("Buttons/Snake/Snake_Button_Up"), game.getAss().getRegion("Buttons/Snake/Snake_Button_Down"))).padTop(1).row();
        table.add(new UnitButton(game.getAss().getRegion("Buttons/Guard/Guard_Button_Up"), game.getAss().getRegion("Buttons/Guard/Guard_Button_Down"))).padTop(1).row();
        table.add(new UnitButton(game.getAss().getRegion("Buttons/Mage/Mage_Button_Up"), game.getAss().getRegion("Buttons/Mage/Mage_Button_Down"))).padTop(1).row();
        table.add(new UnitButton(game.getAss().getRegion("Buttons/Samurai/Samurai_Button_Up"), game.getAss().getRegion("Buttons/Samurai/Samurai_Button_Down"))).padTop(1).row();
        table.add(new AbilityButton(game.getAss().getRegion("Buttons/Ability/Ability_Button_Up"), game.getAss().getRegion("Buttons/Ability/Ability_Button_Down"))).padTop(1);
        table.add(new SoundButton(game.getAss().getRegion("Buttons/Sound/Sound_Button_On"), game.getAss().getRegion("Buttons/Sound/Sound_Button_Off"))).padTop(1).row();

        root.add(table).expand().right().top().padRight(2);
        addActor(root);
    }
}
