package com.example.andrej.seabattle;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.andrej.seabattle.game_elements.Game;
import com.example.andrej.seabattle.game_elements.GameData;
import com.example.andrej.seabattle.game_elements.GameEngine;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GameEngine.sharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        GameEngine.context = getApplicationContext();
        GameEngine.setActualMusic(GameEngine.menuMusicThread);
        GameEngine.loadSettings();
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.button_startGame)){
            GameData.getInstance().game = new Game();
            Intent gameModeIntent = new Intent(getApplicationContext(), GameModeActivity.class);
            startActivity(gameModeIntent);
            overridePendingTransition(R.transition.trans_left_in, R.transition.trans_left_out);
        }
        if(view == findViewById(R.id.button_gamesHistory)){
            Intent gamesHistoryIntent = new Intent(getApplicationContext(), GamesHistoryActivity.class);
            startActivity(gamesHistoryIntent);
            overridePendingTransition(R.transition.trans_right_in, R.transition.trans_right_out);
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
        GameEngine.stopMusic();
        this.finishAffinity();
    }

}
