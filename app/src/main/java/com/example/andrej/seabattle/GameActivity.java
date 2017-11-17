package com.example.andrej.seabattle;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrej.seabattle.game_elements.GameData;
import com.example.andrej.seabattle.game_elements.GameEngine;
import com.example.andrej.seabattle.views.GameGroundView;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{
    public GameGroundView gameGroundPlayer1;
    public GameGroundView gameGroundPlayer2;
    public TextView textViewPlayerName;
    public TextView textViewTimer;

    public long timer = 0;
    public long startTime = 0;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime + timer;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            textViewTimer.setText(String.format("Game time: %d:%02d", minutes, seconds));
            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        loadViews();
        if(GameData.getInstance().game.player1.onTurn){
            textViewPlayerName.setText(GameData.getInstance().game.player1.name);
            gameGroundPlayer2.animate()
                    .translationX(gameGroundPlayer2.canvasWidth)
                    .alpha(0.0f)
                    .setDuration(500);
            Toast.makeText(getApplicationContext(), "player 1 turn", Toast.LENGTH_LONG).show();
        }
        else{
            textViewPlayerName.setText(GameData.getInstance().game.player2.name);
            gameGroundPlayer1.animate()
                    .translationX(gameGroundPlayer1.canvasWidth)
                    .alpha(0.0f)
                    .setDuration(500);
            Toast.makeText(getApplicationContext(), "player 2 turn", Toast.LENGTH_LONG).show();
        }
        setGrounds();
        setGameGroundListeners();
        startTimer();
    }

    private void loadViews() {
        gameGroundPlayer1 = (GameGroundView)findViewById(R.id.gameGroundPlayer1);
        gameGroundPlayer2 = (GameGroundView)findViewById(R.id.gameGroundPlayer2);
        textViewPlayerName = (TextView)findViewById(R.id.textView_playerName);
        textViewTimer = (TextView)findViewById(R.id.textView_timer);
    }


    private void startTimer() {
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
    }

    private void stopTimer(){
        timer = System.currentTimeMillis() - startTime + timer;
        timerHandler.removeCallbacks(timerRunnable);
    }

    private void setGrounds() {
        GameData.getInstance().game.player1.attackGround = gameGroundPlayer1.getAttackTiles();
        GameData.getInstance().game.player2.attackGround = gameGroundPlayer2.getAttackTiles();

        gameGroundPlayer1.setDefenseTiles(GameData.getInstance().game.player2.defenseGround);
        gameGroundPlayer2.setDefenseTiles(GameData.getInstance().game.player1.defenseGround);
    }

    private void setGameGroundListeners() {
        gameGroundPlayer1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(GameData.getInstance().game.player1.onTurn){
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    boolean attackSuccessful = gameGroundPlayer1.makeAttackToField(x, y);
                    //checkGameFinish();
                    handleGameLogic(attackSuccessful);
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
                    boolean attackSuccessful = gameGroundPlayer2.makeAttackToField(x, y);
                    //checkGameFinish();
                    handleGameLogic(attackSuccessful);
                }
                return false;
            }
        });
    }

    private void checkGameFinish() {
        if(GameData.getInstance().game.player1.shipTilesRemaining == 0){
            Toast.makeText(getApplicationContext(), "player 1 win", Toast.LENGTH_LONG).show();
        }
        if(GameData.getInstance().game.player2.shipTilesRemaining == 0){
            Toast.makeText(getApplicationContext(), "player 2 win", Toast.LENGTH_LONG).show();
        }

    }

    private void handleGameLogic(boolean attackSuccessful) {
        if(attackSuccessful){
            checkGameFinish();
            return;
        }
        //Toast.makeText(getApplicationContext(), "change player", Toast.LENGTH_LONG).show();
        if(GameData.getInstance().game.player1.onTurn){
            GameData.getInstance().game.player1.onTurn = false;
            GameData.getInstance().game.player2.onTurn = true;
            textViewPlayerName.setText(GameData.getInstance().game.player2.name);
            GameEngine.bounceView(textViewPlayerName);
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
            GameEngine.bounceView(textViewPlayerName);
            gameGroundPlayer2.animate()
                    .translationX(gameGroundPlayer2.canvasWidth)
                    .alpha(0.0f)
                    .setDuration(500);
            gameGroundPlayer1.animate()
                    .translationX(0)
                    .alpha(1.0f)
                    .setDuration(500);
        }
        //gameGroundPlayer1.invalidate();
        //gameGroundPlayer2.invalidate();
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.button_settings)){
            Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(settingsIntent);
            overridePendingTransition(R.transition.trans_bottom_in, R.transition.trans_bottom_out);
        }
        if(view == findViewById(R.id.button_pause)){
            stopTimer();
            final PauseDialogFragment dialogFragment = new PauseDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "fragment");
            GameEngine.bounceView(view);
        }
    }

    @Override
    public void onBackPressed() {
        stopTimer();
        final PauseDialogFragment dialogFragment = new PauseDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "fragment");
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }


}
