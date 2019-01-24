package com.yourstronghelper.grzegorzmacko.yourstronghelper.exercises;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.R;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.model.Exercise;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.model.TrainingPlan;

import java.util.ArrayList;
import java.util.List;

public class TrainingPlanActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlanAdapter adapter;
    private List<TrainingPlan> trainingPlanList;
    private ProgressBar progressBar;


    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_plan);

        progressBar = findViewById(R.id.progressbar);

        recyclerView = findViewById(R.id.recyclerview_training_plan);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        trainingPlanList = new ArrayList<>();
        adapter = new PlanAdapter(this, trainingPlanList);

        recyclerView.setAdapter(adapter);


        db = FirebaseFirestore.getInstance();

       /* db.collection("plan")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                TrainingPlan p = document.toObject(TrainingPlan.class);
                                trainingPlanList.add(p);
                            }
                        } else {
                            //Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });*/

        db.collection("plan")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        FirebaseUser user = mAuth.getCurrentUser();

                        progressBar.setVisibility(View.GONE);

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {

                                TrainingPlan p = d.toObject(TrainingPlan.class);
                                trainingPlanList.add(p);
                                // p.setId(d.getId());
                              /* if(p.getIdd().equals(user.getUid())){
                                    exerciseList.add(p);
                                }*/


                            }

                            adapter.notifyDataSetChanged();

                        }


                    }
                });
/*
        db.collection("cities").get().then(function(querySnapshot) {
            querySnapshot.forEach(function(doc) {
                // doc.data() is never undefined for query doc snapshots
                console.log(doc.id, " => ", doc.data());
            });
        });*/

    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
        return;
    }
}
