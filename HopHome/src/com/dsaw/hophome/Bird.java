package com.dsaw.hophome;

import com.badlogic.gdx.math.Vector3;

public class Bird extends DynamicGameObject {
	public static final float BIRD_WIDTH = 4.0f;
	public static final float BIRD_HEIGHT = 3.0f;
	public static final float BIRD_DEPTH = 0.5f;
	public static final float BIRD_UP_VELOCITY = 8;
	public static final float BIRD_FORWARD_VELOCITY = -10;
	public static final float BIRD_DOWN_VELOCITY = -6;
	public static final float BIRD_INITIAL_POSITION_X = 5f;
	public static final float BIRD_INITIAL_POSITION_Y = 8;
	public static final float BIRD_INITIAL_POSITION_Z = 2;
	
	//int state;
	float stateTime;
	Vector3 yAccel;
	
	public Bird(float x, float y, float z, int state) {
		super(x, y, z, BIRD_WIDTH, BIRD_HEIGHT, BIRD_DEPTH);
		this.state = state;
		velocity.add(0, BIRD_DOWN_VELOCITY, BIRD_FORWARD_VELOCITY);
		yAccel = new Vector3(0, 5, 0);
	}
	
	public void update(float deltaTime) {
		switch(state) {
		case STATE_ALIVE:
			if(position.z + velocity.z * deltaTime < -10) {
				System.out.print("log died\n");
				state = STATE_DEAD;
			}
			velocity.add(0, yAccel.y * deltaTime, 0);
			break;
		default:
			break;	
		}
		
		if(state != STATE_DEAD) {
			position.add(0, velocity.y*deltaTime, velocity.z * deltaTime);
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
