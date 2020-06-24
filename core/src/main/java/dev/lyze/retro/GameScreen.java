package dev.lyze.retro;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.czyzby.kiwi.log.Logger;
import com.github.czyzby.kiwi.log.LoggerService;
import dev.lyze.retro.game.Game;
import dev.lyze.retro.ui.GameUi;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen implements Screen {
	private static final Logger logger = LoggerService.forClass(GameScreen.class);

	private GameUi ui;
	private Game game;

	public GameScreen() {
		setupGame();
		setupUi();
	}

	private void setupUi() {
	    ui = new GameUi(game);
	}

	private void setupGame() {
		game = new Game();
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		game.getViewport().apply();
		game.act(delta);
		game.draw();

		ui.getViewport().apply();
		ui.act(delta);
		ui.draw();
	}

	@Override
	public void resize(int width, int height) {
	    logger.info("Resizing to {0}x{1}", width, height);

	    game.getViewport().update(width, height, true);
	    ui.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}
}