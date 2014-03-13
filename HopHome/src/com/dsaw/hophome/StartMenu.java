package com.dsaw.hophome;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class StartMenu implements Screen{
	
	OrthographicCamera guiCam;
	//Background
	SpriteBatch batch;
	//Texture background;
	//Texture title;
	
	//Menu Screen Stuff
	private Stage stage;
	
	//Button
	TextureAtlas buttonAtlas; 
	TextButtonStyle buttonStyle; //Font, Color of text in button
	TextButton startButton;
	TextButton extrasButton;
	Skin buttonSkin;
	
	Game game;
	
	public static final int VIRTUAL_WIDTH = 480;
	public static final int VIRTUAL_HEIGHT = 720;
	public static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH/VIRTUAL_HEIGHT;
	
	public StartMenu(Game game) {
		this.game = game;
		
		guiCam = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		guiCam.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
		
		//Assets.startmusic.setVolume(0.7f);
		//Assets.startmusic.setLooping(true);
		Assets.playMusic(Assets.startmusic);
		
	    stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
	}
	
	@Override
	public void render(float delta) {    
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.enableBlending();
		Gdx.gl.glEnable(GL11.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		batch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		guiCam.update();
		batch.setProjectionMatrix(guiCam.combined);
		
		batch.begin();
		batch.draw(Assets.startmenubackground, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		batch.draw(Assets.title, 0, VIRTUAL_HEIGHT - 300, VIRTUAL_WIDTH, 300);
		batch.end();
	    Gdx.gl.glDisable(GL11.GL_BLEND);
		//Act & Draw Stage First
		stage.setCamera(guiCam);
		stage.act();
		stage.draw();
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		Texture.setEnforcePotImages(false);

	    startButton = new TextButton("Play", Assets.startbuttonStyle);
	    startButton.setWidth(VIRTUAL_WIDTH/2);
	    startButton.setHeight(VIRTUAL_HEIGHT/7);
	    startButton.setPosition(VIRTUAL_WIDTH/2 - startButton.getWidth()/2, VIRTUAL_HEIGHT/10 + startButton.getHeight() );
	    
	    stage.addActor(startButton);
	    Gdx.input.setInputProcessor(stage);
	    startButton.addListener(new InputListener(){
	    	@Override
	    	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	    		startButton.setColor(startButton.getColor().sub(0.1f, 0.1f, 0.1f, 0));
	    		return true;
	    	}
	    	
	    	@Override
	    	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	    		Assets.startmusic.stop();
	    		game.setScreen(new GameScreen(game, 0));
	    	}
	    });
	    
	    extrasButton = new TextButton("Options", Assets.startbuttonStyle);
	    extrasButton.setWidth(VIRTUAL_WIDTH/2);
	    extrasButton.setHeight(VIRTUAL_HEIGHT/7);
	    extrasButton.setPosition(VIRTUAL_WIDTH/2 - extrasButton.getWidth()/2, VIRTUAL_HEIGHT/10);
	    
	    stage.addActor(extrasButton);
	    Gdx.input.setInputProcessor(stage);
	    
	    extrasButton.addListener(new InputListener(){
	    	@Override
	    	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	    		extrasButton.setColor(extrasButton.getColor().sub(0.1f, 0.1f, 0.1f, 0));
	    		return true;
	    	}
	    	
	    	@Override
	    	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	    		Assets.startmusic.stop();
	    		game.setScreen(new OptionsScreen(game));
	    	}
	    });
        	    
	    //background = new Texture(Gdx.files.internal("Background1.png"));
	    //title = new Texture(Gdx.files.internal("Hop_Home_title.png"));
	}
	
	/* 
	 * Unused Methods
	 */
	@Override
	public void resize(int width, int height) {
	}
	
	@Override
	public void hide() {
		
		this.dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
