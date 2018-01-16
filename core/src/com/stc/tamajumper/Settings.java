package com.stc.tamajumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by artem on 1/11/18.
 */

public class Settings {
    public static boolean soundEnabled = false;
    public static int[] highscores = new int[] {100, 80, 50, 30, 10};
    public final static String file = ".tamadajumper";

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
