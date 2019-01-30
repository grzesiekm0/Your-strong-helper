package com.yourstronghelper.grzegorzmacko.yourstronghelper.plans;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yourstronghelper.grzegorzmacko.yourstronghelper.R;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.model.TrainingPlan;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> {

    private Context mCtx;
    private List<TrainingPlan> trainingPlanListList;

    public PlanAdapter(Context mCtx, List<TrainingPlan> trainingPlanListList) {
        this.mCtx = mCtx;
        this.trainingPlanListList = trainingPlanListList;
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlanViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_training_plan, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        TrainingPlan tp = trainingPlanListList.get(position);

        holder.textViewName.setText(tp.getName());
        holder.textViewBrand.setText(tp.getPlan());
    }

    @Override
    public int getItemCount() {
        return trainingPlanListList.size();
    }

    class PlanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewName, textViewBrand, textViewDesc, textViewPrice, textViewQty;

        public PlanViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textview_name);
            textViewBrand = itemView.findViewById(R.id.textview_brand);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            /*Exercise exer = exerciseList.get(getAdapterPosition());
            Intent intent = new Intent(mCtx, UpdateExerciseActivity.class);
            intent.putExtra("exercise", exer);
            mCtx.startActivity(intent);*/
        }
    }
}
