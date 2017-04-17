package fi.c5msiren.mobilegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

/**
 * Created by Jari on 17.4.2017.
 */

public class Obstacle {

    // Obstacle image
    private Bitmap obstacleBitmap;
    // Coordinates
    private int x, y;

    private int speed = 1;

    // Boundaries so the obstacle is inside the playing field
    private int maxX;
    private int maxY;
    private int minX;
    private int minY;

    public Obstacle(Context context, int screenX, int screenY) {

        // Setting the image to obstacle
        obstacleBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.obstacle);

        // Initializing min and max coordinates
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        // Generating position for the obstacle
        Random generator = new Random();
        speed = generator.nextInt(6) + 10;
        x = screenX;
        y = generator.nextInt(maxY) - obstacleBitmap.getHeight();
    }

    public void update(int playerSpeed) {
        //decreasing x coordinate so that obstacle will move right to left
        x -= playerSpeed;
        x -= speed;
        //if the obstalce reaches the left edge
        if (x < minX - obstacleBitmap.getWidth()) {
            //adding the obstacle again to the right top or bottom edge
            Random generator = new Random();
            int position = generator.nextInt((1 - 0) + 1) + 0;
            speed = generator.nextInt(10) + 10;
            x = maxX;
            if (position == 0) {
                y = minY;
            } else {
                y = maxY - getObstacleBitmap().getHeight();
            }
        }
    }

    public Bitmap getObstacleBitmap() {
        return obstacleBitmap;
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
