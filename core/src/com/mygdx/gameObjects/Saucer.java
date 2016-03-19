package com.mygdx.gameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

/**
 * Created by Chris on 3/16/2016.
 */
public class Saucer extends SpaceObject{
    private boolean hit;
    private Player target;
    private final float midLength = 15.0f;
    private final float topLength = 8.0f;
    private final float bottomLength = 8.0f;
    private final float glassLength = 6.0f;
    private final float bodyHeight = 5.0f;
    private final float glassHeight = 5.0f;

    private Color bodyColor = Color.GREEN;
    private Color glassColor = Color.MAGENTA;

    private ArrayList<Bullet> bullets;

    private ShapeRenderer shapeRenderer;

    public Saucer(float x, float y, Player player){
        this.x = x;
        this.y = y;

        dx = MathUtils.random(20, 50);
        dy = 0;

        shapex = new float[11];
        shapey = new float[11];

        radians = 0;
        rotationSpeed = 0;

        target = player;
        bullets = new ArrayList<Bullet>();
        shapeRenderer = new ShapeRenderer();
    }

    private void setShape(){
        shapex[0] = x;
        shapey[0] = y;

        shapex[1] = x + midLength / 2.0f;
        shapey[1] = y;

        shapex[2] = x + topLength / 2.0f;
        shapey[2] = y + bodyHeight;

        shapex[3] = x - topLength / 2.0f;
        shapey[3] = y + bodyHeight;

        shapex[4] = x - midLength / 2.0f;
        shapey[4] = y;

        shapex[5] = x - bottomLength / 2.0f;
        shapey[5] = y - bodyHeight;

        shapex[6] = x + bottomLength / 2.0f;
        shapey[6] = y - bodyHeight;

        shapex[7] = x + glassLength / 2.0f;
        shapey[7] = y + bodyHeight;

        shapex[8] = x + glassLength / 2.0f;
        shapey[8] = y + bodyHeight + glassHeight;

        shapex[9] = x - glassLength / 2.0f;
        shapey[9] = y + bodyHeight + glassHeight;

        shapex[10] = x - glassLength / 2.0f;
        shapey[10] = y + bodyHeight;
    }

    public void shoot(){

    }

    public void update(float dt){
        x += dx * dt;
        for(int i = 0; i < bullets.size(); i++){
            if(bullets.get(i).shouldRemove()){
                bullets.remove(i);
                i--;
            } else {
                bullets.get(i).update(dt);
            }
        }

        setShape();
        wrap();
    }

    public void draw(){
        shapeRenderer.setColor(bodyColor);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        //Draw the saucer body
        for(int i = 1; i <= 6; i++){
            int j = i + 1;
            if(i == 6){
                j = 1;
            }
            shapeRenderer.line(shapex[i], shapey[i], shapex[j], shapey[j]);
        }

        //Draw middle line on saucer body
        shapeRenderer.line(shapex[4], shapey[4], shapex[1], shapey[1]);

        //Draw saucer glass
        shapeRenderer.setColor(glassColor);
        for(int i = 7; i < shapex.length - 1; i++){
            int j = i + 1;
            shapeRenderer.line(shapex[i], shapey[i], shapex[j], shapey[j]);
        }

        shapeRenderer.end();
    }
}
