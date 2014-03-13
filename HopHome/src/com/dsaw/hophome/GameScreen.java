package com.dsaw.hophome;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.dsaw.hophome.World.WorldListener;

public class GameScreen implements Screen {
	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;
	static final int BUNNY = 0;
	static final int LOG = 1;
	static final int BEAR = 2;
	static final int TREE = 3;
	static final int BIRD = 4;
	public static final int VIRTUAL_WIDTH = 480;
	public static final int VIRTUAL_HEIGHT = 720;
	public static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH/VIRTUAL_HEIGHT;
	
	private Game game;
	private int state;
	private OrthographicCamera guiCam;
	private Vector3 touchPoint;
	private SpriteBatch batcher;
	private WorldListener worldListener;
	private ShapeRenderer shapeRenderer;
	private World world;
	private WorldRenderer wRenderer;
	
	private int[] actions;
	private float bufferTime;
	private Random rand;
	private boolean triggered;
	private int act;
	
	private Stage stage;
	private int days;
	private Label dayLabel;
	private Label hungerLabel;
	private LabelStyle style;
	private BitmapFont font;
	private int hungerLevel = 100;
	private float ObsRate;
	
	private Button pauseButton;
	private Image carrotImg;
	//Button
	
	private GameScreen curScreen;
	

	
	public GameScreen(final Game game, int numdays) {
		this.game = game;
		state = GAME_RUNNING;
		this.days = numdays;
		curScreen = this;
		actions = new int[5];
		rand = new Random();
		bufferTime = 0;
		triggered = false;
		act = 0;
		ObsRate = 3.0f;
		
		initWorldListener();
		world = new World(worldListener);
		
		guiCam = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		guiCam.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
		
		touchPoint = new Vector3();
		batcher = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		wRenderer = new WorldRenderer(batcher, world);
	    setUpStage();
	    
		Assets.stopMusic(Assets.gamemusic);
		Assets.playMusic(Assets.gamemusic);
	}
	
	public void setUpStage() {
		Texture carrot;
		TextureRegion carrotRegion;
		
		//Set stage to be whole screen
	    stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
	    //Create a font style using libgdx font creator to make a .fnt file.
	    font = Assets.font;
	    //Set the BitmapFont color
	    style = Assets.whitestyle;
	    //Create a label with the style made above.
	    font.setScale(0.2f);
	    dayLabel = new Label("Day: ", style);
	    //   .setSize(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/10);
	    dayLabel.setPosition(0, VIRTUAL_HEIGHT - 38);
	    stage.addActor(dayLabel);
	    
	    stage.addActor(new Actor() {
	    	@Override
	    	public Actor hit(float arg0, float arg1, boolean touchable) {return null;}
	    	@Override
	    	public void draw(SpriteBatch batch, float arg1) {
	    		batch.end();
	    		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
	    		shapeRenderer.setColor(Color.GRAY);
	    		shapeRenderer.rect(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/8);
	    		shapeRenderer.end();
	    		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
	    		shapeRenderer.setColor(Color.BLACK);
	    		shapeRenderer.rect(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/8);
	    		shapeRenderer.end();
	    		batch.begin();
	    	}
	    });
	    
	    pauseButton = new Button(Assets.pausebuttonStyle);
	    pauseButton.setSize(50, 50);
	    pauseButton.setPosition(VIRTUAL_WIDTH - pauseButton.getWidth(), VIRTUAL_HEIGHT - pauseButton.getHeight() );
	    pauseButton.addListener(new InputListener(){
	    	@Override
	    	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	    		System.out.println("in touch down!");
	    		
	    		return true;
	    	}
	    	
	    	@Override
	    	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {		
		    	//Assets.gamemusic.stop();
	    		game.setScreen(new PauseScreen(game, days, curScreen));
	    	}
	    });
	    stage.addActor(pauseButton);
	   
	    
	    
	  //Create a label with the style made above.
	    hungerLabel = new Label("Energy: 100%", style);
	    hungerLabel.setPosition(VIRTUAL_WIDTH - 250, VIRTUAL_HEIGHT - 38);
	    stage.addActor(hungerLabel);
	    
	    carrot = new Texture(Gdx.files.internal("data/carrot.png"));
	    carrotRegion = new TextureRegion(carrot);
	    carrotImg = new Image(carrotRegion);
	    carrotImg.setSize(VIRTUAL_WIDTH/3, VIRTUAL_HEIGHT/8);
	    carrotImg.setPosition(VIRTUAL_WIDTH/2 - carrotImg.getWidth()/2, 0);
	    
		carrotImg.addListener(new InputListener(){
	    	@Override
	    	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	    		carrotImg.setColor(carrotImg.getColor().sub(0.1f, 0.1f, 0.1f, 0));
	    		Assets.vibrate(100);
	    		return true;
	    	}
	    	@Override
	    	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	    		carrotImg.setColor(carrotImg.getColor().add(0.1f, 0.1f, 0.1f, 0));
	    		//Assets.vibrate();
	    		hungerLevel = 100;
	    	}
	    });
		stage.addActor(carrotImg);
	}
	
	public void initWorldListener() {
		worldListener = new WorldListener() {

			@Override
			public void jump() {
				Assets.playSound(Assets.jumpsound);
				
			}

			@Override
			public void duck() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void dodgeLeft() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void dodgeRight() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void hitObject() {
				Assets.stopMusic(Assets.gamemusic);
				Assets.vibrate(200);
				
			}

			@Override
			public void incDay() {
				days++;
				hungerLevel -= 20;
				incObsRate();
				// TODO Auto-generated method stub
				
			}

			@Override
			public void bunnyDie() {
				game.setScreen(new GameOverScreen(game, days));
				
			}

			@Override
			public void incObsRate() {
				if(ObsRate > 0.2) {
					ObsRate -= 0.4f;
				}
			}

			
		};
	}
	
	public void update (float deltaTime) {
		if (deltaTime > 0.1f) deltaTime = 0.1f;
		switch (state) {
		case GAME_READY:
			updateReady();
			break;
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
		case GAME_PAUSED:
			updatePaused();
			break;
		case GAME_LEVEL_END:
			updateLevelEnd();
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		}
	}

	private void updateGameOver() {
		// TODO Auto-generated method stub
		
	}

	private void updateLevelEnd() {
		// TODO Auto-generated method stub
		
	}

	private void updatePaused() {
		
	}

	private void createObstacle(float deltaTime) {
		if(triggered) {
			if(bufferTime > 1.0) {
				actions[act] = World.CREATE_NEW;
				bufferTime = 0;
				triggered = false;
				world.bunny.mode = Bunny.MODE_NORM;
			}
			else {
				bufferTime += deltaTime;
			}
		}
		else {
			if(bufferTime > ObsRate) {
				bufferTime = 0;
				act = rand.nextInt()%4 + 1;
				switch(act) {
				case LOG:
					if(world.log.state == Log.STATE_DEAD) {
						world.bunny.bugEyes();
						//world.bunny.mode = Bunny.MODE_BUGEYE;
						triggered = true;
					}
					break;
				case BEAR:
					if(world.bear.state == Bear.STATE_DEAD) {
						world.bunny.lookRight();
						//world.bunny.mode = Bunny.MODE_LOOKRIGHT;
						triggered = true;
					}
					break;
				case TREE:
					if(world.tree.state == Tree.STATE_DEAD) {
						world.bunny.lookLeft();
						//world.bunny.mode = Bunny.MODE_LOOKLEFT;
						triggered = true;
					}
					break;
				case BIRD:
					if(world.bird.state == Bird.STATE_DEAD) {
						world.bunny.closeEyes();
						//world.bunny.mode = Bunny.MODE_CLOSEEYE;
						triggered = true;
					}
					break;
				default:
					break;
				}
			}
			else {
				bufferTime += deltaTime;
			}
		}
	}
	
	private void updateRunning(float deltaTime) {
		int changeX;
		int changeY;
		
		actions[BUNNY] = Bunny.ACT_STATIC;
		actions[LOG] = World.STAY_AS_IS;
		actions[BEAR] = World.STAY_AS_IS;
		actions[TREE] = World.STAY_AS_IS;
		actions[BIRD] = World.STAY_AS_IS;
		
		createObstacle(deltaTime);
		
		if (Gdx.input.isTouched()) {
			changeX = Gdx.input.getDeltaX();
			changeY = Gdx.input.getDeltaY();
			System.out.print("x change: " + changeX + ", y change: " + changeY +"\n");
			
			
			if(Math.abs(changeX) > Math.abs(changeY) && Math.abs(changeX) > 15) {
				if(changeX > 0) {
					actions[BUNNY] = Bunny.ACT_RIGHT; 
				}
				else {
					actions[BUNNY] = Bunny.ACT_LEFT;
					//actions[BEAR] = World.CREATE_NEW;
				}
			}
			else if (Math.abs(changeY) > 15){
				if(changeY > 0) {
					actions[BUNNY] = Bunny.ACT_DOWN;
					//actions[LOG] = World.CREATE_NEW;
				}
				else {
					actions[BUNNY] = Bunny.ACT_UP;
				}
			}
		}
		world.update(deltaTime, actions);
		ApplicationType appType = Gdx.app.getType();
		
	}

	private void updateReady () {
		if (Gdx.input.justTouched()) {
			state = GAME_RUNNING;
		}
	}

	public void draw(){
		GLCommon gl = Gdx.gl;
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		wRenderer.render();
		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);
		
		
		//Changes day
        dayLabel.setText("Days: " + days);
		
		//Changes hunger
        if (hungerLevel < 100) {
        	hungerLabel.setColor(Color.RED);
        }
        else {
        	hungerLabel.setColor(Color.WHITE);
        }
		hungerLabel.setText("Energy: " + hungerLevel + "%");
		
		if (hungerLevel <= 0 && world.bunny.state != Bunny.STATE_DEAD) {
			world.bunny.mode = Bunny.MODE_DEAD;
			world.bunny.state = Bunny.STATE_DEAD;
			world.bunny.stateTime = 0;
		}

		//Act & Draw Stage First
		stage.setCamera(guiCam);
		stage.act();
		stage.draw();
		
		
	}
	
	@Override
	public void render(float delta) {
		update(delta);
		if(state == GAME_RUNNING) {
			draw();
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		Assets.playMusic(Assets.gamemusic);
		//Assets.gamemusic.play();
		//pauseButton.setDisabled(false);
		//pauseButton.clearListeners();
		//pauseButton.setChecked(false);
		System.out.println("is pressed?: " + pauseButton.isPressed());
		state = GAME_RUNNING;
		
		 Gdx.input.setInputProcessor(stage);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		System.out.println("Disabled?: " + pauseButton.isDisabled() + ", Pressed?: " + pauseButton.isPressed());
		Assets.gamemusic.pause();
		state = GAME_PAUSED;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		game.setScreen(new PauseScreen(game, days, this));
		//if (state == GAME_RUNNING) 
			//state = GAME_PAUSED;
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
