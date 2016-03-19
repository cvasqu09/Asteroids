package com.mygdx.gameObjects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.states.SettingsState;

/**
 * Created by Chris on 2/28/2016.
 */
public class Bullet extends SpaceObject {
    //Determine how long a bullet has been on the screen and how long it can stay
    private float lifeTime;
    private float lifeTimer;

    private boolean remove;

    public Bullet(float x, float y, float radians){
        this.x = x;
        this.y = y;
        this.radians = radians;

        speed = 350;
        dx = MathUtils.cos(radians) * speed;
        dy = MathUtils.sin(radians) * speed;

        width = height = 2;
        lifeTimer = 1;
        lifeTime = 0;
    }

    public boolean shouldRemove(){
        return remove;
    }

    public void update(float dt){
        x += dx * dt;
        y += dy * dt;

        wrap();

        if(lifeTime > lifeTimer){
            remove = true;
        }

        lifeTime += dt;
    }

    public void draw(ShapeRenderer sr){
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(SettingsState.getPlayerColor());
        sr.circle(x - width / 2, y - height / 2, width / 2);
        sr.end();
    }

    public boolean collidesWithAsteroid(Asteroid a){
        if(Math.abs(x - a.getX()) <= 1.50 * a.width &&
                Math.abs(y - a.getY()) <= 1.5 * a.height){
            return true;
        }
        return false;
    }



}
