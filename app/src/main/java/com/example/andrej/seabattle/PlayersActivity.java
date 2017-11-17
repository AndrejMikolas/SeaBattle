package com.example.andrej.seabattle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.andrej.seabattle.game_elements.Game;
import com.example.andrej.seabattle.game_elements.GameData;
import com.example.andrej.seabattle.game_elements.GameEngine;
import com.example.andrej.seabattle.game_elements.Player;

public class PlayersActivity extends AppCompatActivity implements View.OnClickListener{
    public EditText playerOneName;
    public EditText playerTwoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        loadWidgets();
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
        if(view == findViewById(R.id.button_next)){
            GameData.getInstance().game.player1 = new Player(playerOneName.getText().toString().length() == 0 ? "Player 1" : playerOneName.getText().toString(), true);
            GameData.getInstance().game.player2 = new Player(playerTwoName.getText().toString().length() == 0 ? "Player 2" : playerTwoName.getText().toString(), false);
            Intent battlegroundPlayer1Intent = new Intent(this, DefineBattlegroundActivity.class);
            battlegroundPlayer1Intent.putExtra("isLastPlayer", false);
            startActivity(battlegroundPlayer1Intent);
            overridePendingTransition(R.transition.trans_left_in, R.transition.trans_left_out);
        }
    }

    private void loadWidgets(){
        playerOneName = (EditText) findViewById(R.id.editText_playerOneName);
        playerTwoName = (EditText) findViewById(R.id.editText_playerTwoName);
    }
}
