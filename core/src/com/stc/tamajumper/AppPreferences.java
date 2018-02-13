package com.stc.tamajumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class AppPreferences {
    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_SOUND_VOL = "sound";
    private static final String PREFS_NAME = "tamajumperprefs";
    private static final String PREF_ACCEL_SENSITIVITY = "accel_sens";

    protected Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }


    public boolean isSoundEffectsEnabled() {
        boolean val= getPrefs().getBoolean(PREF_SOUND_ENABLED, true);
        System.out.println("isSoundEffectsEnabled="+val);
        return val;
    }

    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        System.out.println("setSoundEffectsEnabled="+soundEffectsEnabled);

        getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
        getPrefs().flush();

    }

    public boolean isMusicEnabled() {
        return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
    }

    public float getMusicVolume() {
        return getPrefs().getFloat(PREF_MUSIC_VOLUME, 0.5f);
    }

    public void setMusicVolume(float volume) {
        getPrefs().putFloat(PREF_MUSIC_VOLUME, volume);
        getPrefs().flush();
    }

    public float getSoundVolume() {
        return getPrefs().getFloat(PREF_SOUND_VOL, 0.5f);
    }

    public void setSoundVolume(float volume) {
        getPrefs().putFloat(PREF_SOUND_VOL, volume);
        getPrefs().flush();
    }

    public float getAccelSensitivity() {
        return getPrefs().getFloat(PREF_ACCEL_SENSITIVITY, Config.AccelerometerValues.DEFAULT_VALUE_SENSITIVITY);
    }

    public void setAccelSensitivity(float value) {
        getPrefs().putFloat(PREF_ACCEL_SENSITIVITY, value);
        getPrefs().flush();
    }


}