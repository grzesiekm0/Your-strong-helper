package com.yourstronghelper.grzegorzmacko.yourstronghelper.plans;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.R;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.model.Exercise;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.model.TrainingPlan;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class TainingPlanAdapter extends RecyclerView.Adapter<TainingPlanAdapter.TrainingViewHolder> {
    private Context mCtx;
    private List<Exercise> exerciseList;
    public List<Exercise> tempExerciseList = new ArrayList<>();
    String test;

    public TainingPlanAdapter(Context mCtx, List<Exercise> exerciseList) {
        this.mCtx = mCtx;
        this.exerciseList = exerciseList;
    }

    public TainingPlanAdapter() {

    }

    @NonNull
    @Override
    public TainingPlanAdapter.TrainingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TainingPlanAdapter.TrainingViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_add_training_plan, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TainingPlanAdapter.TrainingViewHolder holder, int position) {
        Exercise exer = exerciseList.get(position);

        holder.textViewName.setText(exer.getName());
        holder.textViewBrand.setText(exer.getType());
        holder.textViewDesc.setText(String.valueOf(exer.getSeries()));
        holder.textViewPrice.setText(String.valueOf(exer.getQuantity()));


    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public List<Exercise> excample(List<Exercise> temp) {
        System.out.println(temp);
        for (Exercise h : temp) {
            System.out.println("kur " + h.getName());
        }

        return temp;
    }

    public void wlacz() {
        Intent intent = new Intent(mCtx, AddTrainingPlanActivity.class);
        intent.putExtra("trainingPlan", test);
        mCtx.startActivity(intent);
        excample(tempExerciseList);
    }


    class TrainingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewName, textViewBrand, textViewDesc, textViewPrice, textViewQty;
        Button btn;
        RecyclerView recyclerView;

        public TrainingViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textview_name);
            textViewBrand = itemView.findViewById(R.id.textview_brand);
            textViewDesc = itemView.findViewById(R.id.textview_desc);
            textViewPrice = itemView.findViewById(R.id.textview_price);
            // btn = itemView.findViewById(R.id.btn);
            this.btn = (Button) itemView.findViewById(R.id.btn);
            btn.setOnClickListener(this);  // change number 1
            itemView.setOnClickListener(this);


        }

        public void save() {
            TrainingPlan tp;
            FirebaseFirestore db;
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();

            String nameSave = textViewName.getText().toString().trim();
            String planSave = "Ćw1";

            tp = new TrainingPlan(
                    user.getUid(),
                    nameSave,
                    planSave
            );
            db = FirebaseFirestore.getInstance();
            db.collection("trainingPlan")
                    .add(tp)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Log.w(TAG, "Error adding document", e);

                        }
                    });
        }

        @Override
        public void onClick(View v) {
            


        }
    }
}

