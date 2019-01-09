package com.yourstronghelper.grzegorzmacko.yourstronghelper.exercises;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yourstronghelper.grzegorzmacko.yourstronghelper.R;

public class TrainingPlan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_plan);

        //create a TextView with Layout parameters according to your needs
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//if your parent Layout is relativeLayout, just change the word LinearLayout with RelativeLayout
        TextView tv = new TextView(this);
        tv.setLayoutParams(lparams);
        tv.setText("test");
//get the parent layout for your new TextView and add the new TextView to it
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        linearLayout.addView(tv);
    }
}
