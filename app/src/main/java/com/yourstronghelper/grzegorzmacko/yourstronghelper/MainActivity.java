package com.yourstronghelper.grzegorzmacko.yourstronghelper;

import android.content.Intent;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yourstronghelper.grzegorzmacko.yourstronghelper.R;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.exercises.AddExerciseActivity;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.exercises.ExerciseActivity;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.plans.AddTrainingPlanActivity;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.plans.TrainingPlanActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<Integer> alarmDays= new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonAddExercise).setOnClickListener(this);
        findViewById(R.id.buttonTrainingPlan).setOnClickListener(this);
        findViewById(R.id.buttonDisplayExercises).setOnClickListener(this);
        findViewById(R.id.buttonDisplayTrainingsPlans).setOnClickListener(this);
        findViewById(R.id.buttonSetAlarm).setOnClickListener(this);
        alarmDays.add(Calendar.SATURDAY);
        alarmDays.add(Calendar.FRIDAY);
        alarmDays.add(Calendar.SUNDAY);


    }



    public void createAlarm(String message, int hour, int minutes) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes)
                .putExtra(AlarmClock.EXTRA_DAYS, alarmDays);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.buttonAddExercise:

                startActivity(new Intent(this, AddExerciseActivity.class));
                break;

            case R.id.buttonTrainingPlan:

                startActivity(new Intent(this, AddTrainingPlanActivity.class));
                break;


            case R.id.buttonDisplayTrainingsPlans:

                startActivity(new Intent(this, TrainingPlanActivity.class));
                break;

            case R.id.buttonDisplayExercises:

                startActivity(new Intent(this, ExerciseActivity.class));
                break;
            case R.id.buttonSetAlarm:
                    createAlarm("Dupa", 6,12);
                //startActivity(new Intent(this, ExerciseActivity.class));
                break;


        }
    }


}