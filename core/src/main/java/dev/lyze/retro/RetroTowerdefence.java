package dev.lyze.retro;

import com.badlogic.gdx.Game;
import com.github.czyzby.kiwi.log.Logger;
import com.github.czyzby.kiwi.log.LoggerService;
import com.kotcrab.vis.ui.VisUI;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class RetroTowerdefence extends Game {
	private static Logger logger;

	@Override
	public void create() {
		VisUI.load();

		setupLogger();

		logger.info("Hoot!");

		setScreen(new GameScreen());
	}

	private void setupLogger() {
		LoggerService.simpleClassNames(true);
		LoggerService.logTime(true);
		logger = LoggerService.forClass(RetroTowerdefence.class);
	}
}