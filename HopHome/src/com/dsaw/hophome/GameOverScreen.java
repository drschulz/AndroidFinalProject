package com.dsaw.hophome;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class GameOverScreen implements Screen{
	public static final int VIRTUAL_WIDTH = 480;
	public static final int VIRTUAL_HEIGHT = 720;
	public static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH/VIRTUAL_HEIGHT;
	
	//Menu Screen Stuff
	private Stage stage;
	private Label label;
	//private LabelStyle style;
	private BitmapFont font;
	
	//Button
	//TextureAtlas buttonAtlas; 
	//TextButtonStyle buttonStyle; //Font, Color of text in button
	TextButton startButton;
	TextButton extrasButton;
	//Skin buttonSkin;
	
	Game game;
	int days;
	
	OrthographicCamera guiCam;
	
	SpriteBatch batch;
	Texture background;
	Texture gameOver;
	
	public GameOverScreen(Game game, int days) {
		this.game = game;
		this.days = days;
		if (days > Assets.highscore) {
			Assets.highscore = days;
			HopHome.pref.putInteger("highscore", Assets.highscore);
			HopHome.pref.flush();
		}
		batch = new SpriteBatch();
		Texture.setEnforcePotImages(false);
		
		guiCam = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		guiCam.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
		
		
		//Assets.pausemusic.setVolume(0.5f);
		//Assets.pausemusic.setLooping(true);
		Assets.playMusic(Assets.pausemusic);
	}
	
	@Override
	public void render(float delta) {    
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		guiCam.update();
		batch.setProjectionMatrix(guiCam.combined);
		
		batch.begin();
		batch.draw(background, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		batch.draw(gameOver, 40, VIRTUAL_HEIGHT - 200, gameOver.getWidth(), gameOver.getHeight());
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
	    font = Assets.font;//new BitmapFont(Gdx.files.internal("fonts/font.fnt"), false);
	    font.setScale(0.2f);
	    //Set the BitmapFont color
	    //style = Assets.blackstyle;//new LabelStyle(font, Color.BLACK);
	    //Create a label with the style made above.
	    label = new Label("You Survived: " + days + " Days\nBest is " + Assets.highscore, Assets.blackstyle);
	    label.setPosition(VIRTUAL_WIDTH/2 - label.getWidth()/2, VIRTUAL_HEIGHT - 300);
	    label.setAlignment(Align.center);

	    stage.addActor(label);

	    startButton = new TextButton("Retry", Assets.buttonStyle);
	    startButton.setPosition(0.0f, 0.0f);
	    startButton.setSize(VIRTUAL_WIDTH*0.8f, VIRTUAL_HEIGHT*0.3f);
	    startButton.setPosition(VIRTUAL_WIDTH/2 - startButton.getWidth()/2, 200);// + startButton.getHeight());
	    stage.addActor(startButton);
	    Gdx.input.setInputProcessor(stage);
	    startButton.addListener(new InputListener(){
	    	@Override
	    	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	    		return true;
	    	}	    	
	    	@Override
	    	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	    		Assets.pausemusic.stop();
	    		game.setScreen(new GameScreen(game, 0));
	    	}
	    });
	    
	    extrasButton = new TextButton("Quit ", Assets.buttonStyle);
	    extrasButton.setSize(VIRTUAL_WIDTH*0.8f, VIRTUAL_HEIGHT*0.3f);
	    extrasButton.setPosition(VIRTUAL_WIDTH/2 - extrasButton.getWidth()/2, 30);
	    stage.addActor(extrasButton);
	    Gdx.input.setInputProcessor(stage);
	    extrasButton.addListener(new InputListener(){
	    	@Override
	    	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	    		return true;
	    	}
	    	
	    	@Override
	    	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	    		Assets.pausemusic.stop();
	    		game.setScreen(new StartMenu(game));
	    	}
	    });
	    background = new Texture(Gdx.files.internal("gameOverBackground.png"));
	    System.out.println("got to end of show");
	    gameOver = new Texture(Gdx.files.internal("gameover.png"));
	    
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
