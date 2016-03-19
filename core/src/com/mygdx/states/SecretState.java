package com.mygdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.processors.GameStateManager;

/**
 * Created by Chris on 3/6/2016.
 */
public class SecretState extends GameState {
    private Texture secretImage;
    private Sound secretSound;
    private SpriteBatch batch;

    private BitmapFont font;

    private float timeSound;

    public SecretState(GameStateManager gsm){
        super(gsm);
    }

    @Override
    public void init() {
        secretImage = new Texture(Gdx.files.internal("secret.jpg"));
        secretSound = Gdx.audio.newSound(Gdx.files.internal("secretmp3.mp3"));
        batch = new SpriteBatch();
        timeSound = 0.0f;
        secretSound.play(.5f);
        FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Hyperspace Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 18;

        font = fontGen.generateFont(param);
        fontGen.dispose();
    }

    @Override
    public void update(float dt) {
        handleInput();
        if(timeSound >= 3){
            secretSound.stop();
        } else {
            timeSound += Gdx.graphics.getDeltaTime();
        }
    }

    @Override
    public void draw() {
        batch.begin();
        batch.draw(secretImage, 0, 0);
        font.draw(batch, "What's cookin' good lookin'?", 450, 240);
        font.draw(batch, "Press ESC to return.", 450, 200);
        batch.end();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            gsm.setState(GameStateManager.MAINMENU);
            secretSound.stop();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
