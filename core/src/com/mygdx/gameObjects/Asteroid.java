package com.mygdx.gameObjects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Chris on 2/28/2016.
 */
public class Asteroid extends SpaceObject{
    private int type;
    public static final int SMALL = 0;
    public static final int MEDIUM = 1;
    public static final int LARGE = 2;

    //For creating random sized asteroids
    private int numPoints;
    //distance from the radius
    private float dists[];

    private boolean remove;

    private Circle collisionCircle;

    public Asteroid(float x, float y, int type){
        this.x = x;
        this.y = y;
        this.type = type;

        if(type == SMALL){
            numPoints = 8;
            width = height = 12;
            speed = MathUtils.random(70, 100);
        }else if(type == MEDIUM){
            numPoints = 10;
            width = height = 20;
            speed = MathUtils.random(50, 60);
        }else {
            numPoints = 12;
            width = height = 40;
            speed = MathUtils.random(20, 30);
        }

        //Random rotational speed
        rotationSpeed = MathUtils.random(-1, 1);

        //Random direction
        radians = MathUtils.random(2 * MathUtils.PI);

        dx = MathUtils.cos(radians) * speed;
        dy = MathUtils.sin(radians) * speed;

        shapex = new float[numPoints];
        shapey = new float[numPoints];
        dists = new float[numPoints];

        int radius = width / 2;
        for(int i = 0; i < numPoints; i++){
            dists[i] = MathUtils.random(radius / 2, radius);
        }

        collisionCircle = new Circle(x, y, width + 30);

        setShape();
    }

    private void setShape(){
        float angle = 0;
        for(int i = 0; i < numPoints; i++){
            shapex[i] = x + MathUtils.cos(radians + angle) * dists[i];
            shapey[i] = y + MathUtils.sin(radians + angle) * dists[i];
            //Move the angle for the next point
            angle += 2 * MathUtils.PI / numPoints;
        }
    }

    public int getType(){
        return type;
    }

    public Circle getCollisionCircle(){
        return collisionCircle;
    }

    public boolean shouldRemove(){
        return remove;
    }

    public void update(float dt){
        x += dx * dt;
        y += dy * dt;
        radians += rotationSpeed * dt;

        setShape();

        collisionCircle.x = x;
        collisionCircle.y = y;

        wrap();
    }

    public void draw(ShapeRenderer sr) {
        sr.setColor(1, 1, 1, 1);
        sr.begin(ShapeRenderer.ShapeType.Line);

        for(int i = 0; i < shapex.length; i++){
            int j;
            if(i == shapex.length - 1){
                j = 0;
            } else {
                j = i + 1;
            }
            sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
        }

        sr.end();

    }

}
