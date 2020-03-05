package com.cs3305.breakoutpong;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


/**
 * The paddle class represents a paddle that travels across the screen and changes direction when it
 * bounces off a surface
 *
 * Includes code from https://developer.android.com/reference/android/graphics/Bitmap
 * and https://developer.android.com/reference/android/util/DisplayMetrics
 * with modifications as per developer.android.com code reuse licence
 */
public class Paddle {
    /**
     *  Bitmap : Resized bitmap derived from original bitmap (to scale)
     */
    private Bitmap bitmap;
    /**
     * int : x coordinate
     */
    private int x;
    /**
     * int : y coordinate
     */
    private int y;
    /**
     * int : Speed of the brick
     * TODO increase speed per level
     */
    private int speed = 20;
    /**
     * int : Screen width
     */
    private int width;
    /**
     * int : Paddle width
     */
    private int paddleWidth = 200;
    /**
     * Paddle height
     */
    private int paddleHeight = 100;

    /**
     * Constructor to set height, width and speed
     * @param context activity context
     */
    public Paddle(Context context) {
        // Get device width
        width= context.getResources().getDisplayMetrics().widthPixels;
        // Get device height
        int height = context.getResources().getDisplayMetrics().heightPixels;
        // Set device width - size of brick
        x = width/2 - paddleWidth/2;
        // Set device width - size of brick
        y = height - paddleHeight - 50;
        //Getting bitmap from drawable resource
        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.paddle);
        //scaled bitmap
        bitmap = Bitmap.createScaledBitmap(originalBitmap, paddleWidth, paddleHeight, false);
    }

    /**
     * Method to updating paddle position
     * @param s String that represents the data sent from the Arduino
     */
    public void update(String s, String left, String right){
        // left move from Arduino
        if(s.equals("0\r")) {
            // if its not going beyond the side (kept it on screen)
            if(getX() < width - (paddleWidth+1)) {
                x = x + getSpeed();
            }
        }
        // right move from Arduino
        if(s.equals("1\r")) {
            // if its not going beyond the side (kept it on screen)
            if(getX() > 1){
                x= x - getSpeed();
            }
        }
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
     * Setter method for x coordinate
     *
     * @param newX Int : represents x coordinate
     */
    public void setX(int newX){
        this.x = newX;
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
     * Setter method for y coordinate
     *
     * @param newY Int : represents y coordinate
     */
    public void setY(int newY){
        this.y = newY;
    }


    /**
     * Getter method for speed
     *
     * @return int : represents ball speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Setter method for speed
     *
     * @param newSpeed Int : represents ball speed
     */
    public void setSpeed(int newSpeed){
        this.speed = newSpeed;
    }

    /**
     * Getter method for paddleWidth
     *
     * @return int : represents paddle width
     */
    public int getPaddleWidth() {
        return paddleWidth;
    }

    /**
     * Setter method for paddleWidth
     *
     * @param newPaddleWidth Int : represents paddle width
     */
    public void setPaddleWidth(int newPaddleWidth){
        this.paddleWidth = newPaddleWidth;
    }

    /**
     * Getter method for paddleHeight
     *
     * @return int : represents paddle width
     */
    public int getPaddleHeight() {
        return paddleHeight;
    }

}
