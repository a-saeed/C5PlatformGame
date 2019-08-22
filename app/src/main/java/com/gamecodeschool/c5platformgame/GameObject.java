package com.gamecodeschool.c5platformgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public abstract class GameObject {

    private Vector2Point5D worldLocation;

    private float width;
    private float height;

    private boolean active = true;
    private boolean visible = true;
    private int animFrameCount = 1;
    private char type;

    private String bitmapName;


    public abstract void update(long fps , float gravity);

    public Bitmap prepareBitmap(Context context , String bitmapName , int pixelPerMetre)
    {
        //make a resource ID from the bitmap name
        int resID = context.getResources().
                getIdentifier(bitmapName , "drawable" , context.getOpPackageName());

        //create the bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources() , resID);

        //scale the bitmap based on the number of pixels per metre
        //multiply by the number of frames in each image
        //default 1 frame
        bitmap = Bitmap.createScaledBitmap(bitmap ,
                (int) (width * animFrameCount * pixelPerMetre) ,
                (int) (height * pixelPerMetre) ,
                false);

        return bitmap;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isViisible() {
        return visible;
    }

    public void setViisible(boolean viisible) {
        this.visible = viisible;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public String getBitmapName() {
        return bitmapName;
    }

    public void setBitmapName(String bitmapName) {
        this.bitmapName = bitmapName;
    }
}
