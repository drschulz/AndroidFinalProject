package com.dsaw.hophome;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class HopHomePreferences {
	
	private static final String PREFS_NAME = "hophome";
	private static final String NUM_DAYS = "days";
	private static final String HIGH_SCORE_DAYS = "highscore";
	
	public HopHomePreferences() {
		
	}
	
	protected Preferences getPrefs() {
		return Gdx.app.getPreferences(PREFS_NAME);
	}
	
	public int getDays() {
		return getPrefs().getInteger(NUM_DAYS);
	}
	
	public int getHighScore() {
		return getPrefs().getInteger(HIGH_SCORE_DAYS);
	}
	
	public void setDays(int days) {
		getPrefs().putInteger(NUM_DAYS, days);
		getPrefs().flush();
	}
	
}
