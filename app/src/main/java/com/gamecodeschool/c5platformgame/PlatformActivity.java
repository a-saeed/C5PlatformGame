package com.gamecodeschool.c5platformgame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class PlatformActivity extends AppCompatActivity {

    /* our object to handle the view */
    private PlatformView platformView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* capture the resolution of the device's screen */

        //get a Display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();

        //load the resolution into Point object
        Point resolution = new Point();
        display.getSize(resolution);

        //finally set the view of our game
        //also passing in the screen resolution
        platformView = new PlatformView(this , resolution.x , resolution.y);

        //make platformView the view of this activity
        setContentView(platformView);
    }

    /* pause the thread when activity is paused */
    @Override
    public void onPause()
    {
        super.onPause();
        platformView.pause();
    }

    /* resume the thread when activity is resumed */
    @Override
    public void onResume()
    {
        super.onResume();
        platformView.resume();
    }
}
