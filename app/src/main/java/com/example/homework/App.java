package com.example.homework;

import android.app.Application;

import com.example.homework.Utilities.SharedPreferencesManager;
import com.example.homework.Utilities.SignalManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesManager.init(this);
        SignalManager.init(this);
    }
}
