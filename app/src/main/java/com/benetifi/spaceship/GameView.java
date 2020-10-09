package com.benetifi.spaceship;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.benetifi.spaceship.MainActivity.spaceship_speed;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private Background background1;
    private Spaceship spaceship;
    private List<Asteroid> asteroids;
    private Asteroid asteroid1, asteroid2, asteroid3, asteroid4;
    private int screenX, screenY, asterCounter = 0, prevAsteroidY, score, speedUpCounter, increaseSpeed = 0;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private Random random;
    private MainActivity mainActivity;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        mainActivity = (MainActivity) context;

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        asteroids = new ArrayList<>();
        random = new Random();

        asteroid1 = new Asteroid(getResources());
        asteroid2 = new Asteroid(getResources());
        asteroid3 = new Asteroid(getResources());
        asteroid4 = new Asteroid(getResources());
        background1 = new Background(screenX , screenY, getResources());
        spaceship = new Spaceship(this, screenY, getResources());

        paint = new Paint();
        paint.setTextSize(60);
        paint.setColor(Color.WHITE);
    }

    @Override
    public void run() {
        while (isPlaying){
            update();
            draw();
            sleep();
        }
    }

    private void update() {
        if (spaceship.isGoingUp == 1){
            spaceship.y -= spaceship_speed * screenRatioY;
        } else if (spaceship.isGoingUp == 2) {
            spaceship.y += spaceship_speed * screenRatioY;
        }

        if (spaceship.y < 0){
            spaceship.y = 0;
        }

        if (spaceship.y > screenY - spaceship.height){
            spaceship.y = screenY - spaceship.height;
        }

        List<Asteroid> trash = new ArrayList<>();

        if (asteroids.size() <= 0) {
            newAsteroid(asteroid1);
        } else {
            if (asteroids.get(asteroids.size() - 1).x < (screenX / 2) + asteroids.get(asteroids.size() - 1).width){
                asterCounter ++;
                if (asterCounter == 1) {
                    newAsteroid(asteroid2);
                } else if (asterCounter == 2){
                    newAsteroid(asteroid3);
                } else if (asterCounter == 3){
                    newAsteroid(asteroid4);
                } else if (asterCounter == 4){
                    newAsteroid(asteroid1);
                    asterCounter = 0;
                }
            }
        }

        for (Asteroid asteroid : asteroids){
            if (asteroid.x + asteroid.width < 0){
                score ++;
                speedUpCounter ++;
                trash.add(asteroid);
            }
            if (speedUpCounter > 5){
                speedUpCounter = 0;
                increaseSpeed += 2;
            }
            asteroid.x -= asteroid.speed + increaseSpeed;

            if (Rect.intersects(asteroid.getCollisionShape(), spaceship.getCollisionShape())) {
                isGameOver = true;
                return;
            }
        }

        for (Asteroid asteroid : trash){
            asteroids.remove(asteroid);
        }
    }

    private void draw(){
        if (getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(spaceship.getSpaceship(), spaceship.x, spaceship.y, paint);

            for (Asteroid asteroid : asteroids){
                canvas.drawBitmap(asteroid.asteroid1, asteroid.x, asteroid.y, paint);
            }

            canvas.drawText(score + "", screenX / 2f, 60, paint);

            if (isGameOver) {
                isPlaying = false;
                getHolder().unlockCanvasAndPost(canvas);
                waitBeforeExiting();
                return;
            }

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void waitBeforeExiting() {
        try {
            Thread.sleep(1500);
            gameOver();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void sleep(){
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume(){
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause(){
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (event.getY() < screenY / 2) {
                    spaceship.isGoingUp = 1;
                }
                if (event.getY() > screenY / 2) {
                    spaceship.isGoingUp = 2;
                }
                break;
            case MotionEvent.ACTION_UP:
                spaceship.isGoingUp = 0;
                break;
        }
        return true;
    }

    private void newAsteroid(Asteroid aster){
        aster.x = screenX;
        do {
            aster.y = random.nextInt(screenY - aster.height);
        } while (prevAsteroidY == aster.y);
        prevAsteroidY = aster.y;
        asteroids.add(aster);
    }

    private void gameOver(){
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LayoutInflater li = LayoutInflater.from(getContext());
                View promptsView = li.inflate(R.layout.game_over, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getContext());
                alertDialogBuilder.setView(promptsView);
                alertDialogBuilder
                        .setCancelable(true);
                final AlertDialog alertDialog = alertDialogBuilder.create();
                TextView txtGameOverScore = promptsView.findViewById(R.id.txtGameOverScore);
                txtGameOverScore.setText(String.valueOf(score));
                ImageView imgClose = promptsView.findViewById(R.id.imgClose);
                imgClose.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        mainActivity.onBackPressed();
                    }
                });
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
            }
        });
    }
}
