package com.example.homework.UI_Controller;

import android.content.Context;
import android.content.Intent;
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
import com.example.homework.Interfaces.SpeedCallback;
import com.example.homework.Interfaces.StepCallback;
import com.example.homework.Logic.GameManager;
import com.example.homework.R;
import com.example.homework.Utilities.BackgroundSound;
import com.example.homework.Utilities.StepDetector;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_SENSOR = "KEY_SENSOR";
    public static final String KEY_SPEED = "KEY_SPEED";

    private final int rows = 7;
    private final int cols = 5;
    private ShapeableImageView main_IMG_background;
    private FloatingActionButton main_arrow_left;
    private FloatingActionButton main_arrow_right;
    private ShapeableImageView[] main_IMG_hearts;
    private MaterialTextView main_LBL_score;
    private ImageView[] main_IMG_ImageView;
    private ImageView[] main_IMG_player;
    private ArrayList<Integer> imageViewMatrix = new ArrayList<Integer>();
    private GameManager gameManager;
    private boolean timerOn = false;
    final Handler handler = new Handler();
    int currentLane = 2;
    boolean isCrash = false;
    long delay;  //speed of the game
    boolean sensor;
    boolean speed; //If the user has selected fast or normal play
    private StepDetector stepDetector;
    private BackgroundSound backgroundSound;


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, delay);
            startGameLoop();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        Intent previousScreen = getIntent();
        sensor = previousScreen.getBooleanExtra(KEY_SENSOR, false);
        speed = previousScreen.getBooleanExtra(KEY_SPEED, false);
        Glide
                .with(this)
                .load(R.drawable.background)
                .placeholder(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(main_IMG_background);
        gameManager = new GameManager(main_IMG_hearts.length);
        if (speed) {
            delay = 500;
        } else
            delay = 1000;

        if (sensor) {  //A game with a sensor
            main_arrow_left.setVisibility(View.INVISIBLE);
            main_arrow_right.setVisibility(View.INVISIBLE);
            initStepDetector();
        } else { //A game with buttons
            main_arrow_left.setOnClickListener(view -> figureClicked(true));
            main_arrow_right.setOnClickListener(view -> figureClicked(false));
        }
        startTimer();
    }

    private void initStepDetector() {
        stepDetector = new StepDetector(this, new StepCallback() {
            @Override
            public void stepRight() {
                figureClicked(false);
            }

            @Override
            public void stepLeft() {
                figureClicked(true);
            }
        }, new SpeedCallback() {
            @Override
            public void speedGame() {
                if (speed) {
                    stopTimer();
                    delay = 200;
                    startTimer();
                } else {
                    stopTimer();
                    delay = 500;
                    startTimer();
                }
            }

            @Override
            public void regularGame() {
                if (speed) {
                    stopTimer();
                    delay = 500;
                    startTimer();
                } else{
                    stopTimer();
                    delay = 1000;
                    startTimer();
                }

            }
        });
    }


    private void figureClicked(boolean direction) {
        main_IMG_player[currentLane].setVisibility(View.INVISIBLE);
        currentLane = gameManager.figureMoving(direction, currentLane);
        main_IMG_player[currentLane].setVisibility(View.VISIBLE);
        if (gameManager.checkCollision(currentLane)) {
            vibrate();
            toast("Oops");
            backgroundSound.playSound();
        }
        refreshUI();
    }

    private void refreshUI() {
        if (gameManager.isGameLost()) {
            backgroundSound.playSound();
            toast("GAME OVER");
            changeActivity(gameManager.getScore());
        }
        if (gameManager.getNumMistake() != 0)
            main_IMG_hearts[main_IMG_hearts.length - gameManager.getNumMistake()].setVisibility(View.INVISIBLE);

        main_LBL_score.setText(String.valueOf(gameManager.getScore()));
    }


    private void startGameLoop() {
        imageViewMatrix = gameManager.moveImageView();
        makeImageViewVisible();
        isCrash = gameManager.checkCollision(currentLane);
        if (isCrash) {
            vibrate();
            toast("Oops");
            backgroundSound.playSound();
        }
        refreshUI();
    }

    public void makeImageViewVisible() {
        for (int i = 0; i < rows * cols; i++) {
            if (imageViewMatrix.get(i) == 1)
                main_IMG_ImageView[i].setImageResource(R.drawable.tom);
            else if (imageViewMatrix.get(i) == 2)
                main_IMG_ImageView[i].setImageResource(R.drawable.cheese);
            else
                main_IMG_ImageView[i].setImageResource(0);
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

        main_LBL_score = findViewById(R.id.main_LBL_score);

        main_IMG_ImageView = new ImageView[]{
                findViewById(R.id.main_IMG00),
                findViewById(R.id.main_IMG01),
                findViewById(R.id.main_IMG02),
                findViewById(R.id.main_IMG03),
                findViewById(R.id.main_IMG04),
                findViewById(R.id.main_IMG10),
                findViewById(R.id.main_IMG11),
                findViewById(R.id.main_IMG12),
                findViewById(R.id.main_IMG13),
                findViewById(R.id.main_IMG14),
                findViewById(R.id.main_IMG20),
                findViewById(R.id.main_IMG21),
                findViewById(R.id.main_IMG22),
                findViewById(R.id.main_IMG23),
                findViewById(R.id.main_IMG24),
                findViewById(R.id.main_IMG30),
                findViewById(R.id.main_IMG31),
                findViewById(R.id.main_IMG32),
                findViewById(R.id.main_IMG33),
                findViewById(R.id.main_IMG34),
                findViewById(R.id.main_IMG40),
                findViewById(R.id.main_IMG41),
                findViewById(R.id.main_IMG42),
                findViewById(R.id.main_IMG43),
                findViewById(R.id.main_IMG44),
                findViewById(R.id.main_IMG50),
                findViewById(R.id.main_IMG51),
                findViewById(R.id.main_IMG52),
                findViewById(R.id.main_IMG53),
                findViewById(R.id.main_IMG54),
                findViewById(R.id.main_IMG60),
                findViewById(R.id.main_IMG61),
                findViewById(R.id.main_IMG62),
                findViewById(R.id.main_IMG63),
                findViewById(R.id.main_IMG64),

        }; // the order like main activity

        main_IMG_player = new ImageView[]{
                findViewById(R.id.main_player1),
                findViewById(R.id.main_player2),
                findViewById(R.id.main_player3),
                findViewById(R.id.main_player4),
                findViewById(R.id.main_player5),
        };

        main_IMG_background = findViewById(R.id.main_IMG_background);
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

    private void changeActivity(int score) {
        Intent scoreIntent = new Intent(this, ScoreActivity.class);
        scoreIntent.putExtra(ScoreActivity.KEY_SCORE, score);
        startActivity(scoreIntent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        backgroundSound = new BackgroundSound(this);
        if (sensor)
            stepDetector.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
        backgroundSound.stopSound();
        if (sensor)
            stepDetector.stop();
    }
}



