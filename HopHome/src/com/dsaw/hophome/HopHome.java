package com.dsaw.hophome;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Texture;

public class HopHome extends Game implements ApplicationListener {

	@Override
	public void create() {
		Gdx.app.debug("HopHome", "Entered the create");
		Assets.load();
		Texture.setEnforcePotImages(false);
		Gdx.app.debug("OnCreate", "Loaded textures");
		setScreen(new StartMenu(this));
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {	
		Texture.setEnforcePotImages(false);
		super.render();
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
}
