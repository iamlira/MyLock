package com.juanliu.mylock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Administrator on 2015/7/15.
 */
public class MyLockView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private boolean draw=false;
    private int width, height, stick_x, stick_y;
    private Paint_Thread paint_thread;
    private Point[][] list = new Point[3][3];

    public MyLockView(Context context) {
        super(context);
        setFocusable(true);
        holder = getHolder();
        holder.addCallback(this);
        paint_thread = new Paint_Thread();
    }

    public MyLockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        holder = getHolder();
        holder.addCallback(this);
        paint_thread = new Paint_Thread();
    }

    public MyLockView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFocusable(true);
        holder = getHolder();
        holder.addCallback(this);
        paint_thread = new Paint_Thread();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        width = this.getWidth();
        height = this.getHeight();
//        paint_thread.start();
        init_draw();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    class Paint_Thread extends Thread {
        private Canvas canvas = null;
        private Bitmap bt;

        @Override
        public void run() {
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            width = getWidth();
            height = getHeight();
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            bt = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            for (int i = 1; i <= 3; i++)
                for (int j = 1; j <= 3; j++) {
                    canvas.drawBitmap(bt, i * width / 4, j * height / 4, paint);
                }


            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }
    public void clear(){
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        Bitmap bt = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        for (int i = 1; i <= 3; i++)
            for (int j = 1; j <= 3; j++) {
                canvas.drawBitmap(bt, i * width / 4 - bt.getWidth()/2, j * height / 4 - bt.getHeight()/2, paint);
            }

        if (canvas != null) {
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void init_draw() {
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        Bitmap bt = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        for (int i = 1; i <= 3; i++)
            for (int j = 1; j <= 3; j++) {
                canvas.drawBitmap(bt, i * width / 4 - bt.getWidth()/2, j * height / 4 - bt.getHeight()/2, paint);
                list[i - 1][j - 1] = new Point(i * width / 4, j * height / 4);
            }

        if (canvas != null) {
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void move_draw(float x, float y) {
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        Bitmap bt = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        for (int i = 1; i <= 3; i++)
            for (int j = 1; j <= 3; j++) {
                canvas.drawBitmap(bt, i * width / 4 - bt.getWidth()/2, j * height / 4 - bt.getHeight()/2, paint);
            }
        canvas.drawLine(stick_x, stick_y, x, y, paint);
        if (canvas != null) {
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void validate(int x, int y) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                if (x <= list[i][j].x + 40 && x >= list[i][j].x - 40 && y <= list[i][j].y + 40 && y >= list[i][j].y - 40) {
                    stick_x = list[i][j].x;
                    stick_y = list[i][j].y;
                    draw = true;
                    return ;
                }

            }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                validate((int) event.getX(), (int) event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                validate((int) event.getX(), (int) event.getY());
                if(draw)
                move_draw(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                clear();
                draw = false;
                break;
        }
        return true;
    }


    class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
