package com.stc.tamajumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import static com.stc.tamajumper.TamaActor.MOVE_VELOCITY;

public class AppPreferences {
    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_SOUND_VOL = "sound";
    private static final String PREFS_NAME = "tamajumperprefs";
    private static final String PREF_ACCEL_SENSITIVITY = "accel_sens";
    private static boolean soundEnabled =getPrefs().getBoolean(PREF_SOUND_ENABLED);
    private static boolean musicEnabled =getPrefs().getBoolean(PREF_MUSIC_ENABLED);
    private static Preferences prefs;


    private static Preferences getPrefs() {
        if(prefs==null)prefs=Gdx.app.getPreferences(PREFS_NAME);
        return prefs;
    }


    static public boolean isSoundEffectsEnabled() {
        System.out.println("isSoundEffectsEnabled="+soundEnabled);
        return soundEnabled;
    }

    static public boolean toggleSoundEnabled() {
        soundEnabled =!getPrefs().getBoolean(PREF_SOUND_ENABLED);
        System.out.println("toggleSoundEnabled="+soundEnabled);
        getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEnabled);
        getPrefs().flush();
        return soundEnabled;
    }

    static public boolean isMusicEnabled() {
        System.out.println("isMusicEnabled="+musicEnabled);
        return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
    }
    static public boolean toggleMusicEnabled() {
        musicEnabled =!getPrefs().getBoolean(PREF_MUSIC_ENABLED);
        System.out.println("toggleMusicEnabled="+musicEnabled);
        getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
        return musicEnabled;
    }


    static public float getMusicVolume() {
        return getPrefs().getFloat(PREF_MUSIC_VOLUME, 0.5f);
    }

    static public void setMusicVolume(float volume) {
        getPrefs().putFloat(PREF_MUSIC_VOLUME, volume);
        getPrefs().flush();
    }

    static public float getSoundVolume() {
        return getPrefs().getFloat(PREF_SOUND_VOL, 0.5f);
    }

    static public void setSoundVolume(float volume) {
        getPrefs().putFloat(PREF_SOUND_VOL, volume);
        getPrefs().flush();
    }

    static public float getAccelSensitivity() {
        return getPrefs().getFloat(PREF_ACCEL_SENSITIVITY, Config.AccelerometerValues.DEFAULT_VALUE_SENSITIVITY);
    }

    static public void setAccelSensitivity(float value) {
        getPrefs().putFloat(PREF_ACCEL_SENSITIVITY, value);
        getPrefs().flush();
    }

    static public float getNormalizedAcelX(float accelX){
        return accelX*getAccelSensitivity();
    }
    static public float getNormalizedAcelX2(float accelX){
        return -accelX*getAccelSensitivity() / 10 * MOVE_VELOCITY;
    }


}
