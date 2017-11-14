package com.example.andrej.seabattle;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.andrej.seabattle.game_elements.GameEngine;

/**
 * Created by Andrej on 25.10.2017.
 */

public class MenuMusicService extends Service {
    public static boolean isRunning = false;
    MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        setMusicOptions(this, GameEngine.LOOP_MENU_MUSIC, GameEngine.MUSIC_VOLUME, GameEngine.MENU_MUSIC);
    }

    public void setMusicOptions(Context context, boolean isLooped, int volume, int soundFile){
        player = MediaPlayer.create(context, soundFile);
        player.setLooping(isLooped);
        player.setVolume(volume, volume);
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        try{
            player.start();
            isRunning = true;
        }
        catch(Exception e){
            isRunning = false;
            player.stop();
        }
        return flags;
    }

    @Override
    public void onStart(Intent intent, int startId){

    }

    public void onStop(){
        isRunning = false;
    }

    public IBinder onUnBind(Intent arg0){
        return null;
    }

    public void onPause(){

    }

    @Override
    public void onDestroy(){
        player.stop();
        player.release();
    }

    @Override
    public void onLowMemory(){
        player.stop();
    }
}
