package com.example.andrej.seabattle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
            //TODO: prechod na novú aktivitu
            Toast.makeText(this, "New player", Toast.LENGTH_SHORT).show();
        }
        if(view == findViewById(R.id.button_selectPlayer)){
            //TODO: prechod na novú aktivitu
            Toast.makeText(this, "Select player", Toast.LENGTH_SHORT).show();
        }
    }
}
