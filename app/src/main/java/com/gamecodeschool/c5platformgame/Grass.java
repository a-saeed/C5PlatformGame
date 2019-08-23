package com.gamecodeschool.c5platformgame;

public class Grass extends GameObject {

    Grass(float worldStartX , float worldStartY , char type)
    {
        final float HEIGHT = 1;
        final float WIDTH= 1;

        setHeight(HEIGHT); //1 meter tall
        setWidth(WIDTH); //1 meter tall

        setType(type);

        //choose a bitmap
        setBitmapName("turf");

        //where does the tile start
        //x and y location from constructor parameters
        setWorldLocation(worldStartX , worldStartY , 0);
    }

    public void update(long fps , float gravity)
    {}
}
