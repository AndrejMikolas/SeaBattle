package com.example.andrej.seabattle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public Button btn_startGame;
    public Button btn_about;
    public Button btn_settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_startGame = (Button) findViewById(R.id.button_startGame);
        btn_startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gameModeActivity = new Intent(getApplicationContext(), GameModeActivity.class);
                startActivity(gameModeActivity);
                overridePendingTransition(R.transition.trens_left_in, R.transition.trans_left_out);
            }
        });
    }


}
