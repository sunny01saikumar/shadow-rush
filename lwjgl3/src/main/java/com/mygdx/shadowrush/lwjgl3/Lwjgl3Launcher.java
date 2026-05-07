package com.mygdx.shadowrush.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.shadowrush.ShadowRushGame;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new ShadowRushGame(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {

        Lwjgl3ApplicationConfiguration configuration =
            new Lwjgl3ApplicationConfiguration();

        configuration.setTitle("Shadow Rush");

        configuration.useVsync(true);

        configuration.setForegroundFPS(60);

        configuration.setWindowedMode(1204, 800);

        configuration.setResizable(true);

        configuration.setWindowIcon(
            "libgdx128.png",
            "libgdx64.png",
            "libgdx32.png",
            "libgdx16.png"
        );

        return configuration;
    }
}
