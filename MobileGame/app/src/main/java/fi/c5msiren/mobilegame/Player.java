package fi.c5msiren.mobilegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by Miika on 17.4.2017.
 */

public class Player {

    // Character image
    private Bitmap playerBitmap;
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
        x = 75;
        y = 50;
        speed = 1;
        // Setting the player image
        playerBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);

        // Calculating maxY
        maxY = screenY - playerBitmap.getHeight();
        // Top edge's y point is always zero
        minY = 0;

        // Setting the initial value to false
        accelerating = false;

        //initializing rect object
        detectCollision =  new Rect(x, y, playerBitmap.getWidth(), playerBitmap.getHeight());
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
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + playerBitmap.getWidth();
        detectCollision.bottom = y + playerBitmap.getHeight();
    }

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public void startAccelerating() {
        accelerating = true;
    }

    public void stopAccelerating() {
        accelerating = false;
    }

    public Bitmap getPlayerBitmap() {
        return playerBitmap;
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
