package com.example.homework;

import java.util.ArrayList;
import java.util.Random;


public class GameManager {

    private final int rows = 7;
    private final int cols = 3;

    private int mistake = 0;
    private int life;
    private Random random = new Random();
    private int randomNumber = random.nextInt();
    private int flag = random.nextInt();


    private ArrayList<Boolean> isVisebleObstale = new ArrayList<Boolean>();


    public GameManager(int life) {
        this.life = life;
        for (int i = 0; i < rows * cols; i++) {
            isVisebleObstale.add(false);
        }
    }

    public int getLife() {
        return life;
    }

    public int getNumMistake() {
        return mistake;
    }

    public void setNumMistake(int mistake){
      this.mistake=mistake;
    }


    public boolean checkCollision(int currentLane) {
        if (isVisebleObstale.get((rows - 1) * cols + currentLane)) {
            mistake++;
            return true;
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

    public ArrayList<Boolean> moveObstacles() {
        for (int i = rows * cols - 1; i >= (rows - 1) * cols; i--)
            isVisebleObstale.remove(i);

        for (int i = 0; i < cols; i++) {
            isVisebleObstale.add(0, false);
        }
        flag = random.nextInt(2);
        if (flag == 1) {
            randomNumber = random.nextInt(cols);
            isVisebleObstale.set(randomNumber, true);
        }

         return isVisebleObstale;
        }

    public boolean isGameLost() {
        return getLife() == getNumMistake();
    }

}
