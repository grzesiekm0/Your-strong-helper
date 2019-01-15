package com.yourstronghelper.grzegorzmacko.yourstronghelper.exercises;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(v.getId() == R.id.btn) {


            }
            switch (v.getId()) {
                case R.id.btn:
                    System.out.println("dupa");
                    break;
                /*case R.id.:
                    System.out.println("dupa");
                    break;*/
            }

            /*System.out.println("widok "+ v.getId());
            Exercise exer = exerciseList.get(getAdapterPosition());
            Intent intent = new Intent(mCtx, UpdateExerciseActivity.class);
            intent.putExtra("exercise", exer);
            mCtx.startActivity(intent);*/
        }
    }
}
