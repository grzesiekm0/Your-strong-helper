package com.yourstronghelper.grzegorzmacko.yourstronghelper.exercises;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yourstronghelper.grzegorzmacko.yourstronghelper.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonAddExercise).setOnClickListener(this);
        findViewById(R.id.buttonTrainingPlan).setOnClickListener(this);
        findViewById(R.id.buttonGeneratePlan).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.buttonAddExercise:

                startActivity(new Intent(this, AddExerciseActivity.class));
                break;

            case R.id.buttonTrainingPlan:

               // startActivity(new Intent(this, TO DO.class))
                break;

            case R.id.buttonGeneratePlan:

                // startActivity(new Intent(this, TO DO.class))
                break;


        }
    }
}