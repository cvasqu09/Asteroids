package com.mygdx.gameObjects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Game;

/**
 * Created by Chris on 3/6/2016.
 */
public class Debris extends SpaceObject {
    public Debris(){
        x = 0;
        y = 0;
        shapex = new float[2];
        shapey = new float[2];

        dx = MathUtils.random(-5, 5);
        dy = MathUtils.random(-5, 5);

        radians = MathUtils.random(0, MathUtils.PI2);
        rotationSpeed = 3.0f;
    }

    public Debris(float posx, float posy, float x1, float y1, float x2, float y2){
        x = posx;
        y = posy;
        shapex = new float[2];
        shapey = new float[2];
        shapex[0] = x1;
        shapex[1] = x2;
        shapey[0] = y1;
        shapey[1] = y2;

        dx = MathUtils.random(-5, 5);
        dy = MathUtils.random(-5, 5);

        radians = MathUtils.random(0, MathUtils.PI2);
        rotationSpeed = 3.0f;
    }

    public void draw(ShapeRenderer sr){
        sr.line(shapex[0], shapey[0], shapex[1], shapey[1]);
    }

    public void update(float dt){
        x += dx * dt;
        y += dy * dt;
        radians += rotationSpeed * dt;

        for(int i = 0; i < 2; i++){
            shapex[i] += dx + dt;
            shapey[i] += dy * dt;
        }

        wrap();
    }

    public void setLine(float x1, float y1, float x2, float y2){
        shapex[0] = x1;
        shapex[1] = x2;
        shapey[0] = y1;
        shapey[1] = y2;
    }
}
