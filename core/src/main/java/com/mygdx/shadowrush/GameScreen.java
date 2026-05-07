package com.mygdx.shadowrush;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {

    private final ShadowRushGame game;

    private SpriteBatch batch;
    private BitmapFont font;

    private Player player;
    private Array<Enemy> enemies;

    private SaveManager saveManager;

    private float spawnTimer = 0f;
    private int score = 0;
    private int highScore = 0;

    public GameScreen(ShadowRushGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        player = new Player();
        enemies = new Array<>();

        saveManager = new SaveManager();
        highScore = saveManager.getHighScore();
    }

    private void update(float delta) {

        // Dynamic Difficulty System
        spawnTimer += delta;

        float spawnRate = Math.max(0.30f, 1.0f - score * 0.012f);

        if (spawnTimer >= spawnRate) {

            spawnEnemy();

            if (score > 25 && MathUtils.randomBoolean(0.30f)) {
                spawnEnemy();
            }

            if (score > 60 && MathUtils.randomBoolean(0.45f)) {
                spawnEnemy();
            }

            spawnTimer = 0f;
        }


        // Player Movement
        float playerSpeed = 340f;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.bounds.x -= playerSpeed * delta;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.bounds.x += playerSpeed * delta;
        }

        // Screen Bounds
        if (player.bounds.x < 0) {
            player.bounds.x = 0;
        }

        if (player.bounds.x >
            Gdx.graphics.getWidth() - player.bounds.width) {

            player.bounds.x =
                Gdx.graphics.getWidth() - player.bounds.width;
        }


        // Enemy Movement
        for (int i = enemies.size - 1; i >= 0; i--) {

            Enemy enemy = enemies.get(i);

            float enemySpeed = 320f + score * 6f;
            enemy.bounds.y -= enemySpeed * delta;

            // Collision
            if (enemy.bounds.overlaps(player.bounds)) {
                gameOver();
                return;
            }

            // Passed Screen
            if (enemy.bounds.y < -enemy.bounds.height) {
                enemies.removeIndex(i);
                score++;
            }
        }
    }

    private void spawnEnemy() {

        float enemyWidth = 80;

        enemies.add(
            new Enemy(
                MathUtils.random(
                    50,
                    (int)(Gdx.graphics.getWidth() - enemyWidth - 50)
                ),
                Gdx.graphics.getHeight()
            )
        );
    }

    private void gameOver() {

        if (score > highScore) {
            highScore = score;
            saveManager.saveHighScore(highScore);
        }

        game.setScreen(new MenuScreen(game));
    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Draw Player
        batch.draw(
            player.texture,
            player.bounds.x,
            player.bounds.y,
            player.bounds.width,
            player.bounds.height
        );

        // Draw Enemies
        for (Enemy enemy : enemies) {
            batch.draw(
                enemy.texture,
                enemy.bounds.x,
                enemy.bounds.y,
                enemy.bounds.width,
                enemy.bounds.height
            );
        }

        // Draw Score
        font.draw(
            batch,
            "Score: " + score,
            20,
            Gdx.graphics.getHeight() - 20
        );

        font.draw(
            batch,
            "High Score: " + highScore,
            20,
            Gdx.graphics.getHeight() - 50
        );

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
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

        batch.dispose();
        font.dispose();

        player.texture.dispose();

        for (Enemy enemy : enemies) {
            enemy.texture.dispose();
        }
    }
}
