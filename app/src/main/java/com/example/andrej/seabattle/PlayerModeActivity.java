package com.example.andrej.seabattle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.andrej.seabattle.game_elements.GameEngine;

public class PlayerModeActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_mode);
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
        if(view == findViewById(R.id.button_newPlayer)){
            Intent newPlayerIntent = new Intent(getApplicationContext(), NewPlayerActivity.class);
            startActivity(newPlayerIntent);
            overridePendingTransition(R.transition.trans_left_in, R.transition.trans_left_out);
        }
        if(view == findViewById(R.id.button_selectPlayer)){
            Intent selectPlayerIntent = new Intent(getApplicationContext(), SelectPlayerActivity.class);
            startActivity(selectPlayerIntent);
            overridePendingTransition(R.transition.trans_left_in, R.transition.trans_left_out);
        }
    }
}
