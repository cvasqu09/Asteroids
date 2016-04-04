package com.mygdx.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Game;
import com.mygdx.states.PlayState;
import com.mygdx.states.SettingsState;

import java.util.ArrayList;
import java.util.Random;

public class Player extends SpaceObject {
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean hit;

    private float maxSpeed;
    private float acceleration;
    private float deceleration;
    //For animating flame on back of spaceship
    private float acceleratingTimer;

    //Drawing flame at the bottom of the ship
    private float flamex[];
    private float flamey[];

    //List of bullets that the player shoots
    private ArrayList<Bullet> bullets;
    private final int maxBullets = 4;

    //Explosion Time
    private float explosionTime = 0.0f;
    private float explosionTimer = 3.0f;

    //Lives and player score
    private static long playerScore;
    private int extraLives;
    private long extraLifeScore;

    //Sound for shooting
    private Sound shootSFX;
    private Sound hitSFX;

    private boolean dead;

    private Color playerColor;

    //Debris once the player is hit
    private Debris leftDebris;
    private Debris rightDebris;
    private Debris bottomDebris;

    //Asteroid list for respawn check
    public static ArrayList<Asteroid> asteroids;


    public Player(ArrayList<Bullet> bullets){
        //Position
        x = Game.WIDTH / 2;
        y = Game.HEIGHT / 2;

        //Pixels per second
        maxSpeed = 300;
        acceleration = 200;
        deceleration = 10;

        shapex = new float[4];
        shapey = new float[4];

        //Start facing up
        radians = 3.1415f / 2;
        rotationSpeed = 3.0f;

        flamex = new float[3];
        flamey = new float[3];

        leftDebris = new Debris();
        rightDebris = new Debris();
        bottomDebris = new Debris();

        this.bullets = bullets;

        playerScore = 0;
        extraLives = 3;
        extraLifeScore = 10000;

        shootSFX = Gdx.audio.newSound(Gdx.files.internal("asteroids_shoot.wav"));
        playerColor = SettingsState.getPlayerColor();

        asteroids = new ArrayList<Asteroid>();
    }

    public void shoot(){
        if(bullets.size() >= maxBullets){
            return;
        }

        //Adda  bullet starting from player position and direction
        bullets.add(new Bullet(x, y, radians));
        shootSFX.play();
    }

    private void setShape(){
        //Top point for the ship
        //cos(radians should be straight up and 8 pixels forward
        shapex[0] = x + MathUtils.cos(radians) * 8;
        shapey[0] = y + MathUtils.sin(radians) * 8;

        //Bottom left point
        shapex[1] = x + MathUtils.cos(radians - 4 * MathUtils.PI / 5) * 8;
        shapey[1] = y + MathUtils.sin(radians - 4 * MathUtils.PI / 5) * 8;

        //Point below
        shapex[2] = x + MathUtils.cos(radians + MathUtils.PI) * 5;
        shapey[2] = y + MathUtils.sin(radians + MathUtils.PI) * 5;

        //Bottom right point
        shapex[3] = x + MathUtils.cos(radians + 4 * MathUtils.PI / 5) * 8;
        shapey[3] = y + MathUtils.sin(radians + 4 * MathUtils.PI / 5) * 8;

        leftDebris.setLine(shapex[0], shapey[0], shapex[1], shapey[1]);
        rightDebris.setLine(shapex[0], shapey[0], shapex[3], shapey[3]);
        bottomDebris.setLine(shapex[1], shapey[1], shapex[3], shapey[3]);
    }

    private void setFlame(){
        flamex[0] = x + MathUtils.cos(radians - 5 * MathUtils.PI / 6) * 5;
        flamey[0] = y + MathUtils.sin(radians - 5 * MathUtils.PI / 6) * 5;

        flamex[1] = x + MathUtils.cos(radians - MathUtils.PI) * (6 + acceleratingTimer * 50);
        flamey[1] = y + MathUtils.sin(radians - MathUtils.PI) * (6 + acceleratingTimer * 50);

        flamex[2] = x + MathUtils.cos(radians + 5 * MathUtils.PI / 6) * 5;
        flamey[2] = y + MathUtils.sin(radians + 5 * MathUtils.PI / 6) * 5;
    }

    public void setLeft(boolean b){
        left = b;
    }

    public void setRight(boolean b){
        right = b;
    }

    public void setUp(boolean b){
        up = b;
    }

    public void checkShipCollision(Asteroid a){
        //Check left and right wing points and top point
        if(Math.abs(shapex[0] - a.getX()) <= a.width / 2 &&
                Math.abs(shapey[0] - a.getY()) <= a.height / 2){
            hit = true;
        }

        if(Math.abs(shapex[1] - a.getX()) <= a.width / 2 &&
                Math.abs(shapey[1] - a.getY()) <= a.height / 2){
            hit = true;
        }

        if(Math.abs(shapex[3] - a.getX()) <= a.width / 2 &&
                Math.abs(shapey[3] - a.getY()) <= a.height / 2){
            hit = true;
        }
    }

    private void explode(float dt){
        leftDebris.update(dt);
        rightDebris.update(dt);
        bottomDebris.update(dt);
    }

    private void respawn(){
        System.out.println("Entering respawn function.");
        if(extraLives > 0){
            removeLife();
            x = Game.WIDTH / 2;
            y = Game.HEIGHT / 2;

            //Pixels per second
            maxSpeed = 300;
            acceleration = 200;
            deceleration = 10;

            shapex = new float[4];
            shapey = new float[4];

            //Start facing up
            radians = 3.1415f / 2;
            rotationSpeed = 3.0f;

            flamex = new float[3];
            flamey = new float[3];

            leftDebris = new Debris();
            rightDebris = new Debris();
            bottomDebris = new Debris();

            dx = 0.0f;
            dy = 0.0f;

            explosionTime = 0.0f;
            explosionTimer = 3.0f;

            while(hit == true) {
                hit = false;
                for (int i = 0; i < asteroids.size(); i++) {
                    checkShipCollision(asteroids.get(i));
                }
            }

            dead = false;
            setShape();

        }

    }

    public void update(float dt) {
        //If hit
        //System.out.println(hit);

        //Debugging
        //System.out.println("explosion time: " + explosionTime);
        //System.out.println("exp timer: " + explosionTimer);
        if(hit){
            //System.out.println("hit update");

            //Create ship debris for when the ship is hit
            if(explosionTime <= explosionTimer) {
                //Debugging
                //System.out.println("exp time: " + explosionTime);
                //System.out.println("x: " + leftDebris.shapex[0]);
                //System.out.println("y: " + leftDebris.shapey[0]);

                explosionTime += dt;
                explode(dt);
            } else {
                //System.out.println("In respawn");
                explosionTimer = 0;
                respawn();
                //System.out.println("Exiting respawn");
            }
            //System.out.println("Exiting hit update");
        } else {
            //Turning
            if(left){
                radians += rotationSpeed * dt;
            } else if(right){
                radians -= rotationSpeed * dt;
            }

            //Accelerating
            if(up){
                dx += MathUtils.cos(radians) * acceleration * dt;
                dy += MathUtils.sin(radians) * acceleration * dt;
                acceleratingTimer += dt;
                if(acceleratingTimer > 0.1f){
                    acceleratingTimer = 0;
                }
            } else {
                acceleratingTimer = 0;
            }

            //Deceleration
            //dx, dy resultant
            float vec = (float) Math.sqrt(dx * dx + dy * dy);
            if(vec > 0){
                //Normalize the vec vector
                dx -= (dx / vec) * deceleration * dt;
                dy -= (dy / vec) * deceleration * dt;
            }

            if(vec > maxSpeed){
                dx = (dx / vec) * maxSpeed;
                dy = (dy / vec) * maxSpeed;
            }

            //set position
            x += dx * dt;
            y += dy * dt;

            //set shape
            setShape();


            if(up){
                setFlame();
            }
            //screen wrap if players goes off one of the ends
            wrap();
        }
    }

    public void draw(ShapeRenderer shapeRenderer){
        //Set color to white
        shapeRenderer.setColor(playerColor);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        if(hit){
            //System.out.println("Hit draw");
            dead = true;
            if(explosionTime <= explosionTimer) {
                //System.out.println("exploding");
                leftDebris.draw(shapeRenderer);
                rightDebris.draw(shapeRenderer);
                bottomDebris.draw(shapeRenderer);
            }
            //System.out.println("Exiting hit draw");
        }

        if(!dead){
            //Draw ship
            //Draw a line from each point to the next point in the shape arrays.
            for(int i = 0; i <= shapex.length - 1; i++){
                int j;
                if(i == shapex.length - 1){
                    j = 0;
                } else {
                    j = i + 1;
                }
                shapeRenderer.line(shapex[i], shapey[i], shapex[j], shapey[j]);
            }

            shapeRenderer.setColor(Color.WHITE);
            //Draw flame on back of ship.
            if(up){
                for(int i = 0; i <= flamex.length - 1; i++){
                    int j;
                    if(i == flamex.length - 1){
                        j = 0;
                    } else {
                        j = i + 1;
                    }
                    shapeRenderer.line(flamex[i], flamey[i], flamex[j], flamey[j]);
                }
            }
        }

        shapeRenderer.end();
    }

    public static long getPlayerScore(){
        return playerScore;
    }

    public int getExtraLives(){
        return extraLives;
    }

    public void updatePlayerScore(int asteroidType){
        if(playerScore >= extraLifeScore){
            extraLifeScore += extraLifeScore;
            addLife();
        }

        if(asteroidType == Asteroid.SMALL){
            playerScore += 100;
        }

        if(asteroidType == Asteroid.MEDIUM){
            playerScore += 50;
        }

        if(asteroidType == Asteroid.LARGE){
            playerScore += 20;
        }
    }

    public boolean isDead(){
        return dead;
    }

    public boolean isHit(){
        return hit;
    }

    public void removeLife(){
        extraLives -= 1;
    }

    public void addLife(){
        extraLives += 1;
    }

}
