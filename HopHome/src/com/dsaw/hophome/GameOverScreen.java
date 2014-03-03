package com.dsaw.hophome;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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
	
	public GameOverScreen(Game game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {    
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Act & Draw Stage First
		stage.act();
		stage.draw();
	}

	@Override
	public void show() {
	    //Set stage to be whole screen
	    stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
	    //Create a font style using libgdx font creator to make a .fnt file.
	    font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"), false);
	    //Set the BitmapFont color
	    style = new LabelStyle(font, Color.BLACK);
	    //Create a label with the style made above.
	    label = new Label("Game Over", style);
	    label.setPosition(Gdx.graphics.getWidth()/2 - label.getWidth()/2, Gdx.graphics.getHeight() - label.getHeight());

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
	    startButton.setPosition(Gdx.graphics.getWidth()/2 - startButton.getWidth()/2, Gdx.graphics.getHeight()/2 + startButton.getHeight());
	    
	    stage.addActor(startButton);
	    Gdx.input.setInputProcessor(stage);
	    startButton.addListener(new InputListener(){
	    	@Override
	    	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	    		game.setScreen(new GameScreen(game));
	    		return true;
	    	}
	    });
	    
	    extrasButton = new TextButton("Quit", buttonStyle);
	    extrasButton.setPosition(Gdx.graphics.getWidth()/2 - extrasButton.getWidth()/2, Gdx.graphics.getHeight()/2 - extrasButton.getHeight());
	    
	    stage.addActor(extrasButton);
	    Gdx.input.setInputProcessor(stage);
	    extrasButton.addListener(new InputListener(){
	    	@Override
	    	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
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
}
