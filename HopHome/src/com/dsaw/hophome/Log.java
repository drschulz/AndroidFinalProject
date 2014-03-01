package com.dsaw.hophome;

public class Log extends DynamicGameObject {
	public static final float LOG_WIDTH = 3.0f;
	public static final float LOG_HEIGHT = 2.0f;
	public static final float LOG_DEPTH = 0.5f;
	public static final int LOG_STATE_ALIVE = 0;
	public static final int LOG_STATE_DEAD = 1;
	public static final float LOG_UP_VELOCITY = 2;
	public static final float LOG_FORWARD_VELOCITY = 2;
	public static final float LOG_INITIAL_POSITION_X = 3.5f;
	public static final float LOG_INITIAL_POSITION_Y = 0;
	public static final float LOG_INITIAL_POSITION_Z = -2;
	
	int state;
	float stateTime;
	
	public Log(float x, float y, float z, int state) {
		super(x, y, z, LOG_WIDTH, LOG_HEIGHT);
		this.state = state;
		velocity.add(0, LOG_UP_VELOCITY, LOG_FORWARD_VELOCITY);
	}
	
	public void update(float deltaTime) {
		switch(state) {
		case LOG_STATE_ALIVE:
			if(position.y + velocity.y * deltaTime > 7) {
				state = LOG_STATE_DEAD;
			}
			break;
		default:
			break;	
		}
		
		if(state != LOG_STATE_DEAD) {
			position.add(0, velocity.y * deltaTime, velocity.z * deltaTime);
		}
		
		bounds.x = position.x - Log.LOG_WIDTH/2;
		bounds.y = position.y - Log.LOG_HEIGHT/2;
		
		stateTime += deltaTime;
	}
	
	public void recreate() {
		if (state == Log.LOG_STATE_DEAD) {
			state = Log.LOG_STATE_ALIVE;
			position.x = Log.LOG_INITIAL_POSITION_X;
			position.y = Log.LOG_INITIAL_POSITION_Y;
			position.z = Log.LOG_INITIAL_POSITION_Z;
		}
	}

}
