package com.dsaw.hophome;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class OptionsScreen implements Screen{

	//Menu Screen Stuff
	private Stage stage;
	private BitmapFont font;
	
	//Button
	TextButton soundButton;
	TextButton vibsButton;
	TextButton musicButton;
	TextButton returnButton;
	
	String soundText;
	String musicText;
	String vibText;
	
	Game game;
	int days;
	
	OrthographicCamera guiCam;
	
	SpriteBatch batch;
	Texture background;
	Texture gameOver;
	
	public static final int VIRTUAL_WIDTH = 480;
	public static final int VIRTUAL_HEIGHT = 720;
	public static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH/VIRTUAL_HEIGHT;
	
	public OptionsScreen(Game game) {
		this.game = game;
		batch = new SpriteBatch();
		Texture.setEnforcePotImages(false);
		
		guiCam = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		guiCam.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
		
		
		Assets.pausemusic.setVolume(0.5f);
		Assets.pausemusic.setLooping(true);
		Assets.pausemusic.play();
	}
	
	@Override
	public void render(float delta) {    
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		guiCam.update();
		batch.setProjectionMatrix(guiCam.combined);
		
		batch.begin();
		batch.end();
		
		//Act & Draw Stage First
		stage.setCamera(guiCam);
		stage.act();
		stage.draw();
	}

	@Override
	public void show() {
	    //Set stage to be whole screen
	    stage = new Stage(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, true);
	    //Create a font style using libgdx font creator to make a .fnt file.
	    font = Assets.font;
	    font.setScale(0.2f);
	    
	    returnButton = new TextButton(musicText, Assets.buttonStyle);
	    returnButton.setSize(VIRTUAL_WIDTH*0.5f, VIRTUAL_HEIGHT*0.3f);
	    returnButton.setPosition(-40, VIRTUAL_HEIGHT - returnButton.getHeight() + 50);
	    returnButton.setText("Return");
	    stage.addActor(returnButton);
	    Gdx.input.setInputProcessor(stage);
	    returnButton.addListener(new InputListener(){
	    	@Override
	    	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	    		return true;
	    	}	    	
	    	@Override
	    	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	    	   game.setScreen(new StartMenu(game));
	    	}
	    });
	    
	    if (!Settings.musicEnabled()) {
	    	musicText = "Music On";
	    }
	    else {
	    	musicText = "Music Off";
	    }
	    musicButton = new TextButton(musicText, Assets.buttonStyle);
	    musicButton.setPosition(0.0f, 0.0f);
	    musicButton.setSize(VIRTUAL_WIDTH*0.88f, VIRTUAL_HEIGHT*0.3f);
	    musicButton.setPosition(VIRTUAL_WIDTH/2 - musicButton.getWidth()/2, 370);// + startButton.getHeight());
	    stage.addActor(musicButton);
	    Gdx.input.setInputProcessor(stage);
	    musicButton.addListener(new InputListener(){
	    	@Override
	    	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	    		return true;
	    	}	    	
	    	@Override
	    	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	    	   if (Settings.musicEnabled()) {
	    		   Settings.setMusicEnabled(false);
	    		   musicButton.setText("Music On");
	    	   }
	    	   else {
	    		   Settings.setMusicEnabled(true);
	    		   musicButton.setText("Music Off");
	    	   }
	    	}
	    });
	    
	    if (!Settings.soundEnabled()) {
	    	soundText = "Sound On";
	    }
	    else {
	    	soundText = "Sound Off";
	    }
	    soundButton = new TextButton(soundText, Assets.buttonStyle);
	    soundButton.setPosition(0.0f, 0.0f);
	    soundButton.setSize(VIRTUAL_WIDTH*0.88f, VIRTUAL_HEIGHT*0.3f);
	    soundButton.setPosition(VIRTUAL_WIDTH/2 - soundButton.getWidth()/2, 200);// + startButton.getHeight());
	    stage.addActor(soundButton);
	    Gdx.input.setInputProcessor(stage);
	    soundButton.addListener(new InputListener(){
	    	@Override
	    	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	    		return true;
	    	}	    	
	    	@Override
	    	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	    	   if (Settings.soundEnabled()) {
	    		   Settings.setSoundEnabled(false);
	    		   soundButton.setText("Sound On");
	    	   }
	    	   else {
	    		   Settings.setSoundEnabled(true);
	    		   soundButton.setText("Sound Off");
	    	   }
	    	};
	    });
	    
	    if (!Settings.vibrateEnabled()) {
	    	vibText = "Vibrate On";
	    }
	    else {
	    	vibText = "Vibrate Off";
	    }
	    vibsButton = new TextButton(vibText, Assets.buttonStyle);
	    vibsButton.setSize(VIRTUAL_WIDTH*0.88f, VIRTUAL_HEIGHT*0.3f);
	    vibsButton.setPosition(VIRTUAL_WIDTH/2 - vibsButton.getWidth()/2, 30);
	    stage.addActor(vibsButton);
	    Gdx.input.setInputProcessor(stage);
	    vibsButton.addListener(new InputListener(){
	    	@Override
	    	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	    		return true;
	    	}
	    	
	    	@Override
	    	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	    		if (Settings.vibrateEnabled()) {
		    		   Settings.setVibrateEnabled(false);
		    		   vibsButton.setText("Vibrate On");
		    	   }
		    	   else {
		    		   Settings.setVibrateEnabled(true);
		    		   vibsButton.setText("Vibrate Off");
		    	   }
	    	}
	    });
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
		Assets.pausemusic.stop();
		stage.dispose();
	}
}
