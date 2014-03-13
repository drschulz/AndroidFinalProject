package com.dsaw.hophome;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

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
	public static Texture startmenubackground;
	public static Texture title;
	public static Music gamemusic;
	public static Music startmusic;
	public static Music pausemusic;
	public static Sound jumpsound;
	private static int bunnyWidth;
	private static int bunnyHeight;
	public static final int BUNNY_COLS = 7;
	public static final int BUNNY_ROWS = 6;
	public static TextureRegion[] bunnyFrames;
	public static BitmapFont font;
	public static LabelStyle whitestyle;
	public static LabelStyle blackstyle;
	public static TextButtonStyle pausebuttonStyle;
	public static TextButtonStyle buttonStyle;
	public static TextButtonStyle startbuttonStyle;
	
	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}
	
	public static void load() {
		loadGameScreenTextures();
		loadTitleTextures();
		loadButtonStyles();
		loadMusicandSound();
	}
	
	private static void loadTitleTextures() {
		startmenubackground = loadTexture("Background1.png");
	    title = loadTexture("Hop_Home_title.png");
	}
	
	private static void loadMusicandSound() {
		gamemusic = Gdx.audio.newMusic(Gdx.files.internal("data/HopHome.mp3"));
		startmusic = Gdx.audio.newMusic(Gdx.files.internal("data/hophometitle.mp3"));
		pausemusic = Gdx.audio.newMusic(Gdx.files.internal("data/hophomedeathandpause.mp3"));
		jumpsound = Gdx.audio.newSound(Gdx.files.internal("data/jumpsound.wav"));
	}
	
	private static void loadButtonStyles() {
		font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"), false);
	    //Set the BitmapFont color
	    whitestyle = new LabelStyle(font, Color.WHITE);
	    blackstyle = new LabelStyle(font, Color.BLACK);
	    TextureAtlas buttonAtlas; 
		//TextButtonStyle buttonStyle; //Font, Color of text in button
		Skin buttonSkin;
	     
		buttonSkin = new Skin();
	    buttonAtlas = new TextureAtlas("buttons/pause.pack");
	    buttonSkin.addRegions(buttonAtlas);
	    
	    pausebuttonStyle = new TextButtonStyle();
	    pausebuttonStyle.up = buttonSkin.getDrawable("Pause");
	    pausebuttonStyle.over = buttonSkin.getDrawable("Pause");
	    pausebuttonStyle.down = buttonSkin.getDrawable("Pause");
	    pausebuttonStyle.font = font;
		
	    buttonSkin = new Skin();
	    buttonAtlas = new TextureAtlas("buttons/button.pack");
	    buttonSkin.addRegions(buttonAtlas);
	    
	    buttonStyle = new TextButtonStyle();
	    buttonStyle.up = buttonSkin.getDrawable("button");
	    buttonStyle.over = buttonSkin.getDrawable("button");
	    buttonStyle.down = buttonSkin.getDrawable("buttonPressed");
	    buttonStyle.font = font;
	    buttonStyle.font.scale(0.1f);
	    
	    buttonSkin = new Skin();
	    buttonAtlas = new TextureAtlas("buttons/CarrotButton.pack");
	    buttonSkin.addRegions(buttonAtlas);
	    
	    startbuttonStyle = new TextButtonStyle();
	    startbuttonStyle.up = buttonSkin.getDrawable("carrot");
	    startbuttonStyle.over = buttonSkin.getDrawable("carrot");
	    startbuttonStyle.down = buttonSkin.getDrawable("carrot");
	    startbuttonStyle.font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"), false);
	    startbuttonStyle.font.setScale(0.3f);
	}
	
	private static void loadGameScreenTextures() {
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
	}
	
	public static void vibrate(int millis) {
		if(Settings.vibrateEnabled()) {
			Gdx.input.vibrate(millis);
		}
	}
	
	public static void playMusic(Music music) {
		if(Settings.musicEnabled()) {
			music.setVolume(0.5f);
			music.setLooping(true);
			music.play();
		}
	}
	
	public static void stopMusic(Music music) {
		music.stop();
	}
	
	public static void playSound (Sound sound) {
		//if (Settings.soundEnabled) sound.play(1);
		if(Settings.soundEnabled()) {
			sound.play(1.0f);
		}
	}
	
	
}
