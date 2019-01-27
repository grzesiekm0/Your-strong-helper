package com.yourstronghelper.grzegorzmacko.yourstronghelper.exercises;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
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
    private List<Exercise> tempExerciseList;
    private ProgressBar progressBar;
    public Button btnSavePlan;
    TextView textViewName;
    private EditText editTextNamePlan;
    private static final String TAG = "AddTrainingPlanActivity";


    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_training_plan);

        progressBar = findViewById(R.id.progressbar_training);
        findViewById(R.id.buttonSavePlan).setOnClickListener(this);
        textViewName = findViewById(R.id.textview_name);
        recyclerView = findViewById(R.id.recyclerview_training_plan);
        editTextNamePlan = findViewById(R.id.editTextNamePlan);
       recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        db = FirebaseFirestore.getInstance();

        exerciseList = new ArrayList<>();
        tempExerciseList = new ArrayList<>();

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
                         tempExerciseList.add(exerciseList.get(position));
                        System.out.println("Dodano ćwiczenie");
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        System.out.println("fru "+ position);
                    }
                })
        );
        }

        public void SavePlan(){
            String planName = editTextNamePlan.getText().toString().trim();
            StringBuilder sb;
            StringBuilder SB;
                String plan= "";
                String exercise= "";
                SB = new StringBuilder(plan);
            sb = new StringBuilder(exercise);
            for (Exercise d : tempExerciseList) {

                sb.append("Nazwa: "+d.getName()+"\n");
                sb.append("Typ: "+d.getType()+"\n");
                sb.append("Serie: "+d.getSeries()+"\n");
                sb.append("Powtórzenia w pierwszej serii: "+d.getQuantity()+"\n\n");
            }
            exercise = sb.toString();
            SB.append(exercise);
            plan = SB.toString();
            System.out.println(plan);

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();

            TrainingPlan trainingPlan = new TrainingPlan(
                    user.getUid(),
                    planName,
                    plan);

            db.collection("plan")
                    .add(trainingPlan)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast.makeText(AddTrainingPlanActivity.this, "Dodano plan", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddTrainingPlanActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.w(TAG, "Error adding document", e);

                        }
                    });

        }

        @Override
        public void onBackPressed () {
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonSavePlan:
                System.out.println("baton");
                SavePlan();
                break;
            /*case R.id.textview_view_products:
                startActivity(new Intent(this, ProductsActivity.class));
                break;*/
        }
    }
}

