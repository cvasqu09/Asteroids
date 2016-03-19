package com.mygdx.states;


import com.mygdx.processors.GameStateManager;

public abstract class GameState {
    //Need reference to gsm to allow for switching between game states
    protected GameStateManager gsm;

    //Gets the state manager
    protected GameState(GameStateManager gsm){
        this.gsm = gsm;
        init();
    }

    //Methods that a GameState will use
    public abstract void init();
    public abstract void update(float dt);
    public abstract void draw();
    public abstract void handleInput();
    public abstract void dispose();

}
