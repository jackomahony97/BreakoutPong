package com.cs3305.breakoutpong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SingleGameView extends SurfaceView implements Runnable {
    /**
     * boolean : track if the game is playing or not
     */
    volatile boolean playing;
    /**
     * Thread : game thread
     */
    private Thread gameThread = null;
    /**
     * Paddle : instance of paddle class
     */
    private Paddle paddle;
    /**
     * Ball : instance of ball class
     */
    private Ball ball;
    /**
     * Brick : array of brick objects
     */
    private Brick bricks[] = new Brick[30];
    /**
     * Paint : to draw on canvas
     */
    private Paint paint;
    /**
     * SurfaceHolder : instance of ball class
     */
    private SurfaceHolder surfaceHolder;
    /**
     * BluetoothThread : instance of BluetoothThread class
     */
    private BluetoothThread myBluetooth;
    /**
     * String : arduino data
     */
    private String s = "2";

    /**
     * int : Screen width
     */
    private int width;
    /**
     * boolean : whether game is over or not
     */
    private boolean isGameOver;
    /**
     * MediaPlayer : sound for when ball destroys a brick
     */
    private MediaPlayer brickSound;
    /**
     * MediaPlayer : sound for when ball hits paddle
     */
    private MediaPlayer paddleSound;
    /**
     * MediaPlayer : sound for when game is over
     */
    private MediaPlayer gameoverSound;


    /**
     * Constructor to initialize variables and start bluetooth thread and handler
     *
     * @param context
     */
    public SingleGameView(Context context) {
        super(context);

        //iterator to run through and initialize bricks
        int k = 0;
        while (k < bricks.length){
            bricks[k] = new Brick(context, 1);
            k++;
        }

        brickSound = MediaPlayer.create(context, R.raw.brick_sound);
        paddleSound = MediaPlayer.create(context, R.raw.paddle_sound);
        gameoverSound = MediaPlayer.create(context, R.raw.gameover_sound);

        // game is playing
        isGameOver = false;
        // Get device width
        width= context.getResources().getDisplayMetrics().widthPixels;
        //initializing brick object
        paddle = new Paddle(context);
        //initializing ball object
        ball = new Ball(context);
        ball.setTop(1);
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
        /*
           brick.getX(), brick.getY() - brick.height        brick.get(X) + brick.length, brick.getY() - brick.height
                   --------------------------------------------
                   |                                           |
                   |                                           |
                   --------------------------------------------
           brick.getX(), brick.getY()                         brick.get(X) + brick.length, brick.getY()

         */


        paddle.update(s, "0\r", "1\r");
        s="2";
        for (Brick brick : bricks) {
            if (ball.getY() < brick.getY() + 100 && brick.getAlive()) {
                if (ball.getX() >= brick.getX() && ball.getX() <= (brick.getX() + width/6)) {
                    brickSound.start();
                    brick.update();
                    ball.changeUp();
                    break;
                }
            }
        }


        if (ball.getY() > paddle.getY()-25) {

            if (ball.getX() >= paddle.getX() && ball.getX() <= (paddle.getX() + 200)) {
                paddleSound.start();
                ball.update();
            } else {
                gameoverSound.start();
                playing = false;
                isGameOver = true;
            }
        }else {
            ball.update();
        }


    }

    private void draw() {
        //checking if surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            //locking the canvas
            /**
             * Canvas : to draw on
             */
            Canvas canvas = surfaceHolder.lockCanvas();
            //drawing a background color for canvas
            canvas.drawColor(Color.WHITE);
            //Drawing the brick
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

            //Drawing the bricks
            int counter = 0;
            int tempscore = 0;
            for(int column = 0; column < 250; column=column + 50) {
                for (int row = 0; row < width; row = row + width/6) {
                    bricks[counter].setX(row);
                    bricks[counter].setY(column);
                    if(bricks[counter].getAlive()) {
                        tempscore++;
                        canvas.drawBitmap(
                                bricks[counter].getBitmap(),
                                bricks[counter].getX(),
                                bricks[counter].getY(),
                                paint);
                    }
                    counter++;
                }
            }

            int yPos=(int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));


            if(isGameOver || bricks.length - tempscore == 30 ){
                myBluetooth.disconnect();
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);


                canvas.drawText("Game Over", canvas.getWidth()/2,yPos,paint);
                paint.setTextSize(50);
                canvas.drawText("Score = " + (bricks.length - tempscore), canvas.getWidth()/2,yPos + 200,paint);
            }else{
                paint.setTextSize(50);
                paint.setTextAlign(Paint.Align.CENTER);

                canvas.drawText("Score = " + (bricks.length - tempscore), canvas.getWidth()/2,yPos + 200,paint);

            }

            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }

    private void control() {
        try {
            gameThread.sleep(17);
            // exception suggested by IDE
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
            myBluetooth.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        //when the game is resumed
        //starting the thread again
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
        myBluetooth = new BluetoothThread(new Handler() {

            @Override
            public void handleMessage(Message message) {
                s = (String) message.obj;
            }
        });

        myBluetooth.start();
    }
}