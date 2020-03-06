package com.cs3305.breakoutpong;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


/**
 * The brick class represents a brick to be drawn on a canvas
 *
 * Includes code from https://developer.android.com/reference/android/graphics/Bitmap
 * and https://developer.android.com/reference/android/util/DisplayMetrics
 * with modifications as per developer.android.com code reuse licence
 */
public class Brick {
    /**
     *  int : amount of times brick should be rehit
     *
     *  determines if it should be redrawn or not
     *  by default all blocks are drawn
     */
    private int alive;
    /**
     *  Bitmap : Resized bitmap derived from original bitmap (to scale)
     */
    private Bitmap bitmap;
    /**
     * int : x coordinate
     */
    private int x = 0;
    /**
     * int : y coordinate
     */
    private int y = 0;
    /**
     * int : difficulty level
     * TODO increase speed per level
     */

    /**
     * Constructor to set height, width and speed
     * @param context activity context
     */
    public Brick(Context context) {
        //get device width
        int width = context.getResources().getDisplayMetrics().widthPixels;
        //brick width
        int brickWidth = width /6;
        //brick height
        int brickHeight = 100;
        //set difficulty
        //all bricks are initially alive
        setAlive(1);
        //Getting bitmap from drawable resource
        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.brick);
        //scaled bitmap
        bitmap = Bitmap.createScaledBitmap(originalBitmap, brickWidth, brickHeight, false);
    }

    /**
     * Method to update whether brick should be redrawn or not
     */
    public void update(){
        this.alive -= 1;
    }

    /**
     * Getter method for bitmap
     *
     * @return Bitmap : represents a bitmap
     */
    public Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * Getter method for x coordinate
     *
     * @return int : represents x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Getter method for y coordinate
     *
     * @return int : represents y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Setter method for x coordinate
     *
     * @param newX Int : represents x coordinate
     */
    public void setX(int newX){
        this.x = newX;
    }

    /**
     * Setter method for x coordinate
     *
     * @param newY Int : represents y coordinate
     */
    public void setY(int newY){
        this.y = newY;
    }

    /**
     * Setter method for alive
     *
     * @param newAlive boolean : represents whether block has been destroyed (by ball) or not
     */
    public void setAlive(int newAlive){
        this.alive = newAlive;
    }

    /**
     * Getter method for alive
     *
     * @return boolean : represents alive
     */
    public int getAlive(){
        return alive;
    }

}
