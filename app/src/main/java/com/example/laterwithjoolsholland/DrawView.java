package com.example.laterwithjoolsholland;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.math.MathContext;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread drawThread;

    public DrawView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getContext(), getHolder());
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawThread.requestStop();
        boolean retry = true;
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
//
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        drawThread.setTowardPoint((int)event.getX(),(int)event.getY());

        return true;
    }
}

class DrawThread extends Thread {

    private SurfaceHolder surfaceHolder;

    private volatile boolean running = true;
    private Paint backgroundPaint = new Paint();
    private Paint txtPaint = new Paint();
    private Bitmap bitmap;
    private int towardPointX;
    private int towardPointY;
    private ArrayList<Ball> ballz;
    private int counter = 0;
    private Context cont;


    {
        backgroundPaint.setColor(Color.WHITE);
        backgroundPaint.setStyle(Paint.Style.FILL);
        txtPaint.setColor(Color.BLACK);
        txtPaint.setStyle(Paint.Style.FILL);
        txtPaint.setTextSize(70f);
    }

    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.raw.smile);
        this.cont = context;
        this.surfaceHolder = surfaceHolder;
        this.ballz = new ArrayList<>();
        create(5);




    }

    public void requestStop() {
        running = false;
    }

    public void setTowardPoint(int x, int y) {
        towardPointX = x;
        towardPointY = y;
    }

    @Override
    public void run() {




        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                    canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);
                    for (int i = 0; i<ballz.size(); i++){
                        Ball q = ballz.get(i);
                        canvas.drawBitmap(bitmap, (int)q.getX()-bitmap.getWidth()/2, (int)q.getY()-bitmap.getHeight()/2, backgroundPaint);
                    }
                    String txt = " " + counter;


                    canvas.drawText(txt, 550,70,txtPaint);



                    for (int i = 0; i<ballz.size(); i++){

                        Ball q = ballz.get(i);
                        if((towardPointX-bitmap.getWidth()/4 <= q.getX() && q.getX()<=towardPointX+bitmap.getWidth()/4)&&(towardPointY-bitmap.getHeight()/4<=q.getY() && q.getY()<= towardPointY+bitmap.getHeight()/4)){
                            ballz.remove(q);
                            counter +=1;
                            towardPointY = -100;
                            towardPointX = -100;

                        }
                        q.setY(q.getY() + q.getVelocity()*Math.sin(q.getVelocity()));
                        q.setX(q.getX() + q.getVelocity()*Math.cos(q.getDirection()));
                        if(q.getX()>=700){
                            ballz.remove(q);
                            if(ballz.size() <=15) create(2);
                        }
                        if(q.getX()<=100){
                             ballz.remove(q);
                            if(ballz.size() <=15) create(2);}

                        if(q.getY()>=1200) {
                            ballz.remove(q);
                            if(ballz.size() <=15) create(2);
                            }
                        if(q.getY()<=100) {
                            ballz.remove(q);
                            if(ballz.size() <=15) create(2);
                        }
                    }

                }
                catch (Throwable t){
                    Toast.makeText(cont, t.getMessage(), Toast.LENGTH_SHORT).show();
                }finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }


        }
    }

}
    public void create(int n){
        Random random = new Random();
        for(int i = 0; i<n; i++){
            ballz.add(new Ball (random.nextInt(550)+100, random.nextInt(1000)+150, random.nextInt(3)+4 , random.nextFloat()*Math.PI*4));

        }

    }}