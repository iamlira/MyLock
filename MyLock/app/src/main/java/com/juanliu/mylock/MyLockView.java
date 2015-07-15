package com.juanliu.mylock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Administrator on 2015/7/15.
 */
public class MyLockView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private int width, height;
    private Paint_Thread paint_thread;

    public MyLockView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        paint_thread = new Paint_Thread();
    }

    public MyLockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();
        holder.addCallback(this);
        paint_thread = new Paint_Thread();
    }

    public MyLockView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        holder = getHolder();
        holder.addCallback(this);
        paint_thread = new Paint_Thread();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        paint_thread.start();
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


            if(canvas!=null){
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
