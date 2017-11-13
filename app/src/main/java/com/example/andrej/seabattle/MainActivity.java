package com.example.andrej.seabattle;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //final GameEngine engine = new GameEngine();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GameEngine.sharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        GameEngine.loadSettings();

        GameEngine.musicThread = new Thread(){
            public void run(){
                Intent bgMusic = new Intent(getApplicationContext(), MenuMusicService.class);
                startService(bgMusic);
                GameEngine.context = getApplicationContext();
            }
        };
        GameEngine.musicThread.start();

    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.button_startGame)){
            //TODO: prehodiť späť na pôvodné
            Intent gameModeIntent = new Intent(getApplicationContext(), GameModeActivity.class);
            startActivity(gameModeIntent);
            overridePendingTransition(R.transition.trans_left_in, R.transition.trans_left_out);

            GameEngine.onExit();
            /*
            Intent gameModeIntent = new Intent(getApplicationContext(), DefineBattlegroundActivity.class);
            startActivity(gameModeIntent);
            overridePendingTransition(R.transition.trans_left_in, R.transition.trans_left_out);
            */

        }
        if(view == findViewById(R.id.button_settings)){
            Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(settingsIntent);
            overridePendingTransition(R.transition.trans_bottom_in, R.transition.trans_bottom_out);
        }
        if(view == findViewById(R.id.button_about)){
            Intent aboutIntent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(aboutIntent);
            overridePendingTransition(R.transition.trans_bottom_in, R.transition.trans_bottom_out);
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        GameEngine.onExit();
    }

}
