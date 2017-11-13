package com.example.andrej.seabattle;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.andrej.seabattle.views.BattleGroundView;

public class DefineBattlegroundActivity extends AppCompatActivity implements View.OnClickListener{
    public Game game;
    public BattleGroundView battleGroundView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_define_battleground);
        battleGroundView = (BattleGroundView)findViewById(R.id.battleGround);
        this.game = (Game)getIntent().getSerializableExtra("Game");

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.transition.trans_right_in, R.transition.trans_right_out);
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.button_resetShips)){
            this.battleGroundView.resetShips();
        }
        if(view == findViewById(R.id.button_nextPlayer)){

        }
    }

}
