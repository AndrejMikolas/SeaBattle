package com.example.andrej.seabattle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameModeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode);
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
        if(view == findViewById(R.id.button_singleDevice)){
            //TODO: nastaviť property modu
            Intent playerModeIntent = new Intent(getApplicationContext(), PlayerModeActivity.class);
            startActivity(playerModeIntent);
            overridePendingTransition(R.transition.trans_left_in, R.transition.trans_left_out);
        }
        if(view == findViewById(R.id.button_bluetooth)){
            //TODO: nastaviť property modu
            Intent playerModeIntent = new Intent(getApplicationContext(), PlayerModeActivity.class);
            startActivity(playerModeIntent);
            overridePendingTransition(R.transition.trans_left_in, R.transition.trans_left_out);
        }
    }
}
