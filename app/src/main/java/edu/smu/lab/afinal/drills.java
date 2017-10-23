package edu.smu.lab.afinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class drills extends AppCompatActivity {
    private Button drill1,drill2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drills);
        drill1 = (Button) findViewById(R.id.drill1);
        drill2 = (Button) findViewById(R.id.drill2);

        drill1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(drills.this, Main2Activity.class);
                startActivity(nextActivity);
            }
        });
       drill2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent nextActivity = new Intent(drills.this, drillsno1.class);
               startActivity(nextActivity);
           }
       });
    }
}
