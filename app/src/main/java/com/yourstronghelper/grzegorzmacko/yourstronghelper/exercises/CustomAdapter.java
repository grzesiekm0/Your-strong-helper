package com.yourstronghelper.grzegorzmacko.yourstronghelper.exercises;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yourstronghelper.grzegorzmacko.yourstronghelper.R;
import com.yourstronghelper.grzegorzmacko.yourstronghelper.model.Exercise;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Exercise> implements View.OnClickListener{

    private ArrayList<Exercise> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView textViewName;
        TextView textViewBrand;
        TextView textViewDesc;
        TextView textViewPrice;
        ImageView info;
    }

    public CustomAdapter(ArrayList<Exercise> data, Context context) {
        super(context, R.layout.layout_exercise, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        /*int position=(Integer) v.getTag();
        Object object= getItem(position);
        Exercise dataModel=(Exercise)object;

        switch (v.getId())
        {
            case R.id.item_info:
                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }*/
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Exercise dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
       // Exercise exer = exer.getId(position);
        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layout_exercise, parent, false);
            /*viewHolder.textViewName.setText(exer.getName());
            viewHolder.textViewBrand.setText(exer.getType());
            viewHolder.textViewDesc.setText(String.valueOf(exer.getSeries()));
            viewHolder.textViewPrice.setText(String.valueOf(exer.getQuantity()));*/


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        //Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        //result.startAnimation(animation);
        lastPosition = position;

        /*viewHolder.txtName.setText(dataModel.getName());
        viewHolder.txtType.setText(dataModel.getType());
        viewHolder.txtVersion.setText(dataModel.getVersion_number());*/
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}