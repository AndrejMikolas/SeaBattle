package com.example.andrej.seabattle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.andrej.seabattle.views.BattleGroundView;

public class DefineBattlegroundActivity extends AppCompatActivity {

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

}
