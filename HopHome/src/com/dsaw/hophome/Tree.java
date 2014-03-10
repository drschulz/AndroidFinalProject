package com.dsaw.hophome;

public class Tree extends DynamicGameObject {
	public static final float TREE_WIDTH = 10.0f;
	public static final float TREE_HEIGHT = 8f;
	public static final float TREE_DEPTH = 0.5f;
	public static final float TREE_UP_VELOCITY = 8;
	public static final float TREE_FORWARD_VELOCITY = -6;
	public static final float TREE_INITIAL_POSITION_X = 1.5f;//0f;
	public static final float TREE_INITIAL_POSITION_Y = 4;
	public static final float TREE_INITIAL_POSITION_Z = 5;
	
	//int state;
	float stateTime;
	
	public Tree(float x, float y, float z, int state) {
		super(x, y, z, TREE_WIDTH, TREE_HEIGHT, TREE_DEPTH);
		this.state = state;
		velocity.add(0, 0, TREE_FORWARD_VELOCITY);
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
		System.out.println("tree position z: " + position.z);
		stateTime += deltaTime;
	}
	
	public void recreate() {
		if (state == STATE_DEAD) {
			state = STATE_ALIVE;
			position.x = Tree.TREE_INITIAL_POSITION_X;
			position.y = Tree.TREE_INITIAL_POSITION_Y;
			position.z = Tree.TREE_INITIAL_POSITION_Z;
			this.updateBound();
		}
	}

}
