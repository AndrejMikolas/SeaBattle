package com.example.andrej.seabattle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.andrej.seabattle.game_elements.GameData;
import com.example.andrej.seabattle.game_elements.GameEngine;
import com.example.andrej.seabattle.game_elements.GameType;

public class GameModeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode);
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
        if(view == findViewById(R.id.button_singleDevice)){
            GameData.getInstance().game.gameType = GameType.SingleDevice;
            Intent playerModeIntent = new Intent(getApplicationContext(), PlayersActivity.class);
            startActivity(playerModeIntent);
            overridePendingTransition(R.transition.trans_left_in, R.transition.trans_left_out);
        }
        if(view == findViewById(R.id.button_bluetooth)){
            Toast.makeText(getApplicationContext(),
                    "Sorry, Bluetooth mode is not supported now",
                    Toast.LENGTH_LONG).show();
            /*
            GameData.getInstance().game.gameType = GameType.Bluetooth;
            Intent btGameTypeIntent = new Intent(getApplicationContext(), CreateOrJoinGameActivity.class);
            startActivity(btGameTypeIntent);
            overridePendingTransition(R.transition.trans_left_in, R.transition.trans_left_out);
            */
        }
    }
}
