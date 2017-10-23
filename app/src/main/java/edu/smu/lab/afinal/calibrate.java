package edu.smu.lab.afinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class calibrate extends AppCompatActivity {
    SeekBar calibratebar;
    TextView calilabel;

    public static int userCalibrationValue = 18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrate);
        calibratebar = (SeekBar) findViewById(R.id.seekBar);
        calilabel = (TextView) findViewById(R.id.calibri);

        calibratebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                calilabel.setText("" + i);
                if(i>0 && i <=10){
                    userCalibrationValue = 20;
                }
                else if(i>10 && i<=20){
                    userCalibrationValue = 19;
                }
                else if(i>20 && i<=30){
                    userCalibrationValue = 18;
                }
                else if(i>30 && i<=40){
                    userCalibrationValue = 17;
                }
                else if(i>40 && i<=50){
                    userCalibrationValue = 16;
                }
                else if(i>50 && i<=60){
                    userCalibrationValue = 15;
                }
                else if(i>60 && i<=70){
                    userCalibrationValue = 14;
                }
                else if(i>70 && i<=80){
                    userCalibrationValue = 13;
                }
                else if(i>80 && i<=90){
                    userCalibrationValue = 12;
                }
                else if(i>90 && i<=100){
                    userCalibrationValue = 11;
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public static int getCalVal(){
        return userCalibrationValue;
    }
}
