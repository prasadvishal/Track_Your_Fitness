package com.mindfiresolutions.trackyourfittness.MainActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.mindfiresolutions.trackyourfittness.R;
import com.mindfiresolutions.trackyourfittness.RvAdapterHistory.RecyclerItemClickListener;
import com.mindfiresolutions.trackyourfittness.RvAdapterHistory.RvAdapterHistoryList;
import com.mindfiresolutions.trackyourfittness.Utilities.LoggerUtility;

import java.util.ArrayList;
import java.util.HashMap;

import static com.mindfiresolutions.trackyourfittness.Utilities.Constants.DISTANCE_KEY;
import static com.mindfiresolutions.trackyourfittness.Utilities.Constants.HEALTH_DETAILS_LIST;
import static com.mindfiresolutions.trackyourfittness.Utilities.Constants.HIGH_BP_KEY;
import static com.mindfiresolutions.trackyourfittness.Utilities.Constants.ID;
import static com.mindfiresolutions.trackyourfittness.Utilities.Constants.LOW_BP_KEY;
import static com.mindfiresolutions.trackyourfittness.Utilities.Constants.WATER_INTAKE_KEY;
import static com.mindfiresolutions.trackyourfittness.Utilities.Constants.WEIGHT_KEY;

/**
 * Created by Vishal Prasad on 5/23/2017.
 * last modified on 5/25/2017
 */
public class HistoryActivity extends AppCompatActivity {
    private ArrayList<HashMap<String, String>> mHealthDetailsList;
    private static final String sTAG = HistoryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        //check for Actionbar if present then set tittle
        if (ab != null && ab.isShowing()) {
            ab.setTitle(getString(R.string.app_name));
            ab.setSubtitle(R.string.action_history);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        mHealthDetailsList = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra(HEALTH_DETAILS_LIST);
        LoggerUtility.makeLog(sTAG,"List Received");
        for(int i=0; i<mHealthDetailsList.size();i++) {
            LoggerUtility.makeLog(sTAG, "S.No: " + mHealthDetailsList.get(i).get(ID));
            LoggerUtility.makeLog(sTAG, "Weight: " + mHealthDetailsList.get(i).get(WEIGHT_KEY));
            LoggerUtility.makeLog(sTAG, "Water Intake: " + mHealthDetailsList.get(i).get(WATER_INTAKE_KEY));
            LoggerUtility.makeLog(sTAG, "High BP: " + mHealthDetailsList.get(i).get(HIGH_BP_KEY));
            LoggerUtility.makeLog(sTAG, "Low BP: " + mHealthDetailsList.get(i).get(LOW_BP_KEY));
            LoggerUtility.makeLog(sTAG, "Distance: " + mHealthDetailsList.get(i).get(DISTANCE_KEY));
        }
        setListAdapter(mHealthDetailsList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setListAdapter(final ArrayList<HashMap<String, String>> healthDetailsList) {
        RvAdapterHistoryList adapter = new RvAdapterHistoryList(healthDetailsList);
        RecyclerView myView =  (RecyclerView)findViewById(R.id.recyclerview);
        myView.setHasFixedSize(true);
        myView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(HistoryActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        myView.setLayoutManager(llm);

        myView.addOnItemTouchListener(
                new RecyclerItemClickListener(HistoryActivity.this, myView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent i = new Intent(HistoryActivity.this, ShowGraphs.class);
                        startActivity(i);
                        LoggerUtility.makeLog(sTAG, "inside on item selected");
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

}
