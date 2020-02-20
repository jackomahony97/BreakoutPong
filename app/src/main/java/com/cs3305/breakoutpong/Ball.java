package com.cs3305.breakoutpong;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * The ball class represents a ball that travels up and down according to gravity
 */
public class Ball {
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
    private int speed = 0;

    int width;
    int height;

    //constructor
    public Ball(Context context) {
        //Bitmap to get character from image
        Bitmap originalBitmap;
        width= context.getResources().getDisplayMetrics().widthPixels;
        height= context.getResources().getDisplayMetrics().heightPixels;

        x = width/2 - 25;
        y = height/2 - 25;
        speed = 1;


        //Getting bitmap from drawable resource
        originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bally);
        bitmap =
                Bitmap.createScaledBitmap(originalBitmap, 50, 50, false);
    }

    //Method to update coordinate of character
    public void update() {
        //updating paddle position

        if (getY() > 1) {
            updateUp();
        }
    }

    public void updateUp() {
        //updating paddle position
        y = y - 5;
    }

    public void updateDown() {
        //updating paddle position
        y = y + 5;
    }


    /*
     * These are getters you can generate it autmaticallyl
     * right click on editor -> generate -> getters
     * */
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
}
