package com.dsaw.hophome;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class World {
	public interface WorldListener {
		public void jump ();

		public void duck ();

		public void dodgeLeft ();

		public void dodgeRight ();
		
		public void hitLog ();
		
		public void incDay ();
	}
	
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static final int CREATE_NEW = 0;
	public static final int STAY_AS_IS = 1;
	public static final Vector2 gravity = new Vector2(6, -12);
	
	public final Bunny bunny;
	public final Bear bear;
	public Log log;
	public final WorldListener listener;
	public final Random rand;
	public int score;
	public int daysTraveled;
	public int state;
	boolean bunnyHit;
	
	public World(WorldListener listener) {
		this.bunny = new Bunny(Bunny.HORIZ_LIMIT, Bunny.GROUND_LIMIT, -5);
		this.bear = new Bear(Bear.BEAR_INITIAL_POSITION_X, Bear.BEAR_INITIAL_POSITION_Y, Bear.BEAR_INITIAL_POSITION_Z, Bear.STATE_DEAD);
		this.log = new Log(Log.LOG_INITIAL_POSITION_X, Log.LOG_INITIAL_POSITION_Y, Log.LOG_INITIAL_POSITION_Z, Log.STATE_DEAD);
		this.listener = listener;
		rand = new Random();
		generateField();
		this.score = 0;
		this.daysTraveled = 0;
		this.state = WORLD_STATE_RUNNING;
		Gdx.app.debug("World", "Initialized world");
		bunnyHit = false;
	}
	
	private void generateField() {
		
	}
	
	private void checkCollisions() {
		if(bunny.bound.intersects(log.bound)) {
			bunnyHit = true;
			bunny.mode = Bunny.MODE_DEAD;
			bunny.stateTime = 0;
			//listener.hitLog();
		}
		else if(bunny.bound.intersects(bear.bound)) {
			bunnyHit = true;
			bunny.mode = Bunny.MODE_DEAD;
			bunny.stateTime = 0;
			//listener.hitLog();
		}
	}
	
	public void update (float deltaTime, int[] actions) {
		updateBunny(deltaTime, actions[0]);
		if(!bunnyHit) {
			updateLog(deltaTime, actions[1]);
			updateBear(deltaTime, actions[2]);
			checkCollisions();
		}
	}
	
	private void updateLog(float deltaTime, int logAction) {
		switch(logAction) {
		case World.CREATE_NEW:
			log.recreate();
			break;
		default:
			break;
		}
		
		if(log.state != Log.STATE_DEAD) {
			log.update(deltaTime);
		}
	}
	
	private void updateBear(float deltaTime, int bearAction) {
		switch(bearAction) {
		case World.CREATE_NEW:
			bear.recreate();
			break;
		default:
			break;
		}
		if(bear.state != Bear.STATE_DEAD) {
			bear.update(deltaTime);
		}
	}
	
	private void updateBunny(float deltaTime, int action) {
		switch(action) {
		case Bunny.ACT_DOWN:
			break;
		case Bunny.ACT_UP:
			bunny.jumpUp();
			listener.jump();
			break;
		case Bunny.ACT_LEFT:
			bunny.dodgeLeft();
			break;
		case Bunny.ACT_RIGHT:
			bunny.dodgeRight();
			break;
		default:
			break;
		}
		
		bunny.update(deltaTime);
	}
}
