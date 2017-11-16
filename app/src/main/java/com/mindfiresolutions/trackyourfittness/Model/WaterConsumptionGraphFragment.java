package com.mindfiresolutions.trackyourfittness.Model;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.mindfiresolutions.trackyourfittness.DatabaseFiles.DatabaseHandler;
import com.mindfiresolutions.trackyourfittness.R;
import com.mindfiresolutions.trackyourfittness.Utilities.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

import static com.mindfiresolutions.trackyourfittness.Utilities.Constants.ID;
import static com.mindfiresolutions.trackyourfittness.Utilities.Constants.WATER_INTAKE_KEY;
import static com.mindfiresolutions.trackyourfittness.Utilities.Constants.WEIGHT_KEY;

/**
 * Created by Vishal Prasad on 5/24/2017.
 * * modified on 5/25/2017
 */

public class WaterConsumptionGraphFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.water_intake_fragment, container, false);
        final DatabaseHandler db = DatabaseHandler.getInstance(getContext());
        ArrayList<HashMap<String, String>> mHealthDetailsList = db.getHealthDetailsList();
        List<PointValue> values = new ArrayList<PointValue>();
        TextView xaxis = (TextView)rootView.findViewById(R.id.grapg_xaxis_lable);
        TextView yaxis = (TextView)rootView.findViewById(R.id.grapg_yaxis_lable);
        xaxis.setText(getString(R.string.prompt_time));
        yaxis.setText(getString(R.string.glasses));

        for(int i=0;i<mHealthDetailsList.size();i++)
        {
            values.add(new PointValue(Float.parseFloat(mHealthDetailsList.get(i).get(ID)), Float.parseFloat(mHealthDetailsList.get(i).get(WATER_INTAKE_KEY))));
        }

        LineChartView chart = (LineChartView) rootView.findViewById(R.id.chart);

        //In most cased you can call data model methods in builder-pattern-like manner.
        Line line = new Line(values).setColor(Constants.GRAPH_BLUE).setCubic(true);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        chart.setLineChartData(data);

        return rootView;
    }

}