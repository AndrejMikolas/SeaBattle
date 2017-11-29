package com.example.andrej.seabattle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

import com.example.andrej.seabattle.game_elements.GameEngine;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    public ToggleButton mMusicToggle;
    public ToggleButton mSoundsToggle;
    public ToggleButton mVibrationToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        loadToggleButtons();

    }

    private void loadToggleButtons() {
        mMusicToggle = (ToggleButton)findViewById(R.id.toggleButtonMusic);
        mSoundsToggle = (ToggleButton)findViewById(R.id.toggleButtonSounds);
        mVibrationToggle = (ToggleButton)findViewById(R.id.toggleButtonVibration);
        mMusicToggle.setChecked(GameEngine.isMusic());
        mSoundsToggle.setChecked(GameEngine.isSounds());
        mVibrationToggle.setChecked(GameEngine.isVibrations());
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
            GameEngine.setMusic(mMusicToggle.isChecked());
            GameEngine.bounceView(view);
        }
        if(view == findViewById(R.id.toggleButtonSounds)){
            GameEngine.setSounds(mSoundsToggle.isChecked());
            GameEngine.bounceView(view);
        }
        if(view == findViewById(R.id.toggleButtonVibration)){
            GameEngine.setVibrations(mVibrationToggle.isChecked());
            GameEngine.bounceView(view);
        }
    }
}
