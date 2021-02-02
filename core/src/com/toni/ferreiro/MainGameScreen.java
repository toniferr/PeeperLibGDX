package com.toni.ferreiro;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MainGameScreen extends BaseScreen{

    public MainGameScreen(MainGame game) {
        super(game);
    }

    private Stage stage;

    @Override
    public void show() {
        stage = new Stage();
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
}
