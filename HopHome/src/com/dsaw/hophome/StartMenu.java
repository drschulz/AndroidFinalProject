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
	Texture background;
	Texture title;
	
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
	
	public static final int VIRTUAL_WIDTH = 480;
	public static final int VIRTUAL_HEIGHT = 720;
	public static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH/VIRTUAL_HEIGHT;
	
	public StartMenu(Game game) {
		this.game = game;
		
		guiCam = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		guiCam.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
		
		Assets.startmusic.setVolume(0.7f);
		Assets.startmusic.setLooping(true);
		Assets.startmusic.play();
		
		//Set stage to be whole screen
	    stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
	    //Create a font style using libgdx font creator to make a .fnt file.
	    font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"), false);
	    //Set the BitmapFont color
	    style = new LabelStyle(font, Color.BLACK);
	    //Create a label with the style made above.
	    label = new Label("Hop Home", style);
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
		batch.draw(background, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		batch.draw(title, 0, VIRTUAL_HEIGHT - 300, VIRTUAL_WIDTH, 300);
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
		
	    buttonSkin = new Skin();
	    buttonAtlas = new TextureAtlas("buttons/CarrotButton.pack");
	    buttonSkin.addRegions(buttonAtlas);
	    
	    buttonStyle = new TextButtonStyle();
	    buttonStyle.up = buttonSkin.getDrawable("carrot");
	    buttonStyle.over = buttonSkin.getDrawable("carrot");
	    buttonStyle.down = buttonSkin.getDrawable("carrot");
	    buttonStyle.font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"), false);
	    buttonStyle.font.setScale(0.3f);

	    startButton = new TextButton("Play", buttonStyle);
	    startButton.setWidth(VIRTUAL_WIDTH/2);
	    startButton.setHeight(VIRTUAL_HEIGHT/7);
	    startButton.setPosition(VIRTUAL_WIDTH/2 - startButton.getWidth()/2, VIRTUAL_HEIGHT/10 + startButton.getHeight() );
	    
	    stage.addActor(startButton);
	    Gdx.input.setInputProcessor(stage);
	    startButton.addListener(new InputListener(){
	    	@Override
	    	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	    		startButton.setColor(startButton.getColor().sub(0.1f, 0.1f, 0.1f, 0));
	    		Assets.startmusic.stop();
	    		game.setScreen(new GameScreen(game, 0));
	    		return true;
	    	}
	    });
	    
	    //extrasButton = new TextButton("    Extras", buttonStyle);
	    //extrasButton.setSize((float) (Gdx.graphics.getWidth()/(1.5)), Gdx.graphics.getHeight()/3);
	    //extrasButton.setPosition(Gdx.graphics.getWidth()/2 - extrasButton.getWidth()/2, Gdx.graphics.getHeight()/10);
	    
	    //stage.addActor(extrasButton);
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
	    background = new Texture(Gdx.files.internal("Background1.png"));
	    title = new Texture(Gdx.files.internal("Hop_Home_title.png"));
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
