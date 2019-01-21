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

        recyclerView = findViewById(R.id.recyclerview_training_plan);
       recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        textViewName = findViewById(R.id.textview_name);

        //this.btnSavePlan = (Button) findViewById(R.id.buttonSavePlan);
       // btnSavePlan.setOnClickListener(this);  // change number 1



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

        /*recyclerView.setAdapter(new TainingPlanAdapter(exerciseList, new TainingPlanAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Exercise item) {
                Toast.makeText(AddTrainingPlanActivity.this, "Item Clicked", Toast.LENGTH_LONG).show();

            }


        }));
*/
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



        /*TainingPlanAdapter tpa = new TainingPlanAdapter();
        System.out.println();*/
        /*for x each
        for (DocumentSnapshot d : list) {

            Exercise p = d.toObject(Exercise.class);
            p.setId(d.getId());
            System.out.println("p id " + p.getId());
            System.out.println("p idd " + p.getIdd());
            System.out.println("d " + user.getUid());
            if (p.getIdd().equals(user.getUid())) {
                exerciseList.add(p);
            }
            tpa.tempExerciseList;*/
/*
        db.collection("cities").get().then(function(querySnapshot) {
            querySnapshot.forEach(function(doc) {
                // doc.data() is never undefined for query doc snapshots
                console.log(doc.id, " => ", doc.data());
            });
        });*/

        }

        public void SavePlan(){
        String plan = "Plan treningowy";
           /* TainingPlanAdapter tpa = new TainingPlanAdapter();
            tpa.wlacz();
            System.out.println("test "+tpa.test);*/

            /*for(Exercise h : tpa.tempExerciseList){
                System.out.println("kur "+ h.getName());
            }
           //String test = (String) getIntent().getStringExtra("trainingPlan");
           // System.out.println("test "+ test);
            tpa.dupa();*/
        }

        @Override
        public void onBackPressed () {
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }


        /*@Override
        public void onClick (View view){
            switch (view.getId()) {
                case R.id.buttonSavePlan:
                    System.out.println("pizda");
                    SavePlan();

                    System.out.println("tex"+ textViewName);

                    *//*break;
                case R.id.:
                    System.out.println("dupa");
                    break;*//*
            }
        }*/

    @Override
    public void onClick(View v) {
        /*int position = recyclerView.getChildAdapterPosition(v);
        System.out.println("pos "+position);
        if(v.getId() == R.id.btn) {

            System.out.println("dupa");
            System.out.println("pozycja "+ position);
            //Exercise exer = exerciseList.get(position);
            Exercise exer = exerciseList.get(position);
            // System.out.println("nazwa "+exer.getName());
            Exercise exe = new Exercise();
            exe.setName(exer.getName());
            exe.setId(exer.getId());
            exe.setIdd(exer.getIdd());
            exe.setQuantity(exer.getQuantity());
            exe.setSeries(exer.getSeries());
            exe.setType(exer.getType());


            // ładować do osobnej listy obiektów nastepnie wysyłamy do klasy nadrzednej, metoda zapisuje w bazie
            // ladowac tutaj tutaj do listy obiektów zmodyfikować zrenderowac gotowe ćwiczenie i wysłać do widoku
            //stworzyć model training plan i wyslac go do widoku, w widoku otwrozyc i zrenderować do tekstu nastepnie wsadzic do bazy
            int itemPosition = recyclerView.indexOfChild(v);
            System.out.println("poss "+itemPosition);
        }*/
            /*if(v.getId() == R.id.buttonSavePlan){

                for(Exercise h : exerciseList){
                    System.out.println("kur "+ h.getName());
                }
                dupa(tempExerciseList);
            }*/

           /* switch (v.getId()) {
                case R.id.btn:
                    System.out.println("dupa");
                    break;
                case R.id.:
                    System.out.println("dupa");
                    break;*/
    }
}

