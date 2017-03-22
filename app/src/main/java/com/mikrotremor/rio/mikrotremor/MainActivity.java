package com.mikrotremor.rio.mikrotremor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView sumbuX;
    private TextView sumbuY;
    private TextView sumbuZ;
    private long lastUpdate;
    private Sensor senAccelerometer;
    String[] mTestArray;
    Integer i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sumbuX = (TextView) findViewById(R.id.sumbuX);
        sumbuY = (TextView) findViewById(R.id.sumbuY);
        sumbuZ = (TextView) findViewById(R.id.sumbuZ);


        mTestArray = getResources().getStringArray(R.array.seismikX);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        senAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        lastUpdate = System.currentTimeMillis();
    }


    private void updateTextView(){
        TextView randomText = (TextView) findViewById(R.id.randomText);
        Random random = new Random();

        int maxIndex = mTestArray.length;
        int generateIndex = random.nextInt(i);

        randomText.setText(mTestArray[generateIndex]);
        i++;
    }

    private void getAccelerometer(SensorEvent event) {
        float[] value = event.values;
        Float alpha = (float) 13;
        Float x = (float) (value[0]-0.02);
        if(x > 0.018 || x < -0.018){
            x = x*alpha;
        }
        Float y = (float) (value[1]-0.01);
        if(y > 0.018 || y < -0.018){
            y = y*alpha;
        }
        Float z = (float) ((value[2]-0.687)*10);

        String nilaiX = Float.toString(x);

        sumbuX.setText(Float.toString(x));
        sumbuY.setText(Float.toString(y));
        sumbuZ.setText(Float.toString(z));
        // baris = new Float[0];
        // baris[i] = x;
        // i++;
        lastUpdate = event.timestamp;
        //return Float.toString(x);
        // sumbuXX.append(arrayString[i]);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
            getAccelerometer(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
                sensorManager.SENSOR_DELAY_FASTEST);

       // updateTextView();
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
