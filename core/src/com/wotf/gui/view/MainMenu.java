/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wotf.gui.view;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.wotf.game.WotFGame;

/**
 *
 * @author Gebruiker
 */
public class MainMenu implements Screen {

    private Skin skin;
    private Stage stage;
    private SpriteBatch batch;
    private WotFGame game;

    public MainMenu(WotFGame game) {
        this.game = game;
    }

    private NinePatch getNinePatch(String fname) {

        // Get the image
        final Texture t = new Texture(Gdx.files.internal(fname));

        // create a new texture region, otherwise black pixels will show up too, we are simply cropping the image
        // last 4 numbers respresent the length of how much each corner can draw,
        // for example if your image is 50px and you set the numbers 50, your whole image will be drawn in each corner
        // so what number should be good?, well a little less than half would be nice
        return new NinePatch(new TextureRegion(t, 1, 1, t.getWidth() - 2, t.getHeight() - 2), 200, 200, 200, 200);
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);// Make the stage consume events

        // Pak het JSON bestand uit de assets folder. Hier staan alle skins in.
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Label wotflabel = new Label("War of the Figures", skin);
        wotflabel.setPosition(Gdx.graphics.getWidth() / 2 - wotflabel.getWidth() / 2, 740);
        stage.addActor(wotflabel);

        TextButton local = new TextButton("Local", skin); // Use the initialized skin
        local.setColor(Color.BLACK);
        local.setWidth(300);
        local.setHeight(60);
        local.setPosition(Gdx.graphics.getWidth() / 2 - local.getWidth() / 2, 500);
        local.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SessionLocal(game));
            }
        });
        stage.addActor(local);

        TextButton online = new TextButton("Online", skin); // Use the initialized skin
        online.setColor(Color.BLACK);
        online.setWidth(300);
        online.setHeight(60);
        online.setPosition(Gdx.graphics.getWidth() / 2 - online.getWidth() / 2, 430);
        online.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LobbyGUI(game));
            }
        });
        stage.addActor(online);

        TextButton about = new TextButton("About", skin); // Use the initialized skin
        about.setColor(Color.BLACK);
        about.setWidth(300);
        about.setHeight(60);
        about.setPosition(Gdx.graphics.getWidth() / 2 - about.getWidth() / 2, 360);
        stage.addActor(about);

        TextButton exit = new TextButton("Exit", skin); // Use the initialized skin
        exit.setColor(Color.BLACK);
        exit.setWidth(300);
        exit.setHeight(60);
        exit.setPosition(Gdx.graphics.getWidth() / 2 - exit.getWidth() / 2, 290);
        stage.addActor(exit);
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor((float) 122 / 255, (float) 122 / 255, (float) 122 / 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Passes the new width and height to the viewport
        stage.getViewport().update(width, height);
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
    public void hide() {
    }
}
