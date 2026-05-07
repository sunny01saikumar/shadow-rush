package com.mygdx.shadowrush;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SaveManager {

    private final Preferences prefs =
        Gdx.app.getPreferences("shadowrush");

    public void saveHighScore(int score) {
        prefs.putInteger("highscore", score);
        prefs.flush();
    }

    public int getHighScore() {
        return prefs.getInteger("highscore", 0);
    }
}
