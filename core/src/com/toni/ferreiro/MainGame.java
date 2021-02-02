package com.toni.ferreiro;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MainGame extends ApplicationAdapter {

	private Texture player1, pincho;
	private TextureRegion regionPinchos;

	private SpriteBatch batch;

	private int width, height;

	private int widhtPlayer1, heightPlayer1;
	
	@Override
	public void create () {
		player1 = new Texture("player1.png");
		pincho = new Texture("spike.png");

		batch = new SpriteBatch();
		regionPinchos = new TextureRegion(pincho, 80, 80);

		System.out.println("Pantalla: "+ Gdx.graphics.getWidth()+ " x "+Gdx.graphics.getHeight());
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		widhtPlayer1 = player1.getWidth();
		heightPlayer1 = player1.getHeight();
	}

	@Override
	public void dispose() {
		player1.dispose();
		batch.dispose();
		// alternativa, se puede limpiar la pantalla Gdx.gl.glClear
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0 , 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//se puede dibujar fuera de batch pero es necesario bind y otras herramientas

		batch.begin();
		batch.draw(player1, 50, 0);
//		batch.draw(player1, width -widhtPlayer1, 0);
//		batch.draw(player1, 0, height -heightPlayer1);
//		batch.draw(player1, width - widhtPlayer1, height - heightPlayer1);
//		batch.draw(player1, 100, 100, 300, 250);

		batch.draw(regionPinchos, 250, 0);
		batch.end();
	}
}
