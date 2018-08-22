package com.shenkar.galargov.androidfp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
//louk add
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class AnimatedView extends SurfaceView implements  Runnable {
    public static final int HOR_SPEED = 10;
    private int radius = 50;
    // private mDeltaY = 10;

    Paint paint;
    public int mHorSpeed;
    public long mTimeOfDown;
    // the x location of the car at the time of touch down
    public float mXonDown;
    public float DethScore;
    Paint textpaint;
    long myx , myy;
    private int movement = 100,cou,cou2;
    private Canvas animcanvas;
    static int tdw = 400 , tdh = 300;
    private static boolean PathDirection = false;
    private List<PointLine> pathlist;
    private Random RPoor;
    private SurfaceHolder surfaceHolder;
    private boolean canWePlay = false;
    private Thread mThread;
    public static int screenWidth, screenHeight;


    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        textpaint = new Paint();
        textpaint.setAntiAlias(true);
        textpaint.setColor(Color.RED);
        textpaint.setTextSize(70);

        // cannonPaint = new Paint();
        pathlist = new ArrayList<PointLine>();
        PathDirection = false;
        RPoor = new Random();
        myx = 0;
        cou =0;cou2=0;
        myy =0;
        PointLine p1 , p2;
        p1 = new PointLine();
        p2 = new PointLine();
        p1.L.x = this.getWidth() / 2 + 20 + movement;
        p1.L.y = -20;
        p1.R.x = this.getWidth() / 2 + 20 + tdw + movement;
        p1.R.y = -20 + tdh + 200;
        p2.L.x = this.getWidth() / 2 + 200 + movement;
        p2.L.y = 100;
        p2.R.x = this.getWidth() / 2 + 200 + tdw + movement;
        p2.R.y = 100 + tdh + 200;

        pathlist.add(p1);
        pathlist.add(p2);
    }
    public AnimatedView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        init();
    }

    public AnimatedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimatedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public AnimatedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mXonDown = w / 2;
    }
    boolean checkCollision(int a, int b, int c,
                        int x, int y, int radius) {
        // Finding the distance of line from center.
        int dist =  (Math.abs(a * x + b * y + c)) /
                (int) Math.sqrt(a * a + b * b);

        // Checking if the distance is less than,
        // greater than or equal to radius.
        if (radius == dist)return true;
        //cout << "Touch" << endl;
        else if (radius > dist)
            return false;
        else
            return false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();

        if (event.getAction() == event.ACTION_DOWN) {
            if (x < getWidth() / 2) {
                mHorSpeed = -HOR_SPEED;
            }
            else {
                mHorSpeed = HOR_SPEED;
            }
            mTimeOfDown = System.currentTimeMillis();
            postInvalidateOnAnimation();
            return true;
        }
        //PO
        if (event.getAction() == event.ACTION_MOVE) {

        }

        if (event.getAction() == event.ACTION_UP) {

            mXonDown = Calculations.calculateCarLocation(System.currentTimeMillis() - mTimeOfDown, mXonDown, mHorSpeed);
            mHorSpeed = 0;

        }
        // onMoveEvent(x); // todo: relevant only if move will change car direction

        return super.onTouchEvent(event);
    }

    private long fixCarLocation() {
        long x = mHorSpeed == 0 ?
                (long) mXonDown :
                Calculations.calculateCarLocation(System.currentTimeMillis() - mTimeOfDown, mXonDown, mHorSpeed);

        // Keep the ball / car in the screen (check X limits: between 0 to getWidth).
        if (x < radius)
            x = radius;
        if (x > getWidth() - radius)
            x = getWidth() - radius;
        return x;
    }

    public void drawCar(Canvas canvas, long x) {

        canvas.drawColor(Color.WHITE);
        canvas.drawCircle(x, getHeight()-400, radius, paint);
        DrawPaths(canvas,x,getWidth()-400 );
    }



    private void MoveDown(int l){
        PointLine pd = null;
        boolean isZero = false;
        boolean isOutSouth = false;
        for(PointLine p : pathlist){
            if(p.L.y == 0 || p.R.y == 0) { // the second never reached
                isZero = true;
            }
            if(p.L.y > this.getHeight()+tdh+300 && p.R.y > this.getHeight()+tdh+300){
               pd = p;
                isOutSouth = true;
            }else{
                p.L.y += l;
                p.R.y += l;
            }


        }
        if (isZero == true) {
            AddPaths(tdw, tdh, 0);
            isZero = false;
        }
        if(isOutSouth == true){
          // pathlist.remove(0);
            isOutSouth = false;
        }
    }

    private void AddPaths(int dm,int dh,int h){
        PointLine newPoint = new PointLine();
        int d= RPoor.nextInt(200)+150-h;
        dm = RPoor.nextInt(400)+200 ;
        if (PathDirection == false){
            newPoint.L.x = pathlist.get(0).L.x+movement ;
            newPoint.R.x = pathlist.get(0).L.x  + dm+movement;
            PathDirection = true;
        }else{
            newPoint.L.x = pathlist.get(0).L.x + d+movement;
            newPoint.R.x = pathlist.get(0).L.x + d + dm+movement;
            PathDirection = false;
        }

        newPoint.R.y = newPoint.L.y = -dh;


        if(newPoint.R.x >= this.getWidth()-200||newPoint.L.x <= 0){
            if(PathDirection)PathDirection = false;
            else PathDirection = true;

            AddPaths(dm,dh,100);
        }else{
            pathlist.add(newPoint);
        }
    }
     boolean linedeteccicule(int x1 ,int y1, int x2,int y2, int cx , int cy , int cr)
    {
        if(x2-x1==0)return false;
        int m = (y2-y1)/(x2-x1);
        if(m==0)return false;
        int n = -m*x1 +y1;// y = ax + b
        int newy = m*cx+n;
        int newx = (cy-n)/m;

        int d = (int)Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
        if(d  <= radius){
            return true;
        }


       return false;
    }
    private void DrawPaths(Canvas canvas,long x , long y){
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        float t = paint.getStrokeWidth();
        float t2 = rnd.nextFloat()+rnd.nextInt(14);
        int x1,x2,y1,y2;
        // paint.setStrokeWidth(t2);
        //paint.setColor(color);
        MoveDown(10);
        for(int i = 0 ; i < pathlist.size() - 1; ++i) {
            canvas.drawLine(pathlist.get(i).L.x, pathlist.get(i).L.y, pathlist.get(i + 1).L.x, pathlist.get(i + 1).L.y, paint);
            canvas.drawLine(pathlist.get(i).R.x, pathlist.get(i).R.y, pathlist.get(i + 1).R.x, pathlist.get(i + 1).R.y, paint);



            // y-y1 = m (x-x1) ==> y-y1-mx+mx1 ==> ( ax + by + c = 0 ) need ==> ( -mx + 1y + (mx1-y1) )
            int ml = (pathlist.get(i).L.y-pathlist.get(i+1).L.y)/(pathlist.get(i).L.x-pathlist.get(i+1).L.x);

//checkCollision(ml,-1,ml*pathlist.get(i).L.x-pathlist.get(i).L.y,(int)x,(int)y,radius)==true
            if(y<pathlist.get(i).L.y && y > pathlist.get(i+1).L.y&&linedeteccicule(pathlist.get(i).L.x,pathlist.get(i).L.y,pathlist.get(i+1).L.x,pathlist.get(i+1).L.y,(int)x,(int)y,radius)==true){
                DethScore += 0.20321;


            }
            canvas.drawText(""+DethScore,this.getWidth()-300,32,textpaint);

        }
        paint.setStrokeWidth(t);
    }

    @Override
    public void run() {
        long t1 = System.currentTimeMillis();

        while(canWePlay) {
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }
            animcanvas = surfaceHolder.lockCanvas();
            screenHeight = animcanvas.getHeight();
            screenWidth = animcanvas.getWidth();
//            myx = fixCarLocation();
            drawCar(animcanvas, fixCarLocation());
            surfaceHolder.unlockCanvasAndPost(animcanvas);
        }
    }

    public void pause() {
        canWePlay = false;
        while(true){
            try {
                mThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }
        mThread = null;
    }

    public void resume() {
        canWePlay = true;
        mThread = new Thread(this);
        mThread.start(); // Call to run function.
    }

    public void clean(){

    }
}
