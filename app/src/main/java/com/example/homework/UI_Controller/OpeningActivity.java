package com.example.homework.UI_Controller;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homework.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.materialswitch.MaterialSwitch;

public class OpeningActivity extends AppCompatActivity {

    private MaterialSwitch switch_Sensor;
    private MaterialSwitch switch_Speed;
    private MaterialButton main_BTN_start;
    private MaterialButton main_BTN_record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);
        getPermission();
        findViews();
        main_BTN_start.setOnClickListener(view -> clickStart());
        main_BTN_record.setOnClickListener(view -> clickScoreTable());

    }

    private void clickStart() { //if the user start the game
        boolean speed= false;
        boolean sensor= false;
        if (switch_Speed.isChecked())
            speed=true;

        if (switch_Sensor.isChecked())
            sensor=true;

        changeIntentGame(sensor,speed);
    }

    private void clickScoreTable() {
        changeIntentTable();
    }

    private void changeIntentTable() {
        Intent mainIntent = new Intent(this,FinalActivity.class);
        startActivity(mainIntent);
    }


    private void findViews() {
        switch_Sensor= findViewById(R.id.switch_Sensor);
        switch_Speed= findViewById(R.id.switch_Speed);
        main_BTN_start= findViewById(R.id.main_BTN_start);
        main_BTN_record= findViewById(R.id.main_BTN_record);

    }

    private void changeIntentGame(boolean sensor,boolean speed){
        Intent mainIntent = new Intent(this,MainActivity.class);
        mainIntent.putExtra(MainActivity.KEY_SENSOR,sensor);
        mainIntent.putExtra(MainActivity.KEY_SPEED,speed);
        startActivity(mainIntent);
        finish();
    }

    void getPermission() {  // A message for the user to approve sharing a location
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION, false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                // Precise location access granted.
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Only approximate location access granted.
                            } else {
                                finish();
                                // No location access granted.
                            }
                        }
                );
        locationPermissionRequest.launch(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                });
}
}