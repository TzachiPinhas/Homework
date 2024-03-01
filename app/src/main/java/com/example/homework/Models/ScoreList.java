package com.example.homework.Models;

import java.util.ArrayList;

public class ScoreList {

    private ArrayList<Score> scoresArrayList = new ArrayList<>();

    public ScoreList() {
    }

    public ArrayList<Score> getScoresArrayList() {
        return scoresArrayList;
    }


    public ScoreList setScoreArrayList(ArrayList<Score> songArrayList) {
        this.scoresArrayList = songArrayList;
        return this;
    }

    public ScoreList addScore(Score score) {
        this.scoresArrayList.add(score);
        return this;
    }
}
