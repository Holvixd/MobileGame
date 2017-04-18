package fi.c5msiren.mobilegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Miika on 17.4.2017.
 */

public class Player {

    // Array of all the sprites in player animation
    private ArrayList<Bitmap> playerSprites;

    private int currentFrameIndex = 0;
    // Current frame in the animation that is playing
    private Bitmap currentFrame;
    // How long one frame displays in the animation
    private int animationTime;
    private long frameTicker = (long) .01;

    // Coordinates
    private int x, y;
    // Player speed
    private int speed = 0;

    // Track if the user presses the screen
    private boolean accelerating;
    // Acceleration speed
    private int accelerationSpeed = 5;
    // Gravity value to add gravity effect
    private int gravity = -10;

    // Boundaries so player doesn't go outside the screen
    private int maxY, minY;
    // Boundaries to player speed
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    private Rect detectCollision;

    public Player(Context context, int screenX, int screenY) {
        // Player X-position
        x = 75;
        // Player Y-position
        y = 50;
        // Player speed
        speed = 1;

        // Setting the player images
        playerSprites = new ArrayList<>();
        playerSprites.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_01));
        playerSprites.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_02));
        playerSprites.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_03));
        playerSprites.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_04));
        this.currentFrame = playerSprites.get(0);

        // Setting the speed of animation. ms / fps
        animationTime = 1000 / 15;

        // Calculating maxY
        maxY = screenY - playerSprites.get(currentFrameIndex).getHeight();
        // Top edge's y point is always zero
        minY = 0;

        // Setting the initial value to false
        accelerating = false;

        //initializing rect object
        detectCollision =  new Rect(x, y, playerSprites.get(currentFrameIndex).getWidth(), playerSprites.get(currentFrameIndex).getHeight());

        Timer timer = new Timer();
        // Animate player every time animationTime has lasted
        TimerTask animatePlayer = new TimerTask() {
            @Override
            public void run() {
                currentFrameIndex++;
                if (currentFrameIndex >= playerSprites.size()) {
                    currentFrameIndex = 0;
                }
                currentFrame = playerSprites.get(currentFrameIndex);
            }
        };

        timer.scheduleAtFixedRate(animatePlayer, 0, animationTime);

    }

    public void update()  {
        // If player is accelerating
        if (accelerating) {
            speed += accelerationSpeed;
        } else {
            speed -= accelerationSpeed;
        }

        // Boundary for maximum speed
        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }
        // Boundary for minimum speed so the player wont stop
        if (speed < MIN_SPEED) {
            speed = MIN_SPEED;
        }

        // Move player downwards
        y -= speed + gravity;

        // Boundary for player so he won't go off the screen
        if (y < minY) {
            y = minY;
        }
        if (y > maxY) {
            y = maxY;
        }

        //adding top, left, bottom and right to the rect object
        detectCollision.left = x + 50;
        detectCollision.top = y + 25;
        detectCollision.right = x + playerSprites.get(0).getWidth() - 50;
        detectCollision.bottom = y + playerSprites.get(0).getHeight() - 40;
    }

    public Bitmap getCurrentFrame() { return currentFrame; }

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public void startAccelerating() {
        accelerating = true;
    }

    public void stopAccelerating() {
        accelerating = false;
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
