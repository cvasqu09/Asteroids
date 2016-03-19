package com.mygdx.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Chris on 3/5/2016.
 */
public class RandomAsteroidList {

    private ArrayList<Asteroid> randomAsteroids;
    private Random randomNumGen;
    private ShapeRenderer shapeRenderer;

    public RandomAsteroidList(){
        randomNumGen = new Random();

        //Create random asteroids to draw in the background
        randomAsteroids = new ArrayList<Asteroid>();

        //Create shapeRenderer to draw
        shapeRenderer = new ShapeRenderer();

        int randomNumAsteroids = randomNumGen.nextInt(10) + 1;
        for(int i = 0; i < randomNumAsteroids; i++){
            int randomSize = randomNumGen.nextInt(3) + 1;
            randomAsteroids.add(new Asteroid(MathUtils.random(Gdx.graphics.getWidth()),
                    MathUtils.random(Gdx.graphics.getHeight()), randomSize));
        }

    }

    public void update(float dt){
        //Move asteroids in background
        for(int i = 0; i < randomAsteroids.size(); i++){
            randomAsteroids.get(i).update(dt);
        }
    }

    public void draw(){
        //Draw random asteroids
        for(int i = 0; i < randomAsteroids.size(); i++){
            randomAsteroids.get(i).draw(shapeRenderer);
        }
    }

}
