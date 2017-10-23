package edu.smu.lab.afinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button shottimer,drills,help,calibrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shottimer = (Button) findViewById(R.id.shot_timer);
        drills = (Button) findViewById(R.id.drill_list);
        help = (Button) findViewById(R.id.help);
        calibrate = (Button) findViewById(R.id.calibrate);


        shottimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(MainActivity.this, shottimer.class);
                startActivity(nextActivity);

            }
        });

        drills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(MainActivity.this, drills.class);
                startActivity(nextActivity);
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(MainActivity.this, help.class);
                startActivity(nextActivity);
            }
        });
        calibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(MainActivity.this, calibrate.class);
                startActivity(nextActivity);
            }
        });
    }


}
