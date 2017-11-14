package com.example.andrej.seabattle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.andrej.seabattle.game_elements.Game;
import com.example.andrej.seabattle.game_elements.GameData;
import com.example.andrej.seabattle.game_elements.Tile;
import com.example.andrej.seabattle.views.BattleGroundView;

public class DefineBattlegroundActivity extends AppCompatActivity implements View.OnClickListener{
    public BattleGroundView battleGroundView;

    public boolean isLastPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_battleground);
        battleGroundView = (BattleGroundView)findViewById(R.id.battleGround);
        this.isLastPlayer = getIntent().getBooleanExtra("isLastPlayer", false);
        setPlayerName();
    }

    private void setPlayerName() {
        TextView playerName = (TextView)findViewById(R.id.textView_playerName);
        if(!isLastPlayer){
            playerName.setText(GameData.getInstance().game.player1.name);
        }
        else{
            playerName.setText(GameData.getInstance().game.player2.name);
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.transition.trans_right_in, R.transition.trans_right_out);
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.button_resetShips)){
            battleGroundView.resetShips();
        }
        if(view == findViewById(R.id.button_nextPlayer)){
            Tile [][] battleGround = battleGroundView.getBattleGround();
            if(isLastPlayer){
                GameData.getInstance().game.player2.defenseGround = battleGround;
            }
            else{
                GameData.getInstance().game.player1.defenseGround = battleGround;
                Intent intent = new Intent(getApplicationContext(), DefineBattlegroundActivity.class);
                intent.putExtra("isLastPlayer", true);
                startActivity(intent);
                overridePendingTransition(R.transition.trans_left_in, R.transition.trans_left_out);
            }
        }
    }

}
