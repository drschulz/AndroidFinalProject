package com.dsaw.hophome;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HopHome extends Game implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Texture back;
	private Sprite sprite;
	private TextureRegion region;
	private TextureRegion background;
	
	@Override
	public void create() {
		Gdx.app.debug("HopHome", "Entered the create");
		Assets.load();
		Texture.setEnforcePotImages(false);
		Gdx.app.debug("OnCreate", "Loaded textures");
		setScreen(new GameScreen(this));
		
		/*float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(320, 480);
		//camera.position.set(320 / 2, 480/2, 0);
		batch = new SpriteBatch();
		System.out.print("here\n");
		Texture.setEnforcePotImages(false);
		texture = new Texture(Gdx.files.internal("data/rabbit.png"));
		back = new Texture(Gdx.files.internal("data/background.png"));
		System.out.print("here\n");
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		back.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		System.out.print("here\n");
		region = new TextureRegion(texture, 0, 0, 378, 593);
		background = new TextureRegion(back, 0, 0, 320, 480);
		
		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);*/
	}

	@Override
	public void dispose() {
		super.dispose();
		//batch.dispose();
		//texture.dispose();
	}

	@Override
	public void render() {	
		Texture.setEnforcePotImages(false);
		super.render();
		
		/*Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, -camera.viewportWidth/2, -camera.viewportHeight/2);
		batch.draw(region,-camera.viewportWidth/2 + camera.viewportWidth/4,-camera.viewportHeight/2 + camera.viewportHeight/4, camera.viewportWidth/4, camera.viewportHeight/4);
		//batch.draw(texture, 0, 0, camera.viewportWidth/2, camera.viewportHeight/2);
		//sprite.draw(batch);
		batch.end();*/
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
