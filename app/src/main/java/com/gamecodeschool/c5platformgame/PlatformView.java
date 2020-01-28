package com.gamecodeschool.c5platformgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class PlatformView extends SurfaceView implements Runnable {

    /**A T T R I B U T E S*/
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

    /**C O N S T R U C T O R */
    PlatformView(Context context , int screenWidth , int screenHeight)
    {
        super(context);
        this.context = context;

        /* initialize drawing objects */
        surfaceHolder = getHolder();
        paint = new Paint();
        /*initialize the viewport*/
        vp = new Viewport(screenWidth, screenHeight);

        //Load the first level
        loadLevel("LevelCave", 15, 2);
    }

    /**M E T H O D S*/
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
        for (GameObject go : lm.gameObjects)
        {
            if (go.isActive())
            {
                //Clip anything off-screen
                //if the gameObject isn't supposed to be clipped...
                if (!vp.clipObject(go.getWorldLocation().x,
                        go.getWorldLocation().y, go.getWidth(), go.getHeight()))
                {
                    //Set visible flag to true
                    go.setViisible(true);
                }
            }
            else
            {
                //Set visible flag to false
                go.setViisible(false);
                //Now draw() can ignore them
            }
        }
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

    private void loadLevel(String level, float px, float py) {

        lm = null;
         //Create a new level manager
         //pass in a context, screen details, level name and player location
        lm = new LevelManager(context, vp.getPixelsPerMetreX(),
                vp.getScreenWidth(), ic, level, px, py );

        ic = new InputController(vp.getScreenWidth(), vp.getScreenHeight());

        //Set the player's location as the world center
        vp.setWorldCenter(lm.gameObjects.get(lm.playerIndex).getWorldLocation().x,
                           lm.gameObjects.get(lm.playerIndex).getWorldLocation().y);
    }

}
