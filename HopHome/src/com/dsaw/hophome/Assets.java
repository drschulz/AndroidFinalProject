package com.dsaw.hophome;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

	public static TextureRegion background;
	
	public static int highscore;
	public static Texture bunny;
	public static TextureRegion bear;
	public static Animation bunnyAnim;
	public static TextureRegion tree;
	public static TextureRegion bird;
	public static Animation bunnyEyesClosed;
	public static TextureRegion grass;
	public static Texture carrot;
	public static TextureRegion log;
	public static Music gamemusic;
	public static Music startmusic;
	public static Music pausemusic;
	public static Sound jumpsound;
	private static int bunnyWidth;
	private static int bunnyHeight;
	public static final int BUNNY_COLS = 7;
	public static final int BUNNY_ROWS = 6;
	public static TextureRegion[] bunnyFrames;
	
	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}
	
	public static void load() {
		bunnyFrames = new TextureRegion[BUNNY_COLS*BUNNY_ROWS];
		Texture temp;
		Texture.setEnforcePotImages(false);
		bunny = loadTexture("data/Rabbit_Sprite.png");
		bunnyWidth = bunny.getWidth()/BUNNY_COLS;
		bunnyHeight = bunny.getHeight()/BUNNY_ROWS;
		
		temp = loadTexture("data/Bear.png");
		bear = new TextureRegion(temp);
		temp = loadTexture("data/log.png");
		log = new TextureRegion(temp);
		temp = loadTexture("data/bird.png");
		bird = new TextureRegion(temp);
		temp = loadTexture("data/bush.png");
		tree = new TextureRegion(temp);
		TextureRegion[][] tmp = TextureRegion.split(bunny, bunnyWidth, bunnyHeight);

		int idx = 0;
		for(int i = 0; i < BUNNY_ROWS; i++) {
			for(int j = 0; j < BUNNY_COLS; j++) {
				bunnyFrames[idx++] = new TextureRegion(bunny, j*bunnyWidth + 40, i*bunnyHeight + 100,80,125);//tmp[i][j];
			}
		}
		bunnyAnim = new Animation(Bunny.ANIM_SPEED, bunnyFrames);
		carrot = loadTexture("data/carrot.png");
		temp = loadTexture("data/Background.png");
		background = new TextureRegion(temp);
		
		temp = loadTexture("data/Grass Pattern.png");
		grass = new TextureRegion(temp);
		
		gamemusic = Gdx.audio.newMusic(Gdx.files.internal("data/HopHome.mp3"));
		startmusic = Gdx.audio.newMusic(Gdx.files.internal("data/hophometitle.mp3"));
		pausemusic = Gdx.audio.newMusic(Gdx.files.internal("data/hophomedeathandpause.mp3"));
		//music.setLooping(true);
		//music.setVolume(0.5f);
		//music.play();
		
		
		jumpsound = Gdx.audio.newSound(Gdx.files.internal("data/jumpsound.wav"));
	}
	
	public static void playSound (Sound sound) {
		//if (Settings.soundEnabled) sound.play(1);
		sound.play(1.0f);
	}
	
	
}
