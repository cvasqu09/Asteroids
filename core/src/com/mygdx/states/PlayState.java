package com.mygdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Game;
import com.mygdx.gameObjects.Asteroid;
import com.mygdx.gameObjects.Bullet;
import com.mygdx.gameObjects.Player;
import com.mygdx.gameObjects.Saucer;
import com.mygdx.processors.GameKeys;
import com.mygdx.processors.GameStateManager;

import java.util.ArrayList;

public class PlayState extends GameState{

	private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Player player;
    private ArrayList<Bullet> bullets;
    private static ArrayList<Asteroid> asteroids;
    private BitmapFont font;

    private int level;
    private int totalAsteroids;
    private int numAsteroidsLeft;
    private Saucer saucer;

    //Adding music
    Music backgroundMusic;

    public PlayState(GameStateManager gsm){
        //Super calls the parent constructor with the gsm argument
        //The abstract class GameState needs gsm for its constructor
        super(gsm);
    }

    @Override
    public void init() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        //Font generator for the score
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("Hyperspace Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 20;
        font = gen.generateFont(param);
        gen.dispose();

        bullets = new ArrayList<Bullet>();
        asteroids = new ArrayList<Asteroid>();

        player = new Player(bullets);
        saucer = new Saucer(0, MathUtils.random(350, 450), player);

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Throne.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.2f);

        //Start music if setting is set
        if(SettingsState.bMusic){
            backgroundMusic.play();
        } else {
            backgroundMusic.stop();
        }


        level = 1;
        spawnAsteroids();
        givePlayerAsteroids(player);
    }

    private void checkBulletCollisions(){
        //Bullet asteroids
        for(int i = 0; i < bullets.size(); i++){
            for(int j = 0; j < asteroids.size(); j++){
                Bullet b = bullets.get(i);
                Asteroid a = asteroids.get(j);
                if(b.collidesWithAsteroid(a)){
                    player.updatePlayerScore(a.getType());
                    bullets.remove(b);
                    asteroids.remove(a);
                    splitAsteroid(a);

                    break;
                }

            }
            break;
        }

        //
    }

    private void spawnAsteroids(){
        asteroids.clear();
        int numToSpawn = 2 + level - 1;
        totalAsteroids = numToSpawn * 7;
        numAsteroidsLeft = totalAsteroids;

        for(int i = 0; i < numToSpawn; i++){
            //Dont want to spawn on top of player
            float x = MathUtils.random(Game.WIDTH);
            float y = MathUtils.random(Game.HEIGHT);

            float dx = x - player.getX();
            float dy = y - player.getY();
            float dist = (float) Math.sqrt(dx * dx + dy * dy);

            while(dist < 100){
                x = MathUtils.random(Game.WIDTH);
                y = MathUtils.random(Game.HEIGHT);

                dx = x - player.getX();
                dy = y - player.getY();
                dist = (float) Math.sqrt(dx * dx + dy * dy);
            }

            asteroids.add(new Asteroid(x, y, Asteroid.LARGE));
        }
    }

    private void givePlayerAsteroids(Player p){
        p.asteroids = asteroids;
    }

    private void splitAsteroid(Asteroid a){
        numAsteroidsLeft--;
        if(a.getType() == Asteroid.LARGE){
            asteroids.add(new Asteroid(a.getX(), a.getY(), Asteroid.MEDIUM));
            asteroids.add(new Asteroid(a.getX(), a.getY(), Asteroid.MEDIUM));
        } else if(a.getType() == Asteroid.MEDIUM){
            asteroids.add(new Asteroid(a.getX(), a.getY(), Asteroid.SMALL));
            asteroids.add(new Asteroid(a.getX(), a.getY(), Asteroid.SMALL));
        } else {
            return;
        }
    }

    private void newLevel(){
        level++;
        spawnAsteroids();
    }

    public static ArrayList<Asteroid> getAsteroids(){
        return asteroids;
    }

    @Override
    public void update(float dt) {
        if (player.getExtraLives() == 0){
            gsm.setState(GameStateManager.GAMEOVER);
        }

        if (numAsteroidsLeft == 0) {
            newLevel();
        }

        //Get user input
        handleInput();

        //Update plyaer
        player.update(dt);

        //Update player bullets
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).update(dt);
            if (bullets.get(i).shouldRemove()) {
                bullets.remove(i);
                i--;
            }
        }

        //Update asteroids
        for (int i = 0; i < asteroids.size(); i++) {
            asteroids.get(i).update(dt);
            if (asteroids.get(i).shouldRemove()) {
                asteroids.remove(i);
                i--;
            }
            //If the player is near check for collision
            if (asteroids.get(i).getCollisionCircle().contains(player.getX(), player.getY())) {
                player.checkShipCollision(asteroids.get(i));
            }
        }

        //Check bullet collision
        player.checkBulletCollision(saucer.getBullet());

        saucer.update(dt);

        //Check bullet collisions
        checkBulletCollisions();

    }

    @Override
    public void draw() {
        saucer.draw();
        //Draw player
        player.draw(shapeRenderer);

        //Draw bullets
        for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).draw(shapeRenderer);
        }

        //Draw asteroids
        for(int i = 0; i < asteroids.size(); i++){
            asteroids.get(i).draw(shapeRenderer);
        }

        //Draw score
        String livesString = "Lives: " + player.getExtraLives();
        batch.setColor(1, 1, 1, 1);
        batch.begin();
        font.draw(batch, Long.toString(player.getPlayerScore()), 100, 460);
        font.draw(batch, livesString, 100, 440);
        batch.end();
    }

    @Override
    public void handleInput() {
        player.setLeft(GameKeys.isDown(GameKeys.LEFT));
        player.setRight(GameKeys.isDown(GameKeys.RIGHT));
        player.setUp(GameKeys.isDown(GameKeys.UP));
        if(GameKeys.isPressed(GameKeys.SPACE)){
            player.shoot();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        shapeRenderer.dispose();

    }
}
