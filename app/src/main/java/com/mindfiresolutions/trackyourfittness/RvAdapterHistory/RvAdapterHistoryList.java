package com.mindfiresolutions.trackyourfittness.RvAdapterHistory;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.mindfiresolutions.trackyourfittness.R;

import java.util.ArrayList;
import java.util.HashMap;

import static com.mindfiresolutions.trackyourfittness.Utilities.Constants.DATE_KEY;
import static com.mindfiresolutions.trackyourfittness.Utilities.Constants.DISTANCE_KEY;
import static com.mindfiresolutions.trackyourfittness.Utilities.Constants.HIGH_BP_KEY;
import static com.mindfiresolutions.trackyourfittness.Utilities.Constants.LOW_BP_KEY;
import static com.mindfiresolutions.trackyourfittness.Utilities.Constants.WATER_INTAKE_KEY;
import static com.mindfiresolutions.trackyourfittness.Utilities.Constants.WEIGHT_KEY;


/**
 * Created by Vishal Prasad on 5/25/2017.
 * * modified on 5/25/2017
 */

public class RvAdapterHistoryList extends RecyclerView.Adapter<RvAdapterHistoryList.MyViewHolder> {
    public ArrayList<HashMap<String,String>> myValues;
    public RvAdapterHistoryList(ArrayList<HashMap<String,String>> myValues){
        this.myValues= myValues;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_history_list, parent, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mEntryDate.setText(myValues.get(position).get(DATE_KEY));
        holder.mEntryWeight.setText(myValues.get(position).get(WEIGHT_KEY));
        holder.mEntryWaterIntake.setText(myValues.get(position).get(WATER_INTAKE_KEY));
        holder.mEntryHighBp.setText(myValues.get(position).get(HIGH_BP_KEY));
        holder.mEntryLowBp.setText(myValues.get(position).get(LOW_BP_KEY));
        holder.mEntryDistance.setText(myValues.get(position).get(DISTANCE_KEY));

    }


    @Override
    public int getItemCount() {
        return myValues.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mEntryDate,mEntryWeight,mEntryWaterIntake,mEntryHighBp,mEntryLowBp,mEntryDistance;

        public MyViewHolder(View itemView) {
            super(itemView);
            mEntryDate = (TextView)itemView.findViewById(R.id.rv_item_et_date);
            mEntryWeight = (TextView)itemView.findViewById(R.id.rv_item_et_weight);
            mEntryWaterIntake = (TextView)itemView.findViewById(R.id.rv_item_et_wtr_intk);
            mEntryHighBp = (TextView)itemView.findViewById(R.id.rv_item_et_high_bp);
            mEntryLowBp = (TextView)itemView.findViewById(R.id.rv_item_et_low_bp);
            mEntryDistance = (TextView) itemView.findViewById(R.id.rv_item_et_distance);
        }
    }
}