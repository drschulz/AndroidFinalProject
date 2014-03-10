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
import com.badlogic.gdx.graphics.g3d.decals.SimpleOrthoGroupStrategy;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


public class WorldRenderer {
	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 15;
	static final float R_DELTA = 0.00039f;
	static final float G_DELTA = 0.00086f;
	static final float B_DELTA = 0.001f;
	static final float A_DELTA = 0.001f;
	static final float SA_DELTA = 0.002f;
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
	Decal bunnyShadow;
	Decal logDecal;
	Decal bearDecal;
	Decal treeDecal;
	Decal landDecal;
	Decal groundDecal;
	Decal groundDecal2;
	Decal birdDecal;
	//Color colorBottom;
	boolean day;
	ModelBuilder mb;
	Model ground;
	float lr, lg, lb, alph, starAlph;
	float ang;
	float place;
	
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
		backcolor = new Color(R_DELTA,G_DELTA,B_DELTA,1);//new Color(0.54f, 0.61f, 0.94f, 1);
		grasscolor = new Color(0.22f, 0.81f, 0.11f, 1);
		day = false;
		decalBatch = new DecalBatch(new CameraGroupStrategy(this.camera));
		logDecal = Decal.newDecal(Log.LOG_WIDTH, Log.LOG_HEIGHT, Assets.log, true);
		logDecal.setBlending(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE_MINUS_SRC_ALPHA);
		bearDecal = Decal.newDecal(Bear.BEAR_WIDTH, Bear.BEAR_HEIGHT, Assets.bear, true);
		bearDecal.setBlending(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE_MINUS_SRC_ALPHA);
		treeDecal = Decal.newDecal(Tree.TREE_WIDTH, Tree.TREE_HEIGHT, Assets.tree, true);
		treeDecal.setBlending(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE_MINUS_SRC_ALPHA);
		birdDecal = Decal.newDecal(Bird.BIRD_WIDTH, Bird.BIRD_HEIGHT, Assets.bird, true);
		birdDecal.setBlending(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE_MINUS_SRC_ALPHA);
		groundDecal = Decal.newDecal(FRUSTUM_WIDTH*2.5f, FRUSTUM_HEIGHT*4, Assets.grass, true);
		groundDecal.rotateX(90);
		groundDecal.translate(5, 0, -12);
		groundDecal.setBlending(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE_MINUS_SRC_ALPHA);
		groundDecal2 = Decal.newDecal(FRUSTUM_WIDTH*2.5f, FRUSTUM_HEIGHT*4, Assets.grass, true);
		groundDecal2.rotateX(90);
		groundDecal2.translate(5, 0, -12 + FRUSTUM_HEIGHT*4);
		groundDecal2.setBlending(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE_MINUS_SRC_ALPHA);
		landDecal = Decal.newDecal(FRUSTUM_WIDTH*3, FRUSTUM_HEIGHT*3, Assets.background, true);
		lr = lg = lb = 0.3f;//1;
		alph = 0f;
		starAlph = 1;
		ang = 0;
		place = 0;
		//mb.begin();
		//ground = mb.createCylinder(FRUSTUM_)
		
	}
	
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		//Gdx.gl.glEnable(GL20.GL_BLEND);
		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);
		updateValues();
		renderBackground();
		renderLandscape();
		renderBunny();
		renderTree();
		renderLog();
		renderBear();
		renderBird();
		decalBatch.flush();
		//renderCarrot();
	}
	
	public void updateValues() {
		if(day) {
			//backcolor.sub(Color.luminanceAlpha(0.001, 1))
			backcolor.sub(R_DELTA, G_DELTA, B_DELTA, 1);
			grasscolor.sub(0.0005f, 0.0005f, 0.0005f, 1);
			lr = lr - R_DELTA;
			lg = lg - G_DELTA;
			lb = lb - B_DELTA;
			//lr = lr - 0.0005f;
			//lg = lg - 0.0005f;
			//lb = lb - 0.0005f;
			alph = alph - A_DELTA;
			starAlph = starAlph + SA_DELTA;
		}
		else {
			backcolor.add(R_DELTA, G_DELTA, B_DELTA, 1);
			grasscolor.add(0.0005f, 0.0005f, 0.0005f, 1);
			lr = lr + R_DELTA;
			lg = lg + G_DELTA;
			lb = lb + B_DELTA;
			//lr = lr + 0.0005f;
			//lg = lg + 0.0005f;
			//lb = lb + 0.0005f;
			alph = alph + A_DELTA;
			starAlph = starAlph - SA_DELTA;
		}
		if (backcolor.b > 0.9) {//0.94f) {
			day = true;
			//System.out.println("color: r: " + backcolor.r + ", g: " + backcolor.g + ", b: " + backcolor.b);
			
		}
		else if (backcolor.b < B_DELTA) {//0.4f){
			day = false;
			world.listener.incDay();
		}
	}
	
	public void renderLandscape() {
		landDecal.setPosition(5, 0, -18);
		landDecal.setColor(new Color(lr, lg, lb, 1));
		decalBatch.add(landDecal);
	}
	
	public void renderBackground() {
		
		Gdx.app.debug("in render", "here!");
		
		
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(backcolor);
		shapeRenderer.identity();
		shapeRenderer.translate(0, 0, -20);
		shapeRenderer.rect(-FRUSTUM_WIDTH*2, -FRUSTUM_HEIGHT, FRUSTUM_WIDTH*4, FRUSTUM_HEIGHT*5);
		shapeRenderer.end();
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(new Color(1,1f,1,starAlph));
		shapeRenderer.identity();
		shapeRenderer.translate(0, 0, -19);
		shapeRenderer.circle(2, 15, 0.2f);
		shapeRenderer.circle(10, 13, .2f);
		shapeRenderer.circle(5, 18, .2f);
		shapeRenderer.circle(0, 20, .2f);
		shapeRenderer.circle(3, 12, .2f);
		shapeRenderer.circle(18, 16, .2f);
		shapeRenderer.circle(14, 19, .2f);
		/*shapeRenderer.setColor(Color.YELLOW);
		shapeRenderer.identity();
		shapeRenderer.translate(0, 0, -19);
		shapeRenderer.circle(place, 15, 1.5f, 20);*/
		shapeRenderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
		place += 0.01f;
		
		groundDecal.setColor(new Color(lb, lb, lb, 1));
		groundDecal.translateZ(-0.2f);
		if(groundDecal.getZ() < -70) {
			groundDecal.setZ(-12 + FRUSTUM_HEIGHT*4);
		}
		decalBatch.add(groundDecal);
		groundDecal2.setColor(new Color(lb, lb, lb, 1));
		groundDecal2.translateZ(-0.2f);
		if(groundDecal2.getZ() < -70) {
			groundDecal2.setZ(-12 + FRUSTUM_HEIGHT*4);
		}
		decalBatch.add(groundDecal2);
		decalBatch.flush();
		
		
	}
	
	public void renderBunny() {
		TextureRegion keyFrame;
		switch(world.bunny.state) {
		case Bunny.BUNNY_STATE_JUMP:
			keyFrame = Assets.bunnyAnim.getKeyFrame(world.bunny.mode*Assets.BUNNY_COLS*Bunny.ANIM_SPEED + 2*Bunny.ANIM_SPEED);
			break;
		case Bunny.BUNNY_STATE_FLOAT:
			keyFrame = Assets.bunnyAnim.getKeyFrame(world.bunny.mode*Assets.BUNNY_COLS*Bunny.ANIM_SPEED + 3*Bunny.ANIM_SPEED);
			break;
		case Bunny.BUNNY_STATE_FALL:
			keyFrame = Assets.bunnyAnim.getKeyFrame(world.bunny.mode*Assets.BUNNY_COLS*Bunny.ANIM_SPEED + 4*Bunny.ANIM_SPEED);
			break;
		case Bunny.STATE_DEAD:
			keyFrame = Assets.bunnyAnim.getKeyFrame(world.bunny.mode*Assets.BUNNY_COLS*Bunny.ANIM_SPEED + world.bunny.stateTime);
			System.out.println("bunny state time: " + world.bunny.stateTime);
			if(world.bunny.stateTime > (Bunny.NUM_FRAMES-1)*Bunny.ANIM_SPEED) {
				world.listener.bunnyDie();
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
		bunnyShadow = Decal.newDecal(Bunny.BUNNY_WIDTH, Bunny.BUNNY_HEIGHT, keyFrame, true);
		//bunnyDecal.setBlending(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE_MINUS_SRC_ALPHA);
		//Vector3 pos = world.bunny.bound.getMin();
		bunnyShadow.transformationOffset = new Vector2(0, -bunnyShadow.getHeight()/2);
		
		
		//bunnyShadow.rotateY(180);
		
		bunnyDecal.setPosition(world.bunny.position.x, world.bunny.position.y, world.bunny.position.z);
		bunnyShadow.setPosition(world.bunny.position.x, bunnyShadow.getHeight()/2 + 0.2f, world.bunny.position.z);// + Bunny.BUNNY_HEIGHT/2);
		bunnyShadow.rotateX(90);
		bunnyShadow.rotateZ(-90 + ang);
		bunnyShadow.setColor(new Color(0,0,0,alph));
		//bunnyShadow.rotateZ(90 + ang);
		decalBatch.add(bunnyShadow);
		decalBatch.flush();
		decalBatch.add(bunnyDecal);
		
		ang += 0.1f;
		if(ang > 180) {
			ang = 0;
		}
		//decalBatch.flush();
	}
	
	public void renderBear() {
		if(world.bear.state != Bear.STATE_DEAD) {
			//bearDecal.setTextureRegion(Assets.bear);
			Vector3 pos = world.bear.bound.getMin();
			bearDecal.setPosition(world.bear.position.x, world.bear.position.y, world.bear.position.z);
			bearDecal.setColor(new Color(lb, lb, lb, 1));
			decalBatch.add(bearDecal);
		}
	}
	
	public void renderTree() {
		if(world.tree.state != Tree.STATE_DEAD) {
			//bearDecal.setTextureRegion(Assets.bear);
			//Vector3 pos = world.tree.bound.getMin();
			treeDecal.setPosition(world.tree.position.x, world.tree.position.y, world.tree.position.z);
			treeDecal.setColor(new Color(lb, lb, lb, 1));
			decalBatch.add(treeDecal);
		}
	}
	
	public void renderLog() {
		if(world.log.state == Log.STATE_ALIVE) {
			Vector3 pos = world.log.bound.getMin();
			logDecal.setPosition(world.log.position.x, world.log.position.y, world.log.position.z);
			logDecal.setColor(new Color(lb, lb, lb, 1));
			decalBatch.add(logDecal);
		}
	}
	
	public void renderBird() {
		if(world.bird.state == Bird.STATE_ALIVE) {
			//Vector3 pos = world.bird.bound.getMin();
			birdDecal.setPosition(world.bird.position.x, world.bird.position.y, world.bird.position.z);
			birdDecal.setColor(new Color(lb, lb, lb, 1));
			decalBatch.add(birdDecal);
		}
	}
	
}
