package com.mygdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.Game;
import com.mygdx.gameObjects.Player;
import com.mygdx.gameObjects.RandomAsteroidList;
import com.mygdx.processors.GameStateManager;

/**
 * Created by Chris on 4/3/2016.
 */
public class GameOverState extends GameState{

    private SpriteBatch batch;
    private BitmapFont gameOverFont;
    private BitmapFont playerScoreFont;
    private String gameOverPrompt = "GAME OVER";
    private RandomAsteroidList asteroids;
    private String playerScore;

    public GameOverState(GameStateManager gsm){
        super(gsm);
    }

    @Override
    public void init() {
        batch = new SpriteBatch();
        asteroids = new RandomAsteroidList();
        playerScore = String.valueOf(Player.getPlayerScore());

        FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Hyperspace Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();

        param.size = 48;
        gameOverFont = fontGen.generateFont(param);

        param.size = 24;
        playerScoreFont = fontGen.generateFont(param);
        fontGen.dispose();

    }

    @Override
    public void update(float dt) {
        handleInput();
        asteroids.update(dt);
    }

    @Override
    public void draw() {
        float promptXLocation = 280;
        float promptYLocation = 350;
        asteroids.draw();
        batch.begin();
        gameOverFont.draw(batch, gameOverPrompt, promptXLocation, promptYLocation);
        playerScoreFont.draw(batch, "Final Score: " + playerScore, promptXLocation, promptYLocation - 40);
        batch.end();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            System.out.println("Pressed");
            gsm.setState(GameStateManager.MAINMENU);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        gameOverFont.dispose();
        playerScoreFont.dispose();
    }
}
