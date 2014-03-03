package com.dsaw.hophome;

public class Bear extends DynamicGameObject {
	public static final float BEAR_WIDTH = 10.0f;
	public static final float BEAR_HEIGHT = 8.0f;
	public static final float BEAR_DEPTH = 0.5f;
	public static final int BEAR_STATE_ATTACK = 2;
	public static final int BEAR_STATE_RETREAT = 3;
	public static final float BEAR_FORWARD_VELOCITY = 2;
	public static final float BEAR_ATTACK_ACCEL = -10;
	public static final float BEAR_RETURN_VELOCITY = 5;
	public static final float BEAR_INITIAL_POSITION_X = 15f;
	public static final float BEAR_INITIAL_POSITION_Y = 4;
	public static final float BEAR_INITIAL_POSITION_Z = -2;
	
	int state;
	float stateTime;
	
	public Bear(float x, float y, float z, int state) {
		super(x, y, z, BEAR_WIDTH, BEAR_HEIGHT);
		this.state = state;
		velocity.add(0, 0, BEAR_FORWARD_VELOCITY);
	}
	
	public void update(float deltaTime) {
		switch(state) {
		case BEAR_STATE_ATTACK:
			if(position.x + velocity.x * deltaTime < 10) {
				state = BEAR_STATE_RETREAT;
				velocity.x = Bear.BEAR_RETURN_VELOCITY;
			}
			else {
				velocity.x += Bear.BEAR_ATTACK_ACCEL * deltaTime;
			}
			break;
		case BEAR_STATE_RETREAT:
			if(position.x + velocity.x * deltaTime > 15) {
				state = STATE_DEAD;
			}
			break;
		default:
			break;	
		}
		
		if(state != STATE_DEAD) {
			position.add(velocity.x * deltaTime, 0, velocity.z * deltaTime);
		}
		
		bounds.x = position.x - Bear.BEAR_WIDTH/2;
		bounds.y = position.y - Bear.BEAR_HEIGHT/2;
		
		stateTime += deltaTime;
	}
	
	public void recreate() {
		if (state == Bear.STATE_DEAD) {
			state = Bear.BEAR_STATE_ATTACK;
			position.x = Bear.BEAR_INITIAL_POSITION_X;
			position.y = Bear.BEAR_INITIAL_POSITION_Y;
			position.z = Bear.BEAR_INITIAL_POSITION_Z;
		}
	}

}
