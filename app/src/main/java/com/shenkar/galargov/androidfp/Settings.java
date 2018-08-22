package com.shenkar.galargov.androidfp;

import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class Settings extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    SeekBar sb;
    AudioManager am;
    TextView mv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mv = findViewById(R.id.muteView);
        sb = findViewById(R.id.seekBar);
        am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int maxvol = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentvol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        sb.setMax(maxvol);
        sb.setProgress(currentvol);
        sb.setOnSeekBarChangeListener(this);
        mv.setText(String.valueOf(currentvol));
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(progress == 0){
            mv.setText("MUTED");
        }
        else
            mv.setText(String.valueOf(progress));
        am.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void onReturn(View v){
        finish();
    }
}
