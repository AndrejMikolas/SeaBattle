package com.example.andrej.seabattle;

import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by Andrej on 25.10.2017.
 */

public class GameEngine {
    public static final int MENU_MUSIC = R.raw.menu_music;
    public static final int MUSIC_VOLUME = 100;
    public static final boolean LOOP_MENU_MUSIC = true;
    public static Context context;
    public static Thread musicThread;

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
}
