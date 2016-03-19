package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.processors.GameInputProcessor;
import com.mygdx.processors.GameKeys;
import com.mygdx.processors.GameStateManager;

public class Game implements ApplicationListener {

	public static int WIDTH;
	public static int HEIGHT;

	private GameStateManager gsm;
	public static OrthographicCamera cam;

	@Override
	public void create () {
		//Get width and height from configuration;
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		//Set camera to width and height of the game
		cam = new OrthographicCamera(WIDTH, HEIGHT);

		//Need to translate because the center of camera is at the origin
		cam.translate(WIDTH / 2, HEIGHT / 2);

		//Update camera to take on changes from translate
		cam.update();

		//Give libgdx custom input handler
		Gdx.input.setInputProcessor(new GameInputProcessor());

		//Create game state manager to handle states
		gsm = new GameStateManager();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void render () {
		//Clears the screen and sets to black background;
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.draw();
		//Update keys that are pressed
		GameKeys.update();
	}
}
