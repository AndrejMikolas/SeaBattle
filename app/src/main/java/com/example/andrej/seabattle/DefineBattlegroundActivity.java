package com.example.andrej.seabattle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.andrej.seabattle.views.BattleGroundView;

public class DefineBattlegroundActivity extends AppCompatActivity implements View.OnClickListener{

    BattleGroundView battleGroundView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_battleground);

        battleGroundView = new BattleGroundView(getApplicationContext());
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.transition.trans_right_in, R.transition.trans_right_out);
    }


    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.button_resetShips)){
            //battleGroundView.resetShips();
        }
    }
}
