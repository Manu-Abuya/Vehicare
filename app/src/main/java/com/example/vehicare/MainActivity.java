package com.example.vehicare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_TIME_OUT = 2000;
    // After completion of 2000 ms, the next activity will get started.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase.getInstance().getReference().child("ProgrammingKnowledge").child("Android").setValue("abcd");

        //This method is used so that the splash screen activity can cover the entire screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //This binds the MainActivity.class with the activity_main layout
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //Intent is used to switch from one activity to another.
                Intent i = new Intent(MainActivity.this, StartupActivity.class);

                //invoke the Startup Activity
                startActivity(i);

                //The current activity will get finished
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}