package com.mindfiresolutions.trackyourfittness.MainActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mindfiresolutions.trackyourfittness.DatabaseFiles.DatabaseHandler;
import com.mindfiresolutions.trackyourfittness.Model.HealthStatus;
import com.mindfiresolutions.trackyourfittness.R;
import com.mindfiresolutions.trackyourfittness.TextUtils.TextUtility;
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

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private EditText mWeight, mWaterIntake, mHighBP, mLowBP, mDistance;
    private static final String sTAG = HomeActivity.class.getSimpleName();
    private ArrayList<HashMap<String,String>> mHealthDetailsList;
    private boolean hasEntry = false;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final DatabaseHandler db = DatabaseHandler.getInstance(HomeActivity.this);
        makeDrawer();
        initView();
        if(db.hasTodaysEntry(db.getCurrentDate()))
        {
            prefillData();
            hasEntry = true;
        }
        readDataFromDatabase();

    }

    private void prefillData() {
        final DatabaseHandler db = DatabaseHandler.getInstance(HomeActivity.this);
        mWeight.setText(db.getLastEntry().get(0).get(WEIGHT_KEY));
        mWaterIntake.setText(db.getLastEntry().get(0).get(WATER_INTAKE_KEY));
        mHighBP.setText(db.getLastEntry().get(0).get(HIGH_BP_KEY));
        mLowBP.setText(db.getLastEntry().get(0).get(LOW_BP_KEY));
        mDistance.setText(db.getLastEntry().get(0).get(DISTANCE_KEY));
        id=db.getLastEntry().get(0).get(ID);
        ((Button)findViewById(R.id.homepage_submit_button)).setText(getString(R.string.prompt_update));
    }

    private void readDataFromDatabase() {
        mHealthDetailsList.clear();
        final DatabaseHandler db = DatabaseHandler.getInstance(HomeActivity.this);
        mHealthDetailsList = db.getHealthDetailsList();
        if(mHealthDetailsList.size()==0)
        {
            LoggerUtility.makeShortToast(this,getString(R.string.prompt_error_no_value_in_db));
        }
    }

    private void saveData() {

        if(isValidData())
        {
            if(hasEntry) {
                final DatabaseHandler db = DatabaseHandler.getInstance(HomeActivity.this);
                HealthStatus entry = new HealthStatus(TextUtility.getDoubleFromView(mWeight),TextUtility.getDoubleFromView(mWaterIntake),
                        TextUtility.getDoubleFromView(mHighBP),TextUtility.getDoubleFromView(mLowBP),TextUtility.getDoubleFromView(mDistance));
                if(db.updateIntoDatabase(entry,id)==1)
                {
                    LoggerUtility.makeShortToast(HomeActivity.this,getString(R.string.prompt_successfully_updated));
                    Intent i = new Intent(HomeActivity.this,HomeActivity.class);
                    HomeActivity.this.finish();
                    startActivity(i);
                }
            }
            else    
                saveIntoDatabase();
        }
    }



    private void saveIntoDatabase() {

        final DatabaseHandler db = DatabaseHandler.getInstance(HomeActivity.this);
        HealthStatus entry = new HealthStatus(TextUtility.getDoubleFromView(mWeight),TextUtility.getDoubleFromView(mWaterIntake),
                TextUtility.getDoubleFromView(mHighBP),TextUtility.getDoubleFromView(mLowBP),TextUtility.getDoubleFromView(mDistance));

        db.addHealthStatus(entry);
        LoggerUtility.makeShortToast(HomeActivity.this,"Data Entered in SQLite");
        mHealthDetailsList = db.getHealthDetailsList();
        for(int i=0; i<mHealthDetailsList.size();i++) {
            LoggerUtility.makeLog(sTAG, "S.No: " + mHealthDetailsList.get(i).get(ID));
            LoggerUtility.makeLog(sTAG, "Weight: " + mHealthDetailsList.get(i).get(WEIGHT_KEY));
            LoggerUtility.makeLog(sTAG, "Water Intake: " + mHealthDetailsList.get(i).get(WATER_INTAKE_KEY));
            LoggerUtility.makeLog(sTAG, "High BP: " + mHealthDetailsList.get(i).get(HIGH_BP_KEY));
            LoggerUtility.makeLog(sTAG, "Low BP: " + mHealthDetailsList.get(i).get(LOW_BP_KEY));
            LoggerUtility.makeLog(sTAG, "Distance: " + mHealthDetailsList.get(i).get(DISTANCE_KEY));
        }
            Intent intent = new Intent(HomeActivity.this,ShowGraphs.class);
            startActivity(intent);

    }

    private boolean isValidData() {
        //Validate Weight
        if(TextUtility.getTextFromView(mWeight).length()==0)
        {
            TextUtility.requestFocusIfError(mWeight,getString(R.string.error_field_required),sTAG,"Error: No Input for Weight");
            TextUtility.clearTextFromView(mWeight);
            return false;
        }
        if(Double.parseDouble(TextUtility.getTextFromView(mWeight))<0)
        {
            TextUtility.requestFocusIfError(mWeight,getString(R.string.error_invalid_entry),sTAG,"Error: Invalid  -VE Input for Weight");
            TextUtility.clearTextFromView(mWeight);
            return false;
        }
        
        // Validate WaterIntake

        if(TextUtility.getTextFromView(mWaterIntake).length()==0)
        {
            TextUtility.requestFocusIfError(mWaterIntake,getString(R.string.error_field_required),sTAG,"Error: No Input for Weight");
            TextUtility.clearTextFromView(mWaterIntake);
            return false;
        }
        if(Double.parseDouble(TextUtility.getTextFromView(mWaterIntake))<0)
        {
            TextUtility.requestFocusIfError(mWaterIntake,getString(R.string.error_invalid_entry),sTAG,"Error: Invalid  -VE Input for Weight");
            TextUtility.clearTextFromView(mWaterIntake);
            return false;
        }

        // Validate Blood Pressure
        if(TextUtility.getTextFromView(mHighBP).length()==0)
        {
            TextUtility.requestFocusIfError(mHighBP,getString(R.string.error_field_required),sTAG,"Error: No Input for Weight");
            TextUtility.clearTextFromView(mHighBP);
            return false;
        }
        if(Double.parseDouble(TextUtility.getTextFromView(mHighBP))<0)
        {
            TextUtility.requestFocusIfError(mHighBP,getString(R.string.error_invalid_entry),sTAG,"Error: Invalid  -VE Input for Weight");
            TextUtility.clearTextFromView(mHighBP);
            return false;
        }
        if(TextUtility.getTextFromView(mLowBP).length()==0)
        {
            TextUtility.requestFocusIfError(mLowBP,getString(R.string.error_field_required),sTAG,"Error: No Input for Weight");
            TextUtility.clearTextFromView(mLowBP);
            return false;
        }
        if(Double.parseDouble(TextUtility.getTextFromView(mLowBP))<0)
        {
            TextUtility.requestFocusIfError(mLowBP,getString(R.string.error_invalid_entry),sTAG,"Error: Invalid  -VE Input for Weight");
            TextUtility.clearTextFromView(mLowBP);
            return false;
        }

        // Validate Distance
        if(TextUtility.getTextFromView(mDistance).length()==0)
        {
            TextUtility.requestFocusIfError(mDistance,getString(R.string.error_field_required),sTAG,"Error: No Input for Weight");
            TextUtility.clearTextFromView(mDistance);
            return false;
        }
        if(Double.parseDouble(TextUtility.getTextFromView(mDistance))<0)
        {
            TextUtility.requestFocusIfError(mDistance,getString(R.string.error_invalid_entry),sTAG,"Error: Invalid  -VE Input for Weight");
            TextUtility.clearTextFromView(mDistance);
            return false;
        }
        
        return true;
    }

    private void makeDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);                                                               // Toolbar set as Actionbar for Drawer Implementation
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setSubtitle(getString(R.string.prompt_home));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initView() {       // Initialized all view and listners here
                mWeight = (EditText)findViewById(R.id.homepage_weight_input);
                mWaterIntake = (EditText)findViewById(R.id.homepage_water_intake_input);
                mHighBP = (EditText) findViewById(R.id.homepage_high_bp_input);
                mLowBP = (EditText) findViewById(R.id.homepage_low_bp_input);
                mDistance = (EditText) findViewById(R.id.homepage_distance_input);
                mHealthDetailsList = new ArrayList<>();
                findViewById(R.id.homepage_submit_button).setOnClickListener(this);
                findViewById(R.id.imgbtn_homepage_weight_decrement).setOnClickListener(this);
                findViewById(R.id.imgbtn_homepage_weight_increment).setOnClickListener(this);
                findViewById(R.id.imgbtn_homepage_wtr_intk_decrement).setOnClickListener(this);
                findViewById(R.id.imgbtn_homepage_wtr_intk_increment).setOnClickListener(this);
                findViewById(R.id.imgbtn_homepage_high_bp_decrement).setOnClickListener(this);
                findViewById(R.id.imgbtn_homepage_high_bp_increment).setOnClickListener(this);
                findViewById(R.id.imgbtn_homepage_low_bp_decrement).setOnClickListener(this);
                findViewById(R.id.imgbtn_homepage_low_bp_increment).setOnClickListener(this);
                findViewById(R.id.imgbtn_homepage_distance_decrement).setOnClickListener(this);
                findViewById(R.id.imgbtn_homepage_distance_increment).setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            intent.putExtra(HEALTH_DETAILS_LIST, mHealthDetailsList);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        Double data = 0.0;
        if(v.getId()== R.id.homepage_submit_button) {
            saveData();
        }
        if(v.getId()== R.id.imgbtn_homepage_weight_increment) {
            data = TextUtility.getDoubleFromView(mWeight);
            TextUtility.setDoubleIntoView(mWeight,data+0.5);                                        // Inclement Weight by 0.5
        }
        if(v.getId()== R.id.imgbtn_homepage_weight_decrement)
        {
            data = TextUtility.getDoubleFromView(mWeight);
            if(data<=0.0)
                LoggerUtility.makeShortToast(HomeActivity.this,getString(R.string.error_invalid_entry));
            else
                TextUtility.setDoubleIntoView(mWeight,data-0.5);                                        // Inclement Weight by 0.5
        }


        if(v.getId()== R.id.imgbtn_homepage_wtr_intk_increment)
        {
            data = TextUtility.getDoubleFromView(mWaterIntake);
            TextUtility.setDoubleIntoView(mWaterIntake,data+0.5);                                        // Inclement Weight by 0.5
        }
        if(v.getId()== R.id.imgbtn_homepage_wtr_intk_decrement)
        {
            data = TextUtility.getDoubleFromView(mWaterIntake);
            if(data<=0.0)
                LoggerUtility.makeShortToast(HomeActivity.this,getString(R.string.error_invalid_entry));
            else
                TextUtility.setDoubleIntoView(mWaterIntake,data-0.5);                                        // Inclement Weight by 0.5
        }

        if(v.getId()== R.id.imgbtn_homepage_high_bp_increment)
        {
            data = TextUtility.getDoubleFromView(mHighBP);
            TextUtility.setDoubleIntoView(mHighBP,data+0.5);                                        // Inclement Weight by 0.5
        }
        if(v.getId()== R.id.imgbtn_homepage_high_bp_decrement)
        {
            data = TextUtility.getDoubleFromView(mHighBP);
            if(data<=0.0)
                LoggerUtility.makeShortToast(HomeActivity.this,getString(R.string.error_invalid_entry));
            else
                TextUtility.setDoubleIntoView(mHighBP,data-0.5);                                        // Inclement Weight by 0.5
        }

        if(v.getId()== R.id.imgbtn_homepage_low_bp_increment)
        {
            data = TextUtility.getDoubleFromView(mLowBP);
            TextUtility.setDoubleIntoView(mLowBP,data+0.5);                                        // Inclement Weight by 0.5
        }
        if(v.getId()== R.id.imgbtn_homepage_low_bp_decrement)
        {
            data = TextUtility.getDoubleFromView(mLowBP);
            if(data<=0.0)
                LoggerUtility.makeShortToast(HomeActivity.this,getString(R.string.error_invalid_entry));
            else
                TextUtility.setDoubleIntoView(mLowBP,data-0.5);                                        // Inclement Weight by 0.5
        }

        if(v.getId()== R.id.imgbtn_homepage_distance_increment)
        {
            data = TextUtility.getDoubleFromView(mDistance);
            TextUtility.setDoubleIntoView(mDistance,data+0.5);                                        // Inclement Weight by 0.5
        }
        if(v.getId()== R.id.imgbtn_homepage_distance_decrement)
        {
            data = TextUtility.getDoubleFromView(mDistance);
            if(data<=0.0)
                LoggerUtility.makeShortToast(HomeActivity.this,getString(R.string.error_invalid_entry));
            else
                TextUtility.setDoubleIntoView(mDistance,data-0.5);                                        // Inclement Weight by 0.5
        }
    }
}
