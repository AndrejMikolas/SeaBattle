package com.example.andrej.seabattle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PlayersActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
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
        /*
        if(view == findViewById(R.id.button_next)){
            //TODO: dorobiť aktivitu
            //Intent newPlayerIntent = new Intent(getApplicationContext(), NewPlayerActivity.class);
            //startActivity(newPlayerIntent);
            //overridePendingTransition(R.transition.trans_left_in, R.transition.trans_left_out);
        }
        */
    }
}
