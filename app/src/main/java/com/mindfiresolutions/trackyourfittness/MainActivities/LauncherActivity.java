package com.mindfiresolutions.trackyourfittness.MainActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mindfiresolutions.trackyourfittness.DatabaseFiles.DatabaseHandler;
import com.mindfiresolutions.trackyourfittness.R;

import java.util.Timer;
import java.util.TimerTask;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {

                Intent i = new Intent(LauncherActivity.this, HomeActivity.class);
                LauncherActivity.this.finish();
                startActivity(i);
            }

        },getResources().getInteger(R.integer.splash_timing));

    }
}
