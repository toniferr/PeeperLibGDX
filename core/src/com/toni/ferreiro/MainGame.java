package com.toni.ferreiro;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MainGame extends ApplicationAdapter {
	
	@Override
	public void create () {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.3f, 0.3f , 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.justTouched()) {
			System.out.println("Estas tocando la pantalla.");
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.F4)){
			System.out.println("Estas tocando F4");
		}
	}
}
