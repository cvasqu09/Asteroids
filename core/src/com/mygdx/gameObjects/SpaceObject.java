package com.mygdx.gameObjects;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Game;

/**
 * Created by Chris on 2/28/2016.
 */

//This is a super class which other space objects will inherit from
public class SpaceObject {
    //Position
    protected float x;
    protected float y;

    //Speed
    protected float dx;
    protected float dy;

    //Directional angle
    protected float radians;
    protected float speed;
    protected float rotationSpeed;

    //Size
    protected int width;
    protected int height;

    //Shape
    //Shape is constructed by a set of points to create lines from.
    protected float[] shapex;
    protected float[] shapey;

    protected  void wrap(){
        if(x < 0){
            x = Game.WIDTH;
        }

        if(x > Game.WIDTH){
            x = 0;
        }

        if(y < 0){
            y = Game.HEIGHT;
        }

        if(y > Game.HEIGHT){
            y = 0;
        }
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public float[] getShapex(){
        return shapex;
    }

    public float[] getShapey(){
        return shapey;
    }

}
