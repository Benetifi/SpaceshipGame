package com.benetifi.spaceship;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.benetifi.spaceship.GameView.screenRatioX;
import static com.benetifi.spaceship.GameView.screenRatioY;

public class Asteroid {

    public int speed = 10;
    int x = 0, y, width, height;
    Bitmap asteroid1, asteroid2, asteroid3, asteroid4, asteroid5;

    Asteroid (Resources res){

        asteroid1 = BitmapFactory.decodeResource(res, R.drawable.asteroid_1);
        /*asteroid2 = BitmapFactory.decodeResource(res, R.drawable.asteroid_2);
        asteroid3 = BitmapFactory.decodeResource(res, R.drawable.asteroid_3);
        asteroid4 = BitmapFactory.decodeResource(res, R.drawable.asteroid_4);
        asteroid5 = BitmapFactory.decodeResource(res, R.drawable.asteroid_5);*/

        width = asteroid1.getWidth();
        height = asteroid1.getHeight();

        width /= 9;
        height /= 9;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        asteroid1 = Bitmap.createScaledBitmap(asteroid1, width, height, false);
        /*asteroid2 = Bitmap.createScaledBitmap(asteroid2, width, height, false);
        asteroid3 = Bitmap.createScaledBitmap(asteroid3, width, height, false);
        asteroid4 = Bitmap.createScaledBitmap(asteroid4, width, height, false);
        asteroid5 = Bitmap.createScaledBitmap(asteroid5, width, height, false);*/

        y = -height;
    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
}
