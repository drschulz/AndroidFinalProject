package com.dsaw.hophome;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class WorldRenderer {
	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 15;
	World world;
	OrthographicCamera camera;
	SpriteBatch batch;
	TextureRegion background;
	float angle;
	ShapeRenderer shapeRenderer;
	Color backcolor;
	Color grasscolor;
	//Color colorBottom;
	boolean day;
	
	
	public WorldRenderer(SpriteBatch batch, World world) {
		this.world = world;
		this.batch = batch;
		//this.camera = new OrthographicCamera(320, 480);
		this.camera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.camera.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
		angle = 0;
		shapeRenderer = new ShapeRenderer();
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		backcolor = new Color(0.36f, 0.61f, 0.94f, 1);
		grasscolor = new Color(0.22f, 0.81f, 0.11f, 1);
		day = true;
	}
	
	public void render() {
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		shapeRenderer.setProjectionMatrix(camera.combined);
		//Gdx.gl.glClearColor(1, 1, 1, 1);
		//Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//batch.setProjectionMatrix(camera.combined);
		renderBackground();
		if(world.bunny.position.z <= world.log.position.z) {
			renderLog();
			renderBunny();
		}
		else {
			renderBunny();
			renderLog();
		}
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
		shapeRenderer.rect(0, 0, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		shapeRenderer.setColor(grasscolor);
		shapeRenderer.rect(0, 0, FRUSTUM_WIDTH, FRUSTUM_HEIGHT/3);
		shapeRenderer.end();
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(new Color(0,0,0,1));
		shapeRenderer.rect(0, 0, FRUSTUM_WIDTH, FRUSTUM_HEIGHT/3);
		shapeRenderer.end();
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
		batch.enableBlending();
		batch.begin();
		batch.draw(keyFrame, world.bunny.position.x - world.bunny.BUNNY_WIDTH/2, world.bunny.position.y - world.bunny.BUNNY_HEIGHT/2, Bunny.BUNNY_WIDTH, Bunny.BUNNY_HEIGHT);
		//batch.draw(keyFrame, world.bunny.position.x, world.bunny.position.y);
		//batch.draw(Assets.bunny, world.bunny.position.x, world.bunny.position.y, Bunny.BUNNY_WIDTH, Bunny.BUNNY_HEIGHT);
		//TextureRegion keyFrame;
		//keyFrame = new TextureRegion(Assets.bunny, 0, 0, 378, 593);
		//batch.draw(keyFrame, world.bunny.position.x, world.bunny.position.y, camera.viewportWidth/4, camera.viewportHeight/4);
		batch.end();
	}
	
	public void renderLog() {
		if(world.log.state == Log.LOG_STATE_ALIVE) {
			batch.enableBlending();
			batch.begin();
			batch.draw(Assets.log, world.log.position.x, world.log.position.y, Log.LOG_WIDTH, Log.LOG_HEIGHT);
			batch.end();
		}
	}
	
}
