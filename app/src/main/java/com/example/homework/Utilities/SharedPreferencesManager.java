package com.example.homework.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.homework.Models.Score;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPreferencesManager {

    private static volatile SharedPreferencesManager instance = null;
    public static final String SCORELIST = "SCORELIST";
    private SharedPreferences sharedPref;

    private SharedPreferencesManager(Context context) {
        this.sharedPref = context.getSharedPreferences(SCORELIST, Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        synchronized (SharedPreferencesManager.class) {
            if (instance == null) {
                instance = new SharedPreferencesManager(context);
            }
        }
    }

    public static SharedPreferencesManager getInstance() {
        return instance;
    }

    public void putScoreList(ArrayList<Score> scores) {
        Gson gson = new Gson();
        String scoreListAsJson = gson.toJson(scores);
        sharedPref.edit().putString(SCORELIST, scoreListAsJson).apply();
    }

    public ArrayList<Score> getScoreList() {
        String json = sharedPref.getString(SCORELIST, "");
        Type listType = new TypeToken<ArrayList<Score>>() {
        }.getType();

        if (json.equals("")) {
            ArrayList<Score> newScoreList = new ArrayList<>();
            return newScoreList;
        }
        return new Gson().fromJson(json, listType);
    }


}
