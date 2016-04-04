package com.mygdx.processors;

import com.mygdx.states.GameOverState;
import com.mygdx.states.GameState;
import com.mygdx.states.MainMenu;
import com.mygdx.states.PlayState;
import com.mygdx.states.SecretState;
import com.mygdx.states.SettingsState;

public class GameStateManager {
    private GameState gameState;

    public static final int MAINMENU = 4;
    public static final int PLAY = 0;
    public static final int PAUSE = 3;
    public static final int SETTINGS = 2;
    public static final int HIGHSCORES = 1;
    public static final int SECRET = 666593;
    public static final int GAMEOVER = 5;

    public GameStateManager(){
        setState(MAINMENU);
    }

    public void setState(int state){
        if(gameState != null){
            gameState.dispose();
        }

        if(state == MAINMENU){
            //Switch to menu state
            gameState = new MainMenu(this);
        }

        if(state == PLAY){
            gameState = new PlayState(this);
        }

        if(state == PAUSE){
            //""
        }

        if(state == SETTINGS){
            gameState = new SettingsState(this);
        }

        if(state == SECRET){
            gameState = new SecretState(this);
        }

        if(state == GAMEOVER){
            gameState = new GameOverState(this);
        }
    }

    //Float dt represents change in time between current frame and last frame
    public void update(float dt){
        gameState.update(dt);
    }

    public void draw(){
        gameState.draw();
    }


}
