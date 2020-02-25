package com.cs3305.breakoutpong;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Paddle {
    /**
     *  Resized bitmap derived from original bitmap
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
     * Speed of the brick
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
     * Constructor to set height, width and speed
     * @param context activity context
     */
    public Paddle(Context context) {
        // Get device width
        width= context.getResources().getDisplayMetrics().widthPixels;
        // Get device height
        height= context.getResources().getDisplayMetrics().heightPixels;
        // Set device width - size of brick
        x = width/2 - 100;
        // Set device width - size of brick
        y = height - 150;
        setSpeed(20);

        //Getting bitmap from drawable resource
        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.paddle);
        bitmap = Bitmap.createScaledBitmap(originalBitmap, 200, 100, false);
    }

    /**
     * Method to updating brick position
     * @param s String that represents the data sent from the Arduino
     */
    public void update(String s){
        if(s.equals("0\r")) {
            if(getX() < width - 201) {
                x = x + getSpeed();
            }
            s = "2";
        }
        if(s.equals("1\r")) {
            if(getX() > 1){
                x= x - getSpeed();
            }
            s = "2";
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

}
