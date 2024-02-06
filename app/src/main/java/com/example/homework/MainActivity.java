package com.example.homework;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int rows = 7;
    private final int cols = 3;
    private static final long DELAY = 1000;

    private ShapeableImageView main_IMG_background;
    private FloatingActionButton main_arrow_left;
    private FloatingActionButton main_arrow_right;
    private ShapeableImageView[] main_IMG_hearts;
    private ImageView[] main_IMG_obstacle;
    private ImageView[] main_IMG_player;
    private ArrayList<Boolean> imageViewMatrix = new ArrayList<Boolean>();
    private GameManager gameManager;
    private boolean timerOn = false;
    final Handler handler = new Handler();
    int currentLane = 1;
    boolean isCrash = false;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, DELAY);
            startGameLoop();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        Glide
                .with(this)
                .load(R.drawable.background)
                .placeholder(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(main_IMG_background);

        gameManager = new GameManager(main_IMG_hearts.length);
        main_arrow_left.setOnClickListener(view -> figureClicked(true));
        main_arrow_right.setOnClickListener(view -> figureClicked(false));
        startTimer();
    }


    private void figureClicked(boolean direction) {
        main_IMG_player[currentLane].setVisibility(View.INVISIBLE);
        currentLane = gameManager.figureMoving(direction, currentLane);
        main_IMG_player[currentLane].setVisibility(View.VISIBLE);
        if (gameManager.checkCollision(currentLane)) {
            vibrate();
            toast("Oops");
        }
        refreshUI();
    }

    private void refreshUI() {
        if (gameManager.isGameLost()) {
            vibrate();
            toast("GAME OVER");
            gameManager.setNumMistake(0);
            for (int i = 0; i < main_IMG_hearts.length; i++) {
                main_IMG_hearts[i].setVisibility(View.VISIBLE);
            }
        }
        if (gameManager.getNumMistake() != 0)
            main_IMG_hearts[main_IMG_hearts.length - gameManager.getNumMistake()].setVisibility(View.INVISIBLE);
    }


    private void startGameLoop() {
        imageViewMatrix = gameManager.moveObstacles();
        makeObstaleVisible();
        isCrash = gameManager.checkCollision(currentLane);
        if (isCrash) {
            vibrate();
            toast("Oops");
        }
        refreshUI();
    }

    public void makeObstaleVisible() {
        for (int i = 0; i < rows * cols; i++) {
            if (imageViewMatrix.get(i))
                main_IMG_obstacle[i].setVisibility(View.VISIBLE);
            else
                main_IMG_obstacle[i].setVisibility(View.INVISIBLE);
        }
    }


    private void findViews() {
        main_arrow_left = findViewById(R.id.main_arrow_left);
        main_arrow_right = findViewById(R.id.main_arrow_right);
        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };
        main_IMG_obstacle = new ImageView[]{
                findViewById(R.id.main_IMG1),
                findViewById(R.id.main_IMG8),
                findViewById(R.id.main_IMG15),
                findViewById(R.id.main_IMG2),
                findViewById(R.id.main_IMG9),
                findViewById(R.id.main_IMG16),
                findViewById(R.id.main_IMG3),
                findViewById(R.id.main_IMG10),
                findViewById(R.id.main_IMG17),
                findViewById(R.id.main_IMG4),
                findViewById(R.id.main_IMG11),
                findViewById(R.id.main_IMG18),
                findViewById(R.id.main_IMG5),
                findViewById(R.id.main_IMG12),
                findViewById(R.id.main_IMG19),
                findViewById(R.id.main_IMG6),
                findViewById(R.id.main_IMG13),
                findViewById(R.id.main_IMG20),
                findViewById(R.id.main_IMG7),
                findViewById(R.id.main_IMG14),
                findViewById(R.id.main_IMG21),
        }; // the order like main activity

        main_IMG_player = new ImageView[]{
                findViewById(R.id.main_player1),
                findViewById(R.id.main_player2),
                findViewById(R.id.main_player3),
        };

        main_IMG_background = findViewById(R.id.main_IMG_background);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
    }

    private void stopTimer() {
        timerOn = false;
        handler.removeCallbacks(runnable);
    }

    private void startTimer() {
        if (!timerOn) {
            timerOn = true;
            handler.postDelayed(runnable, 0);
        }
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }

    private void toastAndVibrate(String text) {
        vibrate();
        toast(text);
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}



