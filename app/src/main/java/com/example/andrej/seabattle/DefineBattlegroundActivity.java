package com.example.andrej.seabattle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrej.seabattle.game_elements.Game;
import com.example.andrej.seabattle.game_elements.GameData;
import com.example.andrej.seabattle.game_elements.GameEngine;
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
        isLastPlayer = getIntent().getBooleanExtra("isLastPlayer", false);
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
    protected void onRestart() {
        super.onRestart();
        battleGroundView.resetShips();
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.button_resetShips)){
            battleGroundView.resetShips();
            GameEngine.bounceView(view);
        }
        if(view == findViewById(R.id.button_nextPlayer)){
            if(battleGroundView.allShipsPlaced()){
                if(battleGroundView.checkShipsOverlap()){
                    Tile [][] battleGround = battleGroundView.getBattleGround();
                    if(isLastPlayer){
                        Button startGameButton = (Button)findViewById(R.id.button_nextPlayer);
                        startGameButton.setText("Start game");
                        GameData.getInstance().game.player2.defenseGround = battleGround;
                        GameData.getInstance().game.player2.shipTilesRemaining = battleGroundView.getShipsTilesCount();
                        Intent gameIntent= new Intent(getApplicationContext(), GameActivity.class);
                        startActivity(gameIntent);
                        overridePendingTransition(R.transition.trans_left_in, R.transition.trans_left_out);
                        GameEngine.setActualMusic(GameEngine.gameMusicThread);
                    }
                    else{
                        GameData.getInstance().game.player1.defenseGround = battleGround;
                        GameData.getInstance().game.player1.shipTilesRemaining = battleGroundView.getShipsTilesCount();
                        Intent battlegroundPlayer2Intent = new Intent(getApplicationContext(), DefineBattlegroundActivity.class);
                        battlegroundPlayer2Intent.putExtra("isLastPlayer", true);
                        startActivity(battlegroundPlayer2Intent);
                        overridePendingTransition(R.transition.trans_left_in, R.transition.trans_left_out);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Ships cannot be overlapped", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "Place all ships before continue", Toast.LENGTH_LONG).show();
            }
        }
    }

}
