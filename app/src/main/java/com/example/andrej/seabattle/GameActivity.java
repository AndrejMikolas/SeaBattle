package com.example.andrej.seabattle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.button_settings)){
            //battleGroundView.resetShips();
        }
        if(view == findViewById(R.id.button_pause)){

        }
    }
}
