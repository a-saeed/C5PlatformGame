package com.gamecodeschool.c5platformgame;

import android.content.Context;

public class Player extends GameObject {

    Player(Context context ,float worldStartX , float worldStartY , int pixelPerMeter)
    {
        final float HEIGHT = 2;
        final float WIDTH = 1;

        setHeight(HEIGHT); //2 meter tall
        setWidth(WIDTH); //1 meter wide

        setType('p');

        //choose a bitmap (will be added to drawable folder later)
        setBitmapName("player");

        //x and y locations from constructor parameters
        setWorldLocation(worldStartX , worldStartY , 0);
    }

    public void update(long fps , float gravity)
    {

    }
}
