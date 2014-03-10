package com.dsaw.hophome;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;

public class HopHome extends Game implements ApplicationListener {

	Preferences pref;
	
	@Override
	public void create() {
		pref = Gdx.app.getPreferences("HopHome_Pref");
		int highscore = pref.getInteger("highscore", -1);
		if (highscore == -1) {
			Assets.highscore = 0;
		}
		else {
		   Assets.highscore = highscore;	
		}
		Gdx.app.debug("HopHome", "Entered the create");
		Assets.load();
		Texture.setEnforcePotImages(false);
		Gdx.app.debug("OnCreate", "Loaded textures");
		setScreen(new StartMenu(this));
	}

	@Override
	public void dispose() {
		pref.putInteger("highscore", Assets.highscore);
		pref.flush();
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
