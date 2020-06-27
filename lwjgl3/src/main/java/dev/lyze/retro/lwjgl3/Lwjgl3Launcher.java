package dev.lyze.retro.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import dev.lyze.retro.RetroTowerdefence;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
	public static void main(String[] args) {
		createApplication();
	}

	private static Lwjgl3Application createApplication() {
		return new Lwjgl3Application(new RetroTowerdefence(), getDefaultConfiguration());
	}

	private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
		Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
		configuration.setTitle("On Guard - Libgdx GameJam June 2020");
		configuration.setWindowedMode(160 * 5, 144 * 5);
		configuration.setWindowIcon("256.png", "128.png", "48.png", "32.png", "16.png");
		return configuration;
	}
}