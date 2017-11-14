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
        /*
        if(!isLastPlayer){
            super.onBackPressed();
            overridePendingTransition(R.transition.trans_right_in, R.transition.trans_right_out);
        }
        else{
            Intent battlegroundPlayer1Intent = new Intent(getApplicationContext(), DefineBattlegroundActivity.class);
            battlegroundPlayer1Intent.putExtra("isLastPlayer", false);
            startActivity(battlegroundPlayer1Intent);
            overridePendingTransition(R.transition.trans_left_in, R.transition.trans_left_out);
        }
        */
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        battleGroundView.resetShips();
        //battleGroundView.invalidate();
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
                Intent gameIntent= new Intent(getApplicationContext(), GameActivity.class);
                startActivity(gameIntent);
                overridePendingTransition(R.transition.trans_left_in, R.transition.trans_left_out);
            }
            else{
                GameData.getInstance().game.player1.defenseGround = battleGround;
                Intent battlegroundPlayer2Intent = new Intent(getApplicationContext(), DefineBattlegroundActivity.class);
                battlegroundPlayer2Intent.putExtra("isLastPlayer", true);
                startActivity(battlegroundPlayer2Intent);
                overridePendingTransition(R.transition.trans_left_in, R.transition.trans_left_out);
            }
        }
    }

}
