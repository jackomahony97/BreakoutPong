package com.cs3305.breakoutpong;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Brick {

    private boolean alive;
    /**
     *  Resized bitmap derived from original bitmap
     */
    private Bitmap bitmap;
    /**
     * x coordinate
     */
    private int x = 0;
    /**
     * y coordinate
     */
    private int y = 0;
    /**
     * Difficulty level
     * TODO increase speed per level
     */
    private int level;
    /**
     * Screen width
     */
    private int width;
    /**
     * Screen height
     */
    private int height;

    /**
     * Constructor to set height, width and speed
     * @param context activity context
     */
    public Brick(Context context, int level) {
        // Get device width
        width= context.getResources().getDisplayMetrics().widthPixels;
        // Get device height
        height= context.getResources().getDisplayMetrics().heightPixels;


        // set difficulty
        setLevel(level);

        setAlive(true);

        //Getting bitmap from drawable resource
        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bricky);
        bitmap = Bitmap.createScaledBitmap(originalBitmap, width/6, 100, false);
    }


    public void update(){
        this.alive = false;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int newLevel){
        this.level = newLevel;
    }

    public void setX(int newX){
        this.x = newX;
    }

    public void setY(int newY){
        this.y = newY;
    }

    public void setAlive(boolean newAlive){
        this.alive = newAlive;
    }

    public boolean getAlive(){
        return alive;
    }

}
