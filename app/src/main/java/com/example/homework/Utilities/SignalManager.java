package com.example.homework.Utilities;

import android.content.Context;
import android.os.Vibrator;

public class SignalManager {
    private static SignalManager instance = null;

    private Context context;
    private static Vibrator vibrator;

    private SignalManager(Context context) {
        this.context = context;
    }

    public static void init(Context context) {
        synchronized (SignalManager.class) {
            if (instance == null) {
                instance = new SignalManager(context);
                vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            }
        }
    }

    public static SignalManager getInstance() {
        return instance;
    }

}
