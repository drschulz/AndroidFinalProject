package com.dsaw.hophome;

public class Bunny extends DynamicGameObject {

	public static final int BUNNY_STATE_JUMP = 0;
	public static final int BUNNY_STATE_FALL = 1;
	public static final int BUNNY_STATE_STATIC = 2;
	public static final int BUNNY_STATE_DODGE_LEFT = 3;
	public static final int BUNNY_STATE_DODGE_RIGHT = 4;
	public static final float BUNNY_JUMP_VELOCITY = 10;
	public static final float BUNNY_MOVE_VELOCITY = 7;
	public static final float BUNNY_WIDTH = 5.0f;
	public static final float BUNNY_HEIGHT = 8.0f;
	public static final float BUNNY_DEPTH = .5f;
	public static final float GROUND_LIMIT = 8;
	public static final float HORIZ_LIMIT = 5f;
	public static final int ACT_STATIC = 0;
	public static final int ACT_LEFT = 1;
	public static final int ACT_RIGHT = 2;
	public static final int ACT_UP = 3;
	public static final int ACT_DOWN = 4;
	public static final int NUM_FRAMES = 7;
	public static final float ANIM_SPEED = 0.1f;
	public static final int MODE_NORM = 0;
	public static final int MODE_BUGEYE = 1;
	public static final int MODE_CLOSEEYE = 2;
	
	int state;
	float stateTime;
	int mode;
	
	public Bunny(float x, float y, float z) {
		super(x, y, z, BUNNY_WIDTH, BUNNY_HEIGHT);
		state = this.BUNNY_STATE_STATIC;
		stateTime = 0;
		mode = 0;
	}
	
	public void update(float deltaTime) {
		
		switch(state) {
		case BUNNY_STATE_DODGE_LEFT:
			velocity.add(World.gravity.x * deltaTime, 0, 0);
			velocity.y = 0;
			if (position.x + velocity.x * deltaTime > this.HORIZ_LIMIT) {
				state = BUNNY_STATE_STATIC;
			}
			break;
		case BUNNY_STATE_DODGE_RIGHT:
			velocity.add(-World.gravity.x * deltaTime, 0, 0);
			velocity.y = 0;
			if (position.x + velocity.x * deltaTime < this.HORIZ_LIMIT) {
				state = BUNNY_STATE_STATIC;
			}
			break;
		case BUNNY_STATE_JUMP:
			velocity.add(0, World.gravity.y * deltaTime, 0);
			velocity.x = 0;
			if (velocity.y < 0) {
				state = BUNNY_STATE_FALL;
			}
			break;
		case BUNNY_STATE_FALL:
			velocity.add(0, World.gravity.y * deltaTime, 0);
			velocity.x = 0;
			if (position.y + velocity.y * deltaTime < this.GROUND_LIMIT) {
				state = this.BUNNY_STATE_STATIC;
			}
			break;
		default:
			velocity.x = 0;
			velocity.y = 0;
		}
		
		if (state != this.BUNNY_STATE_STATIC) {
			position.add(velocity.x * deltaTime, velocity.y * deltaTime, 0);
		}
		else {
			position.x = Bunny.HORIZ_LIMIT;
			position.y = Bunny.GROUND_LIMIT;
		}
		//velocity.add(World.gravity.x * deltaTime, World.gravity.y * deltaTime, 0);
		
		
		//position.add(velocity.x * deltaTime, velocity.y * deltaTime, 0);
		bounds.x = position.x - Bunny.BUNNY_WIDTH/2;
		bounds.y = position.y - Bunny.BUNNY_HEIGHT/2;
		
		//stateTime += deltaTime;
		if (stateTime < NUM_FRAMES*ANIM_SPEED) {
			stateTime += deltaTime;
		}
		else {
			stateTime = 0;
		}
		
	}
	
	public void jumpUp() {
		if(state == BUNNY_STATE_STATIC) {
			velocity.y += BUNNY_JUMP_VELOCITY;
			state = BUNNY_STATE_JUMP;
			//stateTime = 0;
			System.out.print("bunny should jump\n");
		}
	}
	
	public void dodgeLeft() {
		if(state == BUNNY_STATE_STATIC) {
			velocity.x = -BUNNY_MOVE_VELOCITY;
			state = BUNNY_STATE_DODGE_LEFT;
			//stateTime = 0;
			System.out.print("bunny should dodge left\n");
		}
	}
	
	public void dodgeRight() {
		if(state == BUNNY_STATE_STATIC) {
			velocity.x = BUNNY_MOVE_VELOCITY;
			state = BUNNY_STATE_DODGE_RIGHT;
			//stateTime = 0;
			System.out.print("bunny should dodge right\n");
		}
	}
	
}