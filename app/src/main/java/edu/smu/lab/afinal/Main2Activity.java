package edu.smu.lab.afinal;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.hardware.SensorManager;
import android.widget.Toast;

import java.util.Random;

public class Main2Activity extends AppCompatActivity {

    //Database Variables
    String s1,s2,sp1,s3,sp2,s4,sp3,s5,sp4,last = "";
    final Sqlite obj = new Sqlite(this);

    //Sensor Variables
    private SensorManager myManager;
    private Sensor accelerometer;

    //GUI Initialization
    private Button startButton;
    //    private Button shotButton;
    private Button resetButton;
    private TextView lastShotTime;
    private TextView shot1Time;
    private TextView shot2Time;
    private TextView shot3Time;

    private TextView splitTime1;
    private TextView splitTime2;


    //Timer Variables
    private long startTime = 0L;
    private Handler myHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long newTime = 0L;
    private ToneGenerator startBeep;

    boolean isRunning = false;
    int shotCounter = 0;
    int gunshotPeriodCounter = 0;
    boolean gunShot = false;

    //CALIBRATION Values
    final int gunShotPeriod = 5;
    final int gunShotThreshold = 18;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        myManager =(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = myManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        lastShotTime = (TextView) findViewById(R.id.timer);
        shot1Time = (TextView) findViewById(R.id.shot1);
        shot2Time = (TextView) findViewById(R.id.shot2);
        shot3Time = (TextView) findViewById(R.id.shot3);


        splitTime1 = (TextView) findViewById(R.id.split1);
        splitTime2 = (TextView) findViewById(R.id.split2);

        startButton = (Button) findViewById(R.id.Start);
        resetButton = (Button) findViewById(R.id.reset);

        final Random randomGenerator = new Random();

        startButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                //We want to turn off timer if it is already running
                if (isRunning == false){
                    resetOutputStrings();
                    shotCounter = 0;
                    gunshotPeriodCounter = 0;
                    gunShot = false;

                    timeSwapBuff = 0;
                    startTime = 0;
                    timeInMilliseconds =0;

                    int delayTime = randomGenerator.nextInt(4 - 1) + 1; //Generates random delay for starting the timer
                    startTimer(delayTime);
                }
                else if(isRunning == true){
                    myManager.unregisterListener(sensorListener,accelerometer);

                    stopTimer();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                resetOutputStrings();
                myManager.unregisterListener(sensorListener,accelerometer);
                isRunning = false;
                shotCounter = 0;
                gunshotPeriodCounter = 0;
                gunShot = false;

                timeSwapBuff = 0;
                startTime = 0;
                timeInMilliseconds =0;
                myHandler.removeCallbacks(updateTimer);

            }
        });
    }


    SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            Sensor sensor = event.sensor;
            float X;
            float Y;
            float Z;

            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                Y = event.values[1]; //Grab the y values from the accelerometer


                //we want to maintain a buffer of gunShotPeriod between shots.
                //When we sense a shot, we immediately output the time and set gunShot to true
                //We then wait gunShotPeriod number of samples before we can output another time

                //Test to see if we are in gunshot mode
                if(gunShot == true){
                    gunshotPeriodCounter +=1;
                    if(gunshotPeriodCounter >=gunShotPeriod){
                        gunshotPeriodCounter = 0;
                        gunShot = false;
                    }
                }

                if(Y >= gunShotThreshold){
                    if(gunshotPeriodCounter == 0){
                        gunShot = true;
                        if(isRunning == true){
                            shotCounter +=1;
                            if(shotCounter ==1) {
                                shot1Time.setText(lastShotTime.getText());
                                // variables for inserting data in database table.
                                s1 = shot1Time.getText().toString();
                            }
                            else if(shotCounter ==2){
                                shot2Time.setText(lastShotTime.getText());
                                splitTime1.setText(getSplitTime(shot1Time.getText().toString(), shot2Time.getText().toString()));
                                // variables for inserting data in database table.
                                s2 = shot2Time.getText().toString();
                                sp1 = splitTime1.getText().toString();
                            }
                            else if(shotCounter ==3){
                                shot3Time.setText(lastShotTime.getText());
                                splitTime2.setText(getSplitTime(shot2Time.getText().toString(), shot3Time.getText().toString()));
                                // variables for inserting data in database table.
                                s3 = shot3Time.getText().toString();
                                sp2 = splitTime2.getText().toString();

                                last = lastShotTime.getText().toString();

                                boolean result = obj.insertdata(s1,s2,sp1,s3,sp2,s4,sp3,s5,sp4,last);
                                if (result = true)
                                    Toast.makeText(Main2Activity.this, "Data Inserted", Toast.LENGTH_LONG).show();

                                else
                                    Toast.makeText(Main2Activity.this, "Data not Inserted", Toast.LENGTH_LONG).show();

                                shotCounter = 0;
                                myManager.unregisterListener(sensorListener,accelerometer);
                                stopTimer();
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };



    private void resetOutputStrings(){
        lastShotTime.setText("00:00");
        shot1Time.setText("00:00");
        shot2Time.setText("00:00");
        shot3Time.setText("00:00");

        splitTime1.setText("00:00");
        splitTime2.setText("00:00");

    }

    //subtracts the secondTime from the firstTime
    private String getSplitTime (String firstTime, String secondTime){
        //takes out the ":" from the input strings. Hopefully input is in format of 00:00
        String firstTimeValues = new StringBuilder().append(firstTime.charAt(0)).append(firstTime.charAt(1)).append(firstTime.charAt(3)).append(firstTime.charAt(4)).toString();
        String secondTimeValues = new StringBuilder().append(secondTime.charAt(0)).append(secondTime.charAt(1)).append(secondTime.charAt(3)).append(secondTime.charAt(4)).toString();

        //convert strings to ints so we can do computation
        int timeOne = Integer.parseInt(firstTimeValues);
        int timeTwo = Integer.parseInt(secondTimeValues);

        //compute the difference between the two times
        int timeDifference = timeTwo - timeOne;

        String splitTime = Integer.toString(timeDifference);
        splitTime = String.format("%04d", Integer.parseInt(splitTime));//Ensures that there are at least 4 digits in the output
        splitTime = new StringBuilder(splitTime).insert(splitTime.length()-2, ":").toString(); //Adds the colon for the time 00:00

        return splitTime;
    }

    //This starts the tickdown timer starting after a random start time
    private void startTimer(int randomStart){

        int millisecondStart = randomStart *1000;
        final ToneGenerator startBeep = new ToneGenerator(AudioManager.STREAM_MUSIC,100);

        new CountDownTimer(millisecondStart,1000){
            public void onFinish(){
                myManager.registerListener(sensorListener,accelerometer, SensorManager.SENSOR_DELAY_UI);
                startBeep.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT);
                startTime = SystemClock.uptimeMillis();
                myHandler.postDelayed(updateTimer, 0);
                isRunning = true;
            }
            public void onTick(long millisUntilFinished){
            }
        }.start();
    }

    private void stopTimer(){
        timeSwapBuff += timeInMilliseconds;
        myHandler.removeCallbacks(updateTimer);
        isRunning = false;
    }

    private Runnable updateTimer = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            newTime = timeSwapBuff + timeInMilliseconds;
            int seconds = (int) (newTime / 1000);
            seconds = seconds % 60;
            int milliseconds = (int) (newTime % 1000);
            lastShotTime.setText(""
                    + String.format("%02d", seconds) + ":"
                    + String.format("%03d", milliseconds));
            myHandler.postDelayed(this, 0);
        }
    };

}

