package com.dsaw.hophome;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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

public class StartMenu implements Screen{
	//Background
	SpriteBatch batch;
	Texture background;
	
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
	
	public StartMenu(Game game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {    
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
		
		//Act & Draw Stage First
		stage.act();
		stage.draw();
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		Texture.setEnforcePotImages(false);
		
	    //Set stage to be whole screen
	    stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
	    //Create a font style using libgdx font creator to make a .fnt file.
	    font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"), false);
	    //Set the BitmapFont color
	    style = new LabelStyle(font, Color.BLACK);
	    //Create a label with the style made above.
	    label = new Label("Hop Home", style);
	    label.setPosition(Gdx.graphics.getWidth()/2 - label.getWidth()/2, (float)(Gdx.graphics.getHeight()*0.8 - label.getHeight()));

	     stage.addActor(label);
	    
	    buttonSkin = new Skin();
	    buttonAtlas = new TextureAtlas("buttons/LeafButton.pack");
	    buttonSkin.addRegions(buttonAtlas);
	    
	    buttonStyle = new TextButtonStyle();
	    buttonStyle.up = buttonSkin.getDrawable("Leaf_3a");
	    buttonStyle.over = buttonSkin.getDrawable("Leaf_3a");
	    buttonStyle.down = buttonSkin.getDrawable("Leaf_3a");
	    buttonStyle.font = font;

	    startButton = new TextButton("    Start Game", buttonStyle);
	    startButton.setSize((float) (Gdx.graphics.getWidth()/(1.5)), Gdx.graphics.getHeight()/3);
	    startButton.setPosition(Gdx.graphics.getWidth()/2 - startButton.getWidth()/2, Gdx.graphics.getHeight()/10 + startButton.getHeight() );
	    
	    stage.addActor(startButton);
	    Gdx.input.setInputProcessor(stage);
	    startButton.addListener(new InputListener(){
	    	@Override
	    	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	    		game.setScreen(new GameScreen(game));
	    		return true;
	    	}
	    });
	    
	    extrasButton = new TextButton("    Extras", buttonStyle);
	    extrasButton.setSize((float) (Gdx.graphics.getWidth()/(1.5)), Gdx.graphics.getHeight()/3);
	    extrasButton.setPosition(Gdx.graphics.getWidth()/2 - extrasButton.getWidth()/2, Gdx.graphics.getHeight()/10);
	    
	    stage.addActor(extrasButton);
	    Gdx.input.setInputProcessor(stage);
	    /*
	    extrasButton.addListener(new InputListener(){
	    
	    	@Override
	    	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	    		//TODO
	    		return true;
	    	}
	    });
        */	    
	    background = new Texture(Gdx.files.internal("background1.jpg"));
	}
	
	/* 
	 * Unused Methods
	 */
	@Override
	public void resize(int width, int height) {
/*
		VirtualViewport virtualViewport = multipleVirtualViewportBuilder.getVirtualViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setVirtualViewport(virtualViewport);
		
		camera.updateViewport();
		// centers the camera at 0, 0 (the center of the virtual viewport)
		camera.position.set(0f, 0f, 0f);

		this.extrasButton.setPosition(virtualViewport.getVirtualWidth() * 0.5f - 80, virtualViewport.getVirtualHeight() * 0.5f - 80);
*/
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
