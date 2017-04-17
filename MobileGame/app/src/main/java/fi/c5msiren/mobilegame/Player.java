package fi.c5msiren.mobilegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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

    public Player(Context context) {
        x = 75;
        y = 50;
        speed = 1;

        // Setting the player image
        playerBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
    }

    public void update()  {
        // Update x coordinate
        x++;
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
