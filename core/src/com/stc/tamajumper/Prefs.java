package com.stc.tamajumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Prefs {

    private static final String PREF_SOUND_VOL = "sound";
    private static final String PREFS_NAME = "tamajumperprefs";
    private static final String PREF_SENSITIVITY = "accel_sens";
    private static final String PREF_HIGHSCORE = "highscores";
    public static final int HIGHSCORES_LIST_SIZE = 5;
    private static Preferences prefs;
    public static float volume;
    public static float sensitivity;


    private static Preferences getInstance() {
        checkInit();
        return prefs;
    }
    private static void checkInit(){
        if(prefs==null){
            prefs=Gdx.app.getPreferences(PREFS_NAME);
            volume=prefs.getFloat(PREF_SOUND_VOL, 0.5f);
            sensitivity=prefs.getFloat(PREF_SENSITIVITY, Config.AccelerometerValues.DEFAULT_VALUE_SENSITIVITY);
        }
    }





    static public float getSoundVolume() {
        checkInit();
        return volume;
    }

    static public void setSoundVolume(float newVal) {
        checkInit();
        volume=newVal;
        getInstance().putFloat(PREF_SOUND_VOL, volume);
        getInstance().flush();
    }

    static public void setAccelSensitivity(float newVal) {
        checkInit();
        sensitivity=newVal;
        getInstance().putFloat(PREF_SENSITIVITY, sensitivity);
        getInstance().flush();
    }
    static public float getAccelSensitivity() {
        checkInit();
        return sensitivity;
    }

    static public boolean setHighscore(int value) {
        checkInit();
        List<Integer>list = getHighscores();
        if(list.get(0)<value){
            list.set(list.size()-1,value);
            Collections.sort(list);
            Collections.reverse(list);
            for (int i = 0; i < HIGHSCORES_LIST_SIZE; i++) {
                getInstance().putInteger(PREF_HIGHSCORE+i,list.get(i));
            }
            getInstance().flush();
            return true;
        }
        return false;
    }
    static public List<Integer> getHighscores() {
        checkInit();
        List<Integer>list = new ArrayList<>();
        for (int i = 0; i < HIGHSCORES_LIST_SIZE; i++) {
            list.add(getInstance().getInteger(PREF_HIGHSCORE+i,0));
        }
        return list;
    }






}
