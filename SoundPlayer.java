package com.example.cristian.towerdefence;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Michelle on 5/3/2015.
 */
public class SoundPlayer {
    public static final int S1 = R.raw.bubblepop;

    private static SoundPool soundPool;
    private static ArrayList<Integer> soundPoolMap;

    /** Populate the SoundPool*/
    public static void initSounds(Context context) {
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 100);
        soundPoolMap = new ArrayList<Integer>();

        int i = soundPool.load(context, S1, 1);
        soundPoolMap.add( i );
    }

    public static void playSound(Context context) {

        if(soundPool == null || soundPoolMap == null){
            initSounds(context);
        }
        float volume = 1;

        soundPool.play(soundPoolMap.get(0), volume, volume, 1, 0, 1f);
    }

}
