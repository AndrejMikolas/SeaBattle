package com.example.andrej.seabattle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    ToggleButton musicToggle;
    ToggleButton soundsToggle;
    ToggleButton vibrationToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        loadToggleButtons();

    }

    private void loadToggleButtons() {
        musicToggle = (ToggleButton)findViewById(R.id.toggleButtonMusic);
        soundsToggle = (ToggleButton)findViewById(R.id.toggleButtonSounds);
        vibrationToggle = (ToggleButton)findViewById(R.id.toggleButtonVibration);
        musicToggle.setChecked(GameEngine.isMusic());
        soundsToggle.setChecked(GameEngine.isSounds());
        vibrationToggle.setChecked(GameEngine.isVibrations());
    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.transition.trans_top_in, R.transition.trans_top_out);
    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.button_back)){
            onBackPressed();
        }
        if(view == findViewById(R.id.toggleButtonMusic)){
            GameEngine.setMusic(musicToggle.isChecked());
        }
        if(view == findViewById(R.id.toggleButtonSounds)){
            GameEngine.setSounds(soundsToggle.isChecked());
        }
        if(view == findViewById(R.id.toggleButtonVibration)){
            GameEngine.setVibrations(vibrationToggle.isChecked());
        }
    }
}
