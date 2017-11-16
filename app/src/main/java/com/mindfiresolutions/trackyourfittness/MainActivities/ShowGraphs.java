package com.mindfiresolutions.trackyourfittness.MainActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.mindfiresolutions.trackyourfittness.Model.TabsPagerAdapter;
import com.mindfiresolutions.trackyourfittness.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Vishal Prasad on 5/24/2017.
 * last modified on 5/25/2017
 */

public class ShowGraphs extends AppCompatActivity implements ActionBar.TabListener {
    private static final String TAG = ShowGraphs.class.getSimpleName();
    private ListView mListView;
    private ArrayList<HashMap<String, String>> mHealthDetailsList;
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_my_request);
        initViews();

        String[] tabs = new String[]{getString(R.string.prompt_weight), getString(R.string.prompt_water_intake),
                getString(R.string.prompt_high_bp), getString(R.string.prompt_low_bp), getString(R.string.prompt_running_or_waliking)};
        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getSupportActionBar(); //getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        if (actionBar != null && actionBar.isShowing()) {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            //check for Actionbar if present then set tittle
            actionBar.setTitle(getString(R.string.app_name));
            actionBar.setSubtitle(R.string.prompt_performance_summary);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
        }
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
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

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    private void initViews() {

    }
}
