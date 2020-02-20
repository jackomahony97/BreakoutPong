package com.cs3305.breakoutpong;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Paddle {
    //Bitmap to get character from image
    private Bitmap originalBitmap;
    private Bitmap bitmap;

    //coordinates
    private int x;
    private int y;

    //motion speed of the character
    private int speed = 0;

    int width;
    int height;

    //constructor
    public Paddle(Context context) {
        width= context.getResources().getDisplayMetrics().widthPixels;
        height= context.getResources().getDisplayMetrics().heightPixels;

        x = width/2 - 100;
        y = height - 150;
        speed = 1;


        //Getting bitmap from drawable resource
        originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
        bitmap =
                Bitmap.createScaledBitmap(originalBitmap, 200, 100, false);
    }

    //Method to update coordinate of character
    public void update(String s){
        //updating paddle position
        if(s.equals("0\r")) {
            if(getX() < width - 201) {
                updateRight();
            }
            s = "2";
        }
        if(s.equals("1\r")) {
            if(getX() > 1){
                updateLeft();}
            s = "2";
        }
    }


    //Method to update coordinate of character
    public void updateRight(){
        //updating x coordinate
        x= x + 20;
    }

    //Method to update coordinate of character
    public void updateLeft(){
        //updating x coordinate
        x = x - 20;
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
