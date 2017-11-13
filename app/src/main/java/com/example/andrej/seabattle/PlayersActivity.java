package com.example.andrej.seabattle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PlayersActivity extends AppCompatActivity implements View.OnClickListener{
    public EditText playerOneName;
    public EditText playerTwoName;
    public Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        loadWidgets();
        game = (Game)getIntent().getSerializableExtra("Game");
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
            //TODO: dorobiť aktivitu
            Intent defineBattlegroundIntent = new Intent(getApplicationContext(), DefineBattlegroundActivity.class);
            game.player1 = new Player(playerOneName.getText().toString());
            game.player2 = new Player(playerTwoName.getText().toString());
            defineBattlegroundIntent.putExtra("Game", game);
            defineBattlegroundIntent.putExtra("isLastPlayer", false);
            startActivity(defineBattlegroundIntent);
            overridePendingTransition(R.transition.trans_left_in, R.transition.trans_left_out);
        }
    }

    private void loadWidgets(){
        playerOneName = (EditText) findViewById(R.id.editText_playerOneName);
        playerTwoName = (EditText) findViewById(R.id.editText_playerTwoName);
    }
}
