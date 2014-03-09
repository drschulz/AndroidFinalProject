package com.dsaw.hophome;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class GameOverScreen implements Screen{

	//Menu Screen Stuff
	private Stage stage;
	private Label label;
	private LabelStyle style;
	private BitmapFont font;
	
	//Button
	TextureAtlas buttonAtlas; 
	TextButtonStyle buttonStyle; //Font, Color of text in button
	TextButton startButton;
	TextButton extrasButton;
	Skin buttonSkin;
	
	Game game;
	int days;
	
	OrthographicCamera guiCam;
	
	public static final int VIRTURAL_WIDTH = 320;
	public static final int VIRTURAL_HEIGHT = 480;
	public static final float ASPECT_RATIO = (float)VIRTURAL_WIDTH/VIRTURAL_HEIGHT;
	
	public GameOverScreen(Game game, int days) {
		this.game = game;
		this.days = days;
		
		guiCam = new OrthographicCamera(VIRTURAL_WIDTH, VIRTURAL_HEIGHT);
		guiCam.position.set(VIRTURAL_WIDTH / 2, VIRTURAL_HEIGHT / 2, 0);
		
		
		Assets.pausemusic.setVolume(0.5f);
		Assets.pausemusic.setLooping(true);
		Assets.pausemusic.play();
	}
	
	@Override
	public void render(float delta) {    
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Act & Draw Stage First
		stage.setCamera(guiCam);
		stage.act();
		stage.draw();
	}

	@Override
	public void show() {
	    //Set stage to be whole screen
	    stage = new Stage(VIRTURAL_WIDTH, VIRTURAL_HEIGHT, true);
	    //Create a font style using libgdx font creator to make a .fnt file.
	    font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"), false);
	    //Set the BitmapFont color
	    style = new LabelStyle(font, Color.BLACK);
	    //Create a label with the style made above.
	    label = new Label("Game Over\nYou Survived: " + days + " Days", style);
	    label.setFontScale(ASPECT_RATIO);
	    label.setPosition(0, VIRTURAL_HEIGHT - label.getHeight());

	    stage.addActor(label);
	    
	    buttonSkin = new Skin();
	    buttonAtlas = new TextureAtlas("buttons/button.pack");
	    buttonSkin.addRegions(buttonAtlas);
	    
	    buttonStyle = new TextButtonStyle();
	    buttonStyle.up = buttonSkin.getDrawable("button");
	    buttonStyle.over = buttonSkin.getDrawable("buttonPressed");
	    buttonStyle.down = buttonSkin.getDrawable("buttonPressed");
	    buttonStyle.font = font;
	    

	    startButton = new TextButton("Retry", buttonStyle);
	    startButton.setSize(320f*0.5f, 480f/10.0f);
	    startButton.setPosition(VIRTURAL_WIDTH/2 - startButton.getWidth()/2, VIRTURAL_HEIGHT/2 + startButton.getHeight());
	    
	    stage.addActor(startButton);
	    Gdx.input.setInputProcessor(stage);
	    startButton.addListener(new InputListener(){
	    	@Override
	    	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	    		Assets.pausemusic.stop();
	    		game.setScreen(new GameScreen(game, 0));
	    		return true;
	    	}
	    });
	    
	    extrasButton = new TextButton("Quit", buttonStyle);
	    extrasButton.setSize(320f*0.5f, 480f/10.0f);
	    extrasButton.setPosition(VIRTURAL_WIDTH/2 - extrasButton.getWidth()/2, VIRTURAL_HEIGHT/2 - extrasButton.getHeight());
	    
	    stage.addActor(extrasButton);
	    Gdx.input.setInputProcessor(stage);
	    extrasButton.addListener(new InputListener(){
	    	@Override
	    	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	    		Assets.pausemusic.stop();
	    		game.setScreen(new StartMenu(game));
	    		return true;
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
		stage.dispose();
	}
}
