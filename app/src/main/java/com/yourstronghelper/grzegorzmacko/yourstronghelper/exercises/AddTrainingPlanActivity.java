package com.yourstronghelper.grzegorzmacko.yourstronghelper.exercises;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.R;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.model.Exercise;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.model.TrainingPlan;

import java.util.ArrayList;
import java.util.List;

public class AddTrainingPlanActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private TainingPlanAdapter adapter;
    private List<Exercise> exerciseList;
    private ProgressBar progressBar;
    public Button btnSavePlan;
    TextView textViewName;


    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_training_plan);

        progressBar = findViewById(R.id.progressbar_training);
        findViewById(R.id.buttonSavePlan).setOnClickListener(this);
        textViewName = findViewById(R.id.textview_name);
        recyclerView = findViewById(R.id.recyclerview_training_plan);
       recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        db = FirebaseFirestore.getInstance();

        exerciseList = new ArrayList<>();

        db.collection("exercise")
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

                                Exercise p = d.toObject(Exercise.class);
                                p.setId(d.getId());
                                System.out.println("p id " + p.getId());
                                System.out.println("p idd " + p.getIdd());
                                System.out.println("d " + user.getUid());
                                if (p.getIdd().equals(user.getUid())) {
                                    exerciseList.add(p);
                                }
                            }

                            adapter.notifyDataSetChanged();

                        }
                    }
                });

        adapter = new TainingPlanAdapter(this, exerciseList);

        recyclerView.setAdapter(adapter);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_training_plan);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        System.out.println("sru "+ position);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        System.out.println("fru "+ position);
                    }
                })
        );
        }

        public void SavePlan(){
        String plan = "Plan treningowy";

        }

        @Override
        public void onBackPressed () {
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }

    @Override
    public void onClick(View v) {
        
    }
}

