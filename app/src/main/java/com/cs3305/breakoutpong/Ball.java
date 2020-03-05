package com.cs3305.breakoutpong;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


/**
 * The ball class represents a ball that travels up and down according to gravity and reverses angles
 * when it bounces off a surface
 *
 * Includes code from https://developer.android.com/reference/android/graphics/Bitmap
 * and https://developer.android.com/reference/android/util/DisplayMetrics
 * with modifications as per developer.android.com code reuse licence
 */
public class Ball {
    /**
     * boolean : Direction (left or right)
     */
    private boolean left;
    /**
     * boolean : Ball direction (up or down)
     */
    private boolean up;
    /**
     * Bitmap : Resized bitmap derived from original bitmap
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
     * int : Speed of the ball
     * TODO increase speed per level
     */
    private int speed;
    /**
     * int: Screen width
     */
    private int width;
    /**
     * int : Screen height
     */
    private int height;
    /**
     * int : Ball diameter
     */
    private int ballDiameter = 50;
    /**
     * int : Ball diameter
     */
    private int top = 1;


    /**
     * Constructor to set ball direction, height, width, speed, coordinates and Bitmap
     *
     * @param context activity context
     */
    public Ball(Context context) {
        left = false;
        // Get initial direction
        up = true;
        // Get device width
        width = context.getResources().getDisplayMetrics().widthPixels;
        // Get device height
        height = context.getResources().getDisplayMetrics().heightPixels;
        // Set device width - size of ball
        x = width / 2 - 25;
        // Set device width - size of ball
        y = height / 2 - 25;
        // Set initial speed
        // TODO is this the correct starter speed?
        setSpeed(10);
        //Getting bitmap from drawable resource
        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
        // Scale tp correct size
        bitmap = Bitmap.createScaledBitmap(originalBitmap, ballDiameter, ballDiameter, false);
    }

    /**
     * Method to update ball direction
     */
    public void update() {
        // reverse left or right
        if (getX() < 1 || getX() > width - ballDiameter) {
            left = !left;
        }

        // reverse up or down
        //TODO magic number - get rid of it
        if (!up) {
            updateDown();
            if (getY() > height - 175) {
                up = !up;
            }
        } else {
            updateUp();
            if (getY() < getTop()) {
                up = !up;
            }
        }
    }

    /**
     * Method to update up
     */
    public void updateUp() {
        y = y - getSpeed();
        if (!left) {
            x = x + getSpeed();
        } else {
            x = x - getSpeed();
        }
    }

    /**
     * Method to update down
     */
    public void updateDown() {
        //updating brick position
        y = y + getSpeed();
        if (!left) {
            x = x + getSpeed();
        } else {
            x = x - getSpeed();
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
     * Getter method for y coordinate
     *
     * @return int : represents y coordinate
     */
    public int getY() {
        return y;
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
    public void setSpeed(int newSpeed) {
        this.speed = newSpeed;
    }

    /**
     * Reverse up or down direction
     */
    public void changeUp() {
        this.up = !up;
    }

    /**
     * Getter method for speed
     *
     * @return int : represents ball speed
     */
    public int getTop() {
        return top;
    }

    /**
     * Setter method for speed
     *
     * @param newTop Int : represents ball speed
     */
    public void setTop(int newTop) {
        this.top = newTop;
    }

}
