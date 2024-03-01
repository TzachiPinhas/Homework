package com.example.homework.Models;

public class Score implements Comparable<Score> {
    private String name="";
    private int score=0;

    private double lon=0;

    private double lat=0;


    public Score(String name, int score, double lon, double lat) {
        this.name = name;
        this.score = score;
        this.lon = lon;
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public Score setName(String name) {
        this.name = name;
        return this;
    }

    public int getScore() {
        return score;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    @Override
    public int compareTo(Score other) {
        return Integer.compare(other.getScore(), this.getScore()); // Sort from highest to lowest
    }
}
