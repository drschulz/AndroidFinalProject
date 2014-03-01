package com.dsaw.hophome;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
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
	
	private Game game;
	private int state;
	OrthographicCamera guiCam;
	Vector3 touchPoint;
	SpriteBatch batcher;
	WorldListener worldListener;
	World world;
	WorldRenderer wRenderer;
	
	public GameScreen(Game game) {
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
				// TODO Auto-generated method stub
				
			}
			
		};
		Gdx.app.debug("GameScreen", "Initialized the screen!");
		
		world = new World(worldListener);
		wRenderer = new WorldRenderer(batcher, world);
		
		
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

	private void updateRunning(float deltaTime) {
		int changeX;
		int changeY;
		
		if (Gdx.input.isTouched()) {
			//world.update(deltaTime, GameScreen.ACT_UP);
			changeX = Gdx.input.getDeltaX();
			changeY = Gdx.input.getDeltaY();
			System.out.print("x change: " + changeX + ", y change: " + changeY +"\n");
			
			if(Math.abs(changeX) > Math.abs(changeY) && Math.abs(changeX) > 15) {
				if(changeX > 0) {
					world.update(deltaTime, GameScreen.ACT_RIGHT, World.LOG_STAY_AS_IS);
				}
				else {
					world.update(deltaTime, GameScreen.ACT_LEFT, World.LOG_STAY_AS_IS);
				}
			}
			else if (Math.abs(changeY) > 15){
				if(changeY > 0) {
					world.update(deltaTime, GameScreen.ACT_STATIC, World.LOG_CREATE_NEW);
				}
				else {
					world.update(deltaTime, GameScreen.ACT_UP, World.LOG_STAY_AS_IS);
				}
			}
			
			//world.update(deltaTime, GameScreen.ACT_STATIC);
		}
		world.update(deltaTime, GameScreen.ACT_STATIC, World.LOG_STAY_AS_IS);
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
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//gl.glClearColor(red, green, blue, alpha);
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
