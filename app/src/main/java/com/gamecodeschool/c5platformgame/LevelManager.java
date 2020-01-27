package com.gamecodeschool.c5platformgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.ArrayList;

public class LevelManager {

    /**A T T R I B U T E S*/
    private String level;     //name of the level
    int mapWidth , mapHeight; //in game-world metres.

    Player player;
    int playerIndex; //multiple players , handy to keep an index.

    private boolean playing;
    float gravity;

    LevelData levelData;
    ArrayList<GameObject> gameObjects;

    ArrayList<Rect> currentButtons; //hold representations of the players control buttons.
    Bitmap[] bitmapsArray;  //hold the majority of all the bitmap objects we'll need.

    /**C O N S T R U C T O R*/
    LevelManager(Context context, int pixelsPerMetre,
                 int screenWidth, InputController ic,
                 String level, float px, float py)
    {
        this.level = level;

        switch (level){
            case "LevelCave":
                levelData = new LevelCave();
                break;

            //we can add extra levels here
        }

        //To hold all our GameObjects
        gameObjects = new ArrayList<>();

        //To hold 1 for every Bitmap
        bitmapsArray = new Bitmap[25];

        //Load all the game objects and Bitmaps
        loadMapData(context, pixelsPerMetre, px, py);
        //Ready to play
        playing = true;
    }

    /**M E T H O D S*/
    public boolean isPlaying() {
        return playing;
    }

    /*Get any Bitmap object based on the GameObject
     *each index corresponds to a bitmap
     */
    public  Bitmap getBitmap(char blockType)
    {
        int index;
        switch (blockType){
            case '.':
                index  = 0;
                break;

            case '1':
                index = 1;
                break;

            case 'p':
                index = 2;
                break;

            default:
                index = 0;
                break;
        }//End switch

        return bitmapsArray[index];
    }

    //This method allows each GameObject which 'knows'
    //its type to get the correct index to its Bitmap
    //in the bitmap array
    public int getBitmapIndex(char blockType)
    {
        int index;
        switch (blockType){
            case '.':
                index  = 0;
                break;

            case '1':
                index = 1;
                break;

            case 'p':
                index = 2;
                break;

            default:
                index = 0;
                break;
        }//End switch
        return index;
    }
}
