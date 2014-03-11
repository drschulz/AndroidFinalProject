package com.dsaw.hophome;

public class Settings {
	private static boolean soundEffectsEnabled = true;
	private static boolean musicEnabled = true;
	private static boolean vibrateEnabled = true;
	
	public static void toggleSound() {
		soundEffectsEnabled = !soundEffectsEnabled;
	}
	
	public static void toggleVibration() {
		vibrateEnabled = !vibrateEnabled;
	}
	
	public static void toggleMusic() {
		musicEnabled = !musicEnabled;
	}
	
	public static boolean soundEnabled() {
		return soundEffectsEnabled;
	}
	
	public static boolean musicEnabled() {
		return musicEnabled;
	}
	
	public static boolean vibrateEnabled() {
		return vibrateEnabled;
	}
	
	public static void setSoundEnabled(boolean value) {
		soundEffectsEnabled = value;
	}
	
	public static void setMusicEnabled(boolean value) {
		musicEnabled = value;
	}
	
	public static void setVibrateEnabled(boolean value) {
		vibrateEnabled = value;
	}
	
}
