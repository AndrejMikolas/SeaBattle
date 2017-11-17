package com.example.andrej.seabattle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.andrej.seabattle.game_elements.GameEngine;

public class NewPlayerActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_player);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.transition.trans_right_in, R.transition.trans_right_out);
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.button_back)){
            onBackPressed();
        }
        if(view == findViewById(R.id.button_next)){
            Intent gameTypeIntent = new Intent(getApplicationContext(), CreateOrJoinGameActivity.class);
            startActivity(gameTypeIntent);
            overridePendingTransition(R.transition.trans_left_in, R.transition.trans_left_out);
        }
    }
}
