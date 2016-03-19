package com.mygdx.processors;

/**
 * Created by Chris on 2/28/2016.
 */
public class GameKeys {
    private static final int NUM_KEYS = 8;
    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int DOWN = 2;
    public static final int RIGHT = 3;
    public static final int ENTER = 4;
    public static final int ESCAPE = 5;
    public static final int SPACE = 6;
    public static final int SHIFT = 7;

    //True = key pressed, false if not pressed
    private static boolean[] keys = new boolean[NUM_KEYS];

    //Previous state of the keys
    private static boolean[] pkeys = new boolean[NUM_KEYS];


    public static void update(){
        //Update the state of the keys
        for(int i = 0; i < NUM_KEYS; i++){
            pkeys[i] = keys[i];
        }
    }

    public static void setKey(int key, boolean b){
        keys[key] = b;
    }

    public static boolean isDown(int key){
        return keys[key];
    }

    public static boolean isPressed(int key){
        return keys[key] && !pkeys[key];
    }
}
