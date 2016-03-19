package com.mygdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.gameObjects.Asteroid;
import com.mygdx.gameObjects.Player;
import com.mygdx.gameObjects.RandomAsteroidList;
import com.mygdx.processors.GameStateManager;

import java.util.ArrayList;
import java.util.IdentityHashMap;

/**
 * Created by Chris on 3/5/2016.
 */


public class SettingsState extends GameState{

    private final String settingTitle = "SETTINGS";
    private final String music = "MUSIC";
    private final String upgrades = "ALLOW UPGRADES";
    private final String color = "CHANGE COLOR OF SHIP";

    private final String onTitle = "ON";
    private final String offTitle = "OFF";
    private final String backTitle = "BACK";

    private final String[] colors = {"WHITE", "BLUE", "CYAN", "RED", "PURPLE", "GREEN"};
    private final Color[] colorValues = {new Color(1, 1, 1, 1), new Color(0, 0, 255, 1), new Color(0, 255, 230, 1),
            new Color(255, 0, 0, 1), new Color(230, 0, 255,1), Color.GREEN};

    private int playerColorChoice = 0;
    public static Color playerColor = Color.WHITE;

    private String[] subTitles = {music, upgrades, color};

    private final String secretString = "666593";
    private String userString = "";

    public static boolean bMusic = false;
    public static boolean bUpgrades = false;



    private SpriteBatch batch;
    private BitmapFont font;
    private BitmapFont headingFont;
    private BitmapFont colorsFont;

    private RandomAsteroidList randAsteroids;



    int choice = 0;

    public SettingsState(GameStateManager gsm){
        super(gsm);
    }

    @Override
    public void init() {
        batch = new SpriteBatch();
        FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Hyperspace Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 48;
        headingFont = fontGen.generateFont(param);

        param.size = 24;
        font = fontGen.generateFont(param);
        randAsteroids = MainMenu.getRandomAsteroids();

        param.size = 16;
        colorsFont = fontGen.generateFont(param);
        fontGen.dispose();
    }

    @Override
    public void update(float dt) {
        if(userString.compareTo(secretString) == 0){
            System.out.println("Entering secret");
            gsm.setState(GameStateManager.SECRET);
        }

        handleInput();
        randAsteroids.update(dt);
    }

    @Override
    public void draw() {
        float headerXLocation = 280;
        float headerYLocation = 400;
        batch.begin();
        headingFont.draw(batch, settingTitle, headerXLocation, headerYLocation);

        //Drawing the subtitles
        for(int i = 0; i < subTitles.length; i++){
            if(i == choice){
                font.setColor(1, 0, 0, 1);
                font.draw(batch, subTitles[i], headerXLocation - 200, headerYLocation - 50 * (i + 1));
            } else {
                font.setColor(255, 255, 255, 1);
                font.draw(batch, subTitles[i], headerXLocation - 200, headerYLocation - 50 * (i + 1));
            }
        }

        //On off settings for necessary titles
        for(int i = 0; i < subTitles.length - 1; i++){
            //Draw back button
            if(choice == -1){
                font.setColor(1, 0, 0, 1);
                font.draw(batch, backTitle, headerXLocation - 200, headerYLocation);
            } else {
                font.setColor(255, 255, 255, 1);
                font.draw(batch, backTitle, headerXLocation - 200, headerYLocation);
            }

            if(choice == 0){
                if(bMusic){
                    font.setColor(1, 0, 0, 1);
                    font.draw(batch, onTitle, headerXLocation + 300, headerYLocation - 50);
                    font.setColor(255, 255, 255, 1);
                    font.draw(batch, offTitle, headerXLocation + 350, headerYLocation - 50);
                } else {
                    font.setColor(1, 0, 0, 1);
                    font.draw(batch, offTitle, headerXLocation + 350, headerYLocation - 50);
                    font.setColor(255, 255, 255, 1);
                    font.draw(batch, onTitle, headerXLocation + 300, headerYLocation - 50);
                }
            }

            else if(choice == 1){
                if(bUpgrades){
                    font.setColor(1, 0, 0, 1);
                    font.draw(batch, onTitle, headerXLocation + 300, headerYLocation - 50 * 2);
                    font.setColor(255, 255, 255, 1);
                    font.draw(batch, offTitle, headerXLocation + 350, headerYLocation - 50 * 2);
                } else {
                    font.setColor(1, 0, 0, 1);
                    font.draw(batch, offTitle, headerXLocation + 350, headerYLocation - 50 * 2);
                    font.setColor(255, 255, 255, 1);
                    font.draw(batch, onTitle, headerXLocation + 300, headerYLocation - 50 * 2);
                }
            }

            //Drawing color options
            else if(choice == 2){
                colorsFont.setColor(255, 255, 255, 1);
                for(int j = 0; j < colors.length; j++){
                    if(j == playerColorChoice){
                        colorsFont.setColor(1, 0, 0, 1);
                        colorsFont.draw(batch, colors[j], headerXLocation - 150 + 70 * j, headerYLocation - 200);
                    } else {
                        colorsFont.setColor(255, 255, 255, 1);
                        colorsFont.draw(batch, colors[j], headerXLocation - 150 + 70 * j, headerYLocation - 200);
                    }
                }

            }

        }

        batch.end();
        randAsteroids.draw();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_6) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_6)){
            userString += "6";
            System.out.println("entered: " + userString);
            System.out.println("secret " + secretString);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_5) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_5)){
            userString += "5";
            System.out.println("entered: " + userString);
            System.out.println("secret " + secretString);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_9) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_9)){
            userString += "9";
            System.out.println("entered: " + userString);
            System.out.println("secret " + secretString);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_3)){
            userString += "3";
            System.out.println(userString.compareTo(secretString));
            System.out.println("entered: " + userString);
            System.out.println("secret " + secretString);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            if(choice == subTitles.length - 1){
                choice = -1;
            } else {
                choice++;
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            if(choice == -1){
                choice = subTitles.length - 1;
            } else {
                choice--;
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            if(choice == 0){
                bMusic = !bMusic;
                System.out.println("bMusic: " + bMusic);
            } else if (choice == 1){
                bUpgrades = !bUpgrades;
                System.out.println("Upgrades: " + bUpgrades);
            }

            if(choice == 2){
                if(playerColorChoice == 0){
                    playerColorChoice = colors.length - 1;
                } else {
                    playerColorChoice--;
                }

                System.out.println(colors[playerColorChoice]);
                setPlayerColor(colorValues[playerColorChoice]);
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            if(choice == 0){
                bMusic = !bMusic;
                System.out.println("bMusic: " + bMusic);
            } else if (choice == 1){
                bUpgrades = !bUpgrades;
                System.out.println("Upgrades: " + bUpgrades);
            }

            if(choice == 2){
                if(playerColorChoice == colors.length - 1){
                    playerColorChoice = 0;
                } else {
                    playerColorChoice++;
                }
                System.out.println(colors[playerColorChoice]);
                setPlayerColor(colorValues[playerColorChoice]);

            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            if(choice == -1){
                gsm.setState(GameStateManager.MAINMENU);
            }
        }

    }

    @Override
    public void dispose() {

    }

    public static void setPlayerColor(Color c){
        playerColor = c;
    }

    public static Color getPlayerColor(){
        return playerColor;
    }

}
