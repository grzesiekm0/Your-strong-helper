package com.yourstronghelper.grzegorzmacko.yourstronghelper.exercises;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.R;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.RegActivity;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.model.Exercise;

import java.util.List;

public class TainingPlanAdapter extends RecyclerView.Adapter<TainingPlanAdapter.TrainingViewHolder>  {
    private Context mCtx;
    private List<Exercise> exerciseList;

    public TainingPlanAdapter(Context mCtx, List<Exercise> exerciseList) {
        this.mCtx = mCtx;
        this.exerciseList = exerciseList;
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

    class TrainingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewName, textViewBrand, textViewDesc, textViewPrice, textViewQty;
        Button btn;

        public TrainingViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textview_name);
            textViewBrand = itemView.findViewById(R.id.textview_brand);
            textViewDesc = itemView.findViewById(R.id.textview_desc);
            textViewPrice = itemView.findViewById(R.id.textview_price);
            textViewQty = itemView.findViewById(R.id.textview_quantity);
           // btn = itemView.findViewById(R.id.btn);
            this.btn = (Button) itemView.findViewById(R.id.btn);
            btn.setOnClickListener(this);  // change number 1
            itemView.setOnClickListener(this);

        }

        private void save(){
            FirebaseFirestore db;
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            String nameSave = nameExcercise.getText().toString().trim();
            String typeSave = type;
            int seriesSave = Integer.parseInt( sersiesExercise.getText().toString().trim() );
            int quantitySave = Integer.parseInt( quantityExercise.getText().toString().trim() );

                exer = new Exercise(
                        user.getUid(),
                        nameSave,
                        typeSave,
                        seriesSave,
                        quantitySave
                );
                db = FirebaseFirestore.getInstance();
                db.collection("exercise")
                        .add(exer)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                Toast.makeText(AddExerciseActivity.this, "Dodano cwiczenie", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddExerciseActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                Log.w(TAG, "Error adding document", e);

                            }
                        });
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(v.getId() == R.id.btn) {
                System.out.println("dupa");

                Exercise exer = exerciseList.get(position);
            }
           /* switch (v.getId()) {
                case R.id.btn:
                    System.out.println("dupa");
                    break;
                case R.id.:
                    System.out.println("dupa");
                    break;*/
            }
        }
    }

