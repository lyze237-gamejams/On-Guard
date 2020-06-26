package dev.lyze.retro;

import com.badlogic.gdx.Game;
import com.github.czyzby.kiwi.log.Logger;
import com.github.czyzby.kiwi.log.LoggerService;
import com.kotcrab.vis.ui.VisUI;
import dev.lyze.retro.game.Assets;
import dev.lyze.retro.menu.MenuScreen;
import lombok.Getter;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class RetroTowerdefence extends Game {
	private static Logger logger;

	@Getter
	private Assets ass;

	@Override
	public void create() {
		VisUI.load();

		setupLogger();

		logger.info("Hoot!");

		ass = new Assets();
		setScreen(new MenuScreen(this, ass));
	}

	private void setupLogger() {
		LoggerService.simpleClassNames(true);
		LoggerService.logTime(true);
		logger = LoggerService.forClass(RetroTowerdefence.class);
	}
}