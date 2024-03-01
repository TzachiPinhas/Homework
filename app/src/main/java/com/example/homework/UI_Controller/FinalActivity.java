package com.example.homework.UI_Controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homework.Interfaces.Callback_highScoreClicked;
import com.example.homework.R;
import com.example.homework.Views.ListFragment;
import com.example.homework.Views.MapFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FinalActivity extends AppCompatActivity {

    private FrameLayout main_FRAME_list;
    private FrameLayout main_FRAME_map;
    private MapFragment mapFragment;
    private ListFragment listFragment;
    private FloatingActionButton main_BTN_homepage2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        findViews();
        listFragment = new ListFragment();
        listFragment.setCallbackHighScoreClicked(new Callback_highScoreClicked() {
            @Override
            public void getLocationScore(double lat, double lot) {
                mapFragment.ChangeLocation(lat, lot);
            }
        });
        mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_list, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_map, mapFragment).commit();

        main_BTN_homepage2.setOnClickListener(view -> changeIntent());
    }

    private void findViews() {

        main_FRAME_list = findViewById(R.id.main_FRAME_list);
        main_FRAME_map = findViewById(R.id.main_FRAME_map);
        main_BTN_homepage2 = findViewById(R.id.main_BTN_homepage2);
    }

    private void changeIntent() {
        Intent mainIntent = new Intent(this, OpeningActivity.class);
        startActivity(mainIntent);
        finish();
    }
}