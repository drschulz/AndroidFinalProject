package com.dsaw.hophome;

public class Bear extends DynamicGameObject {
	public static final float BEAR_WIDTH = 3.0f;
	public static final float BEAR_HEIGHT = 2.0f;
	public static final float BEAR_DEPTH = 0.5f;
	public static final int BEAR_STATE_ATTACK = 0;
	public static final int BEAR_STATE_RETREAT = 1;
	public static final int BEAR_STATE_DEAD = 2;
	public static final float BEAR_FORWARD_VELOCITY = 2;
	public static final float BEAR_ATTACK_ACCEL = -5;
	public static final float BEAR_RETURN_VELOCITY = 3;
	public static final float BEAR_INITIAL_POSITION_X = 15f;
	public static final float BEAR_INITIAL_POSITION_Y = 8;
	public static final float BEAR_INITIAL_POSITION_Z = -2;
	
	int state;
	float stateTime;
	
	public Bear(float x, float y, float z, int state) {
		super(x, y, z, BEAR_WIDTH, BEAR_HEIGHT);
		this.state = state;
		velocity.add(BEAR_ATTACK_ACCEL*stateTime, 0, BEAR_FORWARD_VELOCITY);
	}
	
	public void update(float deltaTime) {
		switch(state) {
		case BEAR_STATE_ATTACK:
			if(position.x + velocity.x * deltaTime < 7) {
				state = BEAR_STATE_RETREAT;
			}
			break;
		default:
			break;	
		}
		
		if(state != BEAR_STATE_DEAD) {
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
