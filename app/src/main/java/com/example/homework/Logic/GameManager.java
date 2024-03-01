package com.example.homework.Logic;

import java.util.ArrayList;
import java.util.Random;


public class GameManager {
    private static final int GOOD_COLLISION = 100;
    private static final int BAD_COLLISION = 100;
    private static final int SCORE_FOR_DISTANCE = 10;
    private final int rows = 7;
    private final int cols = 5;
    private int mistake = 0;

    private int score=0;
    private int life;
    private Random random = new Random();
    private int randomNumber = random.nextInt();
    private int flag = random.nextInt();
    private ArrayList<Integer> isVisebleImageView = new ArrayList<Integer>();


    public GameManager(int life) {
        this.life = life;
        for (int i = 0; i < rows * cols; i++) {
            isVisebleImageView.add(0);
        }
    }

    public int getLife() {
        return life;
    }

    public int getNumMistake() {
        return mistake;
    }

    public void setNumMistake(int mistake) {
        this.mistake = mistake;
    }


    public boolean checkCollision(int currentLane) {

        if (isVisebleImageView.get((rows - 1) * cols + currentLane) % 2 ==1) {
            mistake++;
            score -= BAD_COLLISION;
            return true;
        }
        else if (isVisebleImageView.get((rows - 1) * cols + currentLane) == 2){
            score += GOOD_COLLISION;
        }
        return false;
    }


    public int figureMoving(boolean direction, int currentLane) {
        //left
        if (direction) {
            if (currentLane > 0)
                currentLane--;
        }
        //right
        else if (currentLane < (cols - 1)) {
            currentLane++;
        }
        return currentLane;
    }

    public ArrayList<Integer> moveImageView() {
        for (int i = rows * cols - 1; i >= (rows - 1) * cols; i--)
            isVisebleImageView.remove(i);

        for (int i = 0; i < cols; i++) {
            isVisebleImageView.add(i, 0);
        }
        flag = random.nextInt(4);
        if (flag %2 ==1) {
            randomNumber = random.nextInt(cols);
            isVisebleImageView.set(randomNumber, 1);
        }
        if (flag == 2) {
            randomNumber = random.nextInt(cols);
            isVisebleImageView.set(randomNumber, 2);
        }
        score += SCORE_FOR_DISTANCE;
        return isVisebleImageView;
    }

    public boolean isGameLost() {
        return getLife() == getNumMistake();
    }

    public int getScore() {
        return score;
    }

}
