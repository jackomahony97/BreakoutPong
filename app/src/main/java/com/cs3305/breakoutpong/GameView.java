package com.cs3305.breakoutpong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    //boolean variable to track if the game is playing or not
    volatile boolean playing;

    //the game thread
    private Thread gameThread = null;

    //adding the paddle to this class
    private Paddle paddle;

    //adding the ball to this class
    private Ball ball;

    //These objects will be used for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;


    private Handler handler;

    private BluetoothThread myBluetooth;

    private String s = "2";


    //Class constructor
    public GameView(Context context) {
        super(context);


        //initializing paddle object
        paddle = new Paddle(context);

        //initializing ball object
        ball = new Ball(context);

        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        myBluetooth = new BluetoothThread(new Handler() {

            @Override
            public void handleMessage(Message message) {
                s = (String) message.obj;
            }
        });

        myBluetooth.start();
    }


    @Override
    public void run() {
        while (playing) {
            //to update the frame
            update();

            //to draw the frame
            draw();

            //to control
            control();
        }
    }


    private void update() {
        paddle.update(s);
        s="2";
        ball.update();

    }

    private void draw() {
        //checking if surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            //locking the canvas
            canvas = surfaceHolder.lockCanvas();
            //drawing a background color for canvas
            canvas.drawColor(Color.WHITE);
            //Drawing the paddle
            canvas.drawBitmap(
                    paddle.getBitmap(),
                    paddle.getX(),
                    paddle.getY(),
                    paint);

            //Drawing the ball
            canvas.drawBitmap(
                    ball.getBitmap(),
                    ball.getX(),
                    ball.getY(),
                    paint);

            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        //when the game is paused
        //setting the variable to false
        playing = false;
        try {
            //stopping the thread
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        //when the game is resumed
        //starting the thread again
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}