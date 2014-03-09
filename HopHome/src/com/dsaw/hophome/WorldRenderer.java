package com.dsaw.hophome;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;


public class WorldRenderer {
	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 15;
	World world;
	PerspectiveCamera camera;
	SpriteBatch batch;
	DecalBatch decalBatch;
	TextureRegion background;
	float angle;
	ShapeRenderer shapeRenderer;
	Color backcolor;
	Color grasscolor;
	Decal bunnyDecal;
	Decal logDecal;
	Decal bearDecal;
	Decal treeDecal;
	Decal landDecal;
	Decal groundDecal;
	Decal birdDecal;
	//Color colorBottom;
	boolean day;
	ModelBuilder mb;
	Model ground;
	float lr, lg, lb;
	
	public WorldRenderer(SpriteBatch batch, World world) {
		this.world = world;
		this.batch = batch;
		//this.camera = new OrthographicCamera(320, 480);
		
		//this.camera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.camera = new PerspectiveCamera(67, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.camera.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2 - 4, 12);
		//this.camera.position.set(0, 0, 10);
		this.camera.lookAt(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2 - 4, 0);
		this.camera.near = 1f; 
		this.camera.far = 300;
		this.camera.update();
		angle = 0;
		shapeRenderer = new ShapeRenderer();
		backcolor = new Color(0.36f, 0.61f, 0.94f, 1);
		grasscolor = new Color(0.22f, 0.81f, 0.11f, 1);
		day = true;
		decalBatch = new DecalBatch(new CameraGroupStrategy(this.camera));
		logDecal = Decal.newDecal(Log.LOG_WIDTH, Log.LOG_HEIGHT, Assets.log, true);
		bearDecal = Decal.newDecal(Bear.BEAR_WIDTH, Bear.BEAR_HEIGHT, Assets.bear, true);
		treeDecal = Decal.newDecal(Tree.TREE_WIDTH, Tree.TREE_HEIGHT, Assets.tree, true);
		birdDecal = Decal.newDecal(Bird.BIRD_WIDTH, Bird.BIRD_HEIGHT, Assets.bird, true);
		groundDecal = Decal.newDecal(FRUSTUM_WIDTH*2.5f, FRUSTUM_HEIGHT*4, Assets.grass, true);
		groundDecal.rotateX(90);
		groundDecal.translate(5, 0, -12);
		landDecal = Decal.newDecal(FRUSTUM_WIDTH*3f, FRUSTUM_HEIGHT*3, Assets.background, true);
		lr = lg = lb = 1;
		//mb.begin();
		//ground = mb.createCylinder(FRUSTUM_)
		
	}
	
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);
		renderBackground();
		renderLandscape();
		renderLog();
		renderBear();
		renderTree();
		renderBunny();
		renderBird();
		decalBatch.flush();
		//renderCarrot();
	}
	
	public void renderLandscape() {
		landDecal.setPosition(5, 0, -18);
		landDecal.setColor(new Color(lr, lg, lb, 1));
		decalBatch.add(landDecal);
	}
	
	/*public void renderCarrot() {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(new Color(0.54f, 0.55f, 0.73f, 0));
		shapeRenderer.identity();
		shapeRenderer.translate(0, 0, 8);
		shapeRenderer.rect(0, 0, FRUSTUM_WIDTH, 1);
		shapeRenderer.end();
		batch.begin();
		batch.draw(Assets.carrot, 4, 0, 2, 1);
		batch.end();
	}*/
	
	public void renderBackground() {
		if(day) {
			//backcolor.sub(Color.luminanceAlpha(0.001, 1))
			backcolor.sub(0.0005f, 0.0005f, 0.0005f, 1);
			grasscolor.sub(0.0005f, 0.0005f, 0.0005f, 1);
			lr = lr - 0.0005f;
			lg = lg - 0.0005f;
			lb = lb - 0.0005f;
		}
		else {
			backcolor.add(0.0005f, 0.0005f, 0.0005f, 1);
			grasscolor.add(0.0005f, 0.0005f, 0.0005f, 1);
			lr = lr + 0.0005f;
			lg = lg + 0.0005f;
			lb = lb + 0.0005f;
		}
		if (backcolor.b > 0.94f) {
			day = true;
			world.listener.incDay();
		}
		else if (backcolor.b < 0.4f){
			day = false;
		}
		Gdx.app.debug("in render", "here!");
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(backcolor);
		shapeRenderer.identity();
		shapeRenderer.translate(0, 0, -20);
		shapeRenderer.rect(-FRUSTUM_WIDTH*2, -FRUSTUM_HEIGHT, FRUSTUM_WIDTH*4, FRUSTUM_HEIGHT*5);
		shapeRenderer.end();
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(grasscolor);
		//shapeRenderer.identity();
		//shapeRenderer.rotate(1, 0, 0, -90);
		//shapeRenderer.rect(-FRUSTUM_WIDTH, -FRUSTUM_HEIGHT, FRUSTUM_WIDTH*3, FRUSTUM_HEIGHT*2);
		shapeRenderer.end();
		//batch.begin();
		//batch.draw(Assets.grass, -FRUSTUM_WIDTH, -FRUSTUM_HEIGHT, 0, 0, FRUSTUM_WIDTH*3, FRUSTUM_HEIGHT*2, 1, 1, rotation, clockwise);
		//groundDecal.setPosition(5, 0, 0);
		groundDecal.setColor(new Color(lr, lg, lb, 1));
		decalBatch.add(groundDecal);
		
		
	}
	
	public void renderBunny() {
		TextureRegion keyFrame;
		switch(world.bunny.state) {
		case Bunny.BUNNY_STATE_JUMP:
			keyFrame = Assets.bunnyAnim.getKeyFrame(world.bunny.mode*Assets.BUNNY_COLS*Bunny.ANIM_SPEED + 2*Bunny.ANIM_SPEED);
			break;
		case Bunny.BUNNY_STATE_FALL:
			keyFrame = Assets.bunnyAnim.getKeyFrame(world.bunny.mode*Assets.BUNNY_COLS*Bunny.ANIM_SPEED + 4*Bunny.ANIM_SPEED);
			break;
		case Bunny.STATE_DEAD:
			keyFrame = Assets.bunnyAnim.getKeyFrame(world.bunny.mode*Assets.BUNNY_COLS*Bunny.ANIM_SPEED + world.bunny.stateTime);
			System.out.println("bunny state time: " + world.bunny.stateTime);
			if(world.bunny.stateTime > (Bunny.NUM_FRAMES-1)*Bunny.ANIM_SPEED) {
				world.listener.hitLog();
			}
			break;
		case Bunny.BUNNY_STATE_DUCK:
			keyFrame = Assets.bunnyAnim.getKeyFrame(world.bunny.mode*Assets.BUNNY_COLS*Bunny.ANIM_SPEED + 3*Bunny.ANIM_SPEED);
			break;
		default:
			keyFrame = Assets.bunnyAnim.getKeyFrame(world.bunny.mode*Assets.BUNNY_COLS*Bunny.ANIM_SPEED + world.bunny.stateTime);
			break;
		}
		bunnyDecal = Decal.newDecal(Bunny.BUNNY_WIDTH, Bunny.BUNNY_HEIGHT, keyFrame, true);
		Vector3 pos = world.bunny.bound.getMin();
		bunnyDecal.setPosition(world.bunny.position.x, world.bunny.position.y, world.bunny.position.z);
		//bunnyDecal.setColor(Color.CYAN);
		decalBatch.add(bunnyDecal);
	}
	
	public void renderBear() {
		if(world.bear.state != Bear.STATE_DEAD) {
			//bearDecal.setTextureRegion(Assets.bear);
			Vector3 pos = world.bear.bound.getMin();
			bearDecal.setPosition(world.bear.position.x, world.bear.position.y, world.bear.position.z);
			bearDecal.setColor(new Color(lr, lg, lb, 1));
			decalBatch.add(bearDecal);
		}
	}
	
	public void renderTree() {
		if(world.tree.state != Tree.STATE_DEAD) {
			//bearDecal.setTextureRegion(Assets.bear);
			//Vector3 pos = world.tree.bound.getMin();
			treeDecal.setPosition(world.tree.position.x, world.tree.position.y, world.tree.position.z);
			treeDecal.setColor(new Color(lr, lg, lb, 1));
			decalBatch.add(treeDecal);
		}
	}
	
	public void renderLog() {
		if(world.log.state == Log.STATE_ALIVE) {
			Vector3 pos = world.log.bound.getMin();
			logDecal.setPosition(world.log.position.x, world.log.position.y, world.log.position.z);
			logDecal.setColor(new Color(lr, lg, lb, 1));
			decalBatch.add(logDecal);
		}
	}
	
	public void renderBird() {
		if(world.bird.state == Bird.STATE_ALIVE) {
			//Vector3 pos = world.bird.bound.getMin();
			birdDecal.setPosition(world.bird.position.x, world.bird.position.y, world.bird.position.z);
			birdDecal.setColor(new Color(lr, lg, lb, 1));
			decalBatch.add(birdDecal);
		}
	}
	
}
