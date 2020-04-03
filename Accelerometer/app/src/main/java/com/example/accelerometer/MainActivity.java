package com.example.accelerometer;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

        private TextView xText, yText, zText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create Sensor Manager
        SensorManager mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        //Accelerometer sensor
        Sensor mySensor = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // Register SensorListener
        mySensorManager.registerListener(this,mySensor,SensorManager.SENSOR_DELAY_NORMAL);

        // Assign TextViews
        xText = (TextView)findViewById(R.id.xView);
        yText = (TextView)findViewById(R.id.yView);
        zText = (TextView)findViewById(R.id.zView);

        //Assign Button
        Button imgBtn = (Button)findViewById(R.id.imgBtn);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Let's add a vibrator, why not?
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    vibrator.vibrate(500);
                }

                //Open new activity(that shows a skull picture)
                Intent showPicture = new Intent(getApplicationContext(), ShowImage.class);
                startActivity(showPicture);

            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        xText.setText("X: " + event.values[0]);
        yText.setText("Y: " + event.values[1]);
        zText.setText("Z: " + event.values[2]);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not in use
    }
} //end of class
