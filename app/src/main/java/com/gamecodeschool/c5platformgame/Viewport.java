package com.gamecodeschool.c5platformgame;

import android.graphics.Rect;

public class Viewport {

    /* what point in the world is currently the central focus of the viewport */
    private Vector2Point5D currentViewportWorldCenter;

    /* number of meters the viewport will show */
    private int metersToShowX;
    private int metersToShowY;

    /* screen resolution */
    private int screenXResolution;
    private int screenYResolution;

    /* middle of the screen */
    private int screenCenterX;
    private int screenCenterY;

    /* translate game world coordinates into appropriate pixel coordinates to draw on screen */
    private int pixelsPerMeterX;
    private int pixelsPerMeterY;

    /* output debugging tests*/
    private int numClipped;

    private Rect convertedRect;

    /* constructor */
    Viewport(int x , int y)
    {
        screenXResolution = x;
        screenYResolution = y;

        screenCenterX = screenXResolution / 2;
        screenCenterY = screenYResolution / 2;

        pixelsPerMeterX = screenXResolution / 32;
        pixelsPerMeterY = screenYResolution / 18;

        metersToShowX = 34;
        metersToShowY = 20;

        convertedRect = new Rect();
        currentViewportWorldCenter = new Vector2Point5D();
    }

    /* keep bob centered while moving (or other objects)*/
    void setWorldCenter(float x , float y)
    {
        currentViewportWorldCenter.x = x;
        currentViewportWorldCenter.y = y;
    }

    public int getScreenWidth() {
        return screenXResolution;
    }

    public int getScreenHeight() {
        return  screenYResolution;
    }

    public int getPixelsPerMetreX()
    {
        return  pixelsPerMeterX;
    }

    public Rect worldToScreen(float objectX , float objectY ,
                              float objectWidth , float objectHeight)
    {
        int left = (int) (screenCenterX - (currentViewportWorldCenter.x - objectX) * pixelsPerMeterX);

        int top = (int) (screenCenterY - (currentViewportWorldCenter.y - objectY) * pixelsPerMeterY);

        int right = (int) (left + (objectWidth * pixelsPerMeterX));

        int bottom = (int) (top + (objectHeight * pixelsPerMeterY));

        convertedRect.set(left , top , right , bottom);

        return convertedRect;
    }

    public boolean clipObject(float objectX , float objectY ,
                              float objectWidth , float objectHeight)
    {
        //default assumption is that the object is clipped
        boolean clipped = true;

        //check to see if the object is in the range of the viewport
        if (objectX - objectWidth < currentViewportWorldCenter.x + (metersToShowX/2))
        {
            if (objectX + objectWidth > currentViewportWorldCenter.x + (metersToShowX/2))
            {
                if (objectY - objectHeight < currentViewportWorldCenter.y + (metersToShowY/2))
                {
                    if (objectY + objectHeight > currentViewportWorldCenter.y + (metersToShowY/2))
                        clipped = false;
                }
            }
        }
        //for debugging
        if (clipped)
            numClipped++;

        return clipped;
    }

    public int getNumClipped() {
        return numClipped;
    }

    public void resetNumClipped() {
        numClipped = 0;
    }
} //end of viewport
