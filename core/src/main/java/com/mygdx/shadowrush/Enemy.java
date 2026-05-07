package com.mygdx.shadowrush;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Enemy {
    Texture texture;
    Rectangle bounds;

    public Enemy(float x, float y) {
        texture = new Texture("enemy.png");

        bounds = new Rectangle();
        bounds.x = x;
        bounds.y = y;
        bounds.width = 100;
        bounds.height = 100;
    }
}
