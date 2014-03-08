package com.dsaw.hophome;

public class Bird extends DynamicGameObject {
	public static final float BIRD_WIDTH = 8.0f;
	public static final float BIRD_HEIGHT = 2.0f;
	public static final float BIRD_DEPTH = 0.5f;
	public static final float BIRD_UP_VELOCITY = 8;
	public static final float BIRD_FORWARD_VELOCITY = -6;
	public static final float BIRD_INITIAL_POSITION_X = 5f;
	public static final float BIRD_INITIAL_POSITION_Y = 17;
	public static final float BIRD_INITIAL_POSITION_Z = 2;
	
	//int state;
	float stateTime;
	
	public Bird(float x, float y, float z, int state) {
		super(x, y, z, BIRD_WIDTH, BIRD_HEIGHT, BIRD_DEPTH);
		this.state = state;
		velocity.add(0, 0, BIRD_FORWARD_VELOCITY);
	}
	
	public void update(float deltaTime) {
		switch(state) {
		case STATE_ALIVE:
			if(position.z + velocity.z * deltaTime < -10) {
				System.out.print("log died\n");
				state = STATE_DEAD;
			}
			break;
		default:
			break;	
		}
		
		if(state != STATE_DEAD) {
			position.add(0, 0, velocity.z * deltaTime);
		}
		
		this.updateBound();
		
		stateTime += deltaTime;
	}
	
	public void recreate() {
		if (state == STATE_DEAD) {
			state = STATE_ALIVE;
			position.x = Bird.BIRD_INITIAL_POSITION_X;
			position.y = Bird.BIRD_INITIAL_POSITION_Y;
			position.z = Bird.BIRD_INITIAL_POSITION_Z;
			this.updateBound();
		}
	}
}
