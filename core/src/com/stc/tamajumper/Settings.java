package com.stc.tamajumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by artem on 1/11/18.
 */

public class Settings {
    private static float accelerationMultiplier = 2;

    public static boolean soundEnabled = false;
    public static int[] highscores = new int[] {100, 80, 50, 30, 10};
    public final static String file = ".tamadajumper";
    public final static String configFile = ".tamadajumper.config";

    public static void load () {
        try {
            FileHandle filehandle = Gdx.files.external(file);

            String[] strings = filehandle.readString().split("\n");

            soundEnabled = Boolean.parseBoolean(strings[0]);
            System.out.println("load: soundEnabled="+soundEnabled);
            for (int i = 1; i <= 5; i++) {
                highscores[i] = Integer.parseInt(strings[i+1]);
            }
        } catch (Throwable e) {
            // :( It's ok we have defaults
        }
    }
    public static float getAccelMultiplier () {
        try {
            FileHandle filehandle = Gdx.files.external(configFile);

            String[] strings = filehandle.readString().split("\n");

            accelerationMultiplier = Float.parseFloat(strings[0]);
            System.out.println("getAccelMultiplier=" + accelerationMultiplier);
        } catch (Throwable e) {
            // :( It's ok we have defaults
        }
        return accelerationMultiplier;
    }
    public static void updateAccelMultiplier (float newAccel) {
        try {
            FileHandle filehandle = Gdx.files.external(configFile);
            accelerationMultiplier=newAccel;
            System.out.println("updateAccelMultiplier "+accelerationMultiplier);
            filehandle.writeString(Float.toString(accelerationMultiplier)+"\n", false);
        } catch (Throwable e) {
            // :( It's ok we have defaults
        }
    }


    public static void save () {
        try {
            FileHandle filehandle = Gdx.files.external(file);

            filehandle.writeString(Boolean.toString(soundEnabled)+"\n", false);
            System.out.println("load: soundEnabled="+soundEnabled);

            for (int i = 1; i <= 5; i++) {
                filehandle.writeString(Integer.toString(highscores[i])+"\n", true);
            }
        } catch (Throwable e) {
        }
    }

    public static void addScore (int score) {
        for (int i = 0; i < 5; i++) {
            if (highscores[i] < score) {
                for (int j = 4; j > i; j--)
                    highscores[j] = highscores[j - 1];
                highscores[i] = score;
                break;
            }
        }
    }
}
