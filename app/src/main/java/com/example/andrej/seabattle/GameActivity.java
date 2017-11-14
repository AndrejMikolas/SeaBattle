package com.example.andrej.seabattle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrej.seabattle.game_elements.GameData;
import com.example.andrej.seabattle.views.GameGroundView;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{
    GameGroundView gameGroundPlayer1;
    GameGroundView gameGroundPlayer2;
    TextView textViewPlayerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameGroundPlayer1 = (GameGroundView)findViewById(R.id.gameGroundPlayer1);
        gameGroundPlayer2 = (GameGroundView)findViewById(R.id.gameGroundPlayer2);
        textViewPlayerName = (TextView)findViewById(R.id.textView_playerName);

        if(GameData.getInstance().game.player1.onTurn){
            textViewPlayerName.setText(GameData.getInstance().game.player1.name);
        }
        else{
            textViewPlayerName.setText(GameData.getInstance().game.player2.name);
        }

        gameGroundPlayer2.setAlpha(0.0f);

        GameData.getInstance().game.player1.attackGround = gameGroundPlayer1.getAttackTiles();
        GameData.getInstance().game.player2.attackGround = gameGroundPlayer2.getAttackTiles();

        gameGroundPlayer1.setDefenseTiles(GameData.getInstance().game.player1.defenseGround);
        gameGroundPlayer2.setDefenseTiles(GameData.getInstance().game.player2.defenseGround);

        gameGroundPlayer1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(GameData.getInstance().game.player1.onTurn){
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    boolean changePlayer = gameGroundPlayer1.findClickedField(x, y);
                    handleGameLogic(changePlayer);
                }
                return false;
            }
        });

        gameGroundPlayer2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(GameData.getInstance().game.player2.onTurn){
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    boolean changePlayer = gameGroundPlayer2.findClickedField(x, y);
                    handleGameLogic(changePlayer);
                }
                return false;
            }
        });
    }

    private void handleGameLogic(boolean changePlayer) {
        if(changePlayer){
            /*
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.d("SeaBattle", e.toString());
            }
            */
            if(GameData.getInstance().game.player1.onTurn){
                GameData.getInstance().game.player1.onTurn = false;
                GameData.getInstance().game.player2.onTurn = true;
                textViewPlayerName.setText(GameData.getInstance().game.player2.name);
                gameGroundPlayer1.animate()
                        .translationX(gameGroundPlayer1.canvasWidth)
                        .alpha(0.0f)
                        .setDuration(500);
                gameGroundPlayer2.animate()
                        .translationX(0)
                        .alpha(1.0f)
                        .setDuration(500);
            }
            else {
                GameData.getInstance().game.player1.onTurn = true;
                GameData.getInstance().game.player2.onTurn = false;
                textViewPlayerName.setText(GameData.getInstance().game.player1.name);
                gameGroundPlayer2.animate()
                        .translationX(gameGroundPlayer2.canvasWidth)
                        .alpha(0.0f)
                        .setDuration(500);
                gameGroundPlayer1.animate()
                        .translationX(0)
                        .alpha(1.0f)
                        .setDuration(500);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.button_settings)){
            Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(settingsIntent);
            overridePendingTransition(R.transition.trans_bottom_in, R.transition.trans_bottom_out);
        }
        if(view == findViewById(R.id.button_pause)){
            Toast.makeText(this, "Pause", Toast.LENGTH_SHORT).show();
        }
    }
}
