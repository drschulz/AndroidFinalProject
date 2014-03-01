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
	}
	
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static final int ACT_STATIC = 0;
	public static final int ACT_LEFT = 1;
	public static final int ACT_RIGHT = 2;
	public static final int ACT_UP = 3;
	public static final int ACT_DOWN = 4;
	public static final int LOG_CREATE_NEW = 0;
	public static final int LOG_STAY_AS_IS = 1;
	public static final Vector2 gravity = new Vector2(6, -12);
	
	public final Bunny bunny;
	public Log log;
	public final WorldListener listener;
	public final Random rand;
	public int score;
	public int daysTraveled;
	public int state;
	
	public World(WorldListener listener) {
		this.bunny = new Bunny(Bunny.HORIZ_LIMIT, Bunny.GROUND_LIMIT, 1);
		this.log = new Log(Log.LOG_INITIAL_POSITION_X, Log.LOG_INITIAL_POSITION_Y, Log.LOG_INITIAL_POSITION_Z, Log.LOG_STATE_DEAD);
		this.listener = listener;
		rand = new Random();
		generateField();
		this.score = 0;
		this.daysTraveled = 0;
		this.state = WORLD_STATE_RUNNING;
		Gdx.app.debug("World", "Initialized world");
	}
	
	private void generateField() {
		
	}
	
	public void update (float deltaTime, int action, int logAction) {
		updateBunny(deltaTime, action);
		updateLog(deltaTime, logAction);
		if(log.position.z >= bunny.position.z && log.position.z <= bunny.position.z + Bunny.BUNNY_DEPTH ||
		    log.position.z + Log.LOG_DEPTH >= bunny.position.z && log.position.z + Log.LOG_DEPTH <= bunny.position.z + Bunny.BUNNY_DEPTH) {
			if(log.bounds.overlaps(bunny.bounds)) {
				log.state = Log.LOG_STATE_DEAD;
			}
		}
	}
	
	private void updateLog(float deltaTime, int logAction) {
		switch(logAction) {
		case World.LOG_CREATE_NEW:
			log.recreate();
			break;
		default:
			break;
		}
		log.update(deltaTime);
	}
	
	private void updateBunny(float deltaTime, int action) {
		switch(action) {
		case World.ACT_DOWN:
			break;
		case World.ACT_UP:
			bunny.jumpUp();
			listener.jump();
			break;
		case World.ACT_LEFT:
			bunny.dodgeLeft();
			break;
		case World.ACT_RIGHT:
			bunny.dodgeRight();
			break;
		default:
			break;
		}
		
		bunny.update(deltaTime);
	}
}
