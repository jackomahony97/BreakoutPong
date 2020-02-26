package com.cs3305.breakoutpong;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * The ball class represents a ball that travels up and down according to gravity
 */
public class Ball {
    /**
     * Ball direction
     */
    private boolean left;
    /**
     * Ball direction
     */
    private boolean up;
    /**
     * Resized bitmap derived from original bitmap
     */
    private Bitmap bitmap;
    /**
     * x coordinate
     */
    private int x;
    /**
     * y coordinate
     */
    private int y;
    /**
     * Speed of the ball
     * TODO increase speed per level
     */
    private int speed;
    /**
     * Screen width
     */
    private int width;
    /**
     * Screen height
     */
    private int height;
    /**
     * Ball diameter
     */
    private int ballDiameter = 50;


    /**
     * Constructor to set height, width and speed
     * @param context activity context
     */
    public Ball(Context context) {
        left = false;
        // Get initial direction
        up = true;
        // Get device width
        width= context.getResources().getDisplayMetrics().widthPixels;
        // Get device height
        height= context.getResources().getDisplayMetrics().heightPixels;
        // Set device width - size of ball
        x = width/2 - 25;
        // Set device width - size of ball
        y = height/2 - 25;
        // Set initial speed
        // TODO is this the correct starter speed?
        setSpeed(10);

        //Getting bitmap from drawable resource
        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
        // Scale tp correct size
        bitmap = Bitmap.createScaledBitmap(originalBitmap, ballDiameter, ballDiameter, false);
    }

    /**
     * Method to update coordinate of character
     */
    public void update() {
        if (getX() < 1 || getX() > width - ballDiameter){
            left = !left;
        }

        if (!up) {
            updateDown();
            if (getY() > height - Paddle.getPaddleHeight()){
                up = !up;
            }
        }
        else {
            updateUp();
            if (getY() < 1){
                up = !up;
            }
        }
    }

    /**
     * Method
     */
    public void updateUp() {
        //updating brick position
        y = y - getSpeed();
        if(!left){
            x = x + getSpeed();
        }else{
            x = x - getSpeed();
        }
    }

    public void updateDown() {
        //updating brick position
        y = y + getSpeed();
        if(!left){
            x = x + getSpeed();
        }else{
            x = x - getSpeed();
        }
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int newSpeed){
        this.speed = newSpeed;
    }

    public void changeUp(){
        this.up = !up;
    }
}
