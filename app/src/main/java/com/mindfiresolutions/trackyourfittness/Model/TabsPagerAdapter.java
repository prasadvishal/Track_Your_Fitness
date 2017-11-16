package com.mindfiresolutions.trackyourfittness.Model;

/**
 * Created by Vishal Prasad on 5/24/2017.
 * * modified on 5/25/2017
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new WeighGraphFragment();
            case 1:
                // Games fragment activity
                return new WaterConsumptionGraphFragment();

            case 2:
                // Movies fragment activity
                return new HighBpGraphFragment();
            case 3:
                return new LowBpGraphFragment();
            case 4:
                return new DistanceCoverageGraphFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 5;
    }

}