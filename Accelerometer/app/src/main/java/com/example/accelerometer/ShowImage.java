package com.example.accelerometer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ShowImage extends AppCompatActivity {

    private ImageView image;
    private int pic;

    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        /* Shake detector */
        // Assigns Sensor Manager
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // Register SensorListener
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        //Assigns values
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        /* end of Shake detector */

        pic = R.drawable.skull;
        image = (ImageView)findViewById(R.id.imageView);
        scaleImg(image, pic);

    }

    private void scaleImg(ImageView img, int pic) {
        // Scales picture to fit in screen

        // Get screen size
        Display screen = getWindowManager().getDefaultDisplay();
        BitmapFactory.Options options = new BitmapFactory.Options();

        //Set boundries
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(),pic ,options);

        int imgWidth = options.outWidth;
        int screenWidth = screen.getWidth();

        if (imgWidth > screenWidth){
            int ratio = Math.round((float)imgWidth/(float)screenWidth);
            options.inSampleSize = ratio;
        }
        options.inJustDecodeBounds = false;
        Bitmap scaledImg = BitmapFactory.decodeResource(getResources(),pic,options);
        img.setImageBitmap(scaledImg);
    }

    /* Shake detector */

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter


            // rotate ImageView vith accelerometer to always see it "correct"
             float aX = se.values[0];
             float aY = se.values[1];
             int angle = (int) (Math.atan2(aX, aY)/(Math.PI/180));
             image.setRotation(angle);

            //check if shakes
            if (mAccel > 12) {
                // Changes the pic from visible to invisible and vice versa
                if(pic!= 0){
                    image.setImageResource(0);
                    pic = 0;
                } else {
                    image.setImageResource(R.drawable.skull);
                    pic = R.drawable.skull;
                }

                // Needs a timeout, otherwise img just keeps changing
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Adds a message that divice has been shaken, just if you missed it
                Toast toast = Toast.makeText(getApplicationContext(), "Device has shaken.", Toast.LENGTH_LONG);
                toast.show();
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // Not in use
        }
    };
    // for purpose of saving battery life
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }
    /* end of Shake detector */
} //end of class
