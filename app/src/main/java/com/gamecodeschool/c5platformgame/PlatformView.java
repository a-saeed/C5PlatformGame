package com.gamecodeschool.c5platformgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class PlatformView extends SurfaceView implements Runnable {

    private boolean debugging = true;
    private volatile boolean running;
    private Thread gameThread = null;

    /* for drawing */
    private Paint paint;
    private Canvas canvas;  //canvas could be local , but will be used later outside of draw.
    private SurfaceHolder surfaceHolder;

    /* frame related attributes */
    Context context;
    long startFrameTime;
    long timeThisFrame;
    long fps;

    /* our new engine classes */
    private LevelManager lm;
    private Viewport vp;
    InputController ic;

    /* constructor */
    PlatformView(Context context , int screenWidth , int screenHeight)
    {
        super(context);
        this.context = context;

        /* initialize drawing objects */
        surfaceHolder = getHolder();
        paint = new Paint();
        /*initialize the viewport*/
        vp = new Viewport(screenWidth, screenHeight);
    }

    @Override
    public void run()
    {
        while (running)
        {
            startFrameTime = System.currentTimeMillis();

            update();
            draw();

            /* calculate the number of frames oer second our game ran at
             * we can then use the result to
             * time animations and movements
             */
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            
            if (timeThisFrame >= 1)
                fps = 1000 / timeThisFrame;

        }
    }

    private void update()
    {
    }

    private void draw()
    {
        if (surfaceHolder.getSurface().isValid())
        {
            //First we lock the area of memory we will be drawing to
            canvas = surfaceHolder.lockCanvas();

            // Rub out the last frame with arbitrary color
            paint.setColor(Color.argb(255, 0, 0, 255));
            canvas.drawColor(Color.argb(255, 0, 0, 255));

            // New drawing code will go here

            // Unlock and draw the scene
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    /* clean up the thread if the game is interrupted */
    public void pause()
    {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e){
            Log.e("error" , "failed to pause the thread");
        }
    }

    /*make a new thread and start it
    * execution moves to our run method
    */
    public void resume()
    {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}
