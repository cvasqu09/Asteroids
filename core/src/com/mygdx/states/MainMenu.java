package com.mygdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.gameObjects.Asteroid;
import com.mygdx.gameObjects.Bullet;
import com.mygdx.gameObjects.Player;
import com.mygdx.gameObjects.RandomAsteroidList;
import com.mygdx.gameObjects.Saucer;
import com.mygdx.processors.GameStateManager;

import java.util.ArrayList;
import java.util.Random;

public class MainMenu extends GameState{
    private SpriteBatch batch;
    private BitmapFont titleFont;
    private BitmapFont font;

    private final String title = "ASTEROIDS";
    private final String playTitle = "PLAY";
    private final String highScoreTitle = "HIGHSCORES";
    private final String settingsTitle = "SETTINGS";

    private String[] subTitles = {playTitle, highScoreTitle, settingsTitle};

    private Saucer saucer;

    //Current user choice
    private int choice = GameStateManager.PLAY;

    private static RandomAsteroidList randomAsteroids;


    public MainMenu(GameStateManager gsm){
        //Super uses the parent class and calls the constructor
        //with the game state manager passed in as a parameter
        super(gsm);
    }

    @Override
    public void init() {
        batch = new SpriteBatch();
        randomAsteroids = new RandomAsteroidList();

        FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Hyperspace Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();

        //FIXME: Temp
        saucer = new Saucer(400, 400, new Player(new ArrayList<Bullet>()));

        param.size = 48;
        titleFont = fontGen.generateFont(param);
        param.size = 24;
        font = fontGen.generateFont(param);
        fontGen.dispose();
    }

    @Override
    public void update(float dt) {
        handleInput();
        randomAsteroids.update(dt);
        saucer.update(dt);

    }

    @Override
    public void draw() {
        float titleXLocation = 280;
        float titleYLocation = 350;
        randomAsteroids.draw();
        //Debugging for the saucer
        saucer.draw();
        batch.begin();
        titleFont.draw(batch, title, titleXLocation, titleYLocation);

        //Three items for the number of other titles
        for(int i = 0; i < 3; i++){
            if(i == choice){
                font.setColor(1, 0, 0, 1);
                font.draw(batch, subTitles[i], titleXLocation, titleYLocation - 50 * (i + 1));
            } else {
                font.setColor(255, 255, 255, 1);
                font.draw(batch, subTitles[i], titleXLocation, titleYLocation - 50 * (i + 1));
            }
        }
        batch.end();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            if(choice == 2){
                return;
            } else {
                choice++;
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            if(choice == 0){
                return;
            } else {
                choice--;
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            System.out.println(choice);
            gsm.setState(choice);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static RandomAsteroidList getRandomAsteroids(){
        return randomAsteroids;
    }
}
