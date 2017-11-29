package com.example.andrej.seabattle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.andrej.seabattle.game_elements.GameEngine;

public class CreateOrJoinGameActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_join_game);
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
        if(view == findViewById(R.id.button_createGame)){
            Intent createGameIntent = new Intent(getApplicationContext(), CreateGameActivity.class);
            startActivity(createGameIntent);
            overridePendingTransition(R.transition.trans_left_in, R.transition.trans_left_out);
        }
        if(view == findViewById(R.id.button_joinGame)){
            Intent joinGameIntent = new Intent(getApplicationContext(), PairDeviceActivity.class);
            startActivity(joinGameIntent);
            overridePendingTransition(R.transition.trans_left_in, R.transition.trans_left_out);
        }
    }
}
