package com.mygdx.shadowrush;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    Texture texture;
    Rectangle bounds;

    public Player() {
        texture = new Texture("player.png");

        bounds = new Rectangle();
        bounds.x = 100;
        bounds.y = 40;
        bounds.width = 110;
        bounds.height = 110;
    }
}
