package com.benetifi.spaceship;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.benetifi.spaceship.GameView.screenRatioX;
import static com.benetifi.spaceship.GameView.screenRatioY;

public class Spaceship {

    //boolean isGoingUp = false;
    int x, y, width, height, fireCounter = 0, isGoingUp = 0;
    Bitmap spaceship1, spaceship2, spaceship3;
    private GameView gameView;

    Spaceship (GameView gameView, int screenY, Resources res){
        this.gameView = gameView;

        spaceship1 = BitmapFactory.decodeResource(res, R.drawable.spaceship_1_anew);
        spaceship2 = BitmapFactory.decodeResource(res, R.drawable.spaceship_1_a);
        spaceship3 = BitmapFactory.decodeResource(res, R.drawable.spaceship_1_b);

        width = spaceship1.getWidth();
        height = spaceship1.getHeight();

        width /= 6;
        height /= 6;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        spaceship1 = Bitmap.createScaledBitmap(spaceship1, width, height, false);
        spaceship2 = Bitmap.createScaledBitmap(spaceship2, width, height, false);
        spaceship3 = Bitmap.createScaledBitmap(spaceship3, width, height, false);

        y = screenY / 2;
        x = (int) (64 * screenRatioX);
    }

    Bitmap getSpaceship(){
        fireCounter ++;
        if (fireCounter == 3){
            return spaceship1;
        } else if (fireCounter == 6){
            fireCounter = 0;
            return spaceship2;
        } else {
            return spaceship3;
        }
    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
}
