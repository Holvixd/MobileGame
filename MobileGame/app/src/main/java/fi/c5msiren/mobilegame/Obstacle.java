package fi.c5msiren.mobilegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Miika on 17.4.2017.
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

    //creating a rect object for a obstacle
    private Rect detectCollision;

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
        obstaclePosition();

        //initializing rect object
        if (isObstacleAtTopOfScreen()) {
            // If at the top of the screen make normal size
            detectCollision = new Rect(x, y, obstacleBitmap.getWidth(), obstacleBitmap.getHeight());
        } else {
            // Else make 1.4 times bigger version
            detectCollision = new Rect(x, y, (int) (obstacleBitmap.getWidth() * 1.4),
                    (int) (obstacleBitmap.getHeight() * 1.4));
        }

    }

    public void update(int playerSpeed) {
        //decreasing x coordinate so that obstacle will move right to left
        x -= playerSpeed;
        x -= speed;
        //if the obstalce reaches the left edge
        if (x < minX - obstacleBitmap.getWidth()) {
            //adding the obstacle again to the right top or bottom edge
            obstaclePosition();
        }

        //Adding the top, left, bottom and right to the rect object
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + obstacleBitmap.getWidth();
        detectCollision.bottom = y + obstacleBitmap.getHeight();
    }

    public void obstaclePosition() {
        Random generator = new Random();
        int position = generator.nextInt((1 - 0) + 1) + 0;

        // Set X coordinate to right edge
        x = maxX;
        // Set Y coordinate to top or bottom edge
        // depending which number random generator created
        if (position == 0) {
            y = minY;
        } else {
            y = maxY - (int) (getObstacleBitmap().getHeight() * 1.4);
        }
    }

    public boolean isObstacleAtTopOfScreen() {
        Boolean result = false;
        if (this.y == minY) {
            result = true;
        }

        return result;
    }

    public Rect getDetectCollision() {
        return detectCollision;
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
