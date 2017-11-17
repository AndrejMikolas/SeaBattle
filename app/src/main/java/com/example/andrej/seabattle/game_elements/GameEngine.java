package com.example.andrej.seabattle.game_elements;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.andrej.seabattle.MenuMusicService;
import com.example.andrej.seabattle.MyBounceInterpolator;
import com.example.andrej.seabattle.R;

import java.util.HashMap;

/**
 * Created by Andrej on 25.10.2017.
 */

public class GameEngine {
    public static final int MENU_MUSIC = R.raw.menu_music;
    public static final int MUSIC_VOLUME = 100;
    public static final boolean LOOP_MENU_MUSIC = true;
    public static Context context;
    public static Thread musicThread;
    public static MediaPlayer mediaPlayer;


    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    private static boolean music;
    private static boolean sounds;
    private static boolean vibrations;

    public static HashMap<TileType, Integer> bitmaps = new HashMap<>();
    static{
        bitmaps.put(TileType.Water, R.drawable.water);
        bitmaps.put(TileType.Attacked, R.drawable.cross);
    }


    public static void loadSettings(){
        music = sharedPreferences.getBoolean("music", true);
        sounds = sharedPreferences.getBoolean("sounds", true);
        vibrations = sharedPreferences.getBoolean("vibration", true);
    }

    public static boolean isMusic() {
        return music;
    }

    public static void setMusic(boolean music) {
        GameEngine.music = music;
        editor = sharedPreferences.edit();
        editor.putBoolean("music", music);
        editor.apply();
    }

    public static boolean isSounds() {
        return sounds;
    }

    public static void setSounds(boolean sounds) {
        GameEngine.sounds = sounds;
        editor = sharedPreferences.edit();
        editor.putBoolean("sounds", sounds);
        editor.apply();
    }

    public static boolean isVibrations() {
        return vibrations;
    }

    public static void setVibrations(boolean vibrations) {
        GameEngine.vibrations = vibrations;
        editor = sharedPreferences.edit();
        editor.putBoolean("vibration", vibrations);
        editor.apply();
    }

    public static boolean onExit(){
        try{
            Intent bgMusic = new Intent(context, MenuMusicService.class);
            context.stopService(bgMusic);
            //musicThread.stop();
            musicThread.interrupt();
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public static void playSound(int _id) {
        if(isSounds()){
            if(mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
            mediaPlayer = MediaPlayer.create(context, _id);
            if(mediaPlayer != null)
                mediaPlayer.start();
        }
    }

    public static void vibrate(int miliseconds){
        if(isVibrations()){
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            if(vibrator.hasVibrator()){
                vibrator.vibrate(miliseconds);
            }

        }
    }

    public static void bounceView(View view) {
        final Animation viewBounceAnimIn = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        viewBounceAnimIn.setInterpolator(interpolator);
        view.startAnimation(viewBounceAnimIn);
    }
}
