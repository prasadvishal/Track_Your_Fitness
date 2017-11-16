package com.mindfiresolutions.trackyourfittness.TextUtils;

import android.widget.EditText;
import android.widget.TextView;

import static com.mindfiresolutions.trackyourfittness.Utilities.LoggerUtility.makeLog;

/**
 * Created by Vishal Prasad on 5/24/2017.
 */

public class TextUtility {
    public static String getTextFromView(EditText view) {
        return (view.getText().toString().trim());
    }

    public static void clearTextFromView(EditText view) {
        view.setText("");
    }

    public static String getTextFromView(TextView view) {
        return (view.getText().toString().trim());
    }

    public static Double getDoubleFromView(EditText view) {
        if((view.getText().toString().trim().length())==0)
            return 0.0;
        else
        return (Double.parseDouble(view.getText().toString().trim()));
    }

    public static void setDoubleIntoView(EditText view,Double d) {
        view.setText(""+d);
    }

    public static void requestFocusIfError(EditText view, String errorMsg) {
        view.setError(errorMsg);
        view.requestFocus();
    }

    public static void requestFocusIfError(TextView view, String errorMsg, String tag, String errorLogmsg) {
        view.setError(errorMsg);
        view.requestFocus();
        makeLog(tag, errorLogmsg);
    }

    public static void requestFocusIfError(TextView view, String errorMsg) {
        view.setError(errorMsg);
        view.requestFocus();
    }

    public static void requestFocusIfError(EditText view, String errorMsg, String tag, String errorLogmsg) {
        view.setError(errorMsg);
        view.requestFocus();
        makeLog(tag, errorLogmsg);
    }
}

