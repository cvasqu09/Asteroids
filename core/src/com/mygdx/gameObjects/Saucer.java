package com.mygdx.gameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;


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

    private Bullet saucerBullet;
    private ShapeRenderer shapeRenderer;

    private Rectangle collisionRect1;
    private Rectangle collisionRect2;

    private float timeFromLastShot = 0.0f;
    private float bulletFrequency = 2.0f;

    public Saucer(float x, float y, Player player){
        this.x = x;
        this.y = y;

        dx = MathUtils.random(20, 100);
        dy = 0;

        shapex = new float[11];
        shapey = new float[11];

        radians = 0;
        rotationSpeed = 0;

        target = player;
        shapeRenderer = new ShapeRenderer();
        collisionRect1 = new Rectangle(this.x - topLength / 2.0f, this.y - bodyHeight, topLength, bodyHeight * 2.0f);
        collisionRect2 = new Rectangle(this.x - midLength / 2.0f, this.y - 1.0f, midLength, 2.0f);
        saucerBullet = new Bullet();
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
        float distX = target.getX() - this.x;
        float distY = target.getY() - this.y;
        float directionToShoot = MathUtils.atan2(distY, distX);
        saucerBullet = new Bullet(this.x, this.y, directionToShoot);
    }

    public void update(float dt){
        x += dx * dt;

        //Update bullet
        saucerBullet.update(dt);

        //Check to see if saucer needs to shoot
        if(timeFromLastShot >= bulletFrequency){
            shoot();
            timeFromLastShot = 0.0f;
        } else {
            timeFromLastShot += dt;
        }

        //Update collision rectangles
        collisionRect1.x += dx * dt;
        collisionRect2.x += dx * dt;

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


        //Debug Line for path to target
        //shapeRenderer.line(this.x, this.y, target.getX(), target.getY());

        //Debug collision rectangles
        //shapeRenderer.rect(collisionRect1.x, collisionRect1.y, collisionRect1.width, collisionRect1.height);
        //shapeRenderer.rect(collisionRect2.x, collisionRect2.y, collisionRect2.width, collisionRect2.height);

        //Draw middle line on saucer body
        shapeRenderer.line(shapex[4], shapey[4], shapex[1], shapey[1]);

        //Draw saucer glass
        shapeRenderer.setColor(glassColor);
        for(int i = 7; i < shapex.length - 1; i++){
            int j = i + 1;
            shapeRenderer.line(shapex[i], shapey[i], shapex[j], shapey[j]);
        }

        shapeRenderer.end();

        //Draw bullet
        saucerBullet.draw(shapeRenderer);


    }


    public Bullet getBullet(){
        return saucerBullet;
    }
}
