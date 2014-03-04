package com.dsaw.hophome;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dsaw.hophome.World.WorldListener;

public class GameScreen implements Screen {
	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;
	static final int ACT_STATIC = 0;
	static final int ACT_LEFT = 1;
	static final int ACT_RIGHT = 2;
	static final int ACT_UP = 3;
	static final int ACT_DOWN = 4;
	static final int BUNNY = 0;
	static final int LOG = 1;
	static final int BEAR = 2;
	
	
	private Game game;
	private int state;
	OrthographicCamera guiCam;
	Vector3 touchPoint;
	SpriteBatch batcher;
	WorldListener worldListener;
	World world;
	WorldRenderer wRenderer;
	int[] actions;
	float bufferTime;
	Random rand;
	boolean triggered;
	int act;
	
	Stage stage;
	int days;
	
	public GameScreen(final Game game) {
		this.game = game;

		state = GAME_READY;
		guiCam = new OrthographicCamera(320, 480);
		guiCam.position.set(320 / 2, 480 / 2, 0);
		touchPoint = new Vector3();
		batcher = new SpriteBatch();
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
			public void hitLog() {
				Assets.gamemusic.stop();
				game.setScreen(new GameOverScreen(game));
				
			}
			
		};
		Gdx.app.debug("GameScreen", "Initialized the screen!");
		
		world = new World(worldListener);
		wRenderer = new WorldRenderer(batcher, world);
		actions = new int[3];
		rand = new Random();
		bufferTime = 0;
		triggered = false;
		act = 0;
		Assets.gamemusic.setVolume(0.5f);
		Assets.gamemusic.setLooping(true);
		Assets.gamemusic.play();
		
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
		// TODO Auto-generated method stub
		
	}

	private void createObstacle(float deltaTime) {
		if(triggered) {
			if(bufferTime > 0.5) {
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
			if(bufferTime > 1.0) {
				bufferTime = 0;
				act = rand.nextInt()%2 + 1;
				switch(act) {
				case LOG:
					if(world.log.state == Log.STATE_DEAD) {
						world.bunny.mode = Bunny.MODE_BUGEYE;
						triggered = true;
					}
					break;
				case BEAR:
					if(world.bear.state == Bear.STATE_DEAD) {
						world.bunny.mode = Bunny.MODE_LOOKRIGHT;
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
		
		
	}
	
	@Override
	public void render(float delta) {
		update(delta);
		draw();	
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		if (state == GAME_RUNNING) 
			state = GAME_PAUSED;
		
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
