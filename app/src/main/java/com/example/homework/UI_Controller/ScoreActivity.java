package com.example.homework.UI_Controller;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homework.Models.Score;
import com.example.homework.R;
import com.example.homework.Utilities.SharedPreferencesManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreActivity extends AppCompatActivity {

    public static final String KEY_SCORE = "KEY_SCORE";

    private MaterialTextView score_LBL_score;
    private FloatingActionButton main_BTN_homepage;
    private FusedLocationProviderClient fusedLocationProvider;
    private String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this);
        findViews();
        Intent previousScreen = getIntent();
        int score = previousScreen.getIntExtra(KEY_SCORE, 0);
        refreshUI(score);
        showNameInputDialog(score);
        main_BTN_homepage.setOnClickListener(view -> changeIntentToHome());

    }


    private void refreshUI(int score) {
        score_LBL_score.setText("Your score is: \n" + score);
    } // Show the score

    private void findViews() {
        score_LBL_score = findViewById(R.id.score_LBL_score);
        main_BTN_homepage = findViewById(R.id.main_BTN_homepage);

    }

    private void changeIntentToHome() {
        Intent openIntent = new Intent(this, OpeningActivity.class);
        startActivity(openIntent);
        finish();


    }

    private void showNameInputDialog(int score) { //Asks the user for the name and saves the details in a table
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Your Name");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userName = input.getText().toString();

                // Request location
                fusedLocationProvider.getLastLocation().addOnSuccessListener(ScoreActivity.this, location -> {
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        // Save the record with location
                        SharedPreferencesManager manager = SharedPreferencesManager.getInstance();
                        ArrayList<Score> scores = manager.getScoreList();
                        scores.add(new Score(userName, score, longitude, latitude));
                        Collections.sort(scores);
                        manager.putScoreList(scores);
                        changeIntent();
                    }
                });
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    private void changeIntent() {
        Intent mainIntent = new Intent(this, FinalActivity.class);
        startActivity(mainIntent);
        finish();
    }

}