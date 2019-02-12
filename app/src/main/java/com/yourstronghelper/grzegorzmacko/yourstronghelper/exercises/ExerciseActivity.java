package com.yourstronghelper.grzegorzmacko.yourstronghelper.exercises;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.MainActivity;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.R;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.model.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExerciseAdapter adapter;
    private List<Exercise> exerciseList;
    private ProgressBar progressBar;


    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        progressBar = findViewById(R.id.progressbar);

        recyclerView = findViewById(R.id.recyclerview_products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        exerciseList = new ArrayList<>();
        adapter = new ExerciseAdapter(this, exerciseList);

        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

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
                                System.out.println("Moze "+d.getId());
                                if(p.getIdd().equals(user.getUid())){
                                    p.setId(d.getId());
                                    exerciseList.add(p);
                                }


                            }

                            adapter.notifyDataSetChanged();

                        }


                    }
                });
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
        return;
    }
}