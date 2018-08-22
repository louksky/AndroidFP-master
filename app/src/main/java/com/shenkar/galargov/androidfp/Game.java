package com.shenkar.galargov.androidfp;

import android.content.Intent;
import android.graphics.Canvas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;


public class Game extends AppCompatActivity {

    private AnimatedView mLayout;
    private static int lastDir=1;
    private int countTo11=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_animation);

        mLayout = new AnimatedView(this);

        setContentView(mLayout);
    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();

        if (event.getAction() == event.ACTION_DOWN) {
            if (x < mLayout.getWidth() / 2) {
                mLayout.mHorSpeed = -mLayout.HOR_SPEED;
            } else {
                mLayout.mHorSpeed = mLayout.HOR_SPEED;
            }

            mLayout.mTimeOfDown = System.currentTimeMillis();
            mLayout.postInvalidateOnAnimation();

            return true;
        }
        //PO
        if (event.getAction() == event.ACTION_MOVE) {

        }

        if (event.getAction() == event.ACTION_UP) {

            mLayout.mXonDown = Calculations.calculateCarLocation(System.currentTimeMillis() -  mLayout.mTimeOfDown,  mLayout.mXonDown,  mLayout.mHorSpeed);
            mLayout.mHorSpeed = 0;

        }
        // onMoveEvent(x); // todo: relevant only if move will change car direction

        return super.onTouchEvent(event);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mLayout.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLayout.resume();
    }

    public void onReturn(View v) {
        finish();
    }

    private int rand(int min, int max) {
        return (int)(Math.random() * max + min);
    }
}
