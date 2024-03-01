package com.example.homework.Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework.Adapters.ScoreAdapter;
import com.example.homework.Interfaces.Callback_highScoreClicked;
import com.example.homework.Interfaces.ScoreCallback;
import com.example.homework.Models.Score;
import com.example.homework.R;
import com.example.homework.Utilities.SharedPreferencesManager;

import java.util.ArrayList;


public class ListFragment extends Fragment {

    private RecyclerView main_LST_scores;

    private ArrayList<Score> scores;
    private Callback_highScoreClicked callbackHighScoreClicked;

    public ListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        SharedPreferencesManager manager = SharedPreferencesManager.getInstance();

        scores = manager.getScoreList();
        initViews(view);
        return view;
    }

    public void setCallbackHighScoreClicked(Callback_highScoreClicked callbackHighScoreClicked) {
        this.callbackHighScoreClicked = callbackHighScoreClicked;
    }

    private void findViews(View view) {
        main_LST_scores = view.findViewById(R.id.main_LST_scores);
    }

    private void initViews(View view) {
        ScoreAdapter scoreAdapter = new ScoreAdapter(view.getContext(), scores);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        main_LST_scores.setLayoutManager(linearLayoutManager);
        main_LST_scores.setAdapter(scoreAdapter);

        scoreAdapter.setScoreCallBack(new ScoreCallback() {
            @Override
            public void scoreClicked(Score score, int position) {
                callbackHighScoreClicked.getLocationScore(score.getLat(), score.getLon());
            }
        });
    }

}