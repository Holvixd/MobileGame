package fi.c5msiren.mobilegame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Miika on 17.4.2017.
 */

public class GameView extends SurfaceView implements Runnable {

    // Boolean variable to track if the game is playing or not
    volatile boolean playing;

    private Thread gameThread = null;
    private Player player;
    private Score score;

    // These are used for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    // References for the activity and screen sizes;
    private Activity activity;
    private Context context;
    private int screenX;
    private int screenY;

    // List of stars
    private ArrayList<Star> stars = new ArrayList<>();

    // Array of obstacles
    private Obstacle[] obstacles;
    // Amount of obstacles
    private int obstacleCount = 3;

    public GameView(Context context, int screenX, int screenY, String playerName) {
        super(context);

        // Save references
        this.activity = (Activity) context;
        this.context = context;
        this.screenX = screenX;
        this.screenY = screenY;
        // Initialize player object
        player = new Player(context, screenX, screenY);

        //Initialize score object
        score = new Score(playerName, 0);

        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();
        canvas = new Canvas();

        // Adding 100 stars
        int starAmount = 100;
        for (int i = 0; i < starAmount; i++) {
            Star star = new Star(screenX, screenY);
            stars.add(star);
        }

        // Initializing obstacle array
        obstacles = new Obstacle[obstacleCount];
        for (int i = 0; i < obstacleCount; i++) {
            obstacles[i] = new Obstacle(context, screenX, screenY);
        }

        // Add 10 score every second
        Timer timer = new Timer();
        TimerTask addScore = new TimerTask() {
            @Override
            public void run() {
                score.addScore(10);
            }
        };

        timer.schedule(addScore, 1000, 1000);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                // When user releases on the screen
                player.stopAccelerating();
                break;
            case MotionEvent.ACTION_DOWN:
                // When user presses on the screen
                player.startAccelerating();
                break;
        }
        return true;
    }

    @Override
    public void run() {
        while (playing) {
            // To update the frame
            update();

            // To draw the frame
            draw();

            // To control
            control();
        }
    }

    private void update() {
        // Update player position
        player.update();

        // Updating the star position with player speed
        for (Star star : stars) {
            star.update(player.getSpeed() / 2);
        }

        // Updating the obstacle coordinate with respect to player speed
        for (int i = 0; i < obstacleCount; i++) {
            obstacles[i].update(player.getSpeed() / 2);
        }

        // Check if player collides with obstacle
        for (int i = 0; i < obstacleCount; i++) {
            if (Rect.intersects(player.getDetectCollision(), obstacles[i].getDetectCollision())) {

                // Send the current score to the back end
                new sendScore(activity, score.getAmount(), score.getName()).execute("");
                // Reset score
                score.setScore(0);

                // Create new obstacles
                for (int j = 0; j < obstacleCount; j++) {
                    obstacles[j] = new Obstacle(context, screenX, screenY);
                }
                // Reset player position
                player.setY(250);
            }
        }
    }

    private void draw() {
        // Check if surface is valid
        if (surfaceHolder.getSurface().isValid()) {

            if (canvas == null) {
                canvas = new Canvas();
            }
            // Lock the canvas so no one else can write code to the canvas
            canvas = surfaceHolder.lockCanvas();
            // Draw background color
            canvas.drawColor(Color.BLACK);
            // Setting the color to white for score and stars
            paint.setColor(Color.WHITE);
            // Draw the stars
            for (Star star : stars) {
                paint.setStrokeWidth(star.getStarWidth());
                canvas.drawPoint(star.getX(), star.getY(), paint);
            }
            // Draw the score
            paint.setTextSize(30);
            canvas.drawText("Score: "+score.getAmount(),100,50,paint);
            // Draw the player
            canvas.drawBitmap(player.getCurrentFrame(), player.getX(), player.getY(), paint);
            // Draw the obstacles
            for (int i = 0; i < obstacleCount; i++) {
                // If the obstacle is at the top of screen, flip it
                if (obstacles[i].isObstacleAtTopOfScreen()) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(180);
                    Bitmap bitmap = Bitmap.createBitmap(
                            obstacles[i].getObstacleBitmap(),
                            0,
                            0,
                            obstacles[i].getObstacleBitmap().getWidth(),
                            obstacles[i].getObstacleBitmap().getHeight(),
                            matrix,
                            true
                    );
                    canvas.drawBitmap(
                            bitmap,
                            obstacles[i].getX(),
                            obstacles[i].getY(),
                            paint
                    );
                } else {
                    canvas.drawBitmap(
                            obstacles[i].getObstacleBitmap(),
                            obstacles[i].getX(),
                            obstacles[i].getY(),
                            paint
                    );
                }

            }

            // Unlock the canvas and post the code
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        // Game aims to run at 60fps
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {

        // Stopping the thread
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        // Starting the thread again
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}
