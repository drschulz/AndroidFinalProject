package com.dsaw.hophome;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
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
	//Color colorBottom;
	boolean day;
	
	
	public WorldRenderer(SpriteBatch batch, World world) {
		this.world = world;
		this.batch = batch;
		//this.camera = new OrthographicCamera(320, 480);
		
		//this.camera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.camera = new PerspectiveCamera(67, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.camera.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 10);
		//this.camera.position.set(0, 0, 10);
		this.camera.lookAt(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
		this.camera.near = 0.1f;
		this.camera.far = 300;
		this.camera.update();
		angle = 0;
		shapeRenderer = new ShapeRenderer();
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		backcolor = new Color(0.36f, 0.61f, 0.94f, 1);
		grasscolor = new Color(0.22f, 0.81f, 0.11f, 1);
		day = true;
		decalBatch = new DecalBatch(new CameraGroupStrategy(this.camera));
		//bunnyDecal.setDimensions(Bunny.BUNNY_WIDTH, Bunny.BUNNY_HEIGHT);
		//bunnyDecal.setColor(new Color(0,0,1, 0));
		logDecal = Decal.newDecal(Log.LOG_WIDTH, Log.LOG_HEIGHT, Assets.log, true);
		//logDecal.setDimensions(Log.LOG_WIDTH, Log.LOG_HEIGHT);
		bearDecal = Decal.newDecal(Bear.BEAR_WIDTH, Bear.BEAR_HEIGHT, Assets.bear, true);
		//bearDecal.setDimensions(Bear.BEAR_WIDTH, Bear.BEAR_HEIGHT);
	}
	
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		//Gdx.gl.glDepthRangef(camera.near, camera.far);
		camera.update();
		//batch.setProjectionMatrix(camera.combined);
		shapeRenderer.setProjectionMatrix(camera.combined);
		//Gdx.gl.glClearColor(1, 1, 1, 1);
		//Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//batch.setProjectionMatrix(camera.combined);
		//decalBatch.setGroupStrategy(new CameraGroupStrategy(this.camera));
		renderBackground();
		renderBunny();
		renderLog();
		/*if(world.bunny.position.z <= world.log.position.z) {
			renderLog();
			renderBunny();
		}
		else {
			renderBunny();
			renderLog();
		}*/
		renderBear();
		decalBatch.flush();
	}
	
	public void renderBackground() {
		if(day) {
			backcolor.sub(0.0001f, 0.0001f, 0.0001f, 1);
			grasscolor.sub(0.0001f, 0.0001f, 0.0001f, 1);
		}
		else {
			backcolor.add(0.0001f, 0.0001f, 0.0001f, 1);
			grasscolor.add(0.0001f, 0.0001f, 0.0001f, 1);
		}
		if (backcolor.b > 0.94f) {
			day = true;
		}
		else if (backcolor.b < 0.4f){
			day = false;
		}
		Gdx.app.debug("in render", "here!");
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(backcolor);
		//shapeRenderer.box(0, 0, -10, FRUSTUM_WIDTH, FRUSTUM_HEIGHT, .02f);
		shapeRenderer.identity();
		shapeRenderer.translate(0, 0, -20);
		shapeRenderer.rect(-FRUSTUM_WIDTH*2, 0, FRUSTUM_WIDTH*4, FRUSTUM_HEIGHT*5);
		shapeRenderer.end();
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(grasscolor);
		shapeRenderer.identity();
		shapeRenderer.rotate(1, 0, 0, 90);
		//shapeRenderer.box(0, -.02, 1, FRUSTUM_WIDTH, 0.02, 10);
		shapeRenderer.rect(-FRUSTUM_WIDTH, -FRUSTUM_HEIGHT, FRUSTUM_WIDTH*3, FRUSTUM_HEIGHT*2);
		shapeRenderer.end();
		
		/*shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(new Color(0,0,0,1));
		shapeRenderer.rect(0, 0, FRUSTUM_WIDTH, FRUSTUM_HEIGHT/3);
		shapeRenderer.end();*/
		/*batch.disableBlending();
		batch.begin();
		
		//batch.draw(Assets.background, -camera.viewportWidth/2, -camera.viewportHeight/2);
				//batch.draw(Assets.background, -247 + FRUSTUM_WIDTH / 2, );
		//batch.draw(Assets.sky, -247 + FRUSTUM_WIDTH / 2, -460 + FRUSTUM_HEIGHT, 247, 235, 493, 470, 1, 1, angle);

		batch.draw(Assets.grass, camera.position.x - FRUSTUM_WIDTH / 2, camera.position.y - FRUSTUM_HEIGHT / 2, FRUSTUM_WIDTH,
				FRUSTUM_HEIGHT/3);
		
		batch.end();*/
		//angle += .1;
	}
	
	public void renderBunny() {
		TextureRegion keyFrame;// = Assets.bunnyAnim.getKeyFrame(0);
		switch(world.bunny.state) {
		case Bunny.BUNNY_STATE_JUMP:
			
			keyFrame = Assets.bunnyAnim.getKeyFrame(world.bunny.mode*Assets.BUNNY_COLS*Bunny.ANIM_SPEED + 2*Bunny.ANIM_SPEED);
			break;
		case Bunny.BUNNY_STATE_FALL:
			keyFrame = Assets.bunnyAnim.getKeyFrame(world.bunny.mode*Assets.BUNNY_COLS*Bunny.ANIM_SPEED + 4*Bunny.ANIM_SPEED);
			break;
		default:
			keyFrame = Assets.bunnyAnim.getKeyFrame(world.bunny.mode*Assets.BUNNY_COLS*Bunny.ANIM_SPEED + world.bunny.stateTime);
			break;
		}
		bunnyDecal = Decal.newDecal(Bunny.BUNNY_WIDTH, Bunny.BUNNY_HEIGHT, keyFrame, true);
		Vector3 pos = world.bunny.bound.getMin();
		bunnyDecal.setPosition(world.bunny.position.x, world.bunny.position.y, world.bunny.position.z);
		//bunnyDecal.lookAt(camera.position, camera.up);
		//bunnyDecal.setPosition(0, 0, 0);
		decalBatch.add(bunnyDecal);
		/*batch.enableBlending();
		batch.begin();
		batch.draw(keyFrame, world.bunny.bounds.x, world.bunny.bounds.y, Bunny.BUNNY_WIDTH, Bunny.BUNNY_HEIGHT);*/
		
		//batch.draw(keyFrame, world.bunny.position.x, world.bunny.position.y);
		//batch.draw(Assets.bunny, world.bunny.position.x, world.bunny.position.y, Bunny.BUNNY_WIDTH, Bunny.BUNNY_HEIGHT);
		//TextureRegion keyFrame;
		//keyFrame = new TextureRegion(Assets.bunny, 0, 0, 378, 593);
		//batch.draw(keyFrame, world.bunny.position.x, world.bunny.position.y, camera.viewportWidth/4, camera.viewportHeight/4);
		
		//batch.end();
	}
	
	public void renderBear() {
		if(world.bear.state != Bear.STATE_DEAD) {
			//bearDecal.setScale(Bear.BEAR_WIDTH, Bear.BEAR_HEIGHT);
			bearDecal.setTextureRegion(Assets.bear);
			Vector3 pos = world.bear.bound.getMin();
			bearDecal.setPosition(pos.x, pos.y, pos.z);
			//bearDecal.lookAt(camera.position, camera.up);
			decalBatch.add(bearDecal);
			/*batch.enableBlending();
			batch.begin();
			batch.draw(Assets.bear, world.bear.bounds.x, world.bear.position.y, Bear.BEAR_WIDTH, Bear.BEAR_HEIGHT);
			batch.end();*/
		}
	}
	
	public void renderLog() {
		if(world.log.state == Log.STATE_ALIVE) {
			//logDecal.setScale(Log.LOG_WIDTH, Log.LOG_HEIGHT);
			//logDecal.setTextureRegion(Assets.log);
			Vector3 pos = world.log.bound.getMin();
			logDecal.setPosition(world.log.position.x, world.log.position.y, world.log.position.z);
			
			//logDecal.lookAt(camera.position, camera.up);
			decalBatch.add(logDecal);
			/*batch.enableBlending();
			batch.begin();
			batch.draw(Assets.log, world.log.bounds.x, world.log.bounds.y, Log.LOG_WIDTH, Log.LOG_HEIGHT);
			batch.end();*/
		}
	}
	
}
