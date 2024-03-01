package com.example.homework.Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.homework.Interfaces.SpeedCallback;
import com.example.homework.Interfaces.StepCallback;

public class StepDetector {
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;
    private long timeStamp = 0;

    private StepCallback stepCallback;

    private SpeedCallback speedCallback;


    public StepDetector(Context context, StepCallback stepCallback, SpeedCallback speedCallback) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.stepCallback = stepCallback;
        this.speedCallback = speedCallback;
        initEventListener();
    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                calculateStep(x,y);


            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // pass
            }
        };

    }

    private void calculateStep(float x,float y) {
        if (System.currentTimeMillis() - timeStamp > 500) {
            timeStamp = System.currentTimeMillis();
            if (x > 1.0) {
                if (stepCallback != null) {
                    stepCallback.stepLeft();

                }
            } else if (x < -1.0) {
                if (stepCallback != null)
                    stepCallback.stepRight();
            }
            if (y < -2.0) {
                if (speedCallback != null)
                    speedCallback.speedGame();
            } else {
                if (speedCallback != null)
                    speedCallback.regularGame();
            }

        }
    }



    public void start() {
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void stop() {
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );
    }
}
