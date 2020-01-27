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

    //The real work with LevelManager class
    //Load our level from our design
    //for now we just load all the grass tiles
    //and the player. soon we'll have many GameObjects
    private void loadMapData(Context context, int pixelsPerMetre, float px, float py)
    {
        char c;

        //keep track of where we load our Game Objects
        int currentIndex = -1;

        //how wide and high is the map? Viewport needs to know
        mapHeight = levelData.tiles.size();
        mapWidth = levelData.tiles.get(0).length();

        for (int i = 0; i <  levelData.tiles.size(); i++)
        {
            for (int j = 0; j < levelData.tiles.get(i).length(); j++)
            {
                c = levelData.tiles.get(i).charAt(j);

                //Don't want to load the empty spaces
                if (c != '.')
                {
                    currentIndex++;

                    switch (c){
                        case '1':
                            ///Add Grass to the gameObjects
                            gameObjects.add(new Grass(j, i , c));
                            break;

                        case 'p':
                            //Add a player to the gameObjects at passed location
                            gameObjects.add(new Player(context, px, py, pixelsPerMetre));

                            //we want the index of the player
                            playerIndex = currentIndex;
                            //we want a reference to the player
                            player = (Player) gameObjects.get(playerIndex);
                            break;

                    }//End switch

                    //If the bitmap isn't prepared yet
                    if (bitmapsArray[getBitmapIndex(c)] == null)
                    {
                        //Prepare it now and put it in the bitmapsArray
                        bitmapsArray[getBitmapIndex(c)] =
                                gameObjects.get(currentIndex).prepareBitmap(context,
                                        gameObjects.get(currentIndex).getBitmapName(),
                                        pixelsPerMetre);
                    }//End if
                }//End if(c != '.')
            }//End for j
        }//End for i
    }
}
