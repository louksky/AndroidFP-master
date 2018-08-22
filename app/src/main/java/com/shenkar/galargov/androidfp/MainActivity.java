package com.shenkar.galargov.androidfp;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    MediaPlayer fullsong;
    MediaPlayer beep;
    AudioManager am;
    int currentvol;

    private RelativeLayout myLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        beep = MediaPlayer.create(MainActivity.this , R.raw.chips);
        fullsong= MediaPlayer.create(MainActivity.this, R.raw.luck);
        fullsong.setLooping(true);
        fullsong.start();

//        myLayout.setOnTouchListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentvol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        if(currentvol == 0)
            fullsong.pause();
        else
            fullsong.start();
    }


    public void toGame(View v){
        beep.start();
        Intent i = new Intent(this, Game.class);
        startActivity(i);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void toSetting(View v){
        beep.start();
        Intent i = new Intent(this, Settings.class);
        startActivity(i);
    }
}
